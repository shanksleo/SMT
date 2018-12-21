package com.open.fire.utils_package.widget.view;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;

import com.open.fire.utils_package.R;

import java.lang.ref.WeakReference;

/**
 * class description
 *
 * @author shankshu
 * Create on 2018-12-21
 */
public class CustomView extends RelativeLayout {
    private WeakReference<Activity> mActivtyWeakReference;
    private View mView;

    public CustomView(Context context) {
        super(context);
        initView(context);
    }

    public CustomView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public CustomView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public CustomView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initView(context);
    }

    private void initView(Context context) {
        if (context instanceof Activity) {
            mActivtyWeakReference = new WeakReference<Activity>((Activity) context);
        }
        //最后一个参数必须为true
        mView = LayoutInflater.from(context).inflate(R.layout.layout_share_view, this, true);
//        ButterKnife.bind(mView, this);
        mView.setVisibility(GONE);
    }

    public void setContentView() {

    }


    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
    }
}
