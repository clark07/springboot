package com.cs.test.service;

import com.cs.test.db.dao.PeopleDao;
import com.cs.test.db.entity.People;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 * Created by admin on 2016/12/15.
 */
@Service
public class PeopleService {

	@Autowired
	private PeopleDao peopleDao;

	@Cacheable(value = "people", key = "'people:'.concat(#peopleId.toString())" )
	public People getPeople(Integer peopleId) {
		People people = peopleDao.findOne(peopleId);
		return people;
	}

	@CachePut(value = "people", key = "'people:'.concat(#result.id)")
	public People addPeople(People people) {
		peopleDao.save(people);
		return people;
	}

	@CacheEvict(value = "people", key = "'people:'.concat(#people.id)")
	public void deletePeople(People people) {
		peopleDao.delete(people);
	}
}
