package com.cs.test.utils;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;

public class ThreeDESUtil {

	private static final String CryptAlgorithm = "DESede/CBC/PKCS5Padding";
	private static final String KeyAlgorithm = "DESede";
	private static final String Algorithm = "DESede";

	public static String encryptMode(String key, String src) {
		try {
			// 生成密钥
			byte[] keyByte = key.getBytes();
			SecretKey deskey = new SecretKeySpec(keyByte, KeyAlgorithm);

			// 加密
			Cipher c1 = Cipher.getInstance(Algorithm);
			c1.init(Cipher.ENCRYPT_MODE, deskey);
			byte[] encodeByte = c1.doFinal(src.getBytes());

			// base64转明文
			String codeMing = Base64.encode(encodeByte);

			return codeMing;
		} catch (java.security.NoSuchAlgorithmException e1) {
			e1.printStackTrace();
		} catch (javax.crypto.NoSuchPaddingException e2) {
			e2.printStackTrace();
		} catch (java.lang.Exception e3) {
			e3.printStackTrace();
		}
		return null;
	}

	public static String decryptMode(String key, String src) {
		try {
			// 生成密钥
			byte[] keyByte = key.getBytes();
			SecretKey deskey = new SecretKeySpec(keyByte, KeyAlgorithm);

			// base64转密文
			byte[] codeMi = Base64.decode(src);

			// 解密
			Cipher c1 = Cipher.getInstance(Algorithm);
			c1.init(Cipher.DECRYPT_MODE, deskey);
			byte[] decodeByte = c1.doFinal(codeMi);

			return new String(decodeByte);
		} catch (java.security.NoSuchAlgorithmException e1) {
			e1.printStackTrace();
		} catch (javax.crypto.NoSuchPaddingException e2) {
			e2.printStackTrace();
		} catch (java.lang.Exception e3) {
			e3.printStackTrace();
		}
		return null;
	}

	/*
	 * 根据字符串生成密钥字节数组
	 * 
	 * @param keyStr 密钥字符串
	 * 
	 * @return
	 * 
	 * @throws UnsupportedEncodingException
	 */
	public static byte[] generateKey(String keyStr) throws UnsupportedEncodingException {
		byte[] key = new byte[24]; // 声明一个24位的字节数组，默认里面都是0
		byte[] temp = keyStr.getBytes("UTF-8"); // 将字符串转成字节数组

		/*
		 * 执行数组拷贝 System.arraycopy(源数组，从源数组哪里开始拷贝，目标数组，拷贝多少位)
		 */
		if (key.length > temp.length) {
			// 如果temp不够24位，则拷贝temp数组整个长度的内容到key数组中
			System.arraycopy(temp, 0, key, 0, temp.length);
		} else {
			// 如果temp大于24位，则拷贝temp数组24个长度的内容到key数组中
			System.arraycopy(temp, 0, key, 0, key.length);
		}
		return key;
	}

	public static void main(String[] args) {
		// 添加新安全算法,如果用JCE就要把它添加进去
		// Security.addProvider(new com.sun.crypto.provider.SunJCE());

		String key = "123456781234567812345678";
		byte[] keyBytes = key.getBytes();

		// String szSrc = "<?xml version=\"1.0\"
		// encoding=\"utf-8\"?><body><lotteryId>1</lotteryId><issue></issue><records><record><id>taobao20130321000001</id><playType>FT001</playType><lotterySaleId>502</lotterySaleId><userName>userName</userName><phone></phone><idCard></idCard><code>20140321|5|001|3^20140321|5|002|3</code><money>10.00</money><timesCount>5</timesCount><issueCount>1</issueCount><investCount>1</investCount><investType>0</investType></record><record><id>taobao20130321000002</id><playType>FT001</playType><lotterySaleId>502</lotterySaleId><userName>userName</userName><phone></phone><idCard></idCard><code>20140321|5|001|3^20140321|5|003|3</code><money>20.00</money><timesCount>10</timesCount><issueCount>1</issueCount><investCount>1</investCount><investType>0</investType></record></records></body>";
		// String szSrc = "<?xml version=\"1.0\"
		// encoding=\"utf-8\"?><body><messageId>msg2013032100000001</messageId></body>";

		// String szSrc = "<?xml version=\"1.0\"
		// encoding=\"utf-8\"?><body><lotteryId>1</lotteryId><issue></issue><records><record><id>taobao20130321000021</id><playType>FT001</playType><lotterySaleId>502</lotterySaleId><userName>userName</userName><phone></phone><idCard></idCard><code>20140321|5|001|310^20140321|5|002|310</code><money>90.00</money><timesCount>5</timesCount><issueCount>1</issueCount><investCount>9</investCount><investType>0</investType></record><record><id>taobao20130321000022</id><playType>FT001</playType><lotterySaleId>502</lotterySaleId><userName>userName</userName><phone></phone><idCard></idCard><code>20140321|5|001|31^20140321|5|003|3</code><money>40.00</money><timesCount>10</timesCount><issueCount>1</issueCount><investCount>2</investCount><investType>0</investType></record></records></body>";
		String szSrc = "<?xml version=\"1.0\" encoding=\"utf-8\"?><body><lotteryId>1</lotteryId><issue></issue><records><record><id>taobao139631800136</id><playType>FT001</playType><lotterySaleId>500</lotterySaleId><userName>userName</userName><phone></phone><idCard></idCard><code>20140401|2|003|0^</code><money>2.00</money><timesCount>1</timesCount><issueCount>1</issueCount><investCount>1</investCount><investType>0</investType></record><record><id>taobao139631800137</id><playType>FT001</playType><lotterySaleId>508</lotterySaleId><userName>userName</userName><phone></phone><idCard></idCard><code>20140401|2|0001|0^20140401|2|0002|1^20140401|2|0003|3^20140401|2|0004|3^20140401|2|0005|1^20140401|2|0006|0^20140401|2|0007|1^20140401|2|0008|1^</code><money>2.00</money><timesCount>1</timesCount><issueCount>1</issueCount><investCount>1</investCount><investType>0</investType></record></records></body>";

		System.out.println("加密前的字符串:" + szSrc);

		String encodeStr = encryptMode(key, szSrc);
		System.out.println("加密后的字符串:" + encodeStr);

		String decodeStr = decryptMode(key, encodeStr);
		System.out.println("解密后的字符串:" + decodeStr);
	}

}
