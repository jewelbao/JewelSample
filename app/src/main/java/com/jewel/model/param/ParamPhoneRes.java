package com.jewel.model.param;

import com.litesuits.http.request.param.HttpParam;
import com.litesuits.http.request.param.HttpParamModel;

/**
 Created by PC on 2016/3/1.
 号码归属地查询参数
 */
public class ParamPhoneRes implements HttpParamModel
{
	/** 手机号 */
	@HttpParam("phone")
	String phone;

	public ParamPhoneRes(String phone)
	{
		this.phone = phone;
	}
}
