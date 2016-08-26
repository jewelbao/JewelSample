package com.jewel.sample.Java;

/**
 * @author Jewel
 * @version 1.0
 * @since 2016/8/3 0003
 */
public class BaseClass {
	public double weight = 1;

	public void info() {
		System.out.println("执行父类方法");
	}

	public void override() {
		System.out.println("父类中被覆盖的方法");
	}
}
