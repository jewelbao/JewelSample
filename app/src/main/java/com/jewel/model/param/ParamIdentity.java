package com.jewel.model.param;

import com.litesuits.http.request.param.HttpParam;
import com.litesuits.http.request.param.HttpParamModel;

/**
 Created by PC on 2016/3/1.
 身份证查询参数
 */
public class ParamIdentity implements HttpParamModel
{
	/** 身份证 */
	@HttpParam("id")
	String id;

	public ParamIdentity(String id)
	{
		this.id = id;
	}
}
