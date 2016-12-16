package com.cs.test.redis;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.interceptor.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;

import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by admin on 2016/12/1.
 */
@Configuration
//@EnableCaching
public class RedisConfig extends CachingConfigurerSupport {

	/**
	 * Type safe representation of application.properties
	 */
	@Autowired
	ClusterConfigurationProperties clusterProperties;

	@Bean
	public RedisConnectionFactory connectionFactory() {
/*		return new JedisConnectionFactory(
				new RedisClusterConfiguration(clusterProperties.getNodes()));*/
		return new JedisConnectionFactory();
	}


	@Bean
	public CacheManager cacheManager(RedisTemplate redisTemplate){

		Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<Object>(
				Object.class);
		ObjectMapper om = new ObjectMapper();
		om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
		om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
		jackson2JsonRedisSerializer.setObjectMapper(om);
		redisTemplate.setKeySerializer(redisTemplate.getStringSerializer());
		redisTemplate.setValueSerializer(jackson2JsonRedisSerializer);

		return new RedisCacheManager(redisTemplate);
	}

	@Bean
	public CacheResolver cacheResolver(CacheManager cacheManager){
		return new SimpleCacheResolver(cacheManager);
	}

	@Bean
	@Override
	public CacheErrorHandler errorHandler() {
		// 用于捕获从Cache中进行CRUD时的异常的回调处理器。
		return new SimpleCacheErrorHandler();
	}

	@Bean
	public KeyGenerator keyGenerator() {
		return (target, method, params) -> {
			StringBuilder sb = new StringBuilder();
			sb.append(target.getClass().getSimpleName()).append(":");//执行方法所在的类
			//sb.append(method.getName()).append(":");//执行方法所在的方法
			sb.append(Stream.of(params).map(String::valueOf).collect(Collectors.joining("_")));
			return sb.toString();
		};
	}
}