package com.yunjiankang.selectpotodemo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.yunjiankang.selectpotodemo.R;
import com.yunjiankang.selectpotodemo.base.BaseActivity;
import com.yunjiankang.selectpotodemo.service.UpdateService;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class MainActivity extends BaseActivity {

    @BindView(R.id.tv_item1)
    TextView tvItem1;
    @BindView(R.id.tv_item2)
    TextView tvItem2;
    @BindView(R.id.tv_item3)
    TextView tvItem3;
    @BindView(R.id.tv_item4)
    TextView tvItem4;

    @Override
    public int getViewId() {
        return R.layout.activity_main;
    }

    @Override
    public void initData() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({ R.id.tv_item1, R.id.tv_item2, R.id.tv_item3, R.id.tv_item4})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_item1://图片选择一
                startActivity(new Intent(MainActivity.this, SelectPotoActivity1.class));
                break;
            case R.id.tv_item2://图片选择二
                startActivity(new Intent(MainActivity.this, SelectPotoActivity2.class));
                break;
            case R.id.tv_item3://OKGO基本请求
                startActivity(new Intent(MainActivity.this, OkGoActivity.class));
                break;
            case R.id.tv_item4://OKGO下载文件
                Intent intent = new Intent(getApplicationContext(), UpdateService.class);
                intent.putExtra("download", "您的文件名称");
                getApplicationContext().startService(intent);
                break;
        }
    }
}
