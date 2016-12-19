package com.cs.test.test;

import com.cs.test.utils.JsonUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * Created by admin on 2016/12/16.
 */
//@Configuration
//@ImportResource(locations = {"classpath:spring/application-springel.xml"})
public class SpringElDemo {


private final static Logger log = LoggerFactory.getLogger(SpringElDemo.class);
	

	@Autowired
	private List list;

	@Autowired
	private List sublist;

	@PostConstruct
	public void init(){
		log.info(StringUtils.repeat("#", 100));
		list.stream().forEach(o -> log.info(JsonUtil.getJsonFromObject(o)));
		log.info(StringUtils.repeat("#", 100));
		sublist.stream().forEach(o -> log.info(JsonUtil.getJsonFromObject(o)));
		log.info(StringUtils.repeat("#", 100));
	}
}
