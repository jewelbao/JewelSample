package com.jewelbao.customview.Button;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.Button;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Jewel on 2016/5/20 0020.
 * 验证码倒计时专用按钮
 */
public class TimeButton extends Button {

	private static final String TAG = TimeButton.class.getSimpleName();

	private long length = 60 * 1000;// 倒计时长度,这里给了默认60秒
	private String textAfter = "%d 秒后重新获取";
	private String textBefore = "获取验证码";
	private final String TIME = "time";
	private final String CTIME = "ctime";
	private Timer timer;
	private TimerTask timerTask;
	private long time;
	/**
	 * 如果想要倒计时时间在全局生效，那此map可定义到Application中。
	 */
	Map<String, Long> timeMap = new HashMap<>();

	public TimeButton(Context context) {
		super(context);
	}

	public TimeButton(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@SuppressLint("HandlerLeak")
	Handler han = new Handler() {
		public void handleMessage(android.os.Message msg) {
			TimeButton.this.setText(String.format(textAfter, time / 1000));
			time -= 1000;
			if (time < 0) {
				TimeButton.this.setEnabled(true);
				TimeButton.this.setText(textBefore);
				clearTimer();
			}
		}
	};

	public void setTimeMap(Map<String, Long> map) {
		this.timeMap = map;
	}

	public Map<String, Long> getTimeMap() {
		return this.timeMap;
	}

	public String getTextBefore() {
		return this.textBefore;
	}

	private void initTimer() {
		time = length;
		timer = new Timer();
		timerTask = new TimerTask() {

			@Override
			public void run() {
				Log.e(TAG, time / 1000 + "");
				han.sendEmptyMessage(0x01);
			}
		};
	}

	private void clearTimer() {
		// 计时结束
		if (timerTask != null) {
			timerTask.cancel();
			timerTask = null;
		}
		if (timer != null)
			timer.cancel();
		timer = null;
	}

	public void start() {
		initTimer();
		this.setText(String.format(textAfter, time / 1000));
		this.setEnabled(false);
		timer.schedule(timerTask, 0, 1000);
	}

	/**
	 * 和activity的onDestroy()方法同步
	 */
	public void onDestroy() {
		if (timeMap == null)
			timeMap = new HashMap<>();
		timeMap.put(TIME, time);
		timeMap.put(CTIME, System.currentTimeMillis());
		clearTimer();
		Log.e(TAG, "onDestroy");
	}

	/**
	 * 和activity的onCreate()方法同步
	 */
	public void onCreate() {
		Log.e(TAG, timeMap + "");
		if (timeMap == null)
			return;
		if (timeMap.size() <= 0)// 这里表示没有上次未完成的计时
			return;
		long time = System.currentTimeMillis() - timeMap.get(CTIME)
				- timeMap.get(TIME);
		timeMap.clear();
		if (time > 0)
			return;
//		else {
		initTimer();
		this.time = Math.abs(time);
		timer.schedule(timerTask, 0, 1000);
		this.setText(String.format(textAfter, time));
		this.setEnabled(false);
//		}
	}

	/** * 设置计时时候显示的文本 */
	public TimeButton setTextAfter(String text1) {
		this.textAfter = "%d " + text1;
		return this;
	}

	/** * 设置点击之前的文本 */
	public TimeButton setTextBefore(String text0) {
		this.textBefore = text0;
		this.setText(textBefore);
		return this;
	}

	/**
	 * 设置到计时长度
	 *
	 * @param length
	 *            时间 默认毫秒
	 */
	public TimeButton setLength(long length) {
		this.length = length;
		return this;
	}
}
