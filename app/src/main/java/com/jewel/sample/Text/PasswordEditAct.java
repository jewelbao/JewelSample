package com.jewel.sample.Text;

import android.os.Bundle;

import com.jewel.base.JBaseAct;
import com.jewel.sample.R;
import com.jewelbao.customview.TextView.GridPassword.GridPasswordView;
import com.jewelbao.customview.TextView.GridPassword.PasswordType;
import com.jewelbao.library.utils.JLog;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnItemSelected;

/**
 Created by PC on 2016/4/29.
 */
public class PasswordEditAct extends JBaseAct
{
	@Bind(R.id.pw_passwordType)
	GridPasswordView pwPasswordType;
	@Bind(R.id.pw_normal_twice)
	GridPasswordView pwNormalTwice;

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setBaseContentView(R.layout.activity_password);

		showTitleBar(R.string.title_jump_text);

		ButterKnife.bind(this);
		onPwdChangedTest();
	}

	@OnItemSelected(R.id.sp_pw_type)
	public void onTypeSelected(int position)
	{
		switch(position)
		{
			case 0:
				pwPasswordType.setPasswordType(PasswordType.NUMBER);
				break;
			case 1:
				pwPasswordType.setPasswordType(PasswordType.TEXT);
				break;
			case 2:
				pwPasswordType.setPasswordType(PasswordType.TEXT_VISIBLE);
				break;
			case 3:
				pwPasswordType.setPasswordType(PasswordType.TEXT_WEB);
				break;
		}
	}

	@OnCheckedChanged(R.id.pw_visibility_switcher)
	public void onCheckChanged(boolean isChecked)
	{
		pwPasswordType.togglePasswordVisibility();
	}

	boolean isFirst = true;
	String firstPwd;

	// Test GridPasswordView.clearPassword() in OnPasswordChangedListener.
	// Need enter the password twice and then check the password , like Alipay
	void onPwdChangedTest()
	{
		pwNormalTwice.setOnPasswordChangedListener(new GridPasswordView.OnPasswordChangedListener()
		{
			@Override
			public void onTextChanged(String psw)
			{
				if(psw.length() == 4 && isFirst)
				{
					pwNormalTwice.clearPassword();
					isFirst = false;
					firstPwd = psw;
				} else if(psw.length() == 4 && !isFirst)
				{
					if(psw.equals(firstPwd))
					{
						JLog.d("The password is: " + psw);
					} else
					{
						JLog.d("password doesn't match the previous one, try again!");
						pwNormalTwice.clearPassword();
						isFirst = true;
					}
				}
			}

			@Override
			public void onInputFinish(String psw) { }
		});
	}
}
