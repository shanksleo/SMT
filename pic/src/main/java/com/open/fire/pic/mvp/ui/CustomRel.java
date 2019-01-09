package com.open.fire.pic.mvp.ui;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.RelativeLayout;

import com.open.fire.pic.R;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * class description
 *
 * @author shankshu
 * Create on 2019-01-03
 */
public class CustomRel extends RelativeLayout {
    int size = 0;
    int width = 0;
    int height = 0;

    public CustomRel(Context context) {
        super(context);
        init();
    }

    public CustomRel(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CustomRel(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public CustomRel(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        width = getWidth();
        height = getHeight();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        width = getWidth();
        height = getHeight();
      /*  Log.d("CustomRel   -=-", "_onDraw_36_ :\n _[canvas]" + "width " + width + " height " + height);
        Paint paint = new Paint();
        paint.setColor(getResources().getColor(R.color.base_white));
        paint.setAntiAlias(true);
        canvas.drawCircle(3 + size, 3, 3, paint);
        Log.d("CustomRel   -=-", "_onDraw_60_ :\n _[canvas]" + (3 + size));*/

    }


    void init() {

        Observable.interval(10, TimeUnit.MILLISECONDS)

                .flatMap(new Function<Long, ObservableSource<Bitmap>>() {
                    @RequiresApi(api = Build.VERSION_CODES.N)
                    @Override
                    public ObservableSource<Bitmap> apply(Long aLong) throws Exception {
                        size = Math.toIntExact(aLong);
                        Log.d("CustomRel   -=-", "_apply_79_ :\n _[aLong]" + size);
                        Log.d("CustomRel   -=-", "_onDraw_36_ :\n _[canvas]" + "width " + width + " height " + height);
                        Bitmap b = Bitmap.createBitmap(400, 400, Bitmap.Config.ARGB_8888);
                        Canvas canvas = new Canvas(b);
                        Paint paint = new Paint();
                        paint.setColor(getResources().getColor(R.color.base_red));
                        paint.setAntiAlias(true);

                        size = size % ((width - 18 + height - 18) * 2);
                        Log.d("CustomRel   -=-", "_apply_98_ :\n _[size]" + size);

                        if (size < (width - 18)) {

                            canvas.drawCircle(9 + size, 9, 9, paint);
                        } else if (size < (width - 18 + height - 18)) {
                            int c = size - (width - 18);
                            canvas.drawCircle(width - 9, 9 + c, 9, paint);
                        } else if (size < (width - 18 + height - 18 + width - 18)) {
                            int d = size - (width - 18 + height - 18);
                            canvas.drawCircle(width - (d + 9), height - 9, 9, paint);
                        } else if (size < (width - 18 + height - 18 + width - 18 + height - 18)) {
                            int e = size - (width - 18 + height - 18 + width - 18 );
                            canvas.drawCircle(9, height - (e -9), 9, paint);
                        }


                        return Observable.just(b);
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
//                .compose(RxLifecycleUtils.bindToLifecycle(CustomView.this.getContext()))
                .

                        subscribe(new Consumer<Bitmap>() {
                            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                            @Override
                            public void accept(Bitmap r) throws Exception {
                                Log.d("CustomRel   -=-", "_accept_71_ :\n _[r]" + r);

                                CustomRel.this.setBackground(new BitmapDrawable(r));


                            }
                        });
    }


}
