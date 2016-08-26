package com.jewelbao.customview.TextView.GridPassword;

/**
 Created by PC on 2016/4/28.
 */
public interface PasswordView
{
	String getPassword();
	void clearPassword();
	void setPassword(String password);
	void setPasswordVisibility(boolean visibility);
	void togglePasswordVisibility();
	void setOnPasswordChangedListener(GridPasswordView.OnPasswordChangedListener listener);
	void setPasswordType(PasswordType passwordType);
}
