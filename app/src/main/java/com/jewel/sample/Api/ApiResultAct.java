package com.jewel.sample.Api;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewStub;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.jewel.adapter.TXAPIListAdapter;
import com.jewel.base.Api;
import com.jewel.base.Common;
import com.jewel.base.Global;
import com.jewel.base.JBaseAct;
import com.jewel.model.param.ParamIdentity;
import com.jewel.model.param.ParamPhoneRes;
import com.jewel.model.param.ParamTX;
import com.jewel.model.result.ResultIdentity;
import com.jewel.model.result.ResultPhoneRes;
import com.jewel.model.result.ResultTXApi;
import com.jewel.model.result.ResultTXApi.NewsListEntity;
import com.jewel.sample.R;
import com.jewelbao.customview.ListView.LoadMoreRecyclerView;
import com.jewelbao.customview.ListView.impl.ILoadMore;
import com.jewelbao.library.impl.IHttpResponse;
import com.jewelbao.library.utils.JLog;
import com.jewelbao.library.utils.KeyBoardUtil;
import com.jewelbao.library.utils.LiteHttpUtil;
import com.jewelbao.customview.utils.ProgressUtil;
import com.litesuits.http.data.NameValuePair;
import com.litesuits.http.exception.HttpException;
import com.litesuits.http.request.param.HttpParamModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 Created by PC on 2016/3/1.
 */
public class ApiResultAct extends JBaseAct implements IHttpResponse, ILoadMore
{
	public static final int Key_Phone = 0;
	public static final int Key_Identity = 1;
	public static final int Key_Girl = 2;

	private static int NUM = 20;
	private static int PAGE = 1;

	@Bind(R.id.et_content)
	EditText etContent;
	@Bind(R.id.stub_one)
	ViewStub stub_one;
	@Bind(R.id.stub_list)
	ViewStub stub_list;

	TextView resultTextView;

	LoadMoreRecyclerView recyclerView;
	TXAPIListAdapter adapterGirl;
	List<NewsListEntity> listGirl;
	//	int lastVisibleItem; // RecyclerView最底可见Item位置

	List<NameValuePair> httpHeader; // BD_API需要自定义头部参数
	Gson gson;

	String content;
	int key;
	String mTitle[];
	String mUrl[];
	int mRequestID[] = {Api.Request_PhoneRes, Api.Request_IdentityCard, Api.Request_Girl};
	HttpParamModel paramModel;

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setBaseContentView(R.layout.activity_et_btn_result);

		ButterKnife.bind(this);
		initData();
	}

	private void initData()
	{
		mTitle = getResources().getStringArray(R.array.api_item);
		mUrl = getResources().getStringArray(R.array.api_url);

		key = getIntent().getIntExtra("KEY", Key_Phone);
		showTitleBar(mTitle[key]);

		httpHeader = new ArrayList<>();
		httpHeader.add(new NameValuePair("apikey", Api.BD_KEY)); //请求Http Header

		gson = new Gson();

		if(key == Key_Girl)
		{
			initRecyclerView();
			etContent.setHint("请输入关键字");
			etContent.setInputType(InputType.TYPE_CLASS_TEXT);
		} else
		{
			etContent.setInputType(InputType.TYPE_CLASS_NUMBER);
		}
	}

	private void initRecyclerView()
	{
		if(recyclerView == null)
		{
			stub_list.setLayoutResource(R.layout.layout_recyclerview);
			recyclerView = (LoadMoreRecyclerView) stub_list.inflate();

			recyclerView.setHasFixedSize(true);

			recyclerView.setLinearLayoutManager(new LinearLayoutManager(this));

			listGirl = new ArrayList<>();
			adapterGirl = new TXAPIListAdapter(this, listGirl);
			recyclerView.setLoadMoreAdapter(adapterGirl);
			recyclerView.setLoadMore(this);
		}
	}

	@OnClick(R.id.btn_get)
	public void onClick()
	{
		if(TextUtils.isEmpty(etContent.getText()))
			return;

		content = etContent.getText().toString();

		if(key == Key_Phone)
		{
			paramModel = new ParamPhoneRes(content);
		} else if(key == Key_Identity)
		{
			paramModel = new ParamIdentity(content);
		} else
		{
			PAGE = 1;
			paramModel = new ParamTX(content, NUM, PAGE);
		}

		listGirl = new ArrayList<>();
		LiteHttpUtil.getInstance().setLiteHttp(this, httpHeader)
					.executeAsync(mUrl[key], paramModel, mRequestID[key], this);
		ProgressUtil.getInstance(Global.mContext).show();
		KeyBoardUtil.hideSoftInput(etContent);
	}

	@Override
	public void onBackPressed()
	{
		if(ProgressUtil.getInstance(Global.mContext).isShow())
		{
			ProgressUtil.getInstance(Global.mContext).dismiss();
			return;
		}
		super.onBackPressed();
	}

	@Override
	public void onSuccess(int requestID, String result)
	{
		JLog.e(result);

		if(requestID == Api.Request_PhoneRes)
		{
			checkPhoneResult(result);
		} else if(requestID == Api.Request_IdentityCard)
		{
			checkIdentityResult(result);
		} else if(requestID == Api.Request_Girl)
		{
			checkGirlResult(result, requestID);
		}

		ProgressUtil.getInstance(Global.mContext).dismiss();
	}

	@Override
	public void onFailure(int requestID, HttpException e, int code)
	{
		ProgressUtil.getInstance(Global.mContext).dismiss();
	}

	private void checkGirlResult(String result, int requestID)
	{
		ResultTXApi resutlGirl = gson.fromJson(result, ResultTXApi.class);
		if(resutlGirl.code == 200)
		{
			setListResult(resutlGirl.newslist, requestID);
		} else
		{
			if(PAGE == 1)
			{
				setOneResult(resutlGirl.msg);
			} else
			{
				Common.showHint(this, "已加载完毕!试试其他关键词吧!");
			}
		}
	}

	private void checkPhoneResult(String result)
	{
		if(result.contains("[]")) // 如果ResultPhoneRes.retData为空时,会为[]数组，需要转换为{}对象，否则Gson转换会出错
		{
			result = result.replace("[]", "{}");
		}
		ResultPhoneRes resultPhoneRes = gson.fromJson(result, ResultPhoneRes.class);
		if(resultPhoneRes.errNum == 0)
		{
			setOneResult(
					resultPhoneRes.retData.province + "\n" + resultPhoneRes.retData.city + "\n" + resultPhoneRes.retData.supplier + "\n" + resultPhoneRes.retData.suit);
		} else
		{
			setOneResult(resultPhoneRes.retMsg);
		}
	}

	private void checkIdentityResult(String result)
	{
		if(result.contains("[]"))// 如果ResultIdentity.retData为空时,会为[]数组，需要转换为{}对象，否则Gson转换会出错
		{
			result = result.replace("[]", "{}");
		}
		ResultIdentity resultIdentity = gson.fromJson(result, ResultIdentity.class);
		if(resultIdentity.errNum == 0)
		{
			String sex = "未知";
			if(resultIdentity.retData.sex.equals("M"))
			{
				sex = "男";
			} else if(resultIdentity.retData.sex.equals("F"))
			{
				sex = "女";
			}
			setOneResult(
					sex + "\n" + resultIdentity.retData.birthday + "\n" + resultIdentity.retData.address);
		} else
		{
			setOneResult(resultIdentity.retMsg);
		}
	}

	/**
	 显示单数据结果

	 @param result 单个数据结果
	 */
	private void setOneResult(String result)
	{
		if(resultTextView == null)
		{
			stub_one.setLayoutResource(android.R.layout.test_list_item);
			resultTextView = (TextView) stub_one.inflate();
		}

		if(recyclerView != null)
		{
			recyclerView.setVisibility(View.GONE);
		}
		resultTextView.setVisibility(View.VISIBLE);

		resultTextView.setText(result);
	}

	/**
	 显示列表数据结果

	 @param list 数据列表
	 @param requestID 请求ID
	 */
	@SuppressWarnings("unchecked")
	private void setListResult(List<?> list, int requestID)
	{
		recyclerView.setVisibility(View.VISIBLE);
		if(resultTextView != null)
		{
			resultTextView.setVisibility(View.GONE);
		}

		if(requestID == Api.Request_Girl)
		{
			List<NewsListEntity> newList = (List<NewsListEntity>) list;
			if(PAGE == 1)
			{
				listGirl = newList;
				adapterGirl.refresh(newList);
			} else
			{
				adapterGirl.insertItemRange(newList, listGirl.size(), newList.size());
				listGirl.addAll(newList);
			}
		}
	}

	@Override
	public void loadMore()
	{
		PAGE++;
		paramModel = new ParamTX(content, NUM, PAGE);
		LiteHttpUtil.getInstance().setLiteHttp(ApiResultAct.this, httpHeader)
					.executeAsync(mUrl[key], paramModel, mRequestID[key], ApiResultAct.this);
		ProgressUtil.getInstance(Global.mContext).show();
	}
}
