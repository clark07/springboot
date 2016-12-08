package com.cs.test.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;

/**
 * Created by admin on 2016/12/1.
 */
@Configuration
public class AppConfig {

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
}