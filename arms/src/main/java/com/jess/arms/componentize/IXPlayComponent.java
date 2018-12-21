package com.jess.arms.componentize;


import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;

/**
 * XPlay module 对外提供api
 * <p> class description </p>
 *
 * @author linxiao
 * Create on 2018/5/3.
 */
public interface IXPlayComponent extends IComponent {
    
    Fragment getXPlayHomePage();

    /**
     * 跳转直播开车
     */
    void openXPlayHotLive(Context context);
    /**
     * 跳转陪玩达人主页
     */
    void openXPlayerProfile(Context context, int uid);
    
    /**
     * 打开我的陪玩主页
     */
    void openMyXPlayProfile(Context context);
    
    /**
     * 跳转下单
     */
    void generateXPlayOrder(Context context, int gameItemId);
    
    /**
     * 跳转陪玩项目详情
     */
    void openGameItemDetail(Context context, int gameItemId);
    
    /**
     * 打开订单消息页面
     */
    void openOrderMessagePage(Context context);

    /**
     * 打开订单详情页面
     */
    void openOrderDetail(Activity context, String orderId, boolean isPlayer, int requestCode);

    /**
     * IM跳转陪玩达人主页
     */
    void openXPlayerProfile(Activity context, int uid, int requestCode);
}
