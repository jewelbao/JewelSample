package com.jewel.sample.Text;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.EditText;

import com.jewel.base.JBaseAct;
import com.jewel.sample.R;
import com.jewelbao.customview.TextView.SpaceEditText;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2016/2/15 0015.
 * com.jewel.sample.Text 
 * 自动空格EditText示例
 */
public class SpaceEditAct extends JBaseAct
{

    @Bind(R.id.et_str_num)
    EditText etStrNum;
    @Bind(R.id.et_space_num)
    EditText etSpaceNum;
    @Bind(R.id.et_space)
    SpaceEditText etSpace;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setBaseContentView(R.layout.activity_spaceedit);

        showTitleBar(R.string.title_space_edit);

        ButterKnife.bind(this);
    }

    @OnClick(R.id.btn_space_ok)
    public void makeSpaceNum() {
        if(TextUtils.isEmpty(etSpaceNum.getText())) {
            return;
        }

        int num = Integer.parseInt(etSpaceNum.getText().toString());

        etSpace.setSpaceNum(num);
    }

    @OnClick(R.id.btn_str_ok)
    public void makeStrNum() {
        if(TextUtils.isEmpty(etStrNum.getText())) {
            return;
        }

        int num = Integer.parseInt(etStrNum.getText().toString());

        etSpace.setStrNum(num);
    }
}
