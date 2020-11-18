package com.yunjiankang.selectpotodemo;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;

import com.lzy.okgo.OkGo;

import androidx.multidex.MultiDex;

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
 * 类名称: MyApplication
 * 类描述:
 * 创建人:zhengleilei.
 * 创建时间:2020/3/5 10:04
 * 邮箱:lifetime0911@163.com
 * 修改备注:
 * 版本号:V 1.0.0
 */
public class MyApplication extends Application {

    @SuppressLint("StaticFieldLeak")
    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        MultiDex.install(this);
        mContext = this;
        OkGo.getInstance().init(this);
    }

    public static Context getAppContext() {
        return mContext;
    }
}
