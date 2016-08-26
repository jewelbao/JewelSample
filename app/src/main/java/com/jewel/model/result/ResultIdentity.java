package com.jewel.model.result;

/**
 Created by PC on 2016/3/1.
 */
public class ResultIdentity
{
	/**
	 errNum : 0 retMsg : success retData : {"sex":"M","birthday":"1987-04-20","address":"湖北省孝感市汉川市"}
	 */

	/**
	 300101	User's request is expired	用户请求过期<p>
	 300102	User call overrun per day	用户日调用量超限<p>
	 300103	Service call overrun per second	服务每秒调用量超限<p>
	 300104	Service call overrun per day	服务日调用量超限<p>
	 300201	URL cannot be resolved	url无法解析<p>
	 300202	Missing apikey	请求缺少apikey，登录即可获取<p>
	 300203	Apikey or secretkey is NULL	服务没有取到apikey或secretkey<p>
	 300204	Apikey does not exist	apikey不存在<p>
	 300205	Api does not exist	api不存在<p>
	 300206	Api out of service	api已关闭服务<p>
	 300207	Service overdue, please pay in time	余额不足，请充值<p>
	 300208	User not verified	未通过实名验证<p>
	 300209	Service provider response status error	服务商响应status非200<p>
	 300301	Internal error	内部错误<p>
	 300302	Sorry,The system is busy. Please try again late	系统繁忙稍候再试<p>
	 -1	&quot;身份证号码不合法！&quot;	说明输入身份证不合法，输入有效的身份证
	 */
	public int errNum;
	public String retMsg;
	/**
	 sex : M birthday : 1987-04-20 address : 湖北省孝感市汉川市
	 */
	public RetDataEntity retData;

	public static class RetDataEntity
	{
		public String sex;
		public String birthday;
		public String address;
	}
}
