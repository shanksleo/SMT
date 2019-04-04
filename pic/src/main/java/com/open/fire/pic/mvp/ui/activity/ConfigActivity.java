package com.open.fire.pic.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.EditText;
import android.widget.TextView;

import com.jess.arms.base.BaseActivity;
import com.jess.arms.base.ContextProvider;
import com.jess.arms.di.component.AppComponent;
import com.open.fire.pic.R;
import com.open.fire.pic.alarm.AlarmWorker;
import com.open.fire.pic.service.AlarmWorkService;
import com.open.fire.utils_package.qqutils.stage.PreferenceHelper;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author shankshu
 * @date 2019/3/30 上午11:10
 */
public class ConfigActivity extends BaseActivity {
    @BindView(R.id.et_time1)
    EditText etTime1;
    @BindView(R.id.et_num1)
    EditText etNum1;
    @BindView(R.id.et_time2)
    EditText etTime2;
    @BindView(R.id.et_num2)
    EditText etNum2;
    @BindView(R.id.et_time3)
    EditText etTime3;
    @BindView(R.id.et_num3)
    EditText etNum3;
    @BindView(R.id.et_pre_num)
    EditText etPreNum;
    @BindView(R.id.tv_save_config)
    TextView tvSaveConfig;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {

    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_config;


    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        etPreNum.setText(PreferenceHelper.getInstance().getString("pre_num"));
        etTime1.setText(PreferenceHelper.getInstance().getString("1_time"));
        etNum1.setText(PreferenceHelper.getInstance().getString("1_num"));
        etTime2.setText(PreferenceHelper.getInstance().getString("2_time"));
        etNum2.setText(PreferenceHelper.getInstance().getString("2_num"));
        etTime3.setText(PreferenceHelper.getInstance().getString("3_time"));
        etNum3.setText(PreferenceHelper.getInstance().getString("3_num"));
    }


    @OnClick(R.id.tv_save_config)
    public void onViewClicked() {
        PreferenceHelper.put("pre_num", etPreNum.getText().toString());
        PreferenceHelper.put("1_time", etTime1.getText().toString());
        PreferenceHelper.put("1_num", etNum1.getText().toString());
        PreferenceHelper.put("2_time", etTime2.getText().toString());
        PreferenceHelper.put("2_num", etNum2.getText().toString());
        PreferenceHelper.put("3_time", etTime3.getText().toString());
        PreferenceHelper.put("3_num", etNum3.getText().toString());
        AlarmWorker.reflush();
        ConfigActivity.this.finish();

    }
}
