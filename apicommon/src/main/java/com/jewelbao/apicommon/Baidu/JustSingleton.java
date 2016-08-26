package com.jewelbao.apicommon.Baidu;

/**
 * Created by Jewel on 2016/5/25 0025.
 */
public class JustSingleton {
	private static JustSingleton ourInstance = new JustSingleton();

	public static JustSingleton getInstance() {
		return ourInstance;
	}

	private JustSingleton() {
	}
}
