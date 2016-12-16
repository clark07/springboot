package com.cs.test;

import com.cs.test.annotation.JerseyResource;
import org.glassfish.jersey.server.ResourceConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.type.classreading.CachingMetadataReaderFactory;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.util.ClassUtils;
import org.springframework.util.SystemPropertyUtils;

/**
 * Created by admin on 2016/1/11.
 */
public class JerseyConfig extends ResourceConfig{

	private final static Logger log = LoggerFactory.getLogger(JerseyConfig.class);

	public static final String DEFAULT_RESOURCE_PATTERN = "**/*.class";

	public JerseyConfig(){
		log.info("init Jersey config...");

	try {
		String packageSearchPath = ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX
				+ ClassUtils.convertClassNameToResourcePath(SystemPropertyUtils.resolvePlaceholders("com.cs.test"))
				+ "/" + DEFAULT_RESOURCE_PATTERN;

		//获取Spring资源解析器
		ResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver();
		//创建Spring中用来读取resource为class的工具类
		MetadataReaderFactory metadataReaderFactory = new CachingMetadataReaderFactory(resourcePatternResolver);

		Resource[] resources = resourcePatternResolver.getResources(packageSearchPath);

		for (Resource r : resources) {
			MetadataReader metadataReader = metadataReaderFactory.getMetadataReader(r);
			boolean jerseyResource = metadataReader.getAnnotationMetadata().hasAnnotation(JerseyResource.class.getName());
			if (jerseyResource) {
				log.info(String.format("Found jersey API-->%s", metadataReader.getClassMetadata().getClassName()));
				register(Class.forName(metadataReader.getClassMetadata().getClassName()));
			}
		}
			//register(ResponseFilter.class);
			//register(PreMatchFilter.class);
		}catch (Exception e){}

	}

}
