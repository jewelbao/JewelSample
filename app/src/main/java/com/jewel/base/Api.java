package com.jewel.base;

/**
 Created by PC on 2016/3/1.
 */
public class Api
{
	/** 天行数据官网==api.huceo.com */
	public static final String TX_KEY = "73c4ecf61a5548f05e3edea1230f89a9";
	/** 百度API官网==apistore.baidu.com */
	public static final String BD_KEY = "d9251e95814e752e5d79d4474cb13181";
	private static int count = 0;
	public static final int Request_PhoneRes = count++;  // 号码归属地查询
	public static final int Request_IdentityCard = count++; // 身份证查询
	public static final int Request_Girl = count++; // 美女图
	public static final int Request_Social = count++; // 社会
	public static final int Request_Internal = count++; // 国内
	public static final int Request_World = count++; // 国际
	public static final int Request_Sports = count++; // 体育
	public static final int Request_Entertainment = count++; // 花边
	public static final int Request_Technology = count++; // 科技
	public static final int Request_Anecdote = count++; // 奇闻
	public static final int Request_Health = count++; // 健康
	public static final int Request_Travel = count++; //  旅游
	public static final int Request_Apple = count++; // 苹果
}
