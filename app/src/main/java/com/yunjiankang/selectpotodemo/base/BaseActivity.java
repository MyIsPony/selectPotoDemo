package com.yunjiankang.selectpotodemo.base;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.annotation.Nullable;
import butterknife.ButterKnife;

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
 * 类名称: BaseActivity
 * 类描述:
 * 创建人:zhengleilei.
 * 创建时间:2020/3/5 09:59
 * 邮箱:lifetime0911@163.com
 * 修改备注:
 * 版本号:V 1.0.0
 */
public abstract class BaseActivity extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(getViewId());
        initData();
    }

   public abstract int getViewId();//布局


    public  abstract void initData();//初始化数据


    public  void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    /**
     * 设置窗口的背景透明度
     *
     * @param bgAlpha 0.0-1.0
     */
    public void bgAlpha(float bgAlpha) {
//        WindowManager.LayoutParams layoutParams = this.getWindow().getAttributes();
//        layoutParams.alpha = f;
//        this.getWindow().setAttributes(layoutParams);

        WindowManager.LayoutParams lp = this.getWindow()
                .getAttributes();
        lp.alpha = bgAlpha;
        this.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        this.getWindow().setAttributes(lp);
    }
}
