package com.udacity.jwdnd.course1.cloudstorage.common;

import org.springframework.web.multipart.MultipartFile;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

public class CommonUtil {

	public static String getFileName(MultipartFile files) {
		String fileName = files.getOriginalFilename();
		int startIndex = fileName.replaceAll("\\\\", "/").lastIndexOf("/");
		return fileName.substring(startIndex + 1);
	}

	public static String keyGen() {
		SecureRandom random = new SecureRandom();
		byte[] key = new byte[16];
		random.nextBytes(key);
		return Base64.getEncoder().encodeToString(key);
	}

	public static String encryptString(String data, String key) {
		byte[] str = null;

		try {
			Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");

			SecretKey secretKey = new SecretKeySpec(key.getBytes(), "AES");

			cipher.init(Cipher.ENCRYPT_MODE, secretKey);

			str = cipher.doFinal(data.getBytes("UTF-8"));
		} catch (Exception e) {
		}

		return Base64.getEncoder().encodeToString(str);
	}

	public static String decryptString(String data, String key) {
		byte[] decrypStr = null;

		try {
			Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
			SecretKey secretKey = new SecretKeySpec(key.getBytes(), "AES");
			cipher.init(Cipher.DECRYPT_MODE, secretKey);
			decrypStr = cipher.doFinal(Base64.getDecoder().decode(data));
		} catch (Exception e) {
		}

		return new String(decrypStr);
	}
}
