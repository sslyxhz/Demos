/*
 * Copyright 2016 xhz
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.xhz.okhttp3demo;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    public static final String TAG = MainActivity.class.getSimpleName();

    protected Button btnBasicOK, btnPackageOK,btnBasicRetrofit,btnPackageRetrofit,btnTest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();

        requestPermission();
    }

    private void initView() {
        btnBasicOK = (Button) this.findViewById(R.id.btn_basic);
        btnBasicOK.setOnClickListener(this);
        btnPackageOK = (Button) this.findViewById(R.id.btn_package);
        btnPackageOK.setOnClickListener(this);
        btnBasicRetrofit = (Button) this.findViewById(R.id.btn_retrofit);
        btnBasicRetrofit.setOnClickListener(this);
        btnPackageRetrofit = (Button) this.findViewById(R.id.btn_retrofit_package);
        btnPackageRetrofit.setOnClickListener(this);
        btnTest = (Button) this.findViewById(R.id.btn_retrofit_test);
        btnTest.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_basic:
                startActivity(new Intent(MainActivity.this, BasicWayActivity.class));
                break;
            case R.id.btn_package:
                break;
            case R.id.btn_retrofit:
                startActivity(new Intent(MainActivity.this, RetrofitActivity.class));
                break;
            case R.id.btn_retrofit_package:
                break;
            case R.id.btn_retrofit_test:
                TestPresenter presenter = new TestPresenter();
                presenter.test();
                break;
            default:
                Log.w(TAG,"unknown click = " + view.toString());
        }
    }

    public void requestPermission(){
        int REQUEST_EXTERNAL_STORAGE = 1;
        String[] PERMISSIONS_STORAGE = {
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
        };
        int permission = ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    MainActivity.this,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
        }
    }
}
