package com.cs.test.api;

import com.cs.test.db.dao.CategoryDao;
import com.cs.test.db.dao.CityDao;
import com.cs.test.db.entity.Category;
import com.cs.test.db.entity.City;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * Created by admin on 2016/12/2.
 */
@Path("/jpa")
public class JpaApi {


	@Autowired
	private CityDao cityDao;

	@Autowired
	private CategoryDao categoryDao;


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


}
