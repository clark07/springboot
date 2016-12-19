package com.cs.test.tasks;

import com.cs.test.db.entity.Book;
import com.cs.test.db.entity.Chapter;
import com.cs.test.mail.MailService;
import com.cs.test.service.BookService;
import com.cs.test.utils.BookUtils;
import com.cs.test.utils.HttpClientManager;
import com.cs.test.utils.HttpUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.sql.Timestamp;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by admin on 2016/12/6.
 * 示例任务
 * 爬取笔趣阁-玄界之门小说
 * <p>
 * 增量爬取 当有新增章节时  通知
 */
@Component
public class BookJob {

	private final static Logger log = LoggerFactory.getLogger(BookJob.class);

	@Autowired
	private BookService bookService;

	@Autowired
	private RestTemplate restTemplate;
	@Autowired
	private HttpClientManager httpClientManager;

	@Autowired
	private MailService mailService;

	private static ExecutorService chapterExecutor = new ThreadPoolExecutor(10, 30, 30, TimeUnit.SECONDS, new LinkedBlockingQueue<>(50), new  ThreadPoolExecutor.CallerRunsPolicy());
	private static ExecutorService blankChapterExecutor = new ThreadPoolExecutor(10, 30, 30, TimeUnit.SECONDS, new LinkedBlockingQueue<>(50), new  ThreadPoolExecutor.CallerRunsPolicy());
	private static ExecutorService mailExecutor = new ThreadPoolExecutor(3, 10, 30, TimeUnit.SECONDS, new LinkedBlockingQueue<>(5), new  ThreadPoolExecutor.CallerRunsPolicy());


	private final static String indexPage = "http://www.biquge.com.tw/";

	/**
	 * 自动爬取最新书籍
	 *
	 * 暂不启用
	 */
	//@Scheduled(cron = "0 0 0/8 * * ?")
	public void spiderBook() {
		log.info(String.format("begin exec spider book task"));

		String indexContent = HttpUtils.execGet(indexPage, "GBK");
		List indexList = BookUtils.getIndexList(indexContent);

		if(CollectionUtils.isEmpty(indexList)){
			return;
		}

		Map<String, String> topMap = new HashMap<>();

		indexList.forEach(s->{
			String content = HttpUtils.execGet(String.format("%s%s", indexPage, s), "GBK");
			Map<String, String> topBookMap = BookUtils.getTopBook(content);
			topMap.putAll(topBookMap);
		});

		Set<String> bookNameSet = bookService.getAllBookName();


		topMap.forEach((k,v)->{
			if(bookNameSet.contains(k)) return;

			String content = HttpUtils.execGet(v, "GBK");
			Book book = BookUtils.getBookInfo(content);
			if(book != null){
				book.setBaseUrl(indexPage);
				book.setIndexUrl(v);
				book.setSpiderOpen(0);
				book.setCreateTime(new Timestamp(System.currentTimeMillis()));
				book.setUpdateTime(new Timestamp(System.currentTimeMillis()));

				book.setLastestChapterName("");

				bookService.insertBook(book);
			}
		});



		log.info(String.format("end exec spider book task."));
	}

	/**
	 * 爬取章节列表详情
	 */
	@Scheduled(cron = "0 0/5 9-20 * * ?")
	public void spiderBookDetail() {
		log.info(String.format("begin exec spider book detail task"));
		List<Book> needSpiderBooks = bookService.getSpiderBookList();

		if(CollectionUtils.isEmpty(needSpiderBooks)){
			log.info("there is no books need to spider!");
			return;
		}

		for (Book book : needSpiderBooks) {
			log.info(String.format("begin spider book:%s", book.getName()));
			long st = System.currentTimeMillis();

			String indexUrl = book.getIndexUrl();
			String content = HttpUtils.execGet(indexUrl, "GBK");


			Book bookInfo = BookUtils.getBookInfo(content);
			if(bookInfo == null){
				continue;
			}

			String currentChapter = "";
			if(StringUtils.isNotBlank(bookInfo.getLastestChapterName())){
				currentChapter = bookInfo.getLastestChapterName();
			}

			LinkedHashMap<String, String> chapterMap = BookUtils.getChapterMap(content);
			if(MapUtils.isEmpty(chapterMap)){
				continue;
			}

			Set<String> lastest10Chapter = new HashSet<>();

			if(StringUtils.isBlank(currentChapter)){
				int count = 0;
				for (String key: chapterMap.keySet()) {
					currentChapter = key;
					if(chapterMap.size()-10<=count++){
						lastest10Chapter.add(key);
					}

				}
			}

			String lastestChapterName = book.getLastestChapterName();
			if(StringUtils.equals(currentChapter, lastestChapterName)){
				log.info(String.format("%s未发现更新章节!", book.getName()));
				continue;
			}

			LinkedHashMap<String, String> newChapterMap = new LinkedHashMap<>();
			if(StringUtils.isBlank(lastestChapterName)){//全新数据
				newChapterMap.putAll(chapterMap);
			}else{//部分更新
				if(chapterMap.containsKey(lastestChapterName)){//存在该章节 正常情况
					boolean findFlag = false;
					for (Map.Entry<String, String> entry : chapterMap.entrySet()) {
						if(findFlag){
							newChapterMap.put(entry.getKey(), entry.getValue());
						}else{
							if(StringUtils.equals(entry.getKey(), lastestChapterName)){
								findFlag = true;
							}
						}
					}
				}else{//不存在章节 全量匹配
					Set<String> chapterNameSet = bookService.getAllChapterNameByBookId(book.getId());

					if(CollectionUtils.isEmpty(chapterNameSet)){
						newChapterMap.putAll(chapterMap);
					}else{
						for (Map.Entry<String, String> entry : chapterMap.entrySet()) {
							String chapterName = entry.getKey();
							if(!chapterNameSet.contains(chapterName)){
								newChapterMap.put(entry.getKey(), entry.getValue());
							}
						}
					}
				}
			}

			final String bookLastestChapterName = currentChapter;
			for (Map.Entry<String, String> entry : newChapterMap.entrySet()) {
				String chapterName = entry.getKey();
				log.info(String.format("begin analyze %s-->%s...", chapterName, entry.getValue()));
				chapterExecutor.execute(()->{
					String sourceUrl = String.format("%s/%s", book.getBaseUrl(), entry.getValue());
					String response = HttpUtils.execGet(sourceUrl, "GBK");
					String chapterDetail = BookUtils.getChapterDetail(response);

					Chapter chapter = new Chapter();
					chapter.setBookId(book.getId());
					chapter.setBookName(book.getName());
					chapter.setContent(chapterDetail);
					chapter.setSourceUrl(sourceUrl);
					if(StringUtils.isBlank(chapterDetail)){
						chapter.setRetryStatus(1);
						if(!lastest10Chapter.contains(chapterName)){//历史问题章节 忽略
							chapter.setRetryCount(50);
						}
					}
					chapter.setCreateTime(new Timestamp(new Date().getTime()));
					chapter.setUpdateTime(new Timestamp(new Date().getTime()));
					chapter.setName(chapterName);

					bookService.insertNewestChapter(chapter);

					if(StringUtils.equals(bookLastestChapterName, chapterName)){
						bookService.updateLastestChapterInfo(chapter);

						mailExecutor.execute(()->{
							mailService.sendChapterNotify(chapter);
						});
					}
				});
			}

			log.info(String.format("end spider book:%s-->cost:%sms", book.getName(), (System.currentTimeMillis()-st)));
		}

		log.info(String.format("end exec spider book detail task."));
	}

	/**
	 * 爬取空白内容的章节-->更新
	 */
	@Scheduled(cron = "0 0/10 * * * ?")
	public void spiderBlankChapter() {
		log.info(String.format("begin exec spider blank chapter task"));

		List<Chapter> chapterList = bookService.getRetryChapter();
		if(CollectionUtils.isEmpty(chapterList)){
			return;
		}

		chapterList.forEach(chapter->{
			blankChapterExecutor.execute(()->{
				String sourceUrl = chapter.getSourceUrl();
				if(StringUtils.isBlank(sourceUrl)){
					return;
				}

				String response = HttpUtils.execGet(sourceUrl, "GBK");
				String chapterDetail = BookUtils.getChapterDetail(response);

				if(StringUtils.isNotBlank(chapterDetail)){
					chapter.setContent(chapterDetail);
					chapter.setRetryStatus(0);

					bookService.refreshChapterConetent(chapter);
				}else{
					bookService.refreshChapterRetryInfo(chapter.getId());
				}
			});
		});

		log.info(String.format("end exec spider blank chapter task."));
	}


}
