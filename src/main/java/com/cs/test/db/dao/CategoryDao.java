package com.cs.test.db.dao;

import com.cs.test.db.entity.Category;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by admin on 2016/12/2.
 */
public interface CategoryDao extends CrudRepository<Category, Integer> {

	public List<Category> findByName(String name);

	public Category findById(Integer id);
}
