package com.open.fire.pic.mvp.ui;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;

import com.open.fire.pic.R;

/**
 * class description
 *
 * @author shankshu
 * Create on 2018-12-21
 */
public class CustomView extends RelativeLayout {
    public CustomView(Context context) {
        super(context);initView();
    }

    public CustomView(Context context, AttributeSet attrs) {
        super(context, attrs);initView();
    }

    public CustomView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);initView();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public CustomView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initView();
    }

    public void initView(){
        LayoutInflater.from(CustomView.this.getContext()).inflate(R.layout.in,this,true);
    }
}
