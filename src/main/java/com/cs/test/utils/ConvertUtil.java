package com.cs.test.utils;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * map和bean互转工具类
 * 
 * @author clark
 * 
 */
public class ConvertUtil {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

	}

	/**
	 * bean2map
	 * @param obj
	 * @return
	 */
	public static Map<String, Object> convertBean2Map(Object obj) {
		if(obj == null){
			return null;
		}
		Map<String, Object> beanMap = new HashMap<String, Object>();
		try {
			BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());
			PropertyDescriptor[] propertyDescriptors = beanInfo
					.getPropertyDescriptors();
			for (PropertyDescriptor property : propertyDescriptors) {
				String key = property.getName();
				// 过滤class属性
				if (!key.equals("class")) {
					// 得到property对应的getter方法
					Method getter = property.getReadMethod();
					Object value = getter.invoke(obj);
					beanMap.put(key, value);
				}
			}
		} catch (Exception e) {
			System.out.println("transBean2Map Error " + e);
		}
		return beanMap;
	}
	
	/**
	 * map2bean
	 * @param beanMap
	 * @param obj
	 */
	public static void convertMap2Bean(Map<String, Object> beanMap, Object obj) {
		if (beanMap == null) {
			return;
		}
		try {
			BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());
			PropertyDescriptor[] propertyDescriptors = beanInfo
					.getPropertyDescriptors();
			for (PropertyDescriptor property : propertyDescriptors) {
				String key = property.getName();
				if (beanMap.containsKey(key)) {
					Object value = beanMap.get(key);
					// 得到property对应的setter方法
					Method setter = property.getWriteMethod();
					setter.invoke(obj, value);
				}
			}
		} catch (Exception e) {
			System.out.println(String.format(
					"convert %s to Bean exception!", beanMap) + e);
		}
	}

}
