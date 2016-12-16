package com.cs.test.service;

import com.cs.test.db.dao.CityDao;
import com.cs.test.db.entity.City;
import com.cs.test.utils.JsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionException;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by admin on 2016/12/15.
 */
@Service
@Transactional
public class DemoService {


private final static Logger log = LoggerFactory.getLogger(DemoService.class);

	@Autowired
	private CityDao cityDao;

	/**
	 * 只读
	 */
	@Transactional(readOnly = true)
	public void testReadOnly(){
		City one = cityDao.findOne(1L);
		log.info(JsonUtil.getJsonFromObject(one));
	}
	/**
	 * 只读
	 */
	@Transactional(readOnly = true)
	public void testReadOnlyException(){
		City city = new City();
		city.setCountry("c");
		city.setMap("m");
		city.setName("testReadOnlyException");
		city.setState("s");
		cityDao.save(city);
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void testRequired(){
		City city = new City();
		city.setCountry("c");
		city.setMap("m");
		city.setName("testRequired");
		city.setState("s");
		cityDao.save(city);
	}
	@Transactional(propagation = Propagation.NEVER)//未测试成功
	public void testNever(){
		City city = new City();
		city.setCountry("c");
		city.setMap("m");
		city.setName("testNever");
		city.setState("s");
		cityDao.save(city);
	}
	@Transactional(propagation = Propagation.NOT_SUPPORTED)//未测试成功
	public void testNotSupport(){
		City city = new City();
		city.setCountry("c");
		city.setMap("m");
		city.setName("testNotSupport");
		city.setState("s");
		cityDao.save(city);
	}

	@Transactional(timeout = 5)
	public void testTimeout(){
		City city = new City();
		city.setCountry("c");
		city.setMap("m");
		city.setName("testTimeout");
		city.setState("s");
		cityDao.save(city);
	}

	@Transactional(timeout = 5)//判断超时时间为左后一个statment开启时间间隔
	public void testTimeoutException(){

		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		City city = new City();
		city.setCountry("c");
		city.setMap("m");
		city.setName("testTimeoutException");
		city.setState("s");
		cityDao.save(city);
	}

	@Transactional(noRollbackFor = {TransactionException.class})
	public void testNoRollbackFor(){
		City city = new City();
		city.setCountry("c");
		city.setMap("m");
		city.setName("testNoRollbackFor");
		city.setState("s");
		cityDao.save(city);

		throw new TransactionSystemException("testNoRollbackFor");
	}

	@Transactional(rollbackFor = {TransactionException.class})
	public void testRollbackFor(){
		City city = new City();
		city.setCountry("c");
		city.setMap("m");
		city.setName("testRollbackFor");
		city.setState("s");
		cityDao.save(city);
		throw new TransactionSystemException("testRollbackFor");
	}






}
