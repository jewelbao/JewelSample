package com.jewelbao.library.utils;


import android.text.TextUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 String Utils

 @author <a href="http://www.trinea.cn" target="_blank">Trinea</a> 2011-7-22 */
public class StringUtils
{

	private StringUtils()
	{
		throw new AssertionError();
	}


	/**
	 is null or its length is 0 or it is made by space
	 <pre>
	 isBlank(null) = true;
	 isBlank(&quot;&quot;) = true;
	 isBlank(&quot;  &quot;) = true;
	 isBlank(&quot;a&quot;) = false;
	 isBlank(&quot;a &quot;) = false;
	 isBlank(&quot; a&quot;) = false;
	 isBlank(&quot;a b&quot;) = false;
	 </pre>

	 @param str str

	 @return if string is null or its size is 0 or it is made by space, return true, else return
	 false.
	 */
	public static boolean isBlank(String str)
	{

		return (str == null || str.trim().length() == 0);
	}

	/**
	 * Judge whether a string is whitespace, empty ("") or null.
	 *
	 * @param str
	 * @return
	 */
	public static boolean isEmpty(String str) {
		int strLen;
		if (str == null || (strLen = str.length()) == 0 || str.equalsIgnoreCase("null")) {
			return true;
		}
		for (int i = 0; i < strLen; i++) {
			if ((Character.isWhitespace(str.charAt(i)) == false)) {
				return false;
			}
		}
		return true;
	}

	/**
	 get length of CharSequence
	 <pre>
	 length(null) = 0;
	 length(\"\") = 0;
	 length(\"abc\") = 3;
	 </pre>

	 @param str str

	 @return if str is null or empty, return 0, else return {@link CharSequence#length()}.
	 */
	public static int length(CharSequence str)
	{

		return str == null ?
				0 :
				str.length();
	}

	/**
	 * Returns true if a and b are equal, including if they are both null.
	 *
	 * @param a
	 * @param b
	 * @return
	 */
	public static boolean equals(CharSequence a, CharSequence b) {
		return TextUtils.equals(a, b);
	}

	/**
	 * Judge whether a string is number.
	 *
	 * @param str
	 * @return
	 */
	public static boolean isNumeric(String str) {
		for (int i = str.length(); --i >= 0; ) {
			if (!Character.isDigit(str.charAt(i))) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Encode a string
	 *
	 * @param str
	 * @return
	 */
	public static String encodeString(String str) {
		try {
			return URLEncoder.encode(str, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return str;
		}
	}

	/**
	 * Decode a string
	 *
	 * @param str
	 * @return
	 */
	public static String decodeString(String str) {
		try {
			return URLDecoder.decode(str, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return str;
		}
	}

	/**
	 * Converts this string to lower case, using the rules of {@code locale}.
	 *
	 * @param s
	 * @return
	 */
	public static String toLowerCase(String s) {
		return s.toLowerCase(Locale.getDefault());
	}

	/**
	 * Converts this this string to upper case, using the rules of {@code locale}.
	 *
	 * @param s
	 * @return
	 */
	public static String toUpperCase(String s) {
		return s.toUpperCase(Locale.getDefault());
	}


	/**
	 null Object to empty string
	 <pre>
	 nullStrToEmpty(null) = &quot;&quot;;
	 nullStrToEmpty(&quot;&quot;) = &quot;&quot;;
	 nullStrToEmpty(&quot;aa&quot;) = &quot;aa&quot;;
	 </pre>

	 @param str str

	 @return String
	 */
	public static String nullStrToEmpty(Object str)
	{

		return (str == null ?
				"" :
				(str instanceof String ?
						(String) str :
						str.toString()));
	}


	/**
	 @param str str

	 @return String
	 */
	public static String capitalizeFirstLetter(String str)
	{

		if(isEmpty(str))
		{
			return str;
		}

		char c = str.charAt(0);
		return (!Character.isLetter(c) || Character.isUpperCase(c)) ?
				str :
				new StringBuilder(str.length()).append(Character.toUpperCase(c))
											   .append(str.substring(1)).toString();
	}

	/**
	 @param href 字符串

	 @return 返回一个html
	 */
	public static String getHrefInnerHtml(String href)
	{

		if(isEmpty(href))
		{
			return "";
		}

		String hrefReg = ".*<[\\s]*a[\\s]*.*>(.+?)<[\\s]*/a[\\s]*>.*";
		Pattern hrefPattern = Pattern.compile(hrefReg, Pattern.CASE_INSENSITIVE);
		Matcher hrefMatcher = hrefPattern.matcher(href);
		if(hrefMatcher.matches())
		{
			return hrefMatcher.group(1);
		}
		return href;
	}


	/**
	 @param source 字符串

	 @return 返回htmL到字符串
	 */
	public static String htmlEscapeCharsToString(String source)
	{

		return StringUtils.isEmpty(source) ?
				source :
				source.replaceAll("&lt;", "<").replaceAll("&gt;", ">").replaceAll("&amp;", "&")
					  .replaceAll("&quot;", "\"");
	}


	/**
	 @param s str

	 @return String
	 */
	public static String fullWidthToHalfWidth(String s)
	{

		if(isEmpty(s))
		{
			return s;
		}

		char[] source = s.toCharArray();
		for(int i = 0; i < source.length; i++)
		{
			if(source[i] == 12288)
			{
				source[i] = ' ';
				// } else if (source[i] == 12290) {
				// source[i] = '.';
			} else if(source[i] >= 65281 && source[i] <= 65374)
			{
				source[i] = (char) (source[i] - 65248);
			} else
			{
				source[i] = source[i];
			}
		}
		return new String(source);
	}


	/**
	 @param s 字符串

	 @return 返回的数值
	 */
	public static String halfWidthToFullWidth(String s)
	{

		if(isEmpty(s))
		{
			return s;
		}

		char[] source = s.toCharArray();
		for(int i = 0; i < source.length; i++)
		{
			if(source[i] == ' ')
			{
				source[i] = (char) 12288;
				// } else if (source[i] == '.') {
				// source[i] = (char)12290;
			} else if(source[i] >= 33 && source[i] <= 126)
			{
				source[i] = (char) (source[i] + 65248);
			} else
			{
				source[i] = source[i];
			}
		}
		return new String(source);
	}


	/**
	 @param str 资源

	 @return 特殊字符串切换
	 */

	public static String replaceBlanktihuan(String str)
	{

		String dest = "";
		if(str != null)
		{
			Pattern p = Pattern.compile("\\s*|\t|\r|\n");
			Matcher m = p.matcher(str);
			dest = m.replaceAll("");
		}
		return dest;
	}
}

