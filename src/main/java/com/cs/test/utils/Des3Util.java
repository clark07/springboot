package com.cs.test.utils;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;

import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.IvParameterSpec;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.Key;
import java.security.MessageDigest;

public class Des3Util {

	public static byte[] Base64Encode(byte b[]) throws Exception {
		return Base64.encodeBase64(b);
	}

	public static byte[] Base64Decode(byte b[]) throws Exception {
		return Base64.decodeBase64(b);
	}

	public static String URLEncode(String strToBeEncode) throws Exception {
		return URLEncoder.encode(strToBeEncode, CodingType);
	}

	public static String URLDecode(String strToBeDecode) throws Exception {
		return URLDecoder.decode(strToBeDecode, CodingType);
	}

	private static IvParameterSpec IvGenerator(byte b[]) throws Exception {
		IvParameterSpec IV = new IvParameterSpec(b);
		return IV;
	}

	private static Key KeyGenerator(String KeyStr) throws Exception {
		//byte input[] = Hex.decodeHex(KeyStr.toCharArray());
		DESedeKeySpec KeySpec = new DESedeKeySpec(KeyStr.getBytes());
		SecretKeyFactory KeyFactory = SecretKeyFactory
				.getInstance(KeyAlgorithm);
		return KeyFactory.generateSecret(KeySpec);
	}

	public static byte[] IVGenerator(String strIV) throws Exception {
		return Hex.decodeHex(strIV.toCharArray());
	}

	public static String GenerateDigest(String strTobeDigest) throws Exception {
		byte input[] = strTobeDigest.getBytes(CodingType);
		byte output[] = (byte[]) null;
		MessageDigest DigestGenerator = MessageDigest
				.getInstance(DigestAlgorithm);
		DigestGenerator.update(input);
		output = DigestGenerator.digest();
		return new String(Base64Encode(output), CodingType);
	}

	public static String Encrypt(String strTobeEnCrypted, String strKey,
								 byte byteIV[]) throws Exception {
		byte input[] = strTobeEnCrypted.getBytes(CodingType);
		Key k = KeyGenerator(strKey);
		IvParameterSpec IVSpec = byteIV.length != 0 ? IvGenerator(byteIV)
				: IvGenerator(defaultIV);
		Cipher c = Cipher.getInstance(CryptAlgorithm);
		c.init(1, k, IVSpec);
		byte output[] = c.doFinal(input);
		return new String(Base64Encode(output), CodingType);
	}

	public static String Decrypt(String strTobeDeCrypted, String strKey,
								 byte byteIV[]) throws Exception {
		byte input[] = Base64Decode(strTobeDeCrypted.getBytes(CodingType));
		Key k = KeyGenerator(strKey);
		IvParameterSpec IVSpec = byteIV.length != 0 ? IvGenerator(byteIV)
				: IvGenerator(defaultIV);
		Cipher c = Cipher.getInstance(CryptAlgorithm);
		c.init(2, k, IVSpec);
		byte output[] = c.doFinal(input);
		return new String(output, CodingType);
	}

	public static byte[] HexStringToByteArray(String s) {
		byte buf[] = new byte[s.length() / 2];
		for (int i = 0; i < buf.length; i++)
			buf[i] = (byte) (chr2hex(s.substring(i * 2, i * 2 + 1)) * 16 + chr2hex(s
					.substring(i * 2 + 1, i * 2 + 1 + 1)));

		return buf;
	}

	private static byte chr2hex(String chr) {
		if ("0".equals(chr))
			return 0;
		if ("1".equals(chr))
			return 1;
		if ("2".equals(chr))
			return 2;
		if ("3".equals(chr))
			return 3;
		if ("4".equals(chr))
			return 4;
		if ("5".equals(chr))
			return 5;
		if ("6".equals(chr))
			return 6;
		if ("7".equals(chr))
			return 7;
		if ("8".equals(chr))
			return 8;
		if ("9".equals(chr))
			return 9;
		if ("A".equals(chr))
			return 10;
		if ("B".equals(chr))
			return 11;
		if ("C".equals(chr))
			return 12;
		if ("D".equals(chr))
			return 13;
		if ("E".equals(chr))
			return 14;
		return ((byte) (!"F".equals(chr) ? 0 : 15));
	}

	private static String CodingType = "UTF-8";
	private static String DigestAlgorithm = "SHA1";
	private static String CryptAlgorithm = "DESede/CBC/PKCS5Padding";
	private static String KeyAlgorithm = "DESede";
	private static byte defaultIV[] = "12345678".getBytes();

	public static void main(String[] args)
	{String key=";";
	String[] items=key.split(";");
	key=key.trim();//;"123456789123456789123456"
		try {
			key=Encrypt("<?xml version=\"1.0\" encoding=\"utf-8\"?><body><lotteryId>1</lotteryId><issue></issue><records><record><id>record20150908000000000000001</id><channel>shop001</channel><playType>FT002</playType><lotterySaleId>502</lotterySaleId><userName>userName</userName><phone></phone><idCard></idCard><code>20150908|2|021|00^20150908|2|022|00</code><money>200.00</money><timesCount>1</timesCount><issueCount>1</issueCount><investCount>1</investCount><investType>0</investType></record><record><id>record20150908000000000000002</id><playType>FT001</playType><lotterySaleId>502</lotterySaleId><userName>userName</userName><phone></phone><idCard></idCard><code>20150908|2|021|3^20150908|2|022|1</code><money>200.00</money><timesCount>1</timesCount><issueCount>1</issueCount><investCount>1</investCount><investType>0</investType></record></records></body>","123456789123456789123456", new byte[]{});
		
		System.out.println(key);
		String md5 =MD5Util.getMD5Str(key);
		System.out.println(md5);
		String str=Decrypt("EH968iR+lq5qYvT17bgXgV4J2uAnibWLg4WOISA2nQ9JOfIyv8hZEdizuIGTbjpfyK/9uzuUPB0PW/eAUl6MeSrueS+WYpLevJQNju3dPzLW2klFY0KkIgm+QiqWsB/kDhrjL6AKk0ZhrM750tpOxKSc/4k0rRSm5T85Orwdciij8vS4fzMfZ5Ol9CANscAM9kkEbc2vS4dI66GeEWw/FmyALTdgKuXZCGmEhZbnVHgRu4NBolHuIevHvVPXWNNQaDulv8ZLIxQ0BrFjzq3YespHI0FK1H4M7yAOOw98xzolX2E5adbTetkZD4vLvqOcDaap7F3YaTQHC96L7IorEwTDTSH1E88VsF2r/QYvrWfr1ImtUKHZLnzyWkFrbs3gN1Fwq4kZBqAnJwvAU2C10uIxmNihumr4w4B1M+H8V6/aVFTwpUHUfL2d8Mvt8ncwhwiAqsQnb7CmQuqu50AnPWI6y5FKvSL/cfKBk76iVhTh9wnvVHMniv/IzVsZdqSqhVasm8E34/scsrW0r65LUsH4nloVCzxdcNmjX/g3Ij7u4MUCVidlDFfUkCF3cefeaClSh4+CuFOfuOnrut2k46dro91Zu7tvOmg+70Oyd29tztx9bA4CjTSMMszs1e5f6PabEsP+SfUcdQFb+hfKXcd4uf+rEBj/othyOnPNIA7MKXbYVGzyTCFYyu5gHNIYDZb2Pxd2s1RfHsOy/jAHtyBCDmqqng4aSiDKSDJF5YgVv30hcRsP7s1GmMzF+M85f9mVx8Tl6/9qvLHSnNFroyPi0Z/jgxwA+w1r9rwg3tTNsm5c7EunF5VgqKtHZQpyKc6OhLp5cx/PICMpUteJ8Qjy6A7LLY1AhVv0LKjbG/LOi4tdovuP4ASomMkzNYBg/aOqwGpHmrwH8Vhd/O/b7IsKWQK9B/jE3QV/QH51jEMLRh2Hqeg8UUE4bYeRhu1UtE/QVImX0K/xQpO5tV5ZXyx9u7xfy8By333847RDwtzRmx/GvvOrgt+hD/xR7iEv3LK3mfqScfcLs1/Cfg0dUIwvEpU/6U5c1Ea5/Yj8o4mvxMLTSXSC5Zd0e0T4VgnY2kt6/9ajrVdMyo3c/qFDLPlK1tkP8e9W", "123456789123456789123456", new byte[]{});
		System.out.println(str);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
