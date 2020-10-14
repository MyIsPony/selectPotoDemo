package com.yunjiankang.selectpotodemo;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.huantansheng.easyphotos.engine.ImageEngine;

import androidx.annotation.NonNull;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

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
 * 类名称: GlideEngine
 * 类描述:
 * 创建人:zhengleilei.
 * 创建时间:2020/3/10 11:50
 * 邮箱:zhengll@wbpharma.com
 * 修改备注:
 * 版本号:V 1.0.0
 */
public class GlideEngine  implements ImageEngine {
    //单例
    private static GlideEngine instance = null;
    //单例模式，私有构造方法
    private GlideEngine() {
    }
    //获取单例
    public static GlideEngine getInstance() {
        if (null == instance) {
            synchronized (GlideEngine.class) {
                if (null == instance) {
                    instance = new GlideEngine();
                }
            }
        }
        return instance;
    }

    /**
     * 加载图片到ImageView
     *
     * @param context   上下文
     * @param uri 图片路径Uri
     * @param imageView 加载到的ImageView
     */
    //安卓10推荐uri，并且path的方式不再可用
    @Override
    public void loadPhoto(@NonNull Context context, @NonNull Uri uri, @NonNull ImageView imageView) {
        Glide.with(context).load(uri).transition(withCrossFade()).into(imageView);
    }

    /**
     * 加载gif动图图片到ImageView，gif动图不动
     *
     * @param context   上下文
     * @param gifUri   gif动图路径Uri
     * @param imageView 加载到的ImageView
     *                  <p>
     *                  备注：不支持动图显示的情况下可以不写
     */
    //安卓10推荐uri，并且path的方式不再可用
    @Override
    public void loadGifAsBitmap(@NonNull Context context, @NonNull Uri gifUri, @NonNull ImageView imageView) {
        Glide.with(context).asBitmap().load(gifUri).into(imageView);
    }

    /**
     * 加载gif动图到ImageView，gif动图动
     *
     * @param context   上下文
     * @param gifUri   gif动图路径Uri
     * @param imageView 加载动图的ImageView
     *                  <p>
     *                  备注：不支持动图显示的情况下可以不写
     */
    //安卓10推荐uri，并且path的方式不再可用
    @Override
    public void loadGif(@NonNull Context context, @NonNull Uri gifUri, @NonNull ImageView imageView) {
        Glide.with(context).asGif().load(gifUri).transition(withCrossFade()).into(imageView);
    }


    /**
     * 获取图片加载框架中的缓存Bitmap
     *
     * @param context 上下文
     * @param uri    图片路径
     * @param width   图片宽度
     * @param height  图片高度
     * @return Bitmap
     * @throws Exception 异常直接抛出，EasyPhotos内部处理
     */
    //安卓10推荐uri，并且path的方式不再可用
    @Override
    public Bitmap getCacheBitmap(@NonNull Context context, @NonNull Uri uri, int width, int height) throws Exception {
        return Glide.with(context).asBitmap().load(uri).submit(width, height).get();
    }

}

