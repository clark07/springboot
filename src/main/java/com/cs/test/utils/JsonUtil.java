package com.cs.test.utils;


import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JsonUtil {

	private final static Logger log = LoggerFactory.getLogger(JsonUtil.class);

	private static final ObjectMapper objectMapper;

	static {
		objectMapper = new ObjectMapper();

		objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
		//objectMapper.configure(SerializationFeature.WRITE_NULL_MAP_VALUES, false);

		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
	}

	public static <T> List<T> getObjectsFromJson(InputStream inputStream, Class<T> clsT) throws Exception {
		try {
			return objectMapper.readValue(inputStream, new TypeReference<ArrayList<T>>() {
			});
		} catch (JsonParseException e) {
			throw new Exception("parse json error", e);
		} finally {
			try {
				inputStream.close();
			} catch (Exception ignore) {
			}
		}
	}

	public static <T> List<T> getObjectsFromJson(String str, Class<T> clsT) throws Exception {
		try {
			return objectMapper.readValue(str, new TypeReference<ArrayList<T>>() {
			});
		} catch (JsonParseException e) {
			throw new Exception("parse json error, json=" + str + ", class=" + clsT.getName(), e);

		} catch (IOException e) {
			throw new Exception("parse json error, json=" + str + ", class=" + clsT.getName(), e);
		}
	}

	public static <T> T getObjectFromJson(String str, TypeReference<T> typeReference) throws Exception {
		try {
			return objectMapper.readValue(str, typeReference);
		} catch (JsonParseException e) {
			throw new Exception("parse json error, json=" + str + ", type=" + typeReference.getType(), e);

		} catch (IOException e) {
			throw new Exception("parse json error, json=" + str + ", type=" + typeReference.getType(), e);
		}
	}

	public static <T> T getObjectFromJson(InputStream inputStream, Class<T> cls) throws Exception {
		try {
			return objectMapper.readValue(inputStream, cls);
		} catch (JsonParseException e) {
			throw new Exception("parse json error", e);

		} catch (IOException e) {
			throw new Exception("parse json error", e);

		} finally {
			try {
				inputStream.close();
			} catch (Exception ignore) {
			}
		}
	}

	public static <T> T getObjectFromJson(String str, Class<T> cls) throws Exception {
		try {
			return objectMapper.readValue(str, cls);
		} catch (JsonParseException e) {
			throw new Exception("parse json error, json=" + str + ", class=" + cls.getName(), e);
		} catch (IOException e) {
			throw new Exception("parse json error, json=" + str + ", class=" + cls.getName(), e);
		}
	}

	@SuppressWarnings("unchecked")
	public static String getValueByFieldFromJson(String json, String field) throws Exception {
		Map<String, String> mapValue = getObjectFromJson(json, HashMap.class);
		return String.valueOf(mapValue.get(field));
	}

	public static String getJsonFromObject(Object object) {
		try {
			return objectMapper.writeValueAsString(object);
		} catch (Exception e) {
			log.error("get json error", e);
		}
		return "";
	}

	public static String getJsonWithoutNullFromObject(Object object) {
		try {
			ObjectMapper om = new ObjectMapper(); om.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
			om.configure(SerializationFeature.WRITE_NULL_MAP_VALUES, false);

			return om.writeValueAsString(object);
		} catch (Exception e) {
			log.error("get json error", e);
		}
		return "";
	}
}
