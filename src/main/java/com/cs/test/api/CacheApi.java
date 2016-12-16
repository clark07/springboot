package com.cs.test.api;

import com.cs.test.annotation.JerseyResource;
import com.cs.test.db.entity.People;
import com.cs.test.service.PeopleService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Created by admin on 2016/12/15.
 */
@JerseyResource
@Path("/cache")
public class CacheApi {

	@Autowired
	private PeopleService peopleService;

	@GET
	@Path("/get")
	@Produces(MediaType.APPLICATION_JSON)
	public Response get(@QueryParam("id") Integer id){

		People people = peopleService.getPeople(id);

		return Response.ok(people).build();
	}

	@POST
	@Path("/add")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response add(People people){

		peopleService.addPeople(people);

		return Response.ok(people).build();
	}

	@POST
	@Path("/delete")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response delete(People people){
		peopleService.deletePeople(people);
		return Response.ok(people).build();
	}

}
