package com.cs.test.service;

import com.cs.test.db.dao.BookDao;
import com.cs.test.db.dao.ChapterDao;
import com.cs.test.db.entity.Book;
import com.cs.test.db.entity.Chapter;
import com.cs.test.utils.JsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * Created by admin on 2016/12/6.
 */
@Service
@Transactional
public class BookService {

	
	private final static Logger log = LoggerFactory.getLogger(BookService.class);

	@Autowired
	private BookDao bookDao;
	@Autowired
	private ChapterDao chapterDao;

	public List<Book> getSpiderBookList() {
		return bookDao.findBySpiderOpenAndStatus(1, 0);
	}

	public Set<String> getAllChapterNameByBookId(int bookId) {
		return chapterDao.findChapterNameByBookId(bookId);
	}

	public void insertNewestChapter(Chapter chapter) {
		log.info(String.format("Got new chapter:%s", JsonUtil.getJsonFromObject(chapter)));

		Chapter oldChapter = chapterDao.getByName(chapter.getName());
		if(oldChapter != null){
			log.warn(String.format("book:%s-->chapter:%s已存在", chapter.getBookName(), chapter.getName()));
			chapter.setId(oldChapter.getId());
			return;
		}
		chapterDao.save(chapter);
	}

	public List<Chapter> getRetryChapter() {
		return chapterDao.findByRetryStatusAndRetryCountIsLessThan(1, 50);
	}

	public void refreshChapterConetent(Chapter chapter) {
		log.info(String.format("Got refresh chapter:%s", JsonUtil.getJsonFromObject(chapter)));
		chapterDao.refreshContent(chapter.getContent(), chapter.getRetryStatus(), chapter.getId());
	}

	public void insertBook(Book book) {
		log.info(String.format("Got new book:%s", JsonUtil.getJsonFromObject(book)));

		Book oldBook = bookDao.getByName(book.getName());
		if(oldBook != null){
			log.warn(String.format("book:%s已存在", oldBook.getName()));
			return;
		}

		bookDao.save(book);
	}

	public Set<String> getAllBookName() {
		return bookDao.findName();
	}

	public void updateLastestChapterInfo(Chapter chapter) {
		bookDao.updateLastestChapterInfo(chapter.getId(), chapter.getName(), new Date(), chapter.getBookId());
	}

	public Chapter getChapter(String name) {
		return chapterDao.getByName(name);
	}

	public void refreshChapterRetryInfo(int chapterId) {
		chapterDao.refreshChapterRetryInfo(chapterId);
	}

	public List<Book> getAllBook() {
		return bookDao.findAll();
	}

	public Book getBook(int bookId) {
		return bookDao.getOne(bookId);
	}

	public Chapter getChapter(int chapterId) {
		return chapterDao.getOne(chapterId);
	}

	public List<Chapter> getSimpleChapterList(int bookId) {
		return chapterDao.getBasicChapterInfo(bookId);
	}
}
