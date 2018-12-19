package com.open.fire.utils_package.qqutils.click;

/**
 * class description
 *
 * @author shankshu
 * Create on 2018-12-11
 */
public class AntiShake {
    private static LimitQueue<OneClickBean> queue = new LimitQueue<OneClickBean>(20);

    /*
    * <p>
    *     获取当前方法名作为key，如果是 switch ，请传入 id
    *     </p>
    * */

    public static boolean check() {
        return check(null);
    }

    public static boolean check(Object o) {
        String flag;
        if (o == null) {
            //获取当前方法名作为key，如果是 switch ，请传入 id
            flag = Thread.currentThread().getStackTrace()[2].getMethodName();
        } else {
            flag = o.toString();
        }
        for (OneClickBean bean : queue.getArrayList()) {
            if (bean.getMethodName().equals(flag)) {
                return bean.check();
            }
        }
        OneClickBean oneClickBean = new OneClickBean(flag);
        queue.offer(oneClickBean);
        return oneClickBean.check();
    }


}


/*
    使用
* if (AntiShake.check(view.getId())) {    //判断是否多次点击
        return;
    }
*
* */
