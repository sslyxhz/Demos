package com.sslyxhz.demos.nanohttpd;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.sslyxhz.demos.R;

import java.io.IOException;

/**
 * Created by xh.zeng on 2017/6/2.
 */

public class WebViewActivity extends AppCompatActivity {

    private WebView mWebView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);
        mWebView = (WebView) this.findViewById(R.id.fragment_webview_container);

        // add by xhz for : Cannot call determinedVisibility() - never saw a connection for the pid
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.getSettings().setDomStorageEnabled(true);
        mWebView.setWebViewClient(new WebViewClient(){

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                return false;
            }
        });

        testHttpdServer();
    }

    public static final String ROOT_DOCUMENT_PATH = "/";
    private void testHttpdServer() {
//        final String url = "http://localhost:8080/qrcode";
        final String url = "http://localhost:8080/";
        QrServer server;
        try {
            server = new QrServer(8080, ROOT_DOCUMENT_PATH, this);
            server.addTextHandler("/", "Hello");
//            server.addQrCodeHandler("/qrcode");
            server.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
        mWebView.loadUrl(url);
    }
}
