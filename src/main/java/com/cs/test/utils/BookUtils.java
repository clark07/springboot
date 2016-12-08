package com.cs.test.utils;

import com.cs.test.db.entity.Book;
import org.apache.commons.lang3.StringUtils;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by admin on 2016/12/6.
 */
public class BookUtils {

	private final static String anyChars = "[\\s\\S]*?";
	private final static Pattern p_index = Pattern
			.compile("<div" + anyChars + "class=\"nav\">(" + anyChars + ")</div>");
	private final static Pattern p_index_item = Pattern
			.compile("<li>" + anyChars + "<a" + anyChars + "href=\"(" + anyChars + ")\">" + anyChars + "</a>" + anyChars + "</li>");
	private final static Pattern p_list = Pattern.compile("<div" + anyChars + "id=\"list\">(" + anyChars + ")</div>");
	private final static Pattern p_dd = Pattern.compile("<dd>" + anyChars + "</dd>");
	private final static Pattern p_item = Pattern
			.compile("<dd>" + anyChars + "<a" + anyChars + "href=\"/(" + anyChars + "\\.html)\">(" + anyChars + ")</a>" + anyChars + "</dd>");
	private final static Pattern p_context = Pattern
			.compile("<div" + anyChars + "id=\"content\">(" + anyChars + ")</div>");
	private final static Pattern p_top = Pattern.compile("<div" + anyChars + "class=\"r\">(" + anyChars + ")</div>");
	private final static Pattern p_top_item = Pattern
			.compile("<li>" + anyChars + "<a" + anyChars + "href=\"(" + anyChars + ")\">(" + anyChars + ")</a>" + anyChars + "</li>");
	private final static Pattern p_item_detail = Pattern
			.compile("<div" + anyChars + "id=\"maininfo\">" + anyChars + "<div" + anyChars + "id=\"info\">(" + anyChars + ")</div>" + anyChars + "<div" + anyChars + "id=\"intro\">(" + anyChars + ")</div>" + anyChars + "</div>");
	private final static Pattern p_info = Pattern
			.compile("<h1>(" + anyChars + ")</h1>" + anyChars + "<p>(" + anyChars + ")</p>("+anyChars+"<p>最新章节：<a"+anyChars+"href=\"("+ anyChars + ")\">("+anyChars+")</a></p>)?");
	private final static Pattern p_infor = Pattern.compile("<p>(" + anyChars + ")</p>");

	public static void main(String[] args) throws Exception {
		//LinkedHashMap<String, String> gbk = getChapterMap(HttpUtils.execGet("http://www.biquge.com.tw/16_16273/", "GBK"));
		//Map<String, String> topBookMap = getTopBook(HttpUtils.execGet("http://www.biquge.com.tw/kehuan/", "GBK"));
		//List indexList = getIndexList(HttpUtils.execGet("http://www.biquge.com.tw", "GBK"));

		Book book = getBookInfo(HttpUtils.execGet("http://www.biquge.com.tw/16_16273/", "GBK"));
		System.out.println(JsonUtil.getJsonWithoutNullFromObject(book));
	}

	/**
	 * 获取章节主内容
	 * @param content
	 * @return
	 */
	public static String getChapterDetail(String content) {
		Matcher m_context = p_context.matcher(content);

		String context = ""; if (m_context.find()) {
			context = m_context.group(1);
			context = StringUtils.trimToEmpty(context).replace("&nbsp;", "").replace("<br />", "");
		}

		return context;
	}

	public static Map<String, String> getTopBook(String content){
		Map<String, String> topBookMap = new LinkedHashMap<>();

		if(StringUtils.isNotBlank(content)){
			Matcher m_list = p_top.matcher(content);
			if (m_list.find()) {
				content = m_list.group(1);
			}else{
				content = "";
			}
		}

		if(StringUtils.isBlank(content)){
			return topBookMap;
		}

		Matcher m_top_item = p_top_item.matcher(content);

		while (m_top_item.find()) {
			topBookMap.put(m_top_item.group(2), m_top_item.group(1));
		}

		return topBookMap;
	}



	/**
	 * 获取书籍简介
	 * @param content
	 * @return
	 */
	public static Book getBookInfo(String content){

		String info = "";
		String infor = "";
		if(StringUtils.isNotBlank(content)){
			Matcher m_item_detail = p_item_detail.matcher(content);
			if (m_item_detail.find()) {
				info = m_item_detail.group(1);
				infor = m_item_detail.group(2);
			}
		}

		if(StringUtils.isBlank(info)){
			return null;
		}

		Book book = new Book();
		Matcher m_info = p_info.matcher(info);
		if(m_info.find()){
			String bookName = StringUtils.trim(m_info.group(1));
			String bookAuthor = StringUtils.trim(m_info.group(2)).replace("&nbsp;", "").replace("作者：", "");
			String lastestChapterName = StringUtils.trim(m_info.group(5));
			book.setName(bookName);
			book.setAuthor(bookAuthor);
			book.setLastestChapterName(lastestChapterName);
		}
		Matcher m_infor = p_infor.matcher(infor);
		if(m_infor.find()){
			String bookDesc = StringUtils.trim(m_infor.group(1));
			book.setDescription(bookDesc);
		}

		return book;
	}





	/**
	 * 获取目录列表
	 * @param content
	 * @return
	 */
	public static LinkedHashMap<String, String> getChapterMap(String content) {
		LinkedHashMap<String, String> chapterMap = new LinkedHashMap<>();

		if(StringUtils.isNotBlank(content)){
			Matcher m_list = p_list.matcher(content);
			if (m_list.find()) {
				content = m_list.group(1);
			}else{
				content = "";
			}
		}

		if(StringUtils.isBlank(content)){
			return chapterMap;
		}

		Matcher m_dd = p_dd.matcher(content);

		List<String> ddList = new ArrayList<>();
		while (m_dd.find()) {
			ddList.add(m_dd.group());
		}
		ddList.forEach(c -> {
			Matcher m_item = p_item.matcher(c); if (m_item.find()) {
				chapterMap.put(m_item.group(2), m_item.group(1));
			}
		});
		return chapterMap;
	}

	public static List getIndexList(String content) {
		List<String> indexList = new ArrayList<>();

		if(StringUtils.isNotBlank(content)){
			Matcher m_index = p_index.matcher(content);
			if (m_index.find()) {
				content = m_index.group(1);
			}else{
				content = "";
			}
		}
		if(StringUtils.isBlank(content)){
			return indexList;
		}

		Matcher m_index_item = p_index_item.matcher(content);
		while (m_index_item.find()) {
			String index = m_index_item.group(1);
			indexList.add(index.substring(1));
		}

		return indexList;

	}
}
