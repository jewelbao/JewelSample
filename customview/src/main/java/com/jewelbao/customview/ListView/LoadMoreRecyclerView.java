package com.jewelbao.customview.ListView;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

import com.jewelbao.customview.ListView.impl.ILoadMore;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

/**
 Created by PC on 2016/3/2. 实现加载更多的RecyclerView
 */
public class LoadMoreRecyclerView extends RecyclerView
{
	Context context;
	int lastVisibleItem; // RecyclerView最底可见Item位置
	ILoadMore loadMore;
	LinearLayoutManager layoutManager;
	RecyclerView.Adapter adapter;

	public LoadMoreRecyclerView(Context context)
	{
		super(context);
		this.context = context;
	}

	public LoadMoreRecyclerView(Context context, @Nullable AttributeSet attrs)
	{
		super(context, attrs);
		this.context = context;
	}

	public LoadMoreRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle)
	{
		super(context, attrs, defStyle);
		this.context = context;
	}

	public void setLoadMore(ILoadMore loadMore)
	{
		this.loadMore = loadMore;
	}

	/**
	 设置该属性时，已经默认设置了分割线的样式，如需要定制分割线，请参考方法逻辑内的注释说明

	 @param manager
	 */
	public void setLinearLayoutManager(LinearLayoutManager manager)
	{
		this.layoutManager = manager;
		super.setLayoutManager(manager);

		// 分割线,如果需要自定义,可参考https://github.com/yqritc/RecyclerView-FlexibleDivider
		Paint paint = new Paint();
		paint.setStrokeWidth(10);
		paint.setColor(Color.TRANSPARENT);
		paint.setAntiAlias(true);
		addItemDecoration(
				new HorizontalDividerItemDecoration.Builder(context).paint(paint).build());

		setItemAnimator(new DefaultItemAnimator());
	}

	public void setLoadMoreAdapter(RecyclerView.Adapter adapter)
	{
		this.adapter = adapter;
		super.setAdapter(adapter);
	}

	@Override
	public void onScrollStateChanged(int state)
	{
		super.onScrollStateChanged(state);
		if(state == RecyclerView.SCROLL_STATE_IDLE && lastVisibleItem + 1 == adapter.getItemCount())
		{
			getHandler().post(new Runnable()
			{
				@Override
				public void run()
				{
					if(loadMore != null)
					{
						loadMore.loadMore();
					}
				}
			});
		}
	}

	@Override
	public void onScrolled(int dx, int dy)
	{
		super.onScrolled(dx, dy);
		lastVisibleItem = layoutManager.findLastVisibleItemPosition();
	}
}
