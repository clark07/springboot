package com.cs.test.utils;

import org.apache.log4j.Logger;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.StringReader;
import java.io.StringWriter;

public class JAXBContextUtil {
	
	static Logger logger = Logger.getLogger(JAXBContextUtil.class);
	
	public static String BeanToXml(Object obj) {
		JAXBContext context;
		String msg = "";
		
		try {
			context = JAXBContext.newInstance(obj.getClass());
			Marshaller m;
			try {
				m = context.createMarshaller();
				StringWriter sw = new StringWriter();
		        try {
					m.marshal(obj,sw);
					msg = sw.toString();
				} catch (JAXBException e) {
					// TODO Auto-generated catch block
					logger.error(e);
				}
				
			} catch (JAXBException e) {
				// TODO Auto-generated catch block
				logger.error(e);
			}
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			logger.error(e);
		}
		
		return msg;
	}
	
	
	public static <T> T XmlToBean(String xml, T obj) {
		
	        JAXBContext context;
			try {
				context = JAXBContext.newInstance(obj.getClass());
				Unmarshaller um;
				try {
					um = context.createUnmarshaller();
					StringReader sr = new StringReader(xml);
			        try {
						obj = (T)um.unmarshal(sr);
						return obj;
					} catch (JAXBException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				} catch (JAXBException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} catch (JAXBException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        
	        
	        
	        return null;
	}
}
