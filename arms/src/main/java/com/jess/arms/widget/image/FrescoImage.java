package com.jess.arms.widget.image;

import android.annotation.TargetApi;
import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.generic.RoundingParams;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.jess.arms.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 */

public class FrescoImage extends SimpleDraweeView {
    public FrescoImage(Context context, GenericDraweeHierarchy hierarchy) {
        super(context, hierarchy);
    }

    public FrescoImage(Context context) {
        super(context);
    }

    public FrescoImage(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FrescoImage(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public FrescoImage(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    /**
     *  显示本地资源
     * */
    public void setImageResource(int resId) {
        setImageURI(Uri.parse("res://" + this.getContext().getPackageName() + "/" + resId));
    }

    /**
     *  设置圆角边框宽度
     * */
    public void setRoundBorderWidth(int color, int width) {
        RoundingParams roundingParams = this.getHierarchy().getRoundingParams();
        if(null == roundingParams) {
            roundingParams = RoundingParams.fromCornersRadius(0.0f);
        }
        roundingParams.setBorder(color, width);
        roundingParams.setRoundAsCircle(true);
        this.getHierarchy().setRoundingParams(roundingParams);
    }

    /**
     *  设置矩形边框宽度
     * */
    public void setRectBorderColorAndWidth(int color, int width ) {
        RoundingParams roundingParams = this.getHierarchy().getRoundingParams();
        if(null == roundingParams) {
            roundingParams = RoundingParams.fromCornersRadius(0.0f);
        }
        roundingParams.setBorder(color, width);
        roundingParams.setRoundAsCircle(false);
        this.getHierarchy().setRoundingParams(roundingParams);
    }

    /*
    * 增加对本地的gif 支持
    * */
    public void setGifResource(int resId){
        String uriString = "res://" +this.getContext().getPackageName()+"/" +resId;
        Uri uri = Uri.parse(uriString);
        DraweeController controller = Fresco.newDraweeControllerBuilder()
                .setUri(uri)
                .setAutoPlayAnimations(true)
                .build();
        FrescoImage.this.setController(controller);
    }

    @Override
    protected void onDetach() {
        super.onDetach();
        stopGifAnim();
    }

    @Override
    public void setVisibility(int visibility) {
        super.setVisibility(visibility);
        if (FrescoImage.this.getController() == null) {
            return;
        }
        if (FrescoImage.this.getController().getAnimatable() == null) {
            return;
        }
        if (visibility == VISIBLE &&  !FrescoImage.this.getController().getAnimatable().isRunning()) {
            FrescoImage.this.getController().getAnimatable().start();
        }else if (visibility != VISIBLE &&  FrescoImage.this.getController().getAnimatable().isRunning()) {
            FrescoImage.this.getController().getAnimatable().stop();
        }

    }

    public void stopGifAnim(){
        if (FrescoImage.this.getController() == null) {
            return;
        }
        if (FrescoImage.this.getController().getAnimatable() == null) {
            return;
        }
        FrescoImage.this.getController().getAnimatable().stop();
    }
}

/*
*  <com.gameabc.framework.widgets.FrescoImage
            android:id="@+id/video_item_cover"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:scaleType="centerCrop"
            fresco:placeholderImage="@drawable/game_default_cover_image"
            fresco:roundedCornerRadius="0dp"
            fresco:viewAspectRatio="1.78" //设置宽高比
            tools:background="@drawable/game_default_cover_image" />
*
*
* */


/*
* class ItemHolder extends BaseRecyclerViewHolder {
        // 一个holder一个listener
        ControllerListener<ImageInfo> controllerListener = new BaseControllerListener<ImageInfo>() {
            @Override
            public void onFinalImageSet(String id, ImageInfo imageInfo, Animatable animatable) {
                super.onFinalImageSet(id, imageInfo, animatable);
                int height = imageInfo.getHeight();
                int width = imageInfo.getWidth();
//                float ratio = width > height ? width / height : height / width;
//                fciImage.setAspectRatio(ratio);
                int shouldHeight = ZhanqiApplication.dip2px(16);
                RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) fciImage.getLayoutParams();
                layoutParams.height = shouldHeight;
                int shouldWidth = (shouldHeight * width) / height;
                layoutParams.width = shouldWidth;
                fciImage.setLayoutParams(layoutParams);
                itemView.requestLayout();
            }
        };

//    void setMedalCoverImage(String imageUrl) {
//    DraweeController controller = Fresco.newDraweeControllerBuilder()
//            .setControllerListener(controllerListener)
//            .setUri(Uri.parse(imageUrl))
//            .setAutoPlayAnimations(true)//支持 gif
//            .build();
//    fciImage.setController(controller);
//}


    @BindView(R.id.iv_current_status)
    ImageView ivCurrentStatus;
    @BindView(R.id.fci_image)
    FrescoImage fciImage;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.view_over_due_mask)
    RelativeLayout viewOverDueMask;
    @BindView(R.id.view_container)
    RelativeLayout mViewContainer;

    ItemHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        itemView.setOnClickListener(changeFollowListener);
    }
}


private View.OnClickListener changeFollowListener = new View.OnClickListener() {
@Override
public void onClick(View v) {
        int position = (int) v.getTag();

        }
        };
        */



/*
* DraweeController controller = Fresco.newDraweeControllerBuilder()
	.setUri("res:///" + resId)
	.setAutoPlayAnimations(true)
	.build();
*
* */