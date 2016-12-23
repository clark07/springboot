package com.cs.test.api;

import com.cs.test.annotation.JerseyResource;
import com.cs.test.db.entity.Book;
import com.cs.test.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * Created by admin on 2016/12/19.
 */
@JerseyResource
@Path("/book")
//@Api(value = "book", description = "书本相关接口API")
public class BookApi {

	@Autowired
	private BookService bookService;


	@GET
	@Path("/demo")
	@Produces(MediaType.APPLICATION_JSON)
	public Response demo(){
		List<Book> bookByCondition = bookService.findBookByCondition();
		return Response.ok(bookByCondition).build();
	}
	@GET
	@Path("/demo2")
	@Produces(MediaType.APPLICATION_JSON)
	public Response demo2(){
		bookService.lockBook();
		return Response.ok("succ").build();
	}

}
