package com.jewel.sample.Anim;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jewel.base.Global;
import com.jewel.base.JBaseAct;
import com.jewel.sample.R;
import com.jewelbao.customview.ListView.LoadMoreRecyclerView;
import com.jewelbao.customview.utils.LoadingUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Jewel on 2016/6/22 0022.
 * Gif对话框
 */
public class GifDialogAct extends JBaseAct {

	public static int[] IDS = {
			R.drawable.num0, R.drawable.num1, R.drawable.num2, R.drawable.num3, R.drawable.num4,
			R.drawable.num5, R.drawable.num6, R.drawable.num7, R.drawable.num8, R.drawable.num9,
			R.drawable.num10, R.drawable.num11, R.drawable.num12, R.drawable.num13, R.drawable.num14,
			R.drawable.num15, R.drawable.num16, R.drawable.num17, R.drawable.num18, R.drawable.num19,
			R.drawable.num20, R.drawable.num21, R.drawable.num22, R.drawable.num23
	};

	private List<String> datas;

	@Bind(R.id.recyclerView)
	LoadMoreRecyclerView recyclerView;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setBaseContentView(R.layout.layout_recyclerview);

		showTitleBar(R.string.title_anim_gif);

		ButterKnife.bind(this);

		recyclerView.setHasFixedSize(true);
		recyclerView.setLinearLayoutManager(new LinearLayoutManager(this));

		datas = new ArrayList<>();
		for(int i = 0; i < 24; i++) {
			datas.add("GifLoadingView : " + i);
		}
		recyclerView.setLoadMoreAdapter(new GifAdapter());
	}

	class GifAdapter extends RecyclerView.Adapter<GifAdapter.ViewHolder> {

		@Override
		public GifAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
			return new ViewHolder(Global.mInflater.inflate(android.R.layout.simple_list_item_2, parent, false));
		}

		@Override
		public void onBindViewHolder(GifAdapter.ViewHolder holder, final int position) {
			holder.button.setText(datas.get(position));
			holder.button.setOnClickListener(view ->
					LoadingUtil.getInstance().show(getFragmentManager(), IDS[position])
			);
		}

		@Override
		public int getItemCount() {
			return datas.size();
		}

		class ViewHolder extends RecyclerView.ViewHolder {

			TextView button;

			@SuppressWarnings("deprecation")
			public ViewHolder(View itemView) {
				super(itemView);
				button = (TextView) itemView.findViewById(android.R.id.text1);
				button.setTextColor(getResources().getColor(R.color.holo_blue_bright));
			}
		}
	}
}
