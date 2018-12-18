package com.open.fire.utils_package.qqutils.textview;

import android.content.Context;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.interfaces.DraweeHierarchy;
import com.facebook.drawee.span.DraweeSpan;
import com.facebook.drawee.span.DraweeSpanStringBuilder;
import com.open.fire.utils_package.base.ContextProvider;
import com.open.fire.utils_package.base.DeviceUtils;

/**
 * class description
 *
 * @author shankshu
 * Create on 2018-12-07
 */
public class FullStackTextView {


    /**
     * 守护勋章
     */
    public static void setGuardMedal(DraweeSpanStringBuilder builder, Context context, int resId) {

        builder.append("#");
        DraweeHierarchy draweeHierarchy = GenericDraweeHierarchyBuilder.newInstance(context.getResources())
                .build();
        DraweeController controller = Fresco.newDraweeControllerBuilder()
                .setUri("res:///" + resId)
                .build();
        builder.setImageSpan(context, draweeHierarchy, controller, builder.length() - 1,
                DeviceUtils.dpToPixel(18), DeviceUtils.dpToPixel(18), false, DraweeSpan.ALIGN_CENTER);
        builder.append(" ");
    }

    /**
     * 活动勋章
     */
    public static void setActiveMedal(DraweeSpanStringBuilder builder, String imageUrl) {

        builder.append("#");
        DraweeHierarchy draweeHierarchy = GenericDraweeHierarchyBuilder.newInstance(ContextProvider.get().getResources())
                .build();
        DraweeController controller = Fresco.newDraweeControllerBuilder()
                .setUri(imageUrl)
                .setAutoPlayAnimations(true)
                .build();
        builder.setImageSpan(ContextProvider.get(), draweeHierarchy, controller, builder.length() - 1,
                DraweeSpanStringBuilder.UNSET_SIZE, DeviceUtils.dpToPixel(16), true, DraweeSpan.ALIGN_CENTER);
        builder.append(" ");
    }



    /**
     * 添加图片 简单用法 。支持gif
     */
    public static void addImage(DraweeSpanStringBuilder builder, Context context, String url) {
        builder.append("#");    // 占位符不能使用空格，图片可能显示不出来 https://github.com/facebook/fresco/issues/1708
        DraweeHierarchy draweeHierarchy = GenericDraweeHierarchyBuilder.newInstance(context.getResources())
                .build();
        DraweeController controller = Fresco.newDraweeControllerBuilder()
                .setUri(url)
                .setAutoPlayAnimations(true)
                .build();
        builder.setImageSpan(context, draweeHierarchy, controller, builder.length() - 1,
                DraweeSpanStringBuilder.UNSET_SIZE, DeviceUtils.dpToPixel(22), true, DraweeSpan.ALIGN_CENTER);
        //占位符的关键在于 setImageSpan 的时候 index -1
    }






    /*
    * 用法
    *  xml
    * <?xml version="1.0" encoding="utf-8"?>
    <com.facebook.drawee.span.SimpleDraweeSpanTextView xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools"
    android:id="@+id/zqm_chat_list_item_content"
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"
    android:layout_marginBottom="2dp"
    android:layout_marginLeft="3dp"
    android:layout_marginRight="3dp"
    android:layout_marginTop="2dp"
    android:background="@drawable/item_chat_barrage_bg"
    android:gravity="center_vertical"
    android:lineSpacingExtra="2dp"
    tools:text="弹幕弹幕弹幕弹幕弹幕弹幕弹幕弹幕弹幕弹幕弹幕弹幕弹幕弹幕" />
    *
    *
    *
    *viewholder
    * class ChatBarrageHolder extends BaseRecyclerViewHolder {
        SimpleDraweeSpanTextView mChatListContentText;

        DraweeSpanStringBuilder.DraweeSpanChangedListener spanChangedListener = new DraweeSpanStringBuilder.DraweeSpanChangedListener() {
            @Override
            public void onDraweeSpanChanged(DraweeSpanStringBuilder builder) {
                // 自动缩放图片可能造成显示不全，需要重新设置内容 https://github.com/facebook/fresco/issues/2025
                mChatListContentText.setDraweeSpanStringBuilder(builder);
            }
        };

        ChatBarrageHolder(View itemView) {
            super(itemView);
            mChatListContentText = findView(R.id.zqm_chat_list_item_content);
        }
    }
    *
    * */

}
