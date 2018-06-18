package com.sslyxhz.demos;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.sslyxhz.demos.animation.AnimationActivity;
import com.sslyxhz.demos.camera.CameraActivity;
import com.sslyxhz.demos.nanohttpd.WebViewActivity;
import com.sslyxhz.demos.permission.PermissionActivity;
import com.sslyxhz.demos.toast.ToastDemoActivity;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private List<String> mFunctions;
    private RecyclerView mRecyclerView;
    private FunctionsAdapter mFunctionsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initData();
        initView();
    }

    private void initData() {
        mFunctions = new ArrayList<>();
        mFunctions.add("WebView");
        mFunctions.add("Animation");
        mFunctions.add("Camera");
        mFunctions.add("Toast");
        mFunctions.add("Permission");

        mFunctionsAdapter = new FunctionsAdapter(mFunctions);
    }

    private void initView() {
        mRecyclerView = (RecyclerView) this.findViewById(R.id.rv_main_function_items);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mFunctionsAdapter);
    }

    class FunctionsViewHolder extends RecyclerView.ViewHolder{
        private String mFunctionName;
        public TextView mTvFunctionName;

        public FunctionsViewHolder(View itemView) {
            super(itemView);
            mTvFunctionName = (TextView) itemView.findViewById(R.id.tvFunctionName);
            mTvFunctionName.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View v) {
                    if("WebView".equals(mFunctionName)){
                        startActivity(new Intent(MainActivity.this, WebViewActivity.class));
                    } else if("Animation".equals(mFunctionName)){
                        startActivity(new Intent(MainActivity.this, AnimationActivity.class));
                    } else if("Camera".equals(mFunctionName)){
                        startActivity(new Intent(MainActivity.this, CameraActivity.class));
                    } else if("Toast".equals(mFunctionName)){
                        startActivity(new Intent(MainActivity.this, ToastDemoActivity.class));
                    } else if("Permission".equals(mFunctionName)){
                        startActivity(new Intent(MainActivity.this, PermissionActivity.class));
                    } else{
                        Toast.makeText(MainActivity.this, "Miss:"+mFunctionName, Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

        public void bindFunction(String functionName){
            mFunctionName = functionName;
            mTvFunctionName.setText(functionName);
        }
    }

    class FunctionsAdapter extends RecyclerView.Adapter<FunctionsViewHolder>{

        private List<String> mFunctions;

        public FunctionsAdapter(List<String> functions){
            mFunctions = functions;
        }
        @Override
        public FunctionsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(MainActivity.this);
            View view = layoutInflater.inflate(R.layout.list_item_function, parent, false);
            return new FunctionsViewHolder(view);
        }

        @Override
        public void onBindViewHolder(FunctionsViewHolder holder, int position) {
            String functionName = mFunctions.get(position);
            holder.bindFunction(functionName);
        }

        @Override
        public int getItemCount() {
            return mFunctions==null?0:mFunctions.size();
        }
    }


}
