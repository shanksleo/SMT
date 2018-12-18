package com.open.fire.utils_package.qqutils;

import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.widget.TextView;

import com.open.fire.utils_package.R;
import com.open.fire.utils_package.base.ContextProvider;
import com.open.fire.utils_package.base.DeviceUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * class description
 *
 * @author shankshu
 * Create on 2018-12-06
 */
public class TextViewUtils {
    public TextViewUtils() {
        throw new IllegalStateException("you can't instantiate me!");
    }

    /*
     * 仅在需要改变字体颜色和粗体使用
     * <p>
     *     仅在需要改变字体颜色和粗体使用
     *     如果需要图片支持，请使用spinnerbuilder
     * </P>
     * */
    public static void colorfullAndBoldText(TextView textView, boolean isBold) {
        //如果需要直接
        String textStr = "测试啊本月已成功邀请 <strong><font color=\"#FF0000\">" + 100 + "</font><strong>人";
        textView.setText(Html.fromHtml(textStr));

        int expColor = ContextCompat.getColor(ContextProvider.get(), android.R.color.holo_red_dark);
        textView.setText(Html.fromHtml(
                "距离下一次升级还差 <font color=" + expColor + ">" +
                        "关键字" + "</font> 经验值")
        );

    }


    /*
        限制支付长度，末尾...
        android:singleLine="true"。//这两个必须一起用
        android:ellipsize="end"
        android:maxEms="8"     //8个中文或者16个英文
    * */


    /*
     *  改变字体颜色
     * */
    public static SpannableStringBuilder setTextAndColor(String text, int resIdForColor) {
        int resId = ContextCompat.getColor(ContextProvider.get(), resIdForColor);
        SpannableStringBuilder builder = new SpannableStringBuilder();
        if (text == null || text.equals("")) {
            builder.append("");
            return builder;
        }
        builder.append(text);
        ForegroundColorSpan fromNameColor = new ForegroundColorSpan(resId);
        builder.setSpan(fromNameColor, 0, text.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return builder;
    }


    /*
     * 设置drawable padding
     *
     * 如果需要设置动态缩放 DrawableProvider
     * */
    public static void setDrawablePadding(TextView textView, int resId) {
        Drawable drawable = ContextCompat.getDrawable(ContextProvider.get(), R.drawable.icon_sina);
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        textView.setCompoundDrawables(null, null, drawable, null);
        textView.setCompoundDrawablePadding((int) DeviceUtils.dpToPixel(ContextProvider.get(), 12));
    }


    /*
     * 利用正则表达式判断字符串是否是数字
     * 更多的正则表达式 RegexUtils
     * @param str
     * @return
     * */
    public boolean isNumeric(String str) {
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        Pattern pattern = Pattern.compile("[0-9]*");
        Matcher isNum = pattern.matcher(str);
        if (!isNum.matches()) {
            return false;
        }
        return true;
    }


}
