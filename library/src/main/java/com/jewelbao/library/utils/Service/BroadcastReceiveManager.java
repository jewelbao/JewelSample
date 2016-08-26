package com.jewelbao.library.utils.Service;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.annotation.NonNull;
import android.support.v4.content.LocalBroadcastManager;

/**
 * Created by Jewel on 2016/6/24 0024.
 * 广播管理
 */
public class BroadcastReceiveManager {

	private BroadcastReceiveT receiveT;

	private static BroadcastReceiveManager instance;

	private BroadcastReceiveManager(boolean initBroadcastReceive) {
		if(initBroadcastReceive && null == receiveT) {
			receiveT = new BroadcastReceiveT();
		}
	}

	/**
	 * @param initBroadcastReceive 是否初始化广播接收器
	 * @return BroadcastReceiveManager
	 */
	public static BroadcastReceiveManager newInstance(boolean initBroadcastReceive) {
		if(null == instance) {
			instance = new BroadcastReceiveManager(initBroadcastReceive);
		}
		return instance;
	}

	/**
	 * 如果初始化了广播接收器，则可设置广播接收接口
	 *
	 * @param receiver 广播接收接口
	 * @param abort    是否拦截广播
	 */
	public void setRecevier(BroadcastReceiveT.Receiver receiver, boolean abort) {
		if(null != receiveT) {
			receiveT.setReceiver(receiver);
			receiveT.setAbortBroadcast(abort);
		}
	}

	/**
	 * 设置广播接收接口
	 *
	 * @param receiver 广播接收接口
	 */
	public void setReceiver(BroadcastReceiveT.Receiver receiver) {
		if(null != receiveT) {
			receiveT.setReceiver(receiver);
		}
	}

	/**
	 * 设置拦截广播
	 *
	 * @param abort 是否拦截广播
	 */
	public void setAbort(boolean abort) {
		if(null != receiveT) {
			receiveT.setAbortBroadcast(abort);
		}
	}

	/**
	 * 发送广播
	 *
	 * @param context 上下文
	 * @param intent  意图
	 */
	public void sendBroadcast(Context context, Intent intent) {
		context.sendBroadcast(intent);
	}

	/**
	 * 发送有序广播
	 *
	 * @param context 上下文
	 * @param intent  意图
	 */
	public void sendOrderBroadcast(Context context, Intent intent) {
		context.sendOrderedBroadcast(intent, null);
	}

	/**
	 * 发送本地广播（只有当前应用才会接收到的广播）;<p>
	 * 使用此方法发送的广播，必须是由{@link BroadcastReceiveManager#registerLocalReceiver(Context, String...)}注册的广播;</p>
	 * 销毁广播时必须使用{@link BroadcastReceiveManager#unregisterLocalReceiver(Context, boolean)} (Context)}方法.
	 *
	 * @param context 上下文
	 * @param intent  意图
	 */
	public void sendLocalBroadcast(Context context, Intent intent) {
		LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
	}

	/**
	 * 注册广播接收器
	 *
	 * @param context 上下文
	 * @param actions 意图
	 */
	public void registerReceiver(Context context, @NonNull String... actions) {
		if(null == instance) {
			throw new RuntimeException("Stub!");
		}

		IntentFilter filter = new IntentFilter();
		for(String action : actions) {
			filter.addAction(action);
		}
		context.registerReceiver(receiveT, filter);
	}

	/**
	 * 注销广播接收器
	 *
	 * @param context 上下文
	 */
	public void unregisterReceiver(Context context, boolean destroy) {
		if(null != receiveT) {
			context.unregisterReceiver(receiveT);
		}
		if(destroy) {
			instance = null;
		}
	}

	/**
	 * 注册本地广播接收器
	 *
	 * @param context 上下文
	 * @param actions 意图
	 */
	public void registerLocalReceiver(Context context, @NonNull String... actions) {
		if(null == instance) {
			throw new RuntimeException("Stub!");
		}

		IntentFilter filter = new IntentFilter();
		for(String action : actions) {
			filter.addAction(action);
		}
		LocalBroadcastManager.getInstance(context).registerReceiver(receiveT, filter);
	}

	/**
	 * 注销本地广播接收器
	 *
	 * @param context 上下文
	 */
	public void unregisterLocalReceiver(Context context, boolean destroy) {
		if(null != receiveT) {
			LocalBroadcastManager.getInstance(context).unregisterReceiver(receiveT);
		}
		if(destroy) {
			instance = null;
		}
	}
}
