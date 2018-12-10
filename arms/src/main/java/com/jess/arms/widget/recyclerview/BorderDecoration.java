package com.jess.arms.widget.recyclerview;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * 边缘边距装饰器，对于列表布局，特别是有Item有内边距的布局，仅在四边添加补偿边距时使用
 *
 * Create on 2017-11-16
 */

public class BorderDecoration extends RecyclerView.ItemDecoration {
    /**
     * 纵向
     * */
    public static final int VERTICAL = 0;
    /**
     * 横向布局
     * */
    public static final int HORIZONTAL = 1;

    // 布局方向
    private int orientation = VERTICAL;
    // 行/列数
    private int spanCount = 1;
    // 距离大小
    private int spacingSize;

    public BorderDecoration(int spanCount, int spacingSize) {
        this(VERTICAL, spanCount, spacingSize);
    }

    public BorderDecoration(int orientation, int spanCount, int spacingSize) {
        this.orientation = orientation;
        this.spanCount = spanCount;
        this.spacingSize = spacingSize;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        int position = parent.getChildLayoutPosition(view);

        int[] rect = getItemOffsetRect(position, state.getItemCount());

        outRect.set(rect[0], rect[1], rect[2], rect[3]);
    }

    private int[] getItemOffsetRect(int position, int itemCount) {
        int left = 0, top = 0, right = 0, bottom = 0;
        //纵向
        if (orientation == VERTICAL) {
            int lastRowStartIndex = itemCount - spanCount + itemCount % spanCount;
            // 只有一列
            if (spanCount == 1) {
                left = right = spacingSize;
            }
            // 纵向第一列
            else if(position % spanCount == 0) {
                left = spacingSize;
            }
            // 纵向最后一列
            else if ((position + 1) % spanCount == 0) {
                right = spacingSize;
            }
            // 纵向第一行
            if(position < spanCount) {
                top = spacingSize;
            }
            // 纵向最后一行
            else if (position >= lastRowStartIndex) {

                bottom = spacingSize;
            }
        }
        // 横向
        else {
            int lastColumnStartIndex = itemCount - spanCount + itemCount % spanCount;
            // 只有一行
            if (spanCount == 1) {
                top = bottom = spacingSize;
            }
            // 横向第一行
            else if(position % spanCount == 0) {
                top = spacingSize;
            }
            // 横向最后一行
            else if((position + 1) % spanCount == 0) {
                bottom = spacingSize;
            }
            // 横向第一列
            if (position < spanCount) {
                left = spacingSize;
            }
            // 横向最后一列
            else if (position >= lastColumnStartIndex) {
                right = spacingSize;
            }
        }
        return new int[]{left, top, right, bottom};
    }
}
