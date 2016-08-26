package com.jewel.sample.ListView;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.AppCompatCheckBox;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jewel.base.JBaseAct;
import com.jewel.sample.R;
import com.jewelbao.customview.ListView.ExpandableView;

/**
 Created by PC on 2016/4/21.
 */
public class ExpandableAct extends JBaseAct
{
	private ExpandableView topExpandableView;
	private ExpandableView middleExpandableView;

	private ExpandableView expandableViewLevel1;
	private ExpandableView expandableViewLevel2;
	private ExpandableView expandableViewLevel3;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setBaseContentView(R.layout.activity_expandable);
		showTitleBar(R.string.app_name);

		topExpandableView = (ExpandableView) findViewById(R.id.activity_main_top_expandable_view);
		middleExpandableView = (ExpandableView) findViewById(R.id.activity_main_middle_expandable_view);

		expandableViewLevel1 = new ExpandableView(this);
		expandableViewLevel2 = new ExpandableView(this);
		expandableViewLevel3 = new ExpandableView(this);

		createTopExpandableView();
		createMiddleExpandableView();

		createInnerExpandableViewLevel1();
		createInnerExpandableViewLevel2();
		createInnerExpandableViewLevel3();

		expandableViewLevel1.setOutsideContentLayout(topExpandableView.getContentLayout());
		expandableViewLevel2.setOutsideContentLayout(topExpandableView.getContentLayout(), expandableViewLevel1.getContentLayout());
		expandableViewLevel3.setOutsideContentLayout(topExpandableView.getContentLayout(), expandableViewLevel1.getContentLayout(), expandableViewLevel2.getContentLayout());

	}

	public void addContentView(ExpandableView view, String[] stringList, boolean showCheckbox) {

		for(String aStringList : stringList) {
			ExpandedListItemView itemView = new ExpandedListItemView(this);
			itemView.setText(aStringList, showCheckbox);
			view.addContentView(itemView);
		}
	}

	private void createTopExpandableView() {
		String[] androidVersionNameList = getResources().getStringArray(R.array.grid_item);

		topExpandableView.fillData(R.mipmap.ic_launcher, getString(R.string.app_name), true);
		addContentView(topExpandableView, androidVersionNameList, true);
		topExpandableView.addContentView(expandableViewLevel1);
	}

	private void createMiddleExpandableView() {
		String[] androidVersionNameList = getResources().getStringArray(R.array.api_class);

		middleExpandableView.fillData(R.mipmap.ic_launcher, getString(R.string.app_name), false);
		middleExpandableView.setVisibleLayoutHeight(getResources().getDimensionPixelSize(R.dimen.dimen_120));

		addContentView(middleExpandableView, androidVersionNameList, false);
	}

	private void createInnerExpandableViewLevel1() {
		String[] androidVersionNumberList = getResources().getStringArray(R.array.api_url);

		expandableViewLevel1.setBackgroundResource(android.R.color.holo_blue_light);
		expandableViewLevel1.fillData(R.mipmap.ic_launcher, getString(R.string.app_name), false);
		addContentView(expandableViewLevel1, androidVersionNumberList, false);
		expandableViewLevel1.addContentView(expandableViewLevel2);
	}

	private void createInnerExpandableViewLevel2() {
		String[] androidVersionNameList = getResources().getStringArray(R.array.api_title_news);

		expandableViewLevel2.setBackgroundResource(android.R.color.holo_green_light);
		expandableViewLevel2.fillData(R.mipmap.ic_launcher, getString(R.string.app_name), false);
		addContentView(expandableViewLevel2, androidVersionNameList, false);
		expandableViewLevel2.addContentView(expandableViewLevel3);
	}

	private void createInnerExpandableViewLevel3() {
		String[] androidVersionNumberList = getResources().getStringArray(R.array.api_item);

		expandableViewLevel3.setBackgroundResource(android.R.color.holo_blue_light);
		expandableViewLevel3.fillData(R.mipmap.ic_launcher, getString(R.string.app_name), false);
		addContentView(expandableViewLevel3, androidVersionNumberList, false);
	}

	public class ExpandedListItemView extends RelativeLayout
	{

		private RelativeLayout mRoot;

		private TextView mText;

		private AppCompatCheckBox mCheckbox;

		private View mViewSeparator;

		public ExpandedListItemView(Context context) {
			super(context);
			init();
		}

		public ExpandedListItemView(Context context, AttributeSet attrs) {
			super(context, attrs);
			init();
		}

		public ExpandedListItemView(Context context, AttributeSet attrs,
									int defStyleAttr) {
			super(context, attrs, defStyleAttr);
			init();
		}

		private void init() {
			inflate(getContext(), R.layout.expandable_list_item_view, this);

			mRoot = (RelativeLayout) findViewById(R.id.expandable_list_item_view_root);
			mText = (TextView) findViewById(R.id.expandable_list_item_view_text);
			mCheckbox = (AppCompatCheckBox) findViewById(R.id.expandable_list_item_view_checkbox);
			mViewSeparator = findViewById(R.id.expandable_list_item_view_separator);

			this.mRoot.setOnClickListener(v -> mCheckbox.setChecked(!mCheckbox.isChecked()));
		}

		public void setText(String text, boolean showCheckbox) {
			this.mText.setText(text);
			if (!showCheckbox) {
				this.mCheckbox.setVisibility(View.GONE);
				this.mViewSeparator.setVisibility(View.GONE);
			}
		}


	}

}
