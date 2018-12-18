package com.open.fire.utils_package.widget.recyclerview;

import android.support.annotation.NonNull;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * RecyclerView 工具类设置性能优化
 * https://blog.csdn.net/a8688555/article/details/79634295
 * https://blog.csdn.net/limonzet/article/details/56665727
 * https://blog.csdn.net/a8688555/article/details/79634295
 *
 * @author shankshu
 * Create on 2018-12-18
 */
public class RecyclerViewUtils {


    /*
     * 自动滚动到底部
     * mBarrageList.smoothScrollToPosition(chatRoomBarrageListAdapter.getDataSource().size() - 1)
     * */

    /*
        你想要控制其显示的方式，请通过布局管理器LayoutManager
        你想要控制Item间的间隔（可绘制），请通过ItemDecoration
        你想要控制Item增删的动画，请通过ItemAnimator
    * */

    /*
        生命周期

        一个RecyclerView的Item加载是有顺序的，类似于Activity的生命周期（姑且这么叫），具体可以对adapter的每个方法进行重写打下日志进行查看，具体大致为：

        getItemViewType(获取显示类型，返回值可在onCreateViewHolder中拿到，以决定加载哪种ViewHolder)

        onCreateViewHolder(加载ViewHolder的布局)

        onViewAttachedToWindow（当Item进入这个页面的时候调用）

        onBindViewHolder(将数据绑定到布局上，以及一些逻辑的控制就写这啦)

        onViewDetachedFromWindow（当Item离开这个页面的时候调用）

        onViewRecycled(当Item被回收的时候调用)
    */

    /*
    RecyclerView与各种异步图片加载框架不兼容，导致滑动item时，图片时有时无
    * 原因是，网络图片框架自定的ImageView中，覆写了onDetachedFromWindow方法，
    * 目的是为了在ImageView退出屏幕时及时回收Bitmap。
    * 而在RecyclerView中，当一个item被滑动到刚好看不见的位置时，
    * 触发了该item及其子View的onDetachedFromWindow，同样也就调用了setImageDrawable(null)。
    * 但是，RecyclerView.Adapter.onViewRecycled方法没有立刻被调用，而要等到继续滑动RecyclerView时才调用。
    * 也就是说，RecyclerView没有立即回收已经不在显示区域的item。
    * 如果此时将该item拉回，也不会再调用RecyclerView.Adapter.onBindViewHolder，
    * 也就是图片消失之后就不会再显示了，出现了开头提到的问题。

    *
    * */


    /**
     * 当我们确定Item的改变不会影响RecyclerView的宽高的时候可以设置setHasFixedSize(true)，
     * 并通过Adapter的增删改插方法去刷新RecyclerView，而不是通过notifyDataSetChanged()。
     * （其实可以直接设置为true，当需要改变宽高的时候就用notifyDataSetChanged()去整体刷新一下）
     *
     * @param recyclerView
     * @param layoutManager
     */
    public static void configRecyclerView(final RecyclerView recyclerView
            , RecyclerView.LayoutManager layoutManager) {
        recyclerView.setLayoutManager(layoutManager);
        //如果可以确定每个item的高度是固定的，设置这个选项可以提高性能
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        //也可以取消动画
        recyclerView.setItemAnimator(null);
        //取消 到顶拉动效果
        recyclerView.setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER);
    }

    /*
        我们在做需求的时候，很大频率上用到局部刷新item：
        adapter.notifyItemChanged(position);
        但是会觉得卡顿， 这个时候我们只需要在这个方法在加一个参数就解决了
        adapterObtainCardLibrary.notifyItemChanged(position, 0);
    * */
    public static void changeRecyclerView(RecyclerView.Adapter adapter, int postion) {
        adapter.notifyItemChanged(postion, 0);
    }


    /*
     * 图片加载的优化，不仅仅是图片加载，应该说是列表在滚动过程中，如果布局很复杂，而且样式也很多，那就需要考虑滚动的时候不做复杂布局及图片的加载，尽量减少滚动过程中的耗时操作，这样滚动停止的时候再加载可见区域的布局，因为这个时候是停止状态，即使略微耗时一些用户的感知也是比较小的，就会给人一种不卡的假象。
        ，当滚动停止时，刷新界面，实现加载。
     */
    public static void addRecyclerViewScrollingListener(RecyclerView recyclerView, RecyclerView.Adapter adapter) {
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                switch (newState) {
                    case RecyclerView.SCROLL_STATE_IDLE: // The RecyclerView is not currently scrolling.
                        //对于滚动不加载图片的尝试
//                        adapter.setScrolling(false);
                        adapter.notifyDataSetChanged();
                        break;
                    case RecyclerView.SCROLL_STATE_DRAGGING: // The RecyclerView is currently being dragged by outside input such as user touch input.
//                        adapter.setScrolling(false);
                        break;
                    case RecyclerView.SCROLL_STATE_SETTLING: // The RecyclerView is currently animating to a final position while not under
//                        adapter.setScrolling(true);
                        break;
                }


            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (isSlideToBottom(recyclerView)) {
                    //是否已经滚动到底部
                }

            }
        });
    }

    protected static boolean isSlideToBottom(RecyclerView recyclerView) {
        if (recyclerView == null) return false;
        if (recyclerView.computeVerticalScrollExtent() + recyclerView.computeVerticalScrollOffset() >= recyclerView.computeVerticalScrollRange())
            return true;
        return false;
    }


    /*
     *       notifyDataSetChanged时导致图片闪烁
     *       原理 ： 给ImageView和图片做了一个tag绑定，检测到是url没变时，不再重新加载图片，也就不用重新计算、绘制，这样就避免了图片闪烁
     * */
    public static void fixAllImageSpark(RecyclerView.Adapter adapter) {
        adapter.setHasStableIds(true);
        adapter.notifyDataSetChanged();
    }

    public static void isFastScrolling(RecyclerView recyclerView) {
        recyclerView.setOnFlingListener(new RecyclerView.OnFlingListener() {
            @Override
            public boolean onFling(int velocityX, int velocityY) {
                if (Math.abs(velocityY) > 4000) {
                    //是快速滑动
                }
                return false;
            }
        });

    }

    public static void fixScrollView(RecyclerView recyclerView) {
        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()) {
            /**
             * 重写此方法解决ScrollView嵌套RecyclerView滑动卡顿的问题
             */
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });

    }

    /*
    * .RecyclerView 预加载，getExtraLayoutSpace
    一开始我做这个漫画预览界面的时候就要用到提前加载图片
    * */
    public static void getExtraView(RecyclerView recyclerView) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(recyclerView.getContext()) {
            @Override
            protected int getExtraLayoutSpace(RecyclerView.State state) {
                return 300;
            }
        };

    }

    /*
     * 大内存优化
     * */
    public static void update(RecyclerView recyclerView) {
        recyclerView.setItemViewCacheSize(30);
        recyclerView.setDrawingCacheEnabled(true);
        recyclerView.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);

    }


}
