package com.cs.test.api;

import com.cs.test.annotation.JerseyResource;
import com.cs.test.db.dao.CategoryDao;
import com.cs.test.db.dao.CityDao;
import com.cs.test.db.dao.PeopleDao;
import com.cs.test.db.entity.Category;
import com.cs.test.db.entity.City;
import com.cs.test.db.entity.People;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * Created by admin on 2016/12/2.
 */
@JerseyResource
@Path("/jpa")
//@Api(value = "jpa", description = "JPA测试用API")
public class JpaApi {


	@Autowired
	private CityDao cityDao;

	@Autowired
	private CategoryDao categoryDao;

	@Autowired
	private PeopleDao peopleDao;


	@GET
	@Path("/city")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getCity(@QueryParam("name") String name,@QueryParam("pn") @DefaultValue("0") Integer pn,@QueryParam("ps") @DefaultValue("10") Integer ps) {
		List<City> citys = cityDao.findByName(name, new PageRequest(pn, ps));
		return Response.ok(citys).build();
	}

	@GET
	@Path("/city/condition")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getCityCondition(@QueryParam("name") String name,@QueryParam("cid") Integer cid) {
		List<City> citys = cityDao.findByCondition(cid, name);
		return Response.ok(citys).build();
	}

	@POST
	@Path("/city/add")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response addCity(City city) {
		cityDao.save(city);
		return Response.ok("succ").build();
	}

	@GET
	@Path("/category")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getCategory(@QueryParam("name") String name) {
		List<Category> cs = categoryDao.findByName(name);
		return Response.ok(cs).build();
	}
	@GET
	@Path("/people")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getPeople(@QueryParam("age") @DefaultValue("0") Integer age) {
		List<People> peoples = peopleDao.findByAgeGreaterThan(age);
		return Response.ok(peoples).build();
	}


}
