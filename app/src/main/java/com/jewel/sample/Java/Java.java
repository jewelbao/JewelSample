package com.jewel.sample.Java;

/**
 * @author Jewel
 * @version 1.0
 * @since 2016/8/3 0003
 */
public class Java extends BaseClass{

	public String weight = "覆盖了weight变量";

	@Override
	public void override() {
		System.out.println("子类覆盖父类中的方法");
	}

	public static void main(String[] args){
//		BaseClass sam = new BaseClass();
//		sam.info();
//		sam.override();
//
//		Java sam1 = new Java();
//		sam1.info();
//		sam1.override();
//
//		BaseClass sam2 = new Java();
//		sam2.info();
//		sam2.override();

		Imp imp = new ImpI();
		imp.impl();

		Object o = new Object();

	}
}
