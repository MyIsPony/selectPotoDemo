package com.yunjiankang.selectpotodemo.utils;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.text.TextUtils;
import android.widget.Toast;


import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;

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
 * 项目名称: luBanDemo
 * 类名称: LubanUtils
 * 类描述:
 * 创建人:zhengleilei.
 * 创建时间:2020/9/08 16:26
 * 邮箱:lifetime0911@163.com
 * 修改备注:
 * 版本号:V 1.0.0
 */
public class LubanUtils {


    private static LubanUtils lubanUtils;

    public synchronized static LubanUtils getInstance() {
        if (lubanUtils == null) {
            lubanUtils = new LubanUtils();
        }
        return lubanUtils;
    }

    //压缩图片
    public void luBanMore(final List<File> pathList, Activity mActivity, NewPhotoFiles newPhotoFiles) {
        final LinkedList<Runnable> taskList = new LinkedList<>();
        final ArrayList<File> newList = new ArrayList<>();//压缩后的图片路径
        final Handler handler = new Handler();
        class Task implements Runnable {
            File path;

            Task(File path) {
                this.path = path;
            }

            @Override
            public void run() {
                Luban.with(mActivity)
                        .load(path)  //传人要压缩的图片文件
                        .ignoreBy(300)
                        .setTargetDir(getPath())//设置默认路径的文件
                        .setFocusAlpha(false)
                        .setCompressListener(new OnCompressListener() { //设置回调
                            @Override
                            public void onStart() {
                                //TODO 加载框自定义
                            }

                            @Override
                            public void onSuccess(File file) {
                                newList.add(file);
                                if (!taskList.isEmpty()) {
                                    Runnable runnable = taskList.pop();
                                    handler.post(runnable);
                                } else {
                                    newPhotoFiles.lubanFiles(newList);
                                }
                            }

                            @Override
                            public void onError(Throwable e) {
                                newList.add(path);//压缩异常就写入原始File
                                if (!taskList.isEmpty()) {
                                    Runnable runnable = taskList.pop();
                                    handler.post(runnable);
                                } else {
                                    newPhotoFiles.lubanFiles(newList);
                                }
                            }
                        }).launch();    //启动压缩
            }
        }
        //循环遍历原始路径 添加至taskList中
        for (File path : pathList) {
            taskList.add(new Task(path));
        }
        handler.post(taskList.pop());

    }

    //删除指定目录下文件
    public void deleteFile(Activity mActivity) {
        if (!TextUtils.isEmpty(getPath())) {
            try {
                File file = new File(getPath());
                java.io.File[] fileList = file.listFiles();
                assert fileList != null;
                for (int i = 0; i < fileList.length; i++) {
                    if (!fileList[i].isDirectory()) {
                        boolean isDelete = fileList[i].delete();
                        if (isDelete) {
                            Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                            intent.setData(Uri.fromFile(new File(String.valueOf(file))));
                            mActivity.sendBroadcast(intent);
                        }
                    }
                }
                Toast.makeText(mActivity, "删除成功", Toast.LENGTH_LONG).show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    //获取路径
    private String getPath() {
        String path = Environment.getExternalStorageDirectory() + "/Luban/image/";
        File file = new File(path);
        if (file.mkdirs()) {
            return path;
        }
        return path;
    }

    public interface NewPhotoFiles {
        void lubanFiles(ArrayList<File> files);//压缩是否成功都返回
    }
}
