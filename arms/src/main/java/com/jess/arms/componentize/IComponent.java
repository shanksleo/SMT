package com.jess.arms.componentize;

/**
 * 组件抽象模型
 * <p> 用于module级别的API提供 </p>
 *
 * @author linxiao
 * Create on 2018/5/3.
 */
public interface IComponent {
    
    /**
     * 在组件注册时由{@link ComponentBus}调用
     * <strong>请勿在外部调用此方法</strong>
     */
    void onRegister();
    
    /**
     * 在组件解除注册时由{@link ComponentBus}调用
     * <strong>请勿在外部调用此方法</strong>
     */
    void onUnregister();
}
