package com.jewelbao.library.utils;

import android.text.TextUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by Jewel on 2016/6/14 0014.
 * 数组工具类
 */
public class ListUtils {

	private static final String DELIMITER = ",";

	/**
	 * Judge whether a array is null.
	 *
	 * @param array
	 * @return
	 */
	public static <T> boolean isEmpty(T[] array) {
		return (array == null || array.length == 0);
	}

	/**
	 * 遍历数组
	 *
	 * @param array
	 * @param <T>
	 * @return
	 */
	public static <T> String traverseArray(T[] array) {
		if (!isEmpty(array)) {
			int len = array.length;
			StringBuilder builder = new StringBuilder(len);
			int i = 0;
			for (T t : array) {
				if (t == null) {
					continue;
				}
				builder.append(t.toString());
				i++;
				if (i < len) {
					builder.append(DELIMITER);
				}
			}
			return builder.toString();
		}
		return null;
	}


	/**
	 * Judge whether a map is null or size is 0
	 *
	 * @param map
	 * @param <K>
	 * @param <V>
	 * @return
	 */
	public static <K, V> boolean isEmpty(Map<K, V> map) {
		return (map == null || map.size() == 0);
	}

	/**
	 * Map遍历
	 *
	 * @param map
	 * @param <K>
	 * @param <V>
	 * @return
	 */
	public static <K, V> String traverseMap(Map<K, V> map) {
		if (!isEmpty(map)) {
			int len = map.size();
			StringBuilder builder = new StringBuilder(len);
			int i = 0;
			for (Map.Entry<K, V> entry : map.entrySet()) {
				if (entry == null) {
					continue;
				}
				builder.append(entry.getKey().toString() + ":" + entry.getValue().toString());
				i++;
				if (i < len) {
					builder.append(DELIMITER);
				}
			}
			return builder.toString();
		}
		return null;
	}

	/**
	 * 根据值获取键
	 *
	 * @param map
	 * @param value
	 * @param <K>
	 * @param <V>
	 * @return
	 */
	public static <K, V> K getKeyByValue(Map<K, V> map, V value) {
		if (isEmpty(map)) {
			return null;
		}
		for (Map.Entry<K, V> entry : map.entrySet()) {
			if (equals(entry.getValue(), value)) {
				return entry.getKey();
			}
		}
		return null;
	}

	/**
	 * Returns true if a and b are equal.
	 *
	 * @param a
	 * @param b
	 * @return
	 */
	public static boolean equals(Object a, Object b) {
		return a == b || (a == null ? b == null : a.equals(b));
	}


	/**
	 * Judge whether a collection is null or size is 0
	 *
	 * @param c
	 * @param <V>
	 * @return
	 */
	public static <V> boolean isEmpty(Collection<V> c) {
		return (c == null || c.size() == 0);
	}

	/**
	 * Join collection to string, separator is {@link #DELIMITER}
	 *
	 * @param tokens
	 * @return
	 */
	public static String join(Iterable tokens) {
		return tokens == null ? "" : TextUtils.join(DELIMITER, tokens);
	}

	/**
	 * Convert array to list
	 *
	 * @param array
	 * @param <T>
	 * @return
	 */
	public static <T> List<T> arrayToList(T... array) {
		return Arrays.asList(array);
	}

	/**
	 * Convert array to set
	 *
	 * @param array
	 * @param <T>
	 * @return
	 */
	public static <T> Set<T> arrayToSet(T... array) {
		return new HashSet<T>(arrayToList(array));
	}

	/**
	 * Convert collection to array
	 *
	 * @param c
	 * @return
	 */
	public static Object[] listToArray(Collection<?> c) {
		if (!isEmpty(c)) {
			return c.toArray();
		}
		return null;
	}

	/**
	 * Convert list to set
	 *
	 * @param list
	 * @param <T>
	 * @return
	 */
	public static <T> Set<T> listToSet(List<T> list) {
		return new HashSet<T>(list);
	}

	/**
	 * Convert set to list
	 *
	 * @param set
	 * @param <T>
	 * @return
	 */
	public static <T> List<T> setToList(Set<T> set) {
		return new ArrayList<T>(set);
	}

	/**
	 * Traverse collection
	 *
	 * @param c
	 * @param <T>
	 * @return
	 */
	public static <T> String traverseCollection(Collection<T> c) {
		if (!isEmpty(c)) {
			int len = c.size();
			StringBuilder builder = new StringBuilder(len);
			int i = 0;
			for (T t : c) {
				if (t == null) {
					continue;
				}
				builder.append(t.toString());
				i++;
				if (i < len) {
					builder.append(DELIMITER);
				}
			}
			return builder.toString();
		}
		return null;
	}
}
