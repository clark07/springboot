package com.cs.test.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by admin on 2016/11/30.
 */
@Service
public class RedisService {

	@Autowired
	private StringRedisTemplate redisTemplate;

	public String get(String key){
		return redisTemplate.opsForValue().get(key);
	}

	public void set(String key, String value){
		redisTemplate.opsForValue().set(key, value);
	}

	public void setExpire(String key, String value, long expireTime){
		redisTemplate.opsForValue().set(key, value, expireTime, TimeUnit.SECONDS);
	}

	public List<String> getList(String key){
		return redisTemplate.opsForList().range(key, 0, -1);
	}

	public void setListAdd(String key, String value) {
		redisTemplate.opsForList().leftPush(key, value+"left");
		redisTemplate.opsForList().rightPush(key, value+"right");
	}
}
