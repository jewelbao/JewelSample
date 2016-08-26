package com.jewelbao.customview.TextView;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.widget.EditText;

/**
 * Created by Administrator on 2016/2/15 0015.
 * com.jewel.customView 
 * 带空格的EditText
 */
public class SpaceEditText extends EditText {

    private boolean isRun = false;
    private String showStr = ""; // 处理后要显示的字符串

    private String spaceStr = ""; // 空格字符串

    private int spaceNum = 1; // 定义空格数
    private int strNum = 4; // 定义每几个字符串插入空格

    public SpaceEditText setSpaceNum(int spaceNum) {
        this.spaceNum = spaceNum;
        setSpaceStr();
        return this;
    }

    public SpaceEditText setStrNum(int strNum) {
        this.strNum = strNum;
        return this;
    }

    public SpaceEditText(Context context) {
        super(context);
        setSpaceStr();
        setBankCardTypeOn();
    }

    public SpaceEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        setSpaceStr();
        setBankCardTypeOn();
    }

    public SpaceEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setSpaceStr();
        setBankCardTypeOn();
    }

    private void setSpaceStr() {
        spaceStr = "";
        while (spaceNum > spaceStr.length())
            spaceStr += spaceStr + " ";
    }

    private void setBankCardTypeOn() {
        SpaceEditText.this.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                if (isRun) {
                    isRun = false;
                    return;
                }
                isRun = true;
                showStr = "";
                String newStr = s.toString();
                newStr = newStr.replaceAll(" ", "");

                int index = 0;
                while ((index + strNum) < newStr.length()) {
                    showStr += (newStr.substring(index, index + strNum) + spaceStr);
                    index += strNum;
                }
                showStr += (newStr.substring(index, newStr.length()));
//                int i = getSelectionStart();

                SpaceEditText.this.setText(showStr);
                try {
//                    if (i % 2 == 0 && before == 0) {
//                        if (i + 4 <= showStr.length()) {
//                            SpaceEditText.this.setSelection(i + 4);
//                        } else {
//                            SpaceEditText.this.setSelection(showStr.length());
//                        }
//                    }
//					else if (before == 1 && i < d.length()) {
//						HPEditText.this.setSelection(i);
//					} else if (before == 0 && i < d.length()) {
//						HPEditText.this.setSelection(i);
//					} else
                    SpaceEditText.this.setSelection(showStr.length());
                } catch (Exception e) {

                }

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
}
