package com.open.fire.utils_package.widget;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.open.fire.utils_package.R;

import java.util.HashMap;
import java.util.Map;

/**
 * class description
 *
 * @author shankshu
 * Create on 2018-12-21
 */
public class BaseWebViewActivity extends AppCompatActivity {

    /*

    chrome://inspect/#devices
    *
    * 1、WebViewClient就是帮助WebView处理各种通知、请求事件的，
    * 具体来说包括：onLoadResource 、onPageStart 、
    * onPageFinish 、onReceiveError 、onReceivedHttpAuthRequest

    2、WebChromeClient是辅助WebView处理Javascript的对话框，网站图标，网站title，加载进度等

    onCloseWindow(关闭WebView) 、onCreateWindow() 、onJsAlert (WebView上alert是弹不出来东西的，需要定制你的WebChromeClient处理弹出)onJsPrompt 、onJsConfirm 、onProgressChanged 、onReceivedIcon 、onReceivedTitle
    * */


    /**
     * Android 5.0以下版本的文件选择回调
     */
    protected ValueCallback<Uri> mFileUploadCallbackFirst;
    /**
     * Android 5.0及以上版本的文件选择回调
     */
    protected ValueCallback<Uri[]> mFileUploadCallbackSecond;

    protected static final int REQUEST_CODE_FILE_PICKER = 51426;


    protected String mUploadableFileTypes = "image/*";

    private WebView mWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_layout_webview);
        initWebView();
    }

    private void initWebView() {
        mWebView = (WebView) findViewById(R.id.web_view);
        mWebView.setWebViewClient(new WebClient());

        mWebView.loadUrl("file:///android_asset/index.html");
        mWebView.setWebChromeClient(new OpenFileChromeClient());
    }

    private class WebClient extends WebViewClient {
        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
//          当通过 webView.goBack() 方式返回上一级Web页面的时候不会触发这个方法(WebChromeClient 中的onReceivedTitle)，
//          因此会导致标题无法跟随历史记录返回上一级页面。所以需要在 onPageFinished() 中对界面标题重新设置

            setTitle(String.valueOf(view.getTitle()));
        }

    }
    //
    public void loadURLWithHTTPHeaders() {
        final String url = "http://cpacm.net";
        WebView webView = new WebView(getApplicationContext());
        Map<String,String> extraHeaders = new HashMap<String, String>();
        extraHeaders.put("Referer", "http://www.google.com");
        webView.loadUrl(url, extraHeaders);
    }




    private class OpenFileChromeClient extends WebChromeClient {
        @Override
        public void onReceivedTitle(WebView view, String title) {
            super.onReceivedTitle(view, title);
//            获取网页的标题
//            setTitle(title);
        }

        @Override
        public void onReceivedIcon(WebView view, Bitmap icon) {
            super.onReceivedIcon(view, icon);
//            获取网页的图标
//            setIcon(icon);
        }


        //  Android 2.2 (API level 8)到Android 2.3 (API level 10)版本选择文件时会触发该隐藏方法
        @SuppressWarnings("unused")
        public void openFileChooser(ValueCallback<Uri> uploadMsg) {
            openFileChooser(uploadMsg, null);
        }

        // Android 3.0 (API level 11)到 Android 4.0 (API level 15))版本选择文件时会触发，该方法为隐藏方法
        public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType) {
            openFileChooser(uploadMsg, acceptType, null);
        }

        // Android 4.1 (API level 16) -- Android 4.3 (API level 18)版本选择文件时会触发，该方法为隐藏方法
        @SuppressWarnings("unused")
        public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType, String capture) {
            openFileInput(uploadMsg, null, false);
        }

        // Android 5.0 (API level 21)以上版本会触发该方法，该方法为公开方法
        @SuppressWarnings("all")
        public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> filePathCallback, WebChromeClient.FileChooserParams fileChooserParams) {
            if (Build.VERSION.SDK_INT >= 21) {
                final boolean allowMultiple = fileChooserParams.getMode() == FileChooserParams.MODE_OPEN_MULTIPLE;//是否支持多选
                openFileInput(null, filePathCallback, allowMultiple);
                return true;
            } else {
                return false;
            }
        }
    }

    @SuppressLint("NewApi")
    protected void openFileInput(final ValueCallback<Uri> fileUploadCallbackFirst, final ValueCallback<Uri[]> fileUploadCallbackSecond, final boolean allowMultiple) {
        //Android 5.0以下版本
        if (mFileUploadCallbackFirst != null) {
            mFileUploadCallbackFirst.onReceiveValue(null);
        }
        mFileUploadCallbackFirst = fileUploadCallbackFirst;

        //Android 5.0及以上版本
        if (mFileUploadCallbackSecond != null) {
            mFileUploadCallbackSecond.onReceiveValue(null);
        }
        mFileUploadCallbackSecond = fileUploadCallbackSecond;

        Intent i = new Intent(Intent.ACTION_GET_CONTENT);
        i.addCategory(Intent.CATEGORY_OPENABLE);

        if (allowMultiple) {
            if (Build.VERSION.SDK_INT >= 18) {
                i.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
            }
        }

        i.setType(mUploadableFileTypes);

        startActivityForResult(Intent.createChooser(i, "选择文件"), REQUEST_CODE_FILE_PICKER);

    }

    public void onActivityResult(final int requestCode, final int resultCode, final Intent intent) {
        if (requestCode == REQUEST_CODE_FILE_PICKER) {
            if (resultCode == Activity.RESULT_OK) {
                if (intent != null) {
                    //Android 5.0以下版本
                    if (mFileUploadCallbackFirst != null) {
                        mFileUploadCallbackFirst.onReceiveValue(intent.getData());
                        mFileUploadCallbackFirst = null;
                    } else if (mFileUploadCallbackSecond != null) {//Android 5.0及以上版本
                        Uri[] dataUris = null;

                        try {
                            if (intent.getDataString() != null) {
                                dataUris = new Uri[]{Uri.parse(intent.getDataString())};
                            } else {
                                if (Build.VERSION.SDK_INT >= 16) {
                                    if (intent.getClipData() != null) {
                                        final int numSelectedFiles = intent.getClipData().getItemCount();

                                        dataUris = new Uri[numSelectedFiles];

                                        for (int i = 0; i < numSelectedFiles; i++) {
                                            dataUris[i] = intent.getClipData().getItemAt(i).getUri();
                                        }
                                    }
                                }
                            }
                        } catch (Exception ignored) {
                        }
                        mFileUploadCallbackSecond.onReceiveValue(dataUris);
                        mFileUploadCallbackSecond = null;
                    }
                }
            } else {
                //这里mFileUploadCallbackFirst跟mFileUploadCallbackSecond在不同系统版本下分别持有了
                //WebView对象，在用户取消文件选择器的情况下，需给onReceiveValue传null返回值
                //否则WebView在未收到返回值的情况下，无法进行任何操作，文件选择器会失效
                if (mFileUploadCallbackFirst != null) {
                    mFileUploadCallbackFirst.onReceiveValue(null);
                    mFileUploadCallbackFirst = null;
                } else if (mFileUploadCallbackSecond != null) {
                    mFileUploadCallbackSecond.onReceiveValue(null);
                    mFileUploadCallbackSecond = null;
                }
            }
        }
    }


    // 在 Actvity 中监听返回键按钮
    @Override
    public void onBackPressed() {
        if (mWebView != null && mWebView.canGoBack())
            mWebView.goBack();
        else
            super.onBackPressed();
    }


}
