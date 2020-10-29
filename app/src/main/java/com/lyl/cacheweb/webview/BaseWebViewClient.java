package com.lyl.cacheweb.webview;


import android.graphics.Bitmap;
import android.net.http.SslError;
import android.text.TextUtils;
import android.util.Log;
import android.webkit.SslErrorHandler;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * 实现一个基础的 WebViewClient ，如果有更多的需要，直接继承它
 */
public class BaseWebViewClient extends WebViewClient {

    private String TAG = this.getClass().getSimpleName();

    /**
     * 主题CSS样式
     */
    private String mCSSStyle = null;

    public BaseWebViewClient() {

    }

    /**
     * 设置CSS样式
     *
     * @param style
     */
    public void setCSSStyle(String style) {
        Log.d(TAG, "setCSSStyle: ");
        this.mCSSStyle = style;
    }

    /**
     * 多页面在同一个WebView中打开，就是不新建activity或者调用系统浏览器打开
     */
    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        Log.d(TAG, "shouldOverrideUrlLoading:: url:" + url);
        view.loadUrl(url);
        return true;
    }

    @Override
    public void onPageStarted(WebView view, String url, Bitmap favicon) {
        super.onPageStarted(view, url, favicon);
        if (!TextUtils.isEmpty(mCSSStyle)) {
            Log.d(TAG, "onPageStarted: 加载黑色主题 ...");
            view.loadUrl("javascript:(function() {" + "var parent = document.getElementsByTagName('head').item(0);" + "var style = document.createElement('style');" + "style.type = 'text/css';" + "style.innerHTML = window.atob('" + mCSSStyle + "');" + "parent.appendChild(style)" + "})();");
        }
    }

    @Override
    public void onPageFinished(WebView view, String url) {
        super.onPageFinished(view, url);
        if (!TextUtils.isEmpty(mCSSStyle)) {
            Log.d(TAG, "onPageFinished: 加载黑色主题完成 ...");
            view.loadUrl("javascript:(function() {" + "var parent = document.getElementsByTagName('head').item(0);" + "var style = document.createElement('style');" + "style.type = 'text/css';" + "style.innerHTML = window.atob('" + mCSSStyle + "');" + "parent.appendChild(style)" + "})();");
        }
    }

    @Override
    public void doUpdateVisitedHistory(WebView view, String url, boolean isReload) {
        super.doUpdateVisitedHistory(view, url, isReload);
        if (!TextUtils.isEmpty(mCSSStyle)) {
            Log.d(TAG, "doUpdateVisitedHistory: 加载黑色主题 ...");
            view.loadUrl("javascript:(function() {" + "var parent = document.getElementsByTagName('head').item(0);" + "var style = document.createElement('style');" + "style.type = 'text/css';" + "style.innerHTML = window.atob('" + mCSSStyle + "');" + "parent.appendChild(style)" + "})();");
        }
    }

    @Override
    public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
        //super.onReceivedSslError(view, handler, error);
        // handler.cancel();// Android默认的处理方式

        //设置WebView接受所有网站的证书
        //注：在重写WebViewClient的onReceivedSslError方法时，注意一定要去除onReceivedSslError方法的super.onReceivedSslError(view, handler, error);，否则设置无效。
        handler.proceed();// 接受所有网站的证书
        // 进行其他处理
        //handleMessage(Message msg);
    }
}
