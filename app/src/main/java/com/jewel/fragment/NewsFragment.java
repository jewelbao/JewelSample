package com.jewel.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.jewel.adapter.TXAPIListAdapter;
import com.jewel.base.Common;
import com.jewel.base.Global;
import com.jewel.model.param.ParamTX;
import com.jewel.model.result.ResultTXApi;
import com.jewel.model.result.ResultTXApi.NewsListEntity;
import com.jewel.sample.Api.NewsAct;
import com.jewel.sample.R;
import com.jewelbao.customview.ListView.LoadMoreRecyclerView;
import com.jewelbao.customview.ListView.impl.ILoadMore;
import com.jewelbao.library.impl.IHttpResponse;
import com.jewelbao.library.utils.JLog;
import com.jewelbao.library.utils.LiteHttpUtil;
import com.jewelbao.customview.utils.ProgressUtil;
import com.litesuits.http.exception.HttpException;
import com.litesuits.http.request.param.HttpParamModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 Created by PC on 2016/3/2.
 */
public class NewsFragment extends Fragment implements IHttpResponse, ILoadMore
{
	public static final String KEY_REQUEST_ID = "key_pos";
	public static final String KEY_URL = "key_url";

	@Bind(R.id.recyclerView)
	LoadMoreRecyclerView recyclerView;

	TXAPIListAdapter adapter;
	List<NewsListEntity> newsList;

	Gson gson;
	HttpParamModel paramModel;

	private static int NUM = 20;
	private static int PAGE = 1;

	int requestID;
	String url;

	static boolean hasLoadData = false;

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
							 @Nullable Bundle savedInstanceState)
	{
		JLog.e("onCreateView");
		View view = inflater.inflate(R.layout.layout_recyclerview, container, false);
		ButterKnife.bind(this, view);
		initRecyclerView();
		initData();
		hasLoadData = false;
		return view;
	}

	private void initRecyclerView()
	{
		recyclerView.setHasFixedSize(true);

		recyclerView.setLinearLayoutManager(new LinearLayoutManager(getContext()));

		newsList = new ArrayList<>();
		adapter = new TXAPIListAdapter(getContext(), newsList);
		recyclerView.setLoadMoreAdapter(adapter);
		recyclerView.setLoadMore(this);
	}

	private void initData()
	{
		if(gson == null)
		{
			gson = ((NewsAct) getActivity()).getGson();
		}

		requestID = getArguments().getInt(KEY_REQUEST_ID);
		url = getArguments().getString(KEY_URL);
	}

	@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState)
	{
		JLog.e("onActivityCreated" + url);
		if(!hasLoadData) {
			refresh();
		}
		super.onActivityCreated(savedInstanceState);
	}

	@Override
	public void onDestroyView()
	{
		JLog.e("onDestroyView" + url);
		super.onDestroyView();
		ButterKnife.unbind(this);
	}

	private void refresh()
	{
		PAGE = 1;
		paramModel = new ParamTX(null, NUM, PAGE);
		LiteHttpUtil.getInstance().setLiteHttp(getContext())
					.executeAsync(url, paramModel, requestID, this);
		ProgressUtil.getInstance(Global.mContext).show();
	}

	@Override
	public void onSuccess(int requestID, String result)
	{
		JLog.e(result);
		ResultTXApi resultGirl = gson.fromJson(result, ResultTXApi.class);
		hasLoadData = true;
		if(resultGirl.code == 200)
		{
			setListResult(resultGirl.newslist, requestID);
		} else
		{
			if(PAGE == 1)
			{
				Common.showHint(getContext(), "暂无数据!");
			} else
			{
				Common.showHint(getContext(), "已加载完毕!");
			}
		}
		ProgressUtil.getInstance(Global.mContext).dismiss();
	}

	@Override
	public void onFailure(int requestID, HttpException e, int code)
	{
		ProgressUtil.getInstance(Global.mContext).dismiss();
	}

	@Override
	public void loadMore()
	{
		PAGE++;
		paramModel = new ParamTX(null, NUM, PAGE);
		LiteHttpUtil.getInstance().setLiteHttp(getContext())
					.executeAsync(url, paramModel, requestID, this);
		ProgressUtil.getInstance(Global.mContext).show();
	}

	/**
	 显示列表数据结果

	 @param list 数据列表
	 @param requestID 请求ID
	 */
	private void setListResult(List<?> list, int requestID)
	{
		List<NewsListEntity> tempList = (List<NewsListEntity>) list;
		if(PAGE == 1)
		{
			newsList = tempList;
			adapter.refresh(tempList);
		} else
		{
			adapter.insertItemRange(tempList, newsList.size(), tempList.size());
			newsList.addAll(tempList);
		}
	}
}
