package com.jewel.model.result;

/**
 Created by PC on 2016/3/1.
 号码归属地查询结果
 */
public class ResultPhoneRes
{

	/**
	 errNum : 0 retMsg : success retData : {"phone":"15210011578","prefix":"1521001","supplier":"移动
	 ","province":"北京 ","city":"北京 ","suit":"152卡"}
	 */

	/**
	 300101	User's request is expired	用户请求过期<P>
	 300102	User call overrun per day	用户日调用量超限<P>
	 300103	Service call overrun per second	服务每秒调用量超限<P>
	 300104	Service call overrun per day	服务日调用量超限<P>
	 300201	URL cannot be resolved	url无法解析<P>
	 300202	Missing apikey	请求缺少apikey，登录即可获取<P>
	 300203	Apikey or secretkey is NULL	服务没有取到apikey或secretkey<P>
	 300204	Apikey does not exist	apikey不存在<P>
	 300205	Api does not exist	api不存在<P>
	 300206	Api out of service	api已关闭服务<P>
	 300207	Service overdue, please pay in time	余额不足，请充值<P>
	 300208	User not verified	未通过实名验证<P>
	 300209	Service provider response status error	服务商响应status非200<P>
	 300301	Internal error	内部错误<P>
	 300302	Sorry,The system is busy. Please try again late	系统繁忙稍候再试<P>
	 0	success	成功<P>
	 -1	failure	失败
	 */
	public int errNum;
	public String retMsg;
	/**
	 phone : 15210011578 prefix : 1521001 supplier : 移动 province : 北京 city : 北京 suit : 152卡
	 */

	public RetDataEntity retData;

	@SuppressWarnings("unused")
	public static class RetDataEntity
	{
		public String phone;
		public String prefix;
		public String supplier;
		public String province;
		public String city;
		public String suit;
	}
}
