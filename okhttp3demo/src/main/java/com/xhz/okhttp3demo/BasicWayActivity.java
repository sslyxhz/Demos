package com.xhz.okhttp3demo;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by xh.zeng on 2016/11/24.
 */

public class BasicWayActivity extends AppCompatActivity implements View.OnClickListener{
    public static final String TAG = BasicWayActivity.class.getSimpleName();

    protected TextView tvTitle, tvContent;
    protected Button btnGet,btnPost,btnDownload;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basicway);

        initView();

        requestPermission();    // 申请写入权限, 下载用
    }

    private void initView() {
        tvTitle = (TextView) this.findViewById(R.id.tv_title);
        tvContent = (TextView) this.findViewById(R.id.tv_content);
        btnGet = (Button) this.findViewById(R.id.btn_get);
        btnGet.setOnClickListener(this);
        btnPost = (Button) this.findViewById(R.id.btn_post);
        btnPost.setOnClickListener(this);
        btnDownload = (Button) this.findViewById(R.id.btn_download);
        btnDownload.setOnClickListener(this);
    }

    /**
     * 测试Get
     *     错误：缺少网络权限：java.lang.SecurityException: Permission denied
     *     错误：在主线程进行网络交互：android.os.NetworkOnMainThreadException
     */
    private void testGet(){
       final String ENDPOINT = "https://api.github.com/repos/square/okhttp/contributors";
        OkHttpClient okHttpClient = new OkHttpClient();

        Request request = new Request.Builder().url(ENDPOINT)
                .build();
        Call call = okHttpClient.newCall(request);

//        获取Response之一
//        Response = call.execute();

//        获取Response之二：异步回调,asynchronous callback
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try{
                    if(response.isSuccessful()){
                        // response.body().bytes()
                        // response.body().byteStream()
                        // response.code() // 状态码
                        if(null != response.cacheResponse()){
                            String cacheStr = response.cacheResponse().toString();
                            Log.v(TAG, "cache response: "+cacheStr);
                            appendText("cache response: "+cacheStr);
                        } else{
                            String responseResult = response.body().string();
                            Log.v(TAG, "network response: "+responseResult);
                            appendText("network response: "+responseResult);

                        }
                    }
                }
                catch (IOException ex){
                    ex.printStackTrace();
                }
            }
        });
    }


    /**
     * 测试Post
     */
    private void testPost(){
        final String ENDPOINT = "https://api.github.com/repos/square/okhttp/issues";
        OkHttpClient okHttpClient = new OkHttpClient();

        RequestBody body = new FormBody.Builder()
                .add("platform", "android")
                .add("name", "bug")
                .add("subject", "android")
                .build();
        Request request = new Request.Builder()
                .url(ENDPOINT)
                .post(body)
                .build();

        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try{
                    if(response.isSuccessful()){
                        String responseResult = response.body().string();
                        Log.v(TAG, responseResult);
                        appendText(responseResult);
                    }
                }
                catch (IOException ex){
                    ex.printStackTrace();
                }
            }
        });
    }

    /**
     * 测试下载
     */
    private void testDownload(){
        final String uri = "http://i1.hdslb.com/bfs/face/a6f243d8374c623271cad03f0c105ae76e47a004.jpg";
        OkHttpClient okHttpClient = new OkHttpClient();

        Request request = new Request.Builder().url(uri).build();

        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                InputStream inputStream = response.body().byteStream();
                FileOutputStream fileOutputStream = null;
                try{
                    fileOutputStream = new FileOutputStream(new File("/sdcard/download/test.jpg"));
                    byte[] buffer = new byte[2048];
                    int len = 0;
                    while ((len = inputStream.read(buffer)) != -1) {
                        fileOutputStream.write(buffer, 0, len);
                    }
                    fileOutputStream.flush();
                    appendText("下载成功！");
                }
                catch (IOException ex){
                    ex.printStackTrace();
                    appendText("下载失败！");
                }
            }
        });
    }

    public void requestPermission(){
        int REQUEST_EXTERNAL_STORAGE = 1;
        String[] PERMISSIONS_STORAGE = {
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
        };
        int permission = ActivityCompat.checkSelfPermission(BasicWayActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    BasicWayActivity.this,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
        }
    }

    private void appendText(final String content){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
//                tvContent.append(content);
                tvContent.setText(content);
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_get:
                testGet();
                break;
            case R.id.btn_post:
                testPost();
                break;
            case R.id.btn_download:
                testDownload();
                break;
            default:
                Log.w(TAG,"unknown click = " + view.toString());
        }
    }
}
