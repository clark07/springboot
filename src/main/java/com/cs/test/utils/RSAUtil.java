package com.cs.test.utils;

import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.security.*;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

public class RSAUtil {

	private static String KEY_ALGORITHM = "RSA";
	private static String SIGNATURE_ALGORITHM = "MD5withRSA";
	private static Integer KEY_SIZE = 1024;
	private static String CodingType = "UTF-8";
	private static final String RSAPUBLIC_KEY = "RSAPublicKey";
	private static final String RSAPRIVATE_KEY = "RSAPrivateKey";

	private static final int MAX_ENCRYPT_BLOCK = 117;
	private static final int MAX_DECRYPT_BLOCK = 128;

	public static Map<String, Object> initRSAKey() {
		Map<String, Object> keyMap = new HashMap<String, Object>(2);
		try {
			KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance(KEY_ALGORITHM);
			keyPairGen.initialize(KEY_SIZE);
			KeyPair keyPair = keyPairGen.generateKeyPair();
			// 公钥
			RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
			// 私钥
			RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
			keyMap.put(RSAPUBLIC_KEY, publicKey);
			keyMap.put(RSAPRIVATE_KEY, privateKey);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return keyMap;
	}

	/**
	 * 私钥加密
	 * 
	 * @param data
	 *            要加密的数据
	 * @param key
	 *            私钥
	 * @return 返回加密后的数据
	 */
	public static String RSAEncryptByPrivateKey(String strToBeEncrypt, String key) throws Exception {
		// 取得私钥
		byte[] keyBytes = Base64.decodeBase64(key);
		PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
		KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
		PrivateKey privateKey = keyFactory.generatePrivate(pkcs8KeySpec);
		// 对数据加密
		Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
		cipher.init(Cipher.ENCRYPT_MODE, privateKey);
		byte[] data = strToBeEncrypt.getBytes(CodingType);
		int inputLen = data.length;
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		int offSet = 0;
		byte[] cache;
		int i = 0;
		// 对数据分段加密
		while (inputLen - offSet > 0) {
			if (inputLen - offSet > MAX_ENCRYPT_BLOCK) {
				cache = cipher.doFinal(data, offSet, MAX_ENCRYPT_BLOCK);
			} else {
				cache = cipher.doFinal(data, offSet, inputLen - offSet);
			}
			out.write(cache, 0, cache.length);
			i++;
			offSet = i * MAX_ENCRYPT_BLOCK;
		}
		byte[] encryptedData = out.toByteArray();
		out.close();
		return new String(Base64.encodeBase64(encryptedData), CodingType);
	}

	/**
	 * 公钥解密
	 * 
	 * @param data
	 *            要解密的数据
	 * @param key
	 *            公钥
	 * @return 返回解密后的数据
	 */
	public static String RSADecryptByPublicKey(String encryptedStr, String key) throws Exception {
		// 取得公钥
		byte[] keyBytes = Base64.decodeBase64(key);
		X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
		KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
		PublicKey publicKey = keyFactory.generatePublic(x509KeySpec);
		// 对数据解密
		Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
		cipher.init(Cipher.DECRYPT_MODE, publicKey);
		byte[] encryptedData = Base64.decodeBase64(encryptedStr);
		int inputLen = encryptedData.length;
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		int offSet = 0;
		byte[] cache;
		int i = 0;
		// 对数据分段解密
		while (inputLen - offSet > 0) {
			if (inputLen - offSet > MAX_DECRYPT_BLOCK) {
				cache = cipher.doFinal(encryptedData, offSet, MAX_DECRYPT_BLOCK);
			} else {
				cache = cipher.doFinal(encryptedData, offSet, inputLen - offSet);
			}
			out.write(cache, 0, cache.length);
			i++;
			offSet = i * MAX_DECRYPT_BLOCK;
		}
		byte[] decryptedData = out.toByteArray();
		out.close();
		return new String(decryptedData, CodingType);
	}

	/**
	 * 数字签名
	 * 
	 * @param data
	 *            要签名的密文
	 * @param privateKey
	 *            私钥
	 * @return 返回签名信息
	 */
	public static String RSASign(String data, String privateKey) throws Exception {
		// 解密由base64编码的私钥
		byte[] keyBytes = Base64.decodeBase64(privateKey);
		// 构造PKCS8EncodedKeySpec对象
		PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
		// KEY_ALGORITHM 指定的加密算法
		KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
		// 取私钥匙对象
		PrivateKey priKey = keyFactory.generatePrivate(pkcs8KeySpec);
		// 用私钥对信息生成数字签名
		Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
		signature.initSign(priKey);
		signature.update(data.getBytes());
		return new String(Base64.encodeBase64(signature.sign()), CodingType);
	}

	/**
	 * 验证签名
	 * 
	 * @param data
	 *            要验证的密文
	 * @param publicKey
	 *            公钥
	 * @param sign
	 *            签名信息
	 * @return 返回验证成功状态
	 */
	public static boolean RSAVerify(String data, String publicKey, String sign) throws Exception {
		// 解密由base64编码的公钥
		byte[] keyBytes = Base64.decodeBase64(publicKey);
		// 构造X509EncodedKeySpec对象
		X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
		// KEY_ALGORITHM 指定的加密算法
		Signature signature;
		KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
		// 取公钥匙对象
		PublicKey pubKey = keyFactory.generatePublic(keySpec);
		signature = Signature.getInstance(SIGNATURE_ALGORITHM);
		signature.initVerify(pubKey);
		signature.update(data.getBytes());
		// 验证签名是否正常
		return signature.verify(Base64.decodeBase64(sign));
	}

	/**
	 * 
	 * @param filePath
	 *            公钥文件路径
	 * @return 公钥Base64编码字符串
	 * @throws Exception
	 */
	public static String getPubKeyStrFromFile(String filePath) throws Exception {
		CertificateFactory certificatefactory = CertificateFactory.getInstance("X.509");
		FileInputStream fin = new FileInputStream(filePath);// 公钥路径
		X509Certificate certificate = (X509Certificate) certificatefactory.generateCertificate(fin);
		PublicKey pub = certificate.getPublicKey();
		return new String(Base64.encodeBase64(pub.getEncoded()));
	}

	/**
	 * 
	 * @param filePath
	 *            私钥文件路径
	 * @param keyPassword
	 *            keystore密码
	 * @param alias
	 *            表示使用这对公私密钥产生新的keystore入口的别名
	 * @return
	 * @throws Exception
	 */
	public static String getPrivKeyStrFromFile(String filePath, String keyPassword, String alias) throws Exception {
		char[] kpass;
		KeyStore ks = KeyStore.getInstance("JKS");
		FileInputStream ksfis = new FileInputStream(filePath);
		BufferedInputStream ksbufin = new BufferedInputStream(ksfis);
		kpass = new char[keyPassword.length()];
		for (int i = 0; i < keyPassword.length(); i++)
			kpass[i] = keyPassword.charAt(i);
		ks.load(ksbufin, kpass);
		PrivateKey priv = (PrivateKey) ks.getKey(alias, kpass);
		return new String(Base64.encodeBase64(priv.getEncoded()));
	}

	public static void main(String[] args) throws Exception {

		// String priv =
		// getPrivKeyStrFromFile("d://14_private","123456","zhangzhongcai");
		String pub = getPubKeyStrFromFile("C:\\Users\\thinkpad\\Desktop\\14_public.public1.public");
		// System.out.println("private key:\n"+priv);
		System.out.println("public key:\n" + pub);

		/*
		 * String str =
		 * "000<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<msg><head transcode=\"000\" partnerid=\"14\" version=\"1.0\" time=\"2011041101802169\"/><body><msg errorCode=\"9015\" msg=\"订单不存在\"/></body></msg>"
		 * ; String key =
		 * "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAKZQzhTjXSEvr+tXibEXX+jViwZLyGIb9RgWBGLz+OlWhwM0kgA+iJcy+JXG+kMQdlCBJ73cJmlEBSF5/2edg3J5znbLV7MC3uXzOS5y5QbKyLI3qk7orPoOdRYM2/f42f9JD9OH4AgIK1o1JIo3y16cyYkP+kzedaHLlyR8obXrAgMBAAECgYEAh7u+1NFSOjlfC2Tf3QXEopNU6QiEO32xo7ykS9XtORIDEhyfY0+lzjAtLJ+9s2oGBWHC+3JmbtaykUWtqfSlRz2zXmSM1h0nvZOHLUR9Hy7hTWY4ZAgsp8UqJDdK0Vq+VZMBuPVvXGknIPlFh9B2bkIBrX76lBhOSW11eUgWwnECQQDu2sbbVFNgESBgjueqYTi15eeyTMK5jY3UXPIgl2zi78uvB6pqhGNMxN/dqRJbIwSJSSIIBVwikwUNSk35hMJNAkEAskELAIeT2sGe4kvFoaENBXs0ZWwzuJKIlPLAsjIG3ITfxJ05q3O9C0HS5Vr5PsaMyiqMV4iQV9v60IMyemvFFwJAUF7FMt/4/gZFQrhTM31rO6mUgOZbT1xWMPLldhn9xY6ylr6ZlSXe0+IbAdb2Gjx6Nkepb9F94xRdSs+J1T/asQJALRUUMkMX2ujDRVqClllEENHTM5+FCTZOQtWOdMMARz931KMrFbjE5Bb2vrkedokCzJKsQesxDnd9XMUBvEKLawJBAKvCXEPc5aTR2Z0Ka7qr7sMgLDC7qikHLannWgyUoxQDeUYsa+xCpnRG+tELNiCcn22Ay3yIJcI/FMBYY2a/MY4=";
		 * String result = RSASign(str, key); System.out.println(result);
		 * 
		 * String key2 =
		 * "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCmUM4U410hL6/rV4mxF1/o1YsGS8hiG/UYFgRi8/jpVocDNJIAPoiXMviVxvpDEHZQgSe93CZpRAUhef9nnYNyec52y1ezAt7l8zkucuUGysiyN6pO6Kz6DnUWDNv3+Nn/SQ/Th+AICCtaNSSKN8tenMmJD/pM3nWhy5ckfKG16wIDAQAB";
		 * boolean v = RSAVerify(str, key2, result); System.out.println(v);
		 */

		/*
		 * String str =
		 * "<?xml version=\"1.0\" encoding=\"utf-8\"?> <body> <messageId>11224780_1172110105178021_0_21</messageId> </body>"
		 * ; Map<String, Object> keys = initRSAKey(); PrivateKey k =
		 * (PrivateKey) (keys.get(RSAPRIVATE_KEY)); PublicKey k2 = (PublicKey)
		 * (keys.get(RSAPUBLIC_KEY)); String key = new
		 * String(Base64.encodeBase64(k.getEncoded())); String key2 = new
		 * String(Base64.encodeBase64(k2.getEncoded()));
		 * System.out.println("私钥:\n" + key); System.out.println("公钥:\n" +
		 * key2); System.out.println("加密字符串:\n" + str); String result =
		 * RSAEncryptByPrivateKey(str, key); System.out.println("加密后的字符串:\n" +
		 * result); result = RSADecryptByPublicKey(result, key2);
		 * System.out.println("解密后的字符串:\n" + result);
		 */
	}

}
