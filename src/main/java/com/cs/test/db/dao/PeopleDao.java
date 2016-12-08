package com.cs.test.db.dao;

import com.cs.test.db.entity.People;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by admin on 2016/12/5.
 */
@Repository
public interface PeopleDao extends JpaRepository<People, Integer> {

	public List<People> findByAgeGreaterThan(int age);

}
