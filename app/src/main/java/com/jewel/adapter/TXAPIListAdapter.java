package com.jewel.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jewel.base.Common;
import com.jewel.model.result.ResultTXApi;
import com.jewel.sample.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 Created by PC on 2016/3/2.
 */
public class TXAPIListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>
{
	private LayoutInflater inflater;
	private List<ResultTXApi.NewsListEntity> mDataList;
	private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();
	private DisplayImageOptions options;

	public TXAPIListAdapter(Context context, List<ResultTXApi.NewsListEntity> dataList)
	{
		this.mDataList = dataList;
		inflater = LayoutInflater.from(context);
		options = new DisplayImageOptions.Builder()
				.showImageOnLoading(R.mipmap.light_green_square)
				.showImageOnFail(R.drawable.block_canary_icon)
				.showImageForEmptyUri(R.drawable.block_canary_icon)
				.cacheInMemory(true)
				.cacheOnDisk(true)
				.considerExifParams(true)
//				.displayer(new CircleBitmapDisplayer(Color.WHITE, 5))
				.build();
	}

	public void insertItemRange(List<ResultTXApi.NewsListEntity> dataList, int start, int count) {
		mDataList.addAll(dataList);
		notifyItemRangeInserted(start, count);
	}

	public void refresh(List<ResultTXApi.NewsListEntity> dataList) {
		mDataList = dataList;
		notifyDataSetChanged();
	}

	@Override
	public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
	{
		return new GirlItemHolder(inflater.inflate(R.layout.layout_cardview, parent, false));
	}

	@Override
	public void onBindViewHolder(RecyclerView.ViewHolder holder, int position)
	{
		ResultTXApi.NewsListEntity entity = mDataList.get(position);

		if(null == entity)
			return;

		setTitle(entity, ((GirlItemHolder) holder).tv_title);
		setContent(entity, ((GirlItemHolder) holder).tv_content);
		setImage(entity, ((GirlItemHolder) holder).img_girl);
	}

	private void setContent(ResultTXApi.NewsListEntity entity, TextView textView)
	{
		if(TextUtils.isEmpty(entity.description))
		{
			textView.setVisibility(View.GONE);
		} else
		{
			textView.setVisibility(View.VISIBLE);
			textView.setText(entity.description);
		}
	}

	private void setImage(ResultTXApi.NewsListEntity entity, ImageView imageView)
	{
		ImageLoader.getInstance().displayImage(entity.picUrl, imageView, options, animateFirstListener);
	}

	private void setTitle(ResultTXApi.NewsListEntity entity, TextView textView)
	{
		if(TextUtils.isEmpty(entity.title))
		{
			textView.setVisibility(View.GONE);
		} else
		{
			textView.setVisibility(View.VISIBLE);
			textView.setText(entity.title);
		}
	}

	@Override
	public int getItemCount()
	{
		return mDataList.size();
	}

	public class GirlItemHolder extends RecyclerView.ViewHolder
	{

		TextView tv_title;
		TextView tv_content;
		ImageView img_girl;

		public GirlItemHolder(View itemView)
		{
			super(itemView);
			tv_title = (TextView) itemView.findViewById(R.id.tv_title);
			tv_content = (TextView) itemView.findViewById(R.id.tv_content);
			img_girl = (ImageView) itemView.findViewById(R.id.img_girl);
			itemView.findViewById(R.id.layout).setOnClickListener(v -> Common.showHint(v.getContext(), getLayoutPosition() + ""));
		}
	}

	private static class AnimateFirstDisplayListener extends SimpleImageLoadingListener {
		static final List<String> displayedImages = Collections.synchronizedList(new LinkedList<>());

		@Override
		public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage)
		{
			if(loadedImage != null) {
				ImageView imageView = (ImageView)view;
				boolean firstDisplay = !displayedImages.contains(imageUri);
				if(firstDisplay) {
					FadeInBitmapDisplayer.animate(imageView, 500);
					displayedImages.add(imageUri);
				}
			}
		}
	}
}
