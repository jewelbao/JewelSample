package com.jewel.sample.Api;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.google.gson.Gson;
import com.jewel.base.Api;
import com.jewel.base.Global;
import com.jewel.base.JBaseAct;
import com.jewel.fragment.NewsFragment;
import com.jewel.sample.R;
import com.jewelbao.customview.utils.ProgressUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 Created by PC on 2016/3/2.
 */
public class NewsAct extends JBaseAct
{
	@Bind(R.id.tabs)
	TabLayout tabs;
	@Bind(R.id.viewpager)
	ViewPager viewPager;

	Gson gson;

	String mUrl[];
	String mTabTitle[];
	int mRequestID[] = {
			Api.Request_Social, Api.Request_Internal, Api.Request_World, Api.Request_Sports,
			Api.Request_Entertainment, Api.Request_Technology, Api.Request_Anecdote, Api.Request_Health,
			Api.Request_Travel, Api.Request_Apple};

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setBaseContentView(R.layout.activity_tab);
		showTitleBar(R.string.title_new);

		ButterKnife.bind(this);
		initData();
	}

	private void initData()
	{
		mUrl = getResources().getStringArray(R.array.api_url_news);
		mTabTitle = getResources().getStringArray(R.array.api_title_news);

		gson = new Gson();

		initView();
	}

	private void initView()
	{
		List<NewsFragment> fragments = new ArrayList<>();
		for(int i = 0; i < mTabTitle.length; i++)
		{
//			tabs.addTab(tabs.newTab().setText(mTabTitle[i]));

			NewsFragment fragment = new NewsFragment();
			Bundle args = new Bundle();
			args.putString(NewsFragment.KEY_URL, mUrl[i]);
			args.putInt(NewsFragment.KEY_REQUEST_ID, mRequestID[i]);
			fragment.setArguments(args);
			fragments.add(fragment);
		}
		FragmentAdapter adapter = new FragmentAdapter(getSupportFragmentManager(), fragments, mTabTitle);
		viewPager.setAdapter(adapter);
		viewPager.setOffscreenPageLimit(mTabTitle.length);
		tabs.setupWithViewPager(viewPager);
	}

	public Gson getGson()
	{
		return gson;
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

	public static class FragmentAdapter extends FragmentPagerAdapter
	{

		private List<NewsFragment> mFragmentList;
		private String mTitle[];

		public FragmentAdapter(FragmentManager fm, List<NewsFragment> fragmentList, String title[])
		{
			super(fm);
			this.mFragmentList = fragmentList;
			this.mTitle = title;
		}

		@Override
		public int getCount()
		{
			return mFragmentList.size();
		}

		@Override
		public Fragment getItem(int position)
		{
			return mFragmentList.get(position);
		}

		@Override
		public CharSequence getPageTitle(int position)
		{
			return mTitle[position];
		}
	}
}
