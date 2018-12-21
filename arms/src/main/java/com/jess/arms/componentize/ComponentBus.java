package com.jess.arms.componentize;

import android.support.annotation.NonNull;
import android.support.v4.util.ArrayMap;
import android.text.TextUtils;
import android.util.Log;

import java.util.Map;

/**
 * 组件总线
 * <p> 工程组件化模块间调用核心模块，生命周期与Application一致
 *
 * </p>
 *
 * @author linxiao
 * Create on 2018/5/3.
 */
public class ComponentBus {
    
    private static final String TAG = ComponentBus.class.getSimpleName();
    
    // 组件对象缓存Map
    private static final Map<String, IComponent> COMPONENTS = new ArrayMap<>();
    
    //加载类时自动调用初始化：注册所有组件
    //通过auto-register插件生成组件注册代码
    //生成的代码如下:
//  static {
//      register(new ComponentA());
//      register(new ComponentAA());
//  }
    
    /**
     * 总线中是否已注册组件
     * @param clazz 组件一级接口类型
     * @return 是否注册
     */
    public static boolean contains(@NonNull Class<? extends IComponent> clazz) {
        String key = clazz.getName();
        return COMPONENTS.containsKey(key);
    }
    
    /**
     * 获取组件
     * @param clazz 组件实现接口类型
     * @return 组件对象， null - 没有注册此组件
     */
    @SuppressWarnings("unchecked")
    public static <T extends IComponent> T get(@NonNull Class<T> clazz) {
//        if (!contains(clazz)) {
//            Log.e(TAG, "get: component not found");
//            return null;
//        }
        String key = clazz.getName();
//        if (TextUtils.isEmpty(key)) {
//            Log.e(TAG, "get: empty component name");
//            return null;
//        }
        return (T) COMPONENTS.get(key);
    }
    
    /**
     * 注册组件
     * <p>此方法主要由AutoRegister框架在编译期修改字节码调用，AutoRegister会扫描工程内所有IComponent的实现类
     * 并在ComponentBus的static代码块中生成注册代码
     * </p>
     * @param component 组件对象
     */
    public static void register(IComponent component) {
        if (component == null) {
            return;
        }
        String key;
        Class<?> clazz = null;
        for (Class<?> clz : component.getClass().getInterfaces()) {
            if (clz.equals(IComponent.class)) {
                continue;
            }
            clazz = clz;
        }
        if (clazz != null) {
            key = clazz.getName();
        }
        else {
            key = component.getClass().getName();
        }
        if (TextUtils.isEmpty(key)) {
            Log.e(TAG, "register: empty component name");
            return;
        }
        IComponent old = COMPONENTS.put(key, component);
        if (old != null) {
            Log.e(TAG, "register: component has been replaced!");
        }
        component.onRegister();
    }
    
    /**
     * 解除注册组件
     *
     * @param component 组件对象
     */
    public static void unregister(IComponent component) {
        if (component == null) {
            Log.e(TAG, "unregister: null object");
            return;
        }
        if (!contains(component.getClass())) {
            Log.e(TAG, "unregister: component not registered yet");
            return;
        }
        IComponent com = COMPONENTS.remove(component.getClass().getSimpleName());
        com.onUnregister();
    }
}
