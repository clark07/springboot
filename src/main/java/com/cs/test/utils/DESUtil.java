package com.cs.test.utils;

import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import java.security.SecureRandom;

public class DESUtil {
	
	public static final String CodingType = "UTF-8";

	public static String encrypt(String strTobeEnCrypted, String password) {
		try {
			byte datasource[] = strTobeEnCrypted.getBytes(CodingType);
			SecureRandom random = new SecureRandom();
			DESKeySpec desKey = new DESKeySpec(password.getBytes());
			// 创建一个密匙工厂，然后用它把DESKeySpec转换成
			SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
			SecretKey securekey = keyFactory.generateSecret(desKey);
			// Cipher对象实际完成加密操作
			Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
			// 用密匙初始化Cipher对象
			cipher.init(Cipher.ENCRYPT_MODE, securekey, random);
			// 现在，获取数据并加密
			// 正式执行加密操作
			byte output[] = cipher.doFinal(datasource);
			return new String(Base64.encodeBase64(output), CodingType);
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String decrypt(String strTobeDeCrypted, String password) throws Exception {
		byte src[] = Base64.decodeBase64(strTobeDeCrypted.getBytes(CodingType));
		// DES算法要求有一个可信任的随机数源
		SecureRandom random = new SecureRandom();
		// 创建一个DESKeySpec对象
		DESKeySpec desKey = new DESKeySpec(password.getBytes());
		// 创建一个密匙工厂
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
		// 将DESKeySpec对象转换成SecretKey对象
		SecretKey securekey = keyFactory.generateSecret(desKey);
		// Cipher对象实际完成解密操作
		Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
		// 用密匙初始化Cipher对象
		cipher.init(Cipher.DECRYPT_MODE, securekey, random);
		// 真正开始解密操作
		byte[] output = cipher.doFinal(src);
		return new String(output, CodingType);
	}
	
	public static void main(String[] args) throws Exception {
		String ctn = "vLkU8e536FBLtzwOfmLEu7itxiJlGx9IhC\\/tifSLnsLxwXGiHIY7ctrQa8KDOfDo3TGtxyobD54JoPiNAlP\\/CZTlkBwNfyNougNg1W\\/AoXRSz9TWMbszma1c8EnaYzz2e1E\\/5SkAJ6XizQ9XJBmq2WeoOo9s+o2AuVWQ0gNooEKxNqHkWjRmw1MbUt0xoxxWa29gcha\\/NeaP20iUeD6kcU9c6CDzEtYb0pmZ706PgZN7zNw7ruVdsw==";
		System.out.println(decrypt(ctn,"039425e1"));
	}
}
