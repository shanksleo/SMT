package com.open.fire.utils_package.widget.image_utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ImageView;

import com.facebook.binaryresource.FileBinaryResource;
import com.facebook.cache.common.CacheKey;
import com.facebook.cache.disk.FileCache;
import com.facebook.common.executors.CallerThreadExecutor;
import com.facebook.common.executors.UiThreadImmediateExecutorService;
import com.facebook.common.memory.PooledByteBuffer;
import com.facebook.common.memory.PooledByteBufferInputStream;
import com.facebook.common.references.CloseableReference;
import com.facebook.datasource.BaseDataSubscriber;
import com.facebook.datasource.DataSource;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.controller.AbstractDraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.cache.DefaultCacheKeyFactory;
import com.facebook.imagepipeline.core.ImagePipeline;
import com.facebook.imagepipeline.core.ImagePipelineFactory;
import com.facebook.imagepipeline.datasource.BaseBitmapDataSubscriber;
import com.facebook.imagepipeline.image.CloseableImage;
import com.facebook.imagepipeline.postprocessors.IterativeBoxBlurPostProcessor;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;

import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by Administrator on 2017/2/17.
 */
public class FrescoUtil {
    /**
     * Fresco加载网络图片到View
     *
     * @param url  图片url
     * @param view View
     */
    public static void loadUrlImageInto(final String url, final View view) {
        loadUrlImage(url, true, new BaseBitmapDataSubscriber() {
            @Override
            protected void onNewResultImpl(final Bitmap bitmap) {
                view.post(new Runnable() {
                    @Override
                    public void run() {
                        if (view instanceof ImageView) {
                            ((ImageView) view).setImageBitmap(bitmap);
                        } else {
                            view.setBackgroundDrawable(new BitmapDrawable(view.getResources(), bitmap));
                        }
                    }
                });
            }

            @Override
            protected void onFailureImpl(DataSource<CloseableReference<CloseableImage>> dataSource) {
            }
        });
    }

    /**
     * Fresco加载网络图片
     *
     * @param url                      图片url
     * @param bUIThread                是否回调到ui线程
     * @param baseBitmapDataSubscriber 回调函数
     */
    public static void loadUrlImage(String url, boolean bUIThread, BaseBitmapDataSubscriber baseBitmapDataSubscriber) {
        ImageRequest imageRequest = ImageRequestBuilder
                .newBuilderWithSource(Uri.parse(url))
                .build();
        ImagePipeline imagePipeline = Fresco.getImagePipeline();
        DataSource<CloseableReference<CloseableImage>> dataSource = imagePipeline.fetchDecodedImage(imageRequest, null);
        if (bUIThread) {
            dataSource.subscribe(baseBitmapDataSubscriber, UiThreadImmediateExecutorService.getInstance());
        } else {
            dataSource.subscribe(baseBitmapDataSubscriber, CallerThreadExecutor.getInstance());
        }
    }

    /**
     * Fresco加载网络图片
     *
     * @param url                    图片url
     * @param bUIThread              是否回调到ui线程
     * @param baseFileDataSubscriber 回调函数
     */
    public static void loadUrlImage(String url, boolean bUIThread, final BaseFileDataSubscriber baseFileDataSubscriber) {
        ImageRequest imageRequest = ImageRequestBuilder
                .newBuilderWithSource(Uri.parse(url))
                .build();
        final File cacheFile = getCacheFile(imageRequest);
        if (cacheFile.exists()) {
            if (bUIThread) {
                UiThreadImmediateExecutorService.getInstance().execute(new Runnable() {
                    @Override
                    public void run() {
                        baseFileDataSubscriber.onNewResultImpl(cacheFile);
                    }
                });
            } else {
                CallerThreadExecutor.getInstance().execute(new Runnable() {
                    @Override
                    public void run() {
                        baseFileDataSubscriber.onNewResultImpl(cacheFile);
                    }
                });
            }
        } else {
            ImagePipeline imagePipeline = Fresco.getImagePipeline();
            DataSource<CloseableReference<PooledByteBuffer>> dataSource = imagePipeline.fetchEncodedImage(imageRequest, null);
            if (bUIThread) {
                dataSource.subscribe(baseFileDataSubscriber, UiThreadImmediateExecutorService.getInstance());
            } else {
                dataSource.subscribe(baseFileDataSubscriber, CallerThreadExecutor.getInstance());
            }
        }
    }

    public static File getCacheFile(ImageRequest request) {
        FileCache mainFileCache = ImagePipelineFactory
                .getInstance()
                .getMainFileCache();
        final CacheKey cacheKey = DefaultCacheKeyFactory
                .getInstance()
                .getEncodedCacheKey(request, false); // we don't need context, but avoid null
        File cacheFile = request.getSourceFile();
        // http://crashes.to/s/ee10638fb31
        if (mainFileCache.hasKey(cacheKey) && mainFileCache.getResource(cacheKey) != null) {
            cacheFile = ((FileBinaryResource) mainFileCache.getResource(cacheKey)).getFile();
        }
        return cacheFile;
    }

    public abstract static class BaseFileDataSubscriber
            extends BaseDataSubscriber<CloseableReference<PooledByteBuffer>> {
        private File mCacheDir;

        public BaseFileDataSubscriber(Context context) {
            mCacheDir = new File(context.getExternalCacheDir(), "ZhanqiCache");
            if (!mCacheDir.exists() || !mCacheDir.isDirectory()) {
                mCacheDir.mkdir();
            }
        }

        @Override
        protected void onNewResultImpl(DataSource<CloseableReference<PooledByteBuffer>> dataSource) {
            if (!dataSource.isFinished() || dataSource.getResult() == null) {
                return;
            }

            File tempFile = new File(mCacheDir, "fresco_" + System.currentTimeMillis() + ".tmp");

            PooledByteBufferInputStream inputStream = null;
            FileOutputStream outputStream = null;
            try {
                inputStream = new PooledByteBufferInputStream(dataSource.getResult().get());
                outputStream = new FileOutputStream(tempFile);
                IOUtils.copy(inputStream, outputStream);

                onNewResultImpl(tempFile);
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                IOUtils.closeQuietly(inputStream);
                IOUtils.closeQuietly(outputStream);
            }
        }

        protected abstract void onNewResultImpl(@NonNull File file);
    }

    /**
     * 以高斯模糊显示。
     *
     * @param draweeView View。
     * @param url        url.
     * @param iterations 迭代次数，越大越魔化。
     * @param blurRadius 模糊图半径，必须大于0，越大越模糊。
     */
    public static void showUrlBlur(SimpleDraweeView draweeView, String url, int iterations, int blurRadius) {
        try {
            Uri uri = Uri.fromFile(new File(url));
            ImageRequest request = ImageRequestBuilder.newBuilderWithSource(uri)
                    .setPostprocessor(new IterativeBoxBlurPostProcessor(iterations, blurRadius))
                    .build();
            AbstractDraweeController controller = Fresco.newDraweeControllerBuilder()
                    .setOldController(draweeView.getController())
                    .setImageRequest(request)
                    .build();
            draweeView.setController(controller);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
