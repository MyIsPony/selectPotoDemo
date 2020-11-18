package com.yunjiankang.selectpotodemo.activity;

import android.util.Log;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.base.Request;
import com.yunjiankang.selectpotodemo.R;
import com.yunjiankang.selectpotodemo.base.BaseActivity;

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
 * 类名称: OkGoActivity
 * 类描述:
 * 创建人:zhengleilei.
 * 创建时间:2020/11/18 16:11
 * 邮箱:lifetime0911@163.com
 * 修改备注:
 * 版本号:V 1.0.0
 */
public class OkGoActivity extends BaseActivity {
    @Override
    public int getViewId() {
        return R.layout.activity_okgo;
    }

    @Override
    public void initData() {

        //普通的网络请求

        OkGo.<String>post("您的URL")
                .tag(this)//
                .params("kay", "value")
                .retryCount(3)//几次重连
                .execute(new StringCallback() {
                    @Override
                    public void onStart(Request<String, ? extends Request> request) {
                        super.onStart(request);
                        Log.e("select", "开始请求");
                    }

                    @Override
                    public void onSuccess(Response<String> response) {
                        Log.e("select", "请求成功");
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        Log.e("select", "请求失败");
                    }
                });

    }
}
