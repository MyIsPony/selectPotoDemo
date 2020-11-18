package com.yunjiankang.selectpotodemo;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.WindowManager;
import android.widget.TextView;

import com.hjq.permissions.OnPermission;
import com.hjq.permissions.XXPermissions;
import com.yunjiankang.selectpotodemo.activity.MainActivity;
import com.yunjiankang.selectpotodemo.base.BaseActivity;

import java.util.List;

import androidx.annotation.Nullable;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * .                            _ooOoo_
 * .                           o8888888o
 * .                           88" . "88
 * .                           (| -_- |)
 * .                            O\ = /O
 * .                        ____/`---'\____
 * .                      .   ' \\| |// `.
 * .                       / \\||| : |||// \
 * .                     / _||||| -:- |||||- \
 * .                       | | \\\ - /// | |
 * .                     | \_| ''\---/'' | |
 * .                      \ .-\__ `-` ___/-. /
 * .                   ___`. .' /--.--\ `. . __
 * .                ."" '< `.___\_<|>_/___.' >'"".
 * .               | | : `- \`.;`\ _ /`;.`/ - ` : | |
 * .                 \ \ `-. \_ __\ /__ _/ .-` / /
 * .         ======`-.____`-.___\_____/___.-`____.-'======
 * .                            `=---='
 * .
 * .         .............................................
 * .                  佛祖镇楼                  BUG辟易
 * .          佛曰:
 * .                  写字楼里写字间，写字间里程序员；
 * .                  程序人员写程序，又拿程序换酒钱。
 * .                  酒醒只在网上坐，酒醉还来网下眠；
 * .                  酒醉酒醒日复日，网上网下年复年。
 * .                  但愿老死电脑间，不愿鞠躬老板前；
 * .                  奔驰宝马贵者趣，公交自行程序员。
 * .                  别人笑我忒疯癫，我笑自己命太贱；
 * .                  不见满街漂亮妹，哪个归得程序员？
 * .
 * 项目名称: selectPotoDemo
 * 类名称: SplashActivity
 * 类描述:
 * 创建人:zhengleilei.
 * 创建时间:2020/11/18 16:47
 * 邮箱:zhengll@wbpharma.com
 * 修改备注:
 * 版本号:V 1.0.0
 */
public class SplashActivity extends BaseActivity {
    @BindView(R.id.tv_item0)
    TextView tvItem0;

    private int splashTime = 5;//倒计时时间
    //需要申请的权限 不要忘记在xml文件声明
    private final String[] LOCATION_AND_CONTACTS =
            {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.RECORD_AUDIO,
                    Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.READ_PHONE_STATE};

    @Override
    public int getViewId() {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        return R.layout.activity_splash;
    }

    @Override
    public void initData() {
        getPermissions();//获取权限

    }


    private CountDownTimer countDownTimer = new CountDownTimer(splashTime * 1000, 1000) {

        @Override
        public void onTick(long millisUntilFinished) {
            tvItem0.setText(String.valueOf(splashTime + " s跳过"));
            splashTime--;

        }

        @Override
        public void onFinish() {
            startActivity(new Intent(SplashActivity.this, MainActivity.class));
            finish();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick(R.id.tv_item0)
    public void onClick() {
        startActivity(new Intent(SplashActivity.this, MainActivity.class));
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
    }

    //申请权限
    private void getPermissions() {
        XXPermissions.with(this)
                .permission(LOCATION_AND_CONTACTS)
                .request(new OnPermission() {
                    @Override
                    public void hasPermission(List<String> granted, boolean all) {
                        if (all) {
                            if (countDownTimer != null) {
                                countDownTimer.start();
                            }
                        }
                    }

                    @Override
                    public void noPermission(List<String> denied, boolean never) {
                        XXPermissions.startPermissionActivity(SplashActivity.this, denied);
                    }
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == XXPermissions.REQUEST_CODE) {
            if (XXPermissions.hasPermission(this, LOCATION_AND_CONTACTS)) {
                if (countDownTimer != null) {
                    countDownTimer.start();
                }
            }
        }
    }
}

