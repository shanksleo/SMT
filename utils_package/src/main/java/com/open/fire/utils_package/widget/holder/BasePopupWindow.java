package com.open.fire.utils_package.widget.holder;

import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;

import com.jess.arms.R;

/**
 * class description
 *
 * @author shankshu
 * Create on 2018-12-05
 */
public class BasePopupWindow extends PopupWindow {

    private View mMainView;
    private Activity mActivity;
    private Context mContext;
    private View mParent;
    private RecyclerView mRecyclerView;

    private int orientation;


    public BasePopupWindow(Activity activity, View parent) {
        super(activity);
        this.mActivity = activity;

        orientation = activity.getRequestedOrientation();


        this.mParent = parent;
        this.mContext = activity;
    }
/*

        private void initView() {
            mMainView = View.inflate(mActivity, R.layout.popup_window_qupai_more, null);
        */
/*mRecyclerView = (RecyclerView) mMainView.findViewById(R.id.recycler_view);
        //取消 RecyclerView 刷新动画,解决局部刷新,item 闪动问题
        ((SimpleItemAnimator)mRecyclerView.getItemAnimator()).setSupportsChangeAnimations(false);
        mRecyclerView.setLayoutManager(new GridLayoutManager(mActivity, 4));
        mQupaiMorePopAdapter = new QupaiMorePopAdapter(this);
        mQupaiMorePopAdapter.setCountDownListener(new QupaiMorePopAdapter.OnSlotCountDownListener() {
            @Override
            public void onComplete() {
                updateSlotMachineStatus();
            }
        });
        mRecyclerView.setAdapter(mQupaiMorePopAdapter);*//*


        }

        private void clearRecyclerViewAnim() {
            mRecyclerView = (RecyclerView) mMainView.findViewById(R.id.recycler_view);
            //取消 RecyclerView 刷新动画,解决局部刷新,item 闪动问题
            ((SimpleItemAnimator) mRecyclerView.getItemAnimator()).setSupportsChangeAnimations(false);
        }
*/

    public void setMainView(View view) {
        this.mMainView = view;
        setupView();
    }

    private void setupView() {


        final float scale = mActivity.getResources().getDisplayMetrics().density;
        int height = (int) ((120 * scale) + 0.5f);
        // 设置SelectPicPopupWindow的View
        this.setContentView(mMainView);
        // 设置SelectPicPopupWindow弹出窗体的宽
        this.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        // 设置SelectPicPopupWindow弹出窗体的高
//        this.setHeight(height);
        this.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
//        this.setAnimationStyle(R.style.AnimBottom);
        // 设置SelectPicPopupWindow弹出窗体可点击
        this.setFocusable(true);
        // 设置SelectPicPopupWindow弹出窗体动画效果
        // this.setAnimationStyle(R.style.AnimBottom);
        // 实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(00000000);
        // 设置SelectPicPopupWindow弹出窗体的背景
        this.setBackgroundDrawable(dw);
//        this.showAtLocation(mParent, Gravity.RIGHT | Gravity.CENTER_VERTICAL, 0, 0);
        // mMenuView添加OnTouchListener监听判断获取触屏位置如果在选择框外面则销毁弹出框
        if (mMainView == null || mMainView.findViewById(R.id.main_view) == null) {
            return;
        }
        mMainView.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                int top = mMainView.findViewById(R.id.main_view).getTop();
                int left = mMainView.findViewById(R.id.main_view).getLeft();
                int x = (int) event.getX();
                int y = (int) event.getY();
                int w = mMainView.findViewById(R.id.main_view).getWidth();
                int h = mMainView.findViewById(R.id.main_view).getHeight();
                Rect r = new Rect(left, top, left + w, top + h);
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (!checkInRect(x, y, r))
                        dismiss();
                }
                return true;
            }
        });
    }

    private boolean checkInRect(int x, int y, Rect r) {
        if ((x > r.left && x < r.left + r.width()) && (y > r.top && y < r.top + r.height()))
            return true;
        else
            return false;
    }

    public void show() {
        if (mMainView != null) {
            AnimationUtils.slideToUp(mMainView);
        }
        this.showAtLocation(mParent, Gravity.BOTTOM, 0, 0);

    }

    /**
     * Gravity.BOTTOM
     */

    public void showFromPlace(int gravity) {
        this.showAtLocation(mParent, gravity, 0, 0);

    }
}



