package org.secbug.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.security.MessageDigest;

public class Md5Utils {

	/** 16进制的字符数组 */
	private final static String[] hexDigits = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d",
			"e", "f" };

	/**
	 * 
	 * 
	 * @param source
	 *            需要加密的原字符串
	 * @param encoding
	 *            指定编码类型
	 * @param uppercase
	 *            是否转为大写字符串
	 * @return
	 */
	public static String MD5Encode(String source, String encoding, boolean uppercase) {
		String result = null;
		try {
			result = source;
			// 获得MD5摘要对象
			MessageDigest messageDigest = MessageDigest.getInstance("MD5");
			// 使用指定的字节数组更新摘要信息
			messageDigest.update(result.getBytes(encoding));
			// messageDigest.digest()获得16位长度
			result = byteArrayToHexString(messageDigest.digest());

		} catch (Exception e) {
			e.printStackTrace();
		}
		return uppercase ? result.toUpperCase() : result;
	}

	/**
	 * 转换字节数组为16进制字符串
	 * 
	 * @param bytes
	 *            字节数组
	 * @return
	 */
	private static String byteArrayToHexString(byte[] bytes) {
		StringBuilder stringBuilder = new StringBuilder();
		for (byte tem : bytes) {
			stringBuilder.append(byteToHexString(tem));
		}
		return stringBuilder.toString();
	}

	/**
	 * 转换byte到16进制
	 * 
	 * @param b
	 *            要转换的byte
	 * @return 16进制对应的字符
	 */
	private static String byteToHexString(byte b) {
		int n = b;
		if (n < 0) {
			n = 256 + n;
		}
		int d1 = n / 16;
		int d2 = n % 16;
		return hexDigits[d1] + hexDigits[d2];
	}

	/**
	 * 文件计算md5值
	 * 
	 * @return value
	 */
	public static String getMd5ByFile(File file) throws FileNotFoundException {
		String value = null;
		FileInputStream in = null;
		try {
			in = new FileInputStream(file);
			MappedByteBuffer byteBuffer = in.getChannel().map(FileChannel.MapMode.READ_ONLY, 0, file.length());
			MessageDigest md5 = MessageDigest.getInstance("MD5");
			md5.update(byteBuffer);
			BigInteger bi = new BigInteger(1, md5.digest());
			value = bi.toString(16);
		} catch (Exception e) {
			// e.printStackTrace();
		} finally {
			if (null != in) {
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return value;
	}

}
