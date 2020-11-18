package com.yunjiankang.selectpotodemo.service;

import android.app.Service;
import android.content.Intent;
import android.os.Environment;
import android.os.IBinder;
import android.text.format.Formatter;
import android.util.Log;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.FileCallback;
import com.lzy.okgo.model.Progress;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.base.Request;

import java.io.File;

import androidx.annotation.Nullable;

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
 * 类名称: UpdateService
 * 类描述:
 * 创建人:zhengleilei.
 * 创建时间:2020/11/18 16:15
 * 邮箱:lifetime0911@163.com
 * 修改备注:
 * 版本号:V 1.0.0
 */
public class UpdateService extends Service {

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (null != intent) {
            downLoadApk(String.valueOf(intent.getSerializableExtra("download")));
        }
        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    private final String apkPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/okgo/";
    private final String fileName = "okgo.apk";

    private void downLoadApk(final String downApkUrl) {
        OkGo.<File>get(downApkUrl).execute(new FileCallback(apkPath, fileName) {
            @Override
            public void onSuccess(Response<File> response) {
                Log.e("select", "下载成功" + response.message());
            }

            @Override
            public void onStart(Request<File, ? extends Request> request) {
                super.onStart(request);
                Log.e("select", "开始下载");
            }

            @Override
            public void onError(Response<File> response) {
                super.onError(response);
                Log.e("select", "开始失败" + response.message());
            }

            @Override
            public void downloadProgress(Progress progress) {
                super.downloadProgress(progress);
                String filePath = progress.filePath;
                String fileName = progress.fileName;
                String downloadLength = Formatter.formatFileSize(getApplicationContext(), progress.currentSize);
                String totalLength = Formatter.formatFileSize(getApplicationContext(), progress.totalSize);
                Log.e("select", "开始下载进度" + downloadLength + "///" + totalLength);
            }
        });
    }

}
