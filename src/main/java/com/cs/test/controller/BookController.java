/*
 * Copyright 2012-2016 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.cs.test.controller;

import com.cs.test.db.entity.Book;
import com.cs.test.db.entity.Chapter;
import com.cs.test.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * @author Rob Winch
 * @author Doo-Hwan Kwak
 */
@Controller
@RequestMapping("/book")
public class BookController {

	@Autowired
	private BookService bookService;

	@GetMapping
	public ModelAndView list() {
		List<Book> books = bookService.getAllBook();
		ModelAndView mv = new ModelAndView("books/list", "books", books);
		return mv;
	}


	@GetMapping("{id}")
	public ModelAndView viewBook(@PathVariable("id") Book book) {
		ModelAndView mv = new ModelAndView("books/bookDetail", "book", book);

		List<Chapter> simpleChapterList = bookService.getSimpleChapterList(book.getId());
/*
		List<Chapter> chapterList = simpleChapterList.stream().sorted((c1, c2)->{
			return BookUtils.getIndexByChapterName(c1.getName())- BookUtils.getIndexByChapterName(c2.getName());
		}).collect(Collectors.toList());*/

		mv.addObject("chapters", simpleChapterList);

		return mv;
	}

}
