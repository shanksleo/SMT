package com.jess.arms.componentize;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * 应用内H5 Scheme跳转处理类
 * <p> 各个Module将自己的Scheme跳转注册到AppScheme内，需要执行跳转时使用AppScheme处理跳转链接 </p>
 *
 * @author linxiao
 * Create on 2018/6/4.
 */
public class AppSchemes {
    
    private static final String TAG = AppSchemes.class.getSimpleName();
    
    public static abstract class SchemeHandler {
    
        protected String  TAG;
    
        public SchemeHandler() {
            TAG = this.getClass().getSimpleName();
        }
        
        /**
         * Scheme处理方法，返回值标识此次事件是否被消费
         * @param urlScheme 需要处理的Scheme
         * @return true - 事件被消费，停止向下传递，false - 事件未被消费，继续向下传递
         */
        public abstract boolean onHandleUrlScheme(Context context, String urlScheme, String from);
    }
    
    // scheme处理节点链
    private static final List<SchemeHandler> schemeHandlerList = new ArrayList<>();
    
    
    
    public static boolean handleUrlScheme(Context context, String urlScheme) {
        return handleUrlScheme(context, urlScheme, "");
    }
    
    public static boolean handleUrlScheme(Context context, String urlScheme, String from) {
        for (SchemeHandler handler : schemeHandlerList) {
            boolean consumed = handler.onHandleUrlScheme(context, urlScheme, from);
            if (consumed) {
                return true;
            }
        }
        
        Log.i(TAG, "handleUrlScheme: scheme unhandled, scheme = " + urlScheme);
        return false;
    }
    
    
    /**
     * 注册默认Scheme处理节点到AppSchemes的协议处理责任链
     * <p>每个类型的节点只能注册一个实例</p>
     * @param handler 协议处理节点实例
     */
    public static void registerHandler(SchemeHandler handler) {
        for (SchemeHandler h : schemeHandlerList) {
            if (h.getClass().getName().equals(handler.getClass().getName())) {
                return;
            }
        }
        schemeHandlerList.add(handler);
    }
    
    /**
     * 从AppSchemes的协议处理责任链移除某个类型的节点
     * @param clazz 处理节点类型
     */
    public static void unregisterHandler(@NonNull Class<? extends SchemeHandler> clazz) {
        Iterator<SchemeHandler> iterator = schemeHandlerList.iterator();
        while (iterator.hasNext()) {
            if (iterator.next().getClass().getName().equals(clazz.getName())) {
                iterator.remove();
            }
        }
    }
    
}
