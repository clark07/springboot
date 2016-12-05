package com.cs.test.db.dao;

import com.cs.test.db.entity.City;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by admin on 2016/12/2.
 */
@Transactional
@Repository
public interface CityDao extends CrudRepository<City, Long> {
	public List<City> findByName(String name, Pageable page);


	@Query("select c from City c where c.id=?1 and c.name=?2")
	public List<City> findByCondition(long cid, String name);
}
