package com.jewel.sample.Tools;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.AppCompatCheckBox;
import android.widget.Button;
import android.widget.Toast;

import com.jewel.base.JBaseAct;
import com.jewel.sample.R;
import com.jewelbao.library.utils.Service.BroadcastReceiveManager;
import com.jewelbao.library.utils.Service.BroadcastReceiveT;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;

/**
 * 广播实例
 * @author Jewel
 * @since 2016/6/24 0024
 */
public class BroadcastAct extends JBaseAct implements BroadcastReceiveT.Receiver {

	public static final String ACTION_NORMAL = "action.normal";
	public static final String ACTION_LOCAL = "action.local";
	public static final String ACTION_ORDER = "action.order";

//	public static String[] Broadcast = {"普通广播", "有序广播", "本地广播"};

//	@Bind(R.id.recyclerView)
//	LoadMoreRecyclerView recyclerView;

	BroadcastReceiveManager manager;

	@Bind(R.id.checkbox_normal)
	AppCompatCheckBox checkboxNormal;
	@Bind(R.id.checkbox_order)
	AppCompatCheckBox checkboxOrder;
	@Bind(R.id.checkbox_local)
	AppCompatCheckBox checkboxLocal;
	@Bind(R.id.btn_broadcast)
	Button btnBroadcast;
	@Bind(R.id.checkbox_abort)
	AppCompatCheckBox checkboxAbort;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setBaseContentView(R.layout.activity_broadcast);

		showTitleBar(R.string.title_tools_broadcast);

		ButterKnife.bind(this);

		manager = BroadcastReceiveManager.newInstance(true);
		manager.setReceiver(this);
	}

	@OnCheckedChanged(R.id.checkbox_abort)
	void abortChange(boolean isCheck) {
		if(isCheck) {
			if(checkboxNormal.isChecked()) {
				checkboxAbort.setChecked(false);
				Toast.makeText(BroadcastAct.this, "普通广播无法开启广播拦截", Toast.LENGTH_SHORT).show();
				return;
			}
			manager.setAbort(true);
			Toast.makeText(BroadcastAct.this, "开启广播拦截", Toast.LENGTH_SHORT).show();
		} else {
			manager.setAbort(false);
		}
	}

	@OnClick(R.id.checkbox_normal)
	void normalChange() {
		checkboxNormal.setChecked(checkboxNormal.isChecked());
		checkboxLocal.setChecked(false);
		checkboxOrder.setChecked(false);

		if(checkboxNormal.isChecked()) {
			// 普通广播不能拦截
			checkboxAbort.setChecked(false);
			setButton(true, checkboxNormal.getText().toString());
		} else {
			manager.unregisterReceiver(this, false);
			setButton(false, null);
		}
	}

	@OnClick(R.id.checkbox_order)
	void orderChange() {
		checkboxOrder.setChecked(checkboxOrder.isChecked());
		checkboxLocal.setChecked(false);
		checkboxNormal.setChecked(false);

		if(checkboxOrder.isChecked()) {
			setButton(true, checkboxOrder.getText().toString());
		} else {
			manager.unregisterReceiver(this, false);
			setButton(false, null);
		}
	}

	@OnClick(R.id.checkbox_local)
	void localChange() {
		checkboxLocal.setChecked(checkboxLocal.isChecked());
		checkboxNormal.setChecked(false);
		checkboxOrder.setChecked(false);

		if(checkboxLocal.isChecked()) {
			setButton(true, checkboxLocal.getText().toString());
		} else {
			manager.unregisterLocalReceiver(this, false);
			setButton(false, null);
		}
	}

	@OnClick(R.id.btn_broadcast)
	void buttonClick() {
		String btnText = btnBroadcast.getText().toString();
		if(btnText.equals(checkboxNormal.getText().toString())) {
			manager.sendBroadcast(this, new Intent(ACTION_NORMAL));
		} else if(btnText.equals(checkboxOrder.getText().toString())) {
			manager.sendOrderBroadcast(this, new Intent(ACTION_ORDER));
		} else if(btnText.equals(checkboxLocal.getText().toString())) {
			manager.sendLocalBroadcast(this, new Intent(ACTION_LOCAL));
		}
	}

	@OnCheckedChanged(R.id.checkbox_normal)
	void normalChange(boolean isChecked) {
		if(isChecked) {
			manager.registerReceiver(this, ACTION_NORMAL);
		}
	}

	@OnCheckedChanged(R.id.checkbox_local)
	void localChange(boolean isChecked) {
		if(isChecked) {
			manager.registerLocalReceiver(this, ACTION_LOCAL);
		}
	}

	@OnCheckedChanged(R.id.checkbox_order)
	void orderChange(boolean isChecked) {
		if(isChecked) {
			manager.registerReceiver(this, ACTION_ORDER);
		}
	}

	private void setButton(boolean clickable, String text) {
		btnBroadcast.setClickable(clickable);
		if(clickable) {
			btnBroadcast.setText(text);
		} else {
			btnBroadcast.setText("null");
		}
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		manager.unregisterLocalReceiver(this, true);
		manager.unregisterReceiver(this, true);
	}

	@Override
	public void onReceive(Context context, Intent intent) {
		if(intent.getAction().equals(ACTION_NORMAL)) {
			Toast.makeText(BroadcastAct.this, "normal broadcast", Toast.LENGTH_SHORT).show();
		} else if(intent.getAction().equals(ACTION_LOCAL)) {
			Toast.makeText(BroadcastAct.this, "local broadcast", Toast.LENGTH_SHORT).show();
		} else if(intent.getAction().equals(ACTION_ORDER)) {
			Toast.makeText(BroadcastAct.this, "order broadcast", Toast.LENGTH_SHORT).show();
		}
	}

}
