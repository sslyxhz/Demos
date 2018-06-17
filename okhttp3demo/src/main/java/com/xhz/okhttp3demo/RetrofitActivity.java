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

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.xhz.okhttp3demo.api.GankService;
import com.xhz.okhttp3demo.model2.GankResult;
import com.xhz.okhttp3demo.model2.bean.GankTypeData;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Converter;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Subscriber;
import rx.Observable;
import rx.schedulers.Schedulers;

/**
 * 测试Retrofit
 * Created by xh.zeng on 2016/11/25.
 */
public class RetrofitActivity extends AppCompatActivity implements View.OnClickListener{
    public static final String TAG = RetrofitActivity.class.getSimpleName();

    protected TextView tvTitle, tvContent;
    protected Button btnGet,btnPost,btnConverter,btnRxjava;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrofit);

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
        btnConverter = (Button) this.findViewById(R.id.btn_converter);
        btnConverter.setOnClickListener(this);
        btnRxjava = (Button) this.findViewById(R.id.btn_rxjava);
        btnRxjava.setOnClickListener(this);
    }

    /**
     * 最简单的使用方式，无转换，直接提取
     */
    private void testGet(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(GankService.HOST)
                .build();
        GankService service = retrofit.create(GankService.class);

//        Call<ResponseBody> call = service.getDayHistory();
        Call<ResponseBody> call = service.getDataOfRandom("Android", 5);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()){
                    try{
                        Log.v(TAG,"testGet-onResponse, Thread id="+Thread.currentThread().getId());
                        final String result = response.body().string();
                        Log.v(TAG, "response:"+result);
                        appendText(result);
                    }
                    catch (IOException ex){
                        ex.printStackTrace();
                    }
                }
                else{
                    Log.v(TAG,"testGet-onResponse, code="+response.code());
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    // 自定义Converter实现RequestBody到String的转换
    public static class MyConverter implements Converter<ResponseBody, String>{

        public static final MyConverter INSTANCE = new MyConverter();
        @Override
        public String convert(ResponseBody value) throws IOException {
            return value.string();
        }
    }
    // 用于向Retrofit提供MyConverter
    public static class MyConverterFactory extends Converter.Factory {
        public static final MyConverterFactory INSTANCE = new MyConverterFactory();

        public static MyConverterFactory create(){
            return INSTANCE;
        }

        @Override
        public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
            if(type == String.class){
                return MyConverter.INSTANCE;
            }
            return null;
        }
    }

    /**
     * 自定义Converter简单转换
     */
    private void testGet2(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(GankService.HOST)
                .addConverterFactory(MyConverterFactory.create())
                .build();
        GankService service = retrofit.create(GankService.class);

        Call<String> call = service.getDataOfRandom1("Android", 5);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()){
                    Log.v(TAG,"testGet2-onResponse, Thread id="+Thread.currentThread().getId());
                    Log.v(TAG, "response:"+ response.body()); // 直接使用body
                    appendText(response.body());
                }
                else{
                    Log.v(TAG,"testGet2-onResponse, code="+response.code());
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    /**
     * 主要是GsonConverterFactory
     */
    private void testConverter(){
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd hh:mm:ss")
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(GankService.HOST)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        GankService service = retrofit.create(GankService.class);

        Call<GankResult<GankTypeData>> call = service.getDataOfType2(GankService.GankType.Android.name(),5,1);
        call.enqueue(new Callback<GankResult<GankTypeData>>() {
            @Override
            public void onResponse(Call<GankResult<GankTypeData>> call, Response<GankResult<GankTypeData>> response) {
                Log.v(TAG,"testConverter-onResponse, Thread id="+Thread.currentThread().getId());
                GankResult<GankTypeData> result = response.body();
                Log.v(TAG, "response:"+result.toString());
                appendText(result.toString());
            }

            @Override
            public void onFailure(Call<GankResult<GankTypeData>> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    /**
     * 结合Rxjava
     */
    private void testRxjava(){
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd hh:mm:ss")
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(GankService.HOST)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();

        GankService service = retrofit.create(GankService.class);

       service.getDataOfType3(GankService.GankType.Android.name(),5,1)
               .subscribeOn(Schedulers.io())
               .subscribe(new Subscriber<GankResult<GankTypeData>>(){

                   @Override
                   public void onNext(GankResult<GankTypeData> gankResult) {
                       Log.v(TAG,"testRxjava-onNext, Thread id="+Thread.currentThread().getId());
                       Log.v(TAG, "response:"+gankResult.toString());
                       appendText(gankResult.toString());
                   }

                   @Override
                   public void onCompleted() {
                       Log.v(TAG,"testRxjava-onCompleted, Thread id="+Thread.currentThread().getId());

                   }

                   @Override
                   public void onError(Throwable e) {
                       Log.v(TAG,"testRxjava-onError, Thread id="+Thread.currentThread().getId());
                       e.printStackTrace();
                   }

               });
    }

    public void requestPermission(){
        int REQUEST_EXTERNAL_STORAGE = 1;
        String[] PERMISSIONS_STORAGE = {
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
        };
        int permission = ActivityCompat.checkSelfPermission(RetrofitActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    RetrofitActivity.this,
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
            case R.id.btn_converter:
//                testGet2();
                testConverter();
                break;
            case R.id.btn_rxjava:
                testRxjava();
                break;
            default:
                Log.w(TAG,"unknown click = " + view.toString());
        }
    }
}
