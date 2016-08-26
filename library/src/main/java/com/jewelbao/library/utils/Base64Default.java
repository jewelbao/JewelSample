package com.jewelbao.library.utils;

/**
 * Created by Jewel on 2016/6/14 0014.
 * 系统API的base64封装
 */
@SuppressWarnings("unused")
public class Base64Default {
	/**
	 * base64编码
	 *
	 * @param input input
	 * @return byte[]
	 */
	public static byte[] encodeBase64(byte[] input) {
		return android.util.Base64.encode(input, android.util.Base64.DEFAULT);
	}

	/**
	 * base64编码
	 *
	 * @param s input
	 * @return String
	 */
	public static String encodeBase64(String s) {
		return android.util.Base64.encodeToString(s.getBytes(), android.util.Base64.DEFAULT);
	}

	/**
	 * base64解码
	 *
	 * @param input input
	 * @return byte[]
	 */
	public static byte[] decodeBase64(byte[] input) {
		return android.util.Base64.decode(input, android.util.Base64.DEFAULT);
	}

	/**
	 * base64解码
	 *
	 * @param s input
	 * @return String d
	 */
	public static String decodeBase64(String s) {
		return new String(android.util.Base64.decode(s, android.util.Base64.DEFAULT));
	}

}
