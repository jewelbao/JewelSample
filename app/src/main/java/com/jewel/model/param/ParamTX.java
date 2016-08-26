package com.jewel.model.param;

import android.text.TextUtils;

import com.jewel.base.Api;
import com.litesuits.http.request.param.HttpParam;
import com.litesuits.http.request.param.HttpParamModel;

/**
 Created by PC on 2016/3/1. TX请求参数
 */
@SuppressWarnings("unused")
public class ParamTX implements HttpParamModel
{
	/** 天行数据 API密钥（请在个人中心获取） */
	@HttpParam("key")
	String key;
	/** 指定返回数量，最大50 */
	@HttpParam("num")
	int num;
	/** 参数值为1则随机获取 */
	@HttpParam("rand")
	int rand;
	/** 检索关键词：上海 */
	@HttpParam("word")
	String word;
	/** 翻页，每页输出数量跟随num参数 */
	@HttpParam("page")
	int page;

	public ParamTX(String word, int num, int page)
	{
		this.key = Api.TX_KEY;
		this.num = num;
		if(!TextUtils.isEmpty(word))
		{
			this.word = word;
		}
		this.page = page;
	}
}