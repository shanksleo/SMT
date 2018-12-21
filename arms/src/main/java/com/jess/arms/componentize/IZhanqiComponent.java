package com.jess.arms.componentize;

import android.content.Context;
import android.graphics.drawable.Drawable;

import java.util.Map;

/**
 * zhanqi module 对外提供api
 * <p> class description </p>
 *
 * @author linxiao
 * Create on 2018/5/3.
 */
public interface IZhanqiComponent extends IComponent {
    
    /**
     * 启动登录页
     */
    void loginRequest(Context context);

    /**
     * 启动绑定手机页面
     */
    void requestBindMobile(Context context);

    /**
     * 进入"我的"页面
     */
    void startMinePage(Context context);
    
    /**
     * 进入IM会话列表页面
     * @param context
     */
    void openIMConversationList(Context context);
    
    /**
     * 进入IM页面
     */
    void startIMChat(Context context, int uid, Map<String, Object> params);
    
    /**
     * 进入直播间
     * <p>以下参数足以判断是进入美拍直播间还是游戏直播间，其余直播间类型与陪玩无关</p>
     * @param roomId roomId
     * @param roomStyle verscr
     */
    void startLiveRoom(Context context, int roomId, int roomStyle);

    /**
     *进入用户粉丝列表
     */
    void startUserFans(Context context, int uid, int fuid);

    /**
     *进入用户关注列表
     */
    void startUserFollow(Context context, int uid, int fuid);
    
    /**
     * 打开Web页面
     * @param context context
     * @param title title
     * @param url url
     */
    void openWebPage(Context context, String title, String url);
    
    
    /**
     * 获取守护icon
     * @param guard guard id
     * @return drawable
     */
    String getFansGuardIconUri(Context context, int guard);
    
    /**
     * 获取粉丝等级icon
     *
     */
    Drawable getFansLevelIcon(Context context, int fansLevel, String fansTitle);
    
    /**
     * 获取用户全站等级icon
     */
    String getSLevelIconUri(Context context, int pos, int slevel);
}
