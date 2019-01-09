package com.open.fire.utils_package.qqutils.date;

import com.open.fire.utils_package.qqutils.stage.PreferenceHelper;

import java.util.Calendar;

/**
 * class description
 * <p>
 * <p>
 * 时间工具，不进行时间处理
 * </p>
 *
 * @author shankshu
 * Create on 2018-12-06
 */
public class TimeDetailUtils {
    public TimeDetailUtils() {
        throw new IllegalStateException("you can't instantiate me!");
    }



    /*
     * <p>
     *     封装的简单工具，
     *     判断是否为当天第一次
     * </p>
     * */

    public static boolean isFirstTimeToday() {
        //当天第一次进入
        long time = PreferenceHelper.getInstance().getLong("enter_time", 0);
        PreferenceHelper.getInstance().put("enter_time", System.currentTimeMillis());
        if (time == 0) {
            return true;
        }
        Calendar preEntryTime = Calendar.getInstance();
        preEntryTime.setTimeInMillis(time);
        Calendar curEntryTime = Calendar.getInstance();
        curEntryTime.set(Calendar.HOUR_OF_DAY, 0);
        curEntryTime.set(Calendar.MILLISECOND, 0);
        curEntryTime.set(Calendar.SECOND, 0);
        if (preEntryTime.before(curEntryTime)) {
            return true;
        }
        return false;
    }

    /**
     * <p>规则：
     * 开始时间小于今天的全部显示标准日期格式，最小到分钟
     * 开始时间在今天内的显示 "今天 HH:mm"
     * 开始时间在明天内的显示 "明天 HH:mm"
     * 开始时间在后天内的显示 "后天 HH:mm"
     * 未知情况下默认输出值为 "yyyy.MM.dd HH:mm"
     * </p>
     *
     * @return formatted time string
     */
    public static String getOrderStartTimeString(long currentTimeStamp) {
        long zeroTimeToday = DateUtil.getZeroTimeOfToday();
        long diff = currentTimeStamp - zeroTimeToday;
        long startTimestamp = currentTimeStamp;
        if (diff < 0) {
            return DateUtil.formatDate("yyyy.MM.dd HH:mm", startTimestamp);
        }
        if (diff < DateUtil.MS_ONE_DAY) {
            return "今天" + " " + DateUtil.formatDate("HH:mm", startTimestamp);
        }
        if (diff < DateUtil.MS_ONE_DAY * 2) {
            return "明天" + " " + DateUtil.formatDate("HH:mm", startTimestamp);
        }
        if (diff < DateUtil.MS_ONE_DAY * 3) {
            return "后天" + " " + DateUtil.formatDate("HH:mm", startTimestamp);
        }
        return DateUtil.formatDate("yyyy.MM.dd HH:mm", startTimestamp);
    }


}
