package com.yunjiankang.selectpotodemo;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import androidx.annotation.NonNull;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;


public class MainActivity extends BaseActivity implements EasyPermissions.PermissionCallbacks {

    private final String[] LOCATION_AND_CONTACTS =
            {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.RECORD_AUDIO, Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.READ_PHONE_STATE};

    private final int RC_LOCATION_CONTACTS_PERM = 124;
    @BindView(R.id.tv_item0)
    TextView tvItem0;

    @Override
    int getViewId() {
        return R.layout.activity_main;
    }

    @Override
    void initData() {

    }

    //申请权限
    private void getPermissions() {
        if (EasyPermissions.hasPermissions(this, LOCATION_AND_CONTACTS)) {
            startActivity(new Intent(MainActivity.this, SelectPotoActivity.class));
        } else {
            EasyPermissions.requestPermissions(
                    this,
                    "为保证软件正常使用,请先同意获取权限",
                    RC_LOCATION_CONTACTS_PERM,
                    LOCATION_AND_CONTACTS);
        }
    }

    @Override
    public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {
        Toast.makeText(MainActivity.this, "触发了", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            new AppSettingsDialog.Builder(this).build().show();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick(R.id.tv_item0)
    public void onClick() {
        getPermissions();
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        Toast.makeText(MainActivity.this, "有权限了", Toast.LENGTH_LONG).show();
        startActivity(new Intent(MainActivity.this, SelectPotoActivity.class));
    }
}
