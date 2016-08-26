package com.jewelbao.library.utils.Service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by Jewel on 2016/6/24 0024.
 * 广播接收器模版
 */
public class BroadcastReceiveT extends BroadcastReceiver {

	Receiver receiver;

	/**
	 * 是否拦截广播
	 */
	private boolean abortBroadcast = false;

	public void setReceiver(Receiver receiver) {
		this.receiver = receiver;
	}

	/**
	 * 设置广播拦截，普通广播不能拦截.
	 * @param abort true：拦截广播(当前应用处理完后其他应用无法接受到广播)
	 */
	public void setAbortBroadcast(boolean abort) {
		abortBroadcast = abort;
	}

	@Override
	public void onReceive(Context context, Intent intent) {
		if(null != receiver) {
			receiver.onReceive(context, intent);
		}

		if(abortBroadcast) {
			abortBroadcast();
		}
	}

	public interface Receiver {
		void onReceive(Context context, Intent intent);
	}
}
