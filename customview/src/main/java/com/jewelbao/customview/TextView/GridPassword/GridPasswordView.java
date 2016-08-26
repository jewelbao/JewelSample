package com.jewelbao.customview.TextView.GridPassword;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.PasswordTransformationMethod;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jewelbao.customview.R;

/**
 Created by PC on 2016/4/28.
 */
public class GridPasswordView extends LinearLayout implements PasswordView
{
	private static final int DEFAULT_LENGTH = 4;
	private static final int DEFAULT_TEXT_SIZE = 16;
	private static final int DEFAULT_LINE_COLOR = 0xaa555555;
	private static final int DEFAULT_GRID_COLOR = 0xffffff;
	private static final String DEFAULT_TRANSFORMATION = "卐";

	private ColorStateList mTextColor;
	private int mTextSize = DEFAULT_TEXT_SIZE;
	private int mLineWidth;
	private int mLineColor;
	private int mGridColor;

	private Drawable mLineDrawable;
	private Drawable mOuterLineDrawable;

	private int mPasswordLength;
	private int mPasswordType;
	private String mPasswordTransformation;

	private String[] mPasswordArray;
	private TextView[] mViewArray;

	private PasswordEditText mInputView;
	private OnPasswordChangedListener mListener;
	private PasswordTransformationMethod mTransformationMethod;

	public GridPasswordView(Context context)
	{
		super(context);
		initViews(context);
		init(context, null, 0);
	}

	public GridPasswordView(Context context, AttributeSet attrs)
	{
		super(context, attrs);
		init(context, attrs, 0);
	}

	public GridPasswordView(Context context, AttributeSet attrs, int defStyleAttr)
	{
		super(context, attrs, defStyleAttr);
		init(context, attrs, defStyleAttr);
	}

	@TargetApi(Build.VERSION_CODES.LOLLIPOP)
	public GridPasswordView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes)
	{
		super(context, attrs, defStyleAttr, defStyleRes);
		init(context, attrs, defStyleAttr);
	}

	private void init(Context context, AttributeSet attrs, int defStyleAttr)
	{
		initAttrs(context, attrs, defStyleAttr);
		initViews(context);
	}

	private void initAttrs(Context context, AttributeSet attrs, int defStyleAttr)
	{
		TypedArray ta = context
				.obtainStyledAttributes(attrs, R.styleable.GridPasswordView, defStyleAttr, 0);

		mTextColor = ta.getColorStateList(R.styleable.GridPasswordView_pwTextColor);
		if(mTextColor == null)
			mTextColor = ColorStateList
					.valueOf(getResources().getColor(android.R.color.primary_text_light));
		int textSize = ta.getDimensionPixelSize(R.styleable.GridPasswordView_pwTextSize, -1);
		if(textSize != -1)
		{
			this.mTextSize = px2sp(context, textSize);
		}

		mLineWidth = (int) ta.getDimension(R.styleable.GridPasswordView_pwLineWidth,
										   dp2px(getContext(), 1));
		mLineColor = ta.getColor(R.styleable.GridPasswordView_pwLineColor, DEFAULT_LINE_COLOR);
		mGridColor = ta.getColor(R.styleable.GridPasswordView_pwGridColor, DEFAULT_GRID_COLOR);
		mLineDrawable = ta.getDrawable(R.styleable.GridPasswordView_pwLineColor);
		if(mLineDrawable == null)
			mLineDrawable = new ColorDrawable(mLineColor);
		mOuterLineDrawable = generateBackgroundDrawable();

		mPasswordLength = ta
				.getInt(R.styleable.GridPasswordView_pwLength, DEFAULT_LENGTH);
		mPasswordTransformation = ta
				.getString(R.styleable.GridPasswordView_pwTransformation);
		if(TextUtils.isEmpty(mPasswordTransformation))
			mPasswordTransformation = DEFAULT_TRANSFORMATION;


		mPasswordType = ta.getInt(R.styleable.GridPasswordView_pwType, 0);

		ta.recycle();

		mPasswordArray = new String[mPasswordLength];
		mViewArray = new TextView[mPasswordLength];
	}

	private void initViews(Context context)
	{
		super.setBackgroundDrawable(mOuterLineDrawable);
		setShowDividers(SHOW_DIVIDER_NONE);
		setOrientation(HORIZONTAL);

		mTransformationMethod = new CustomPasswordTransformationMethod(mPasswordTransformation);
		inflaterViews(context);
	}

	private void inflaterViews(Context context)
	{
		LayoutInflater inflater = LayoutInflater.from(context);
		inflater.inflate(R.layout.password_edittext, this);

		mInputView = (PasswordEditText) findViewById(R.id.inputView);
		mInputView.setMaxEms(mPasswordLength);
		mInputView.addTextChangedListener(textWatcher);
		mInputView.setDelKeyEventListener(onDelKeyEventListener);
		setCustomAttr(mInputView);

		mViewArray[0] = mInputView;

		int index = 1;
		while(index < mPasswordLength)
		{
			View dividerView = inflater.inflate(R.layout.password_divider, null);
			LayoutParams dividerParams = new LayoutParams(mLineWidth, LayoutParams.MATCH_PARENT);
			dividerView.setBackgroundDrawable(mLineDrawable);
			addView(dividerView, dividerParams);

			TextView textView = (TextView) inflater.inflate(R.layout.password_text, null);
			setCustomAttr(textView);
			LayoutParams textViewParams = new LayoutParams(0, LayoutParams.MATCH_PARENT, 1f);
			addView(textView, textViewParams);

			mViewArray[index] = textView;
			index++;
		}

		setOnClickListener(mOnClickListener);
	}

	private void setCustomAttr(TextView view)
	{
		if(mTextColor != null)
			view.setTextColor(mTextColor);
		view.setTextSize(mTextSize);

		int inputType = InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_VARIATION_PASSWORD;
		switch(mPasswordType)
		{

			case 1:
				inputType = InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD;
				break;

			case 2:
				inputType = InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD;
				break;

			case 3:
				inputType = InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_WEB_PASSWORD;
				break;
		}
		view.setInputType(inputType);
		view.setTransformationMethod(mTransformationMethod);
	}

	private OnClickListener mOnClickListener = new OnClickListener()
	{
		@Override
		public void onClick(View v)
		{
			forceInputViewGetFocus();
		}
	};

	private GradientDrawable generateBackgroundDrawable()
	{
		GradientDrawable drawable = new GradientDrawable();
		drawable.setColor(mGridColor);
		drawable.setStroke(mLineWidth, mLineColor);
		return drawable;
	}

	private void forceInputViewGetFocus()
	{
		mInputView.setFocusable(true);
		mInputView.setFocusableInTouchMode(true);
		mInputView.requestFocus();
		InputMethodManager imm = (InputMethodManager) getContext()
				.getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.showSoftInput(mInputView, InputMethodManager.SHOW_IMPLICIT);
	}

	private PasswordEditText.OnDelKeyEventListener onDelKeyEventListener = new PasswordEditText.OnDelKeyEventListener()
	{

		@Override
		public void onDeleteClick()
		{
			for(int i = mPasswordArray.length - 1; i >= 0; i--)
			{
				if(mPasswordArray[i] != null)
				{
					mPasswordArray[i] = null;
					mViewArray[i].setText(null);
					notifyTextChanged();
					break;
				} else
				{
					mViewArray[i].setText(null);
				}
			}
		}
	};

	private TextWatcher textWatcher = new TextWatcher()
	{
		@Override
		public void beforeTextChanged(CharSequence s, int start, int count, int after)
		{

		}

		@Override
		public void onTextChanged(CharSequence s, int start, int before, int count)
		{
			if(s == null)
			{
				return;
			}

			String newStr = s.toString();
			if(newStr.length() == 1)
			{
				mPasswordArray[0] = newStr;
				notifyTextChanged();
			} else if(newStr.length() == 2)
			{
				String newNum = newStr.substring(1);
				for(int i = 0; i < mPasswordArray.length; i++)
				{
					if(mPasswordArray[i] == null)
					{
						mPasswordArray[i] = newNum;
						mViewArray[i].setText(newNum);
						notifyTextChanged();
						break;
					}
				}
				mInputView.removeTextChangedListener(this);
				mInputView.setText(mPasswordArray[0]);
				if(mInputView.getText().length() >= 1)
				{
					mInputView.setSelection(1);
				}
				mInputView.addTextChangedListener(this);
			}
		}

		@Override
		public void afterTextChanged(Editable s)
		{

		}
	};

	@Deprecated
	private OnKeyListener onKeyListener = new OnKeyListener()
	{
		@Override
		public boolean onKey(View v, int keyCode, KeyEvent event)
		{
			if(event.getAction() == KeyEvent.ACTION_DOWN && event
					.getKeyCode() == KeyEvent.KEYCODE_DEL)
			{
				onDelKeyEventListener.onDeleteClick();
				return true;
			}
			return false;
		}
	};

	private void notifyTextChanged()
	{
		if(mListener == null)
			return;

		String currentPsw = getPassword();
		mListener.onTextChanged(currentPsw);

		if(currentPsw.length() == mPasswordLength)
			mListener.onInputFinish(currentPsw);
	}

	@Override
	protected Parcelable onSaveInstanceState()
	{
		Bundle bundle = new Bundle();
		bundle.putParcelable("instanceState", super.onSaveInstanceState());
		bundle.putStringArray("passwordArr", mPasswordArray);
		return bundle;
	}

	@Override
	protected void onRestoreInstanceState(Parcelable state)
	{
		if(state instanceof Bundle)
		{
			Bundle bundle = (Bundle) state;
			mPasswordArray = bundle.getStringArray("passwordArr");
			state = bundle.getParcelable("instanceState");
			mInputView.removeTextChangedListener(textWatcher);
			setPassword(getPassword());
			mInputView.addTextChangedListener(textWatcher);
		}
		super.onRestoreInstanceState(state);
	}

	//TODO
	//@Override
	private void setError(String error)
	{
		mInputView.setError(error);
	}

	/**
	 Return the text the PasswordView is displaying.
	 */
	@Override
	public String getPassword()
	{
		StringBuilder sb = new StringBuilder();
		for(int i = 0; i < mPasswordArray.length; i++)
		{
			if(mPasswordArray[i] != null)
				sb.append(mPasswordArray[i]);
		}
		return sb.toString();
	}

	/**
	 Clear the passwrod the PasswordView is displaying.
	 */
	@Override
	public void clearPassword()
	{
		for(int i = 0; i < mPasswordArray.length; i++)
		{
			mPasswordArray[i] = null;
			mViewArray[i].setText(null);
		}
	}

	/**
	 Sets the string value of the PasswordView.
	 */
	@Override
	public void setPassword(String password)
	{
		clearPassword();

		if(TextUtils.isEmpty(password))
			return;

		char[] pswArr = password.toCharArray();
		for(int i = 0; i < pswArr.length; i++)
		{
			if(i < mPasswordArray.length)
			{
				mPasswordArray[i] = pswArr[i] + "";
				mViewArray[i].setText(mPasswordArray[i]);
			}
		}
	}

	/**
	 Set the enabled state of this view.
	 */
	@Override
	public void setPasswordVisibility(boolean visible)
	{
		for(TextView textView : mViewArray)
		{
			textView.setTransformationMethod(visible ?
													 null :
													 mTransformationMethod);
			if(textView instanceof EditText)
			{
				EditText et = (EditText) textView;
				et.setSelection(et.getText().length());
			}
		}
	}

	/**
	 Toggle the enabled state of this view.
	 */
	@Override
	public void togglePasswordVisibility()
	{
		boolean currentVisible = getPassWordVisibility();
		setPasswordVisibility(!currentVisible);
	}

	/**
	 Get the visibility of this view.
	 */
	private boolean getPassWordVisibility()
	{
		return mViewArray[0].getTransformationMethod() == null;
	}

	/**
	 Register a callback to be invoked when password changed.
	 */
	@Override
	public void setOnPasswordChangedListener(OnPasswordChangedListener listener)
	{
		this.mListener = listener;
	}

	@Override
	public void setPasswordType(PasswordType passwordType)
	{
		boolean visible = getPassWordVisibility();
		int inputType = InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_VARIATION_PASSWORD;
		switch(passwordType)
		{

			case TEXT:
				inputType = InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD;
				break;

			case TEXT_VISIBLE:
				inputType = InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD;
				break;

			case TEXT_WEB:
				inputType = InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_WEB_PASSWORD;
				break;
		}

		for(TextView textView : mViewArray)
			textView.setInputType(inputType);

		setPasswordVisibility(visible);
	}

	@Override
	public void setBackground(Drawable background)
	{
	}

	@Override
	public void setBackgroundColor(int color)
	{
	}

	@Override
	public void setBackgroundResource(int resid)
	{
	}

	@Override
	public void setBackgroundDrawable(Drawable background)
	{
	}


	/**
	 Interface definition for a callback to be invoked when the password changed or is at the maximum
	 length.
	 */
	public interface OnPasswordChangedListener
	{

		/**
		 Invoked when the password changed.

		 @param psw new text
		 */
		void onTextChanged(String psw);

		/**
		 Invoked when the password is at the maximum length.

		 @param psw complete text
		 */
		void onInputFinish(String psw);

	}

	private int px2sp(Context context, float pxValue)
	{
		final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
		return (int) (pxValue / fontScale + 0.5f);
	}

	private int dp2px(Context context, int dp)
	{
		DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
		return (int) ((dp * displayMetrics.density) + 0.5);
	}
}
