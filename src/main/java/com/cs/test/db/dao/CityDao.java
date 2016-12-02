package com.cs.test.db.dao;

import com.cs.test.db.entity.City;
import org.springframework.data.domain.Pageable;
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
}
