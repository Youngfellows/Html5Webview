package com.lyl.cacheweb;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;

public class MainActivity extends Activity {
    private String TAG = this.getClass().getSimpleName();

    private TextView mTxtHost;
    private EditText mEdtUrl;
    private Button mBtnSreach;
    private RadioGroup mRadioGroup;
    private String mUrl;

    /**
     * 是否支持CSS样式
     */
    private boolean isSupport = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
        initEvents();
    }

    private void initViews() {
        mTxtHost = (TextView) findViewById(R.id.host);
        mEdtUrl = (EditText) findViewById(R.id.edt_url);
        mBtnSreach = (Button) findViewById(R.id.btn_sreach);
        mRadioGroup = (RadioGroup) findViewById(R.id.rg);
    }

    private void initEvents() {
        // 搜索按钮 和 输入法右下角的“搜索” 点击事件是一致的
        mBtnSreach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sreachUrl();
            }
        });
        mEdtUrl.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    sreachUrl();
                }
                return false;
            }
        });

        // 点击前面 “https://” 切换 http
        mTxtHost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String host = mTxtHost.getText().toString().trim();
                if (host.startsWith("https")) {
                    mTxtHost.setText("https://");
                } else {
                    mTxtHost.setText("http://");
                }
            }
        });

        //单选框点击事件
        mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                Log.d(TAG, "onCheckedChanged: " + checkedId);
                switch (checkedId) {
                    case R.id.rb_support_css:
                        Log.d(TAG, "onCheckedChanged: 选中了支持CSS样式 ...");
                        isSupport = true;
                        break;
                    case R.id.rg_no_support_css:
                        Log.d(TAG, "onCheckedChanged: 选中了不支持CSS样式 ...");
                        isSupport = false;
                        break;
                }
            }
        });
    }


    private void sreachUrl() {
        String edt = mEdtUrl.getText().toString().trim();
        if (edt.startsWith("https") || edt.startsWith("http")) {
            mUrl = edt;
        } else {
            mUrl = mTxtHost.getText().toString() + edt;
        }

        Intent intent = new Intent(MainActivity.this, Html5Activity.class);
        if (!TextUtils.isEmpty(edt)) {
            Bundle bundle = new Bundle();
            bundle.putString("url", mUrl);
            bundle.putBoolean("support_css", isSupport);
            intent.putExtra("bundle", bundle);
        }
        startActivity(intent);
    }
}
