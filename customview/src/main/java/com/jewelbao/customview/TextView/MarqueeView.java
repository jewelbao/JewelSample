package com.jewelbao.customview.TextView;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.ViewTreeObserver;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.jewelbao.customview.R;
import com.jewelbao.customview.utils.DensityUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jewel on 2016/6/9 0009.
 * 上下跑马灯TextView
 */
public class MarqueeView extends ViewFlipper {

	private Context mContext;
	private List<String> messages;
	private boolean isSetAnimDuration = false;

	// 间隔时间
	private int interval = 2000;
	// 动画时间
	private int animDuration = 500;
	// 文字大小
	private int textSize = 14;
	// 文字颜色
	private int textColor = 0xffffffff;

	public MarqueeView(Context context) {
		super(context);
		init(context, null, 0);
	}

	public MarqueeView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context, attrs, 0);
	}

	private void init(Context context, AttributeSet attrs, int defStyleAttr) {
		this.mContext = context;
		if(messages == null) {
			messages = new ArrayList<>();
		}

		if(attrs != null) {
			TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.MarqueeView, defStyleAttr, 0);
			interval = typedArray.getInteger(R.styleable.MarqueeView_Interval, interval);
			isSetAnimDuration = typedArray.hasValue(R.styleable.MarqueeView_AnimDuration);
			animDuration = typedArray.getInteger(R.styleable.MarqueeView_AnimDuration, animDuration);
			if(typedArray.hasValue(R.styleable.MarqueeView_TextSize)) {
				textSize = (int) typedArray.getDimension(R.styleable.MarqueeView_TextSize, textSize);
				textSize = (int) DensityUtil.px2sp(context, textSize);
			}
			textColor = typedArray.getColor(R.styleable.MarqueeView_TextColor, textColor);
			typedArray.recycle();
		}

		setFlipInterval(interval);

		Animation animIn = AnimationUtils.loadAnimation(context, R.anim.anim_marquee_in);
		if(isSetAnimDuration) {
			animIn.setDuration(animDuration);
		}
		setInAnimation(animIn);

		Animation animOut = AnimationUtils.loadAnimation(context, R.anim.anim_marquee_out);
		if(isSetAnimDuration) {
			animOut.setDuration(animDuration);
		}
		setOutAnimation(animOut);
	}

	/**
	 * 根据消息启动轮播
	 *
	 * @param message
	 */
	public void startWithMessage(final String message) {
		if(TextUtils.isEmpty(message)) return;
		getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
			@Override
			public void onGlobalLayout() {
				getViewTreeObserver().removeGlobalOnLayoutListener(this);
				startWithFixedWidth(message, getWidth());
			}
		});
	}

	/**
	 * 根据公告 列表启动轮播
	 *
	 * @param messageList
	 */
	public void startWithList(List<String> messageList) {
		setMessageList(messageList);
		start();
	}

	/**
	 * 根据宽度和消息启动轮播
	 *
	 * @param message
	 * @param width
	 */
	public void startWithFixedWidth(String message, int width) {
		int length = message.length();
		int dpW = (int) DensityUtil.px2dp(mContext, width);
		int limit = dpW / textSize;

		if(dpW <= 0) {
			throw new RuntimeException("MarqueeView width cant be smaller than 0!! Please set MarqueeView width!");
		}

		if(length <= limit) {
			messages.add(message);
		} else {
			int size = length / limit + (length % limit != 0 ? 1 : 0);
			for(int i = 0; i < size; i++) {
				int startIndex = i * limit;
				int endIndex = ((i + 1) * limit >= length ? length : (i + 1) * limit);
				messages.add(message.substring(startIndex, endIndex));
			}
		}
		start();
	}

	/**
	 * 启动轮播
	 *
	 * @return
	 */
	public boolean start() {
		if(messages == null || messages.size() == 0) {
			return false;
		}

		removeAllViews();
		for(String message : messages) {
			addView(createTextView(message));
		}
		if(messages.size() > 1) {
			startFlipping();
		}
		return true;
	}

	/**
	 * 创建ViewFlipper下的TextView
	 *
	 * @param text
	 * @return
	 */
	private TextView createTextView(String text) {
		TextView textView = new TextView(mContext);
		textView.setGravity(Gravity.LEFT | Gravity.CENTER_VERTICAL);
		textView.setText(text);
		textView.setTextColor(textColor);
		textView.setTextSize(textSize);
		return textView;
	}

	public List<String> getMessageList() {
		return messages;
	}

	public void setMessageList(List<String> messageList) {
		this.messages = messageList;
	}
}
