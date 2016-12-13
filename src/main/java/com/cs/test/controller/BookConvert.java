package com.cs.test.controller;

import com.cs.test.db.entity.Book;
import com.cs.test.db.entity.Chapter;
import com.cs.test.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;

/**
 * Created by admin on 2016/12/13.
 */
@Configuration
public class BookConvert {

	@Autowired
	private BookService bookService;

	@Bean
	public Converter<String, Book> bookConverter() {
		return new Converter<String, Book>() {
			@Override
			public Book convert(String id) {
				return bookService.getBook(Integer.parseInt(id));
			}
		};	}

	@Bean
	public Converter<String, Chapter> chapterConverter() {
		return new Converter<String, Chapter>() {
			@Override
			public Chapter convert(String id) {
				return bookService.getChapter(Integer.parseInt(id));
			}
		};
	}

}
