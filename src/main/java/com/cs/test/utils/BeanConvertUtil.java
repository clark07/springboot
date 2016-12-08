package com.cs.test.utils;

import com.cs.test.exception.ConvertRuntimeException;
import org.apache.commons.collections.MapUtils;
import org.apache.log4j.Logger;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class BeanConvertUtil {
	
	private final static Logger logger = Logger.getLogger(BeanConvertUtil.class);
	
	public static void convert(Object targetObj, Object sourceObj, Map<String, String> nameMap) throws ConvertRuntimeException {
		if(sourceObj == null || targetObj ==null){
			throw new ConvertRuntimeException("Invaild convertParam!");
		}
		if(MapUtils.isEmpty(nameMap)){
			return;
		}
		try {
			BeanInfo beanInfo = Introspector.getBeanInfo(sourceObj.getClass());
			PropertyDescriptor[] sropertyDescriptors = beanInfo.getPropertyDescriptors();
			for (PropertyDescriptor propertyDescriptor : sropertyDescriptors) {
				String propertyName = propertyDescriptor.getName();
				if(nameMap.containsKey(propertyName)){
					Method getter = propertyDescriptor.getReadMethod();
					Object value = getter.invoke(sourceObj);
					
					try {
						Field field = targetObj.getClass().getDeclaredField(nameMap.get(propertyName));
						field.setAccessible(true);
						field.set(targetObj, value);
					} catch (Exception e) {
						logger.warn(String.format("get target [%s] field exception!", nameMap.get(propertyName)), e);
					}
					
					/*String methodName = "set" + StringUtils.capitalize(nameMap.get(propertyName));
					try {
						Method setter = targetObj.getClass().getMethod(methodName, value.getClass());
						setter.invoke(targetObj, value);
					} catch (Exception e) {
						
						if(value.getClass() == Integer.class){
							try {
								Method setter = targetObj.getClass().getMethod(methodName, int.class);
								setter.invoke(targetObj, value);
							} catch (Exception e2) {
								logger.warn(String.format("get target [%s] setter method exception!", methodName), e2);
							}
						}else{
							logger.warn(String.format("get target [%s] setter method exception!", methodName), e);
						}
					}*/
				}
				
			}
		} catch (Exception e) {
			String errMsg = String
					.format("convert %s to %s exception!", sourceObj.getClass().getName(), targetObj.getClass().getName());
			logger.error(errMsg, e);
			throw new ConvertRuntimeException(errMsg, e);
		}
		
	}
	
	public static void main(String[] args) throws Exception {
		Demo1 demo1 = new Demo1(1, 2);
		Demo2 demo2 = new Demo2();
		Map<String, String> namemap = new HashMap<String, String>();
		namemap.put("s1", "s1");
		namemap.put("s2", "s3");
		System.out.println("before convert:");
		System.out.println(JsonUtil.getJsonFromObject(demo1));
		System.out.println(JsonUtil.getJsonFromObject(demo2));
		convert(demo2, demo1, namemap);
		System.out.println("after convert:");
		System.out.println(JsonUtil.getJsonFromObject(demo1));
		System.out.println(JsonUtil.getJsonFromObject(demo2));
	}
	
	
	static class Demo1 {
		private int s1;
		private int s2;
		
		public Demo1() {
		}
		public Demo1(int s1, int s2) {
			super();
			this.s1 = s1;
			this.s2 = s2;
		}
		public int getS1() {
			return s1;
		}
		public void setS1(int s1) {
			this.s1 = s1;
		}
		public int getS2() {
			return s2;
		}
		public void setS2(int s2) {
			this.s2 = s2;
		}
		
	}
	static class Demo2 {
		private int s1;
		private int s3;
		public Demo2() {
		}
		public Demo2(int s1, int s3) {
			super();
			this.s1 = s1;
			this.s3 = s3;
		}
		public int getS1() {
			return s1;
		}
		public void setS1(int s1) {
			this.s1 = s1;
		}
		public int getS3() {
			return s3;
		}
		public void setS3(int s3) {
			this.s3 = s3;
		}
		
	}
}
