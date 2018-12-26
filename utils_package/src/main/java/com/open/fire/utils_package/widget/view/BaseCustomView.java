package com.open.fire.utils_package.widget.view;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.support.annotation.IdRes;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;

import com.open.fire.utils_package.R;

import java.lang.ref.WeakReference;
import java.util.List;

/**
 * class description
 *
 * @author shankshu
 * Create on 2018-12-21
 */
public class BaseCustomView extends RelativeLayout {
    private WeakReference<Activity> mActivityWeakReference;
    List<?> mObjects;
    private View mView;

    public BaseCustomView(Context context) {
        super(context);
        initView(context);
    }

    public BaseCustomView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public BaseCustomView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public BaseCustomView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initView(context);
    }

    private void initView(Context context) {
        if (context instanceof Activity) {
            mActivityWeakReference = new WeakReference<Activity>((Activity) context);
        }
        //最后一个参数必须为true
        //root 父布局，如果不为空的话。attach to root .true 就会增加到父布局 false 就不会
        mView = LayoutInflater.from(context).inflate(R.layout.layout_share_view, this, true);
//        ButterKnife.bind(mView, this);
        mView.setVisibility(GONE);
    }

    public int setContentView(@IdRes int resId) {
        return resId;
    }


    @Override
    protected void onDetachedFromWindow() {
        mActivityWeakReference = null;
        super.onDetachedFromWindow();
    }
}
