package com.open.fire.utils_package.app;

/**
 * class description
 * <p>双重校验锁实现单例（线程安全）</p>
 *
 * @author shankshu
 * Create on 2019-01-09
 */
public class BaseSingleton {

    //指令重排 在单线程上不会出问题，但是在多线程下 会导致一个线程获得 未初始化 的对象
    private volatile static BaseSingleton uniqueInstance;

    private BaseSingleton() {
    }

    public static BaseSingleton getUniqueInstance(){
        //先判断对象是否已经实例过，没有实例过才能进入加锁代码
        if (uniqueInstance == null ) {
            synchronized (BaseSingleton.class) {
                if (uniqueInstance == null) {
                    uniqueInstance = new BaseSingleton();
                    {
                        //uniqueInstance = new BaseSingleton();
                        //实际上是按照三步来执行的。
                        //1。为uniqueInstance分配内存空间
                        //2。初始化uniqueInstance
                        //3。将uniqueInstance指向分配内存地址


                        //但是因为jvm 的指令重排的特性，执行顺序会变成1-3-2




                    }


                }
            }
        }
        return uniqueInstance;
    }



    /*  什么是指令重排？

        在计算机执行指令的顺序在经过程序编译器编译之后形成的指令序列，
        一般而言，这个指令序列是会输出确定的结果；
        以确保每一次的执行都有确定的结果。
        但是，一般情况下，CPU和编译器为了提升程序执行的效率，
        会按照一定的规则允许进行指令优化，在某些情况下，这种优化会带来一些执行的逻辑问题，
        主要的原因是代码逻辑之间是存在一定的先后顺序，
        在并发执行情况（多线程）下，会发生二义性，即按照不同的执行逻辑，
        会得到不同的结果信息。




*/
}
