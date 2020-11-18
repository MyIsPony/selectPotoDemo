package com.yunjiankang.selectpotodemo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.huantansheng.easyphotos.EasyPhotos;
import com.huantansheng.easyphotos.models.album.entity.Photo;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.yunjiankang.selectpotodemo.R;
import com.yunjiankang.selectpotodemo.base.BaseActivity;
import com.yunjiankang.selectpotodemo.utils.GlideEngine;
import com.yunjiankang.selectpotodemo.utils.LubanUtils;

import java.io.File;
import java.util.ArrayList;

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
 * 类名称: OkGoUploadActivity
 * 类描述:
 * 创建人:zhengleilei.
 * 创建时间:2020/11/18 17:08
 * 邮箱:zhengll@wbpharma.com
 * 修改备注:
 * 版本号:V 1.0.0
 */
public class OkGoUploadActivity extends BaseActivity {
    @BindView(R.id.iv_item0)
    ImageView ivItem0;
    @BindView(R.id.iv_close)
    ImageView ivClose;

    private ArrayList<Photo> showOnePhoto = new ArrayList<>();//原始单图片
    private ArrayList<File> newOnePhotoFiles = new ArrayList<>();//压缩后的图片

    @Override
    public int getViewId() {
        return R.layout.activity_okgo_upload;
    }

    @Override
    public void initData() {
        showToast("压缩完成后自动上传");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.iv_close, R.id.iv_item0})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_item0:
                EasyPhotos.createAlbum(this, true, GlideEngine.getInstance())
                        .setFileProviderAuthority("com.yunjiankang.selectpotodemo.fileprovider")
                        .setSelectedPhotos(showOnePhoto)
                        .setCount(1)
                        .start(1);
                break;
            case R.id.iv_close:
                showOnePhoto.clear();
                Glide.with(OkGoUploadActivity.this)
                        .load(getResources().getDrawable(R.drawable.btn_add_pic))
                        .thumbnail(0.1f)
                        .into(ivItem0);
                ivClose.setVisibility(View.GONE);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (RESULT_OK == resultCode) {//是否已经选择
            if (requestCode == 1 && data != null) {//相机或相册回调
                ArrayList<Photo> resultPhotos = data.getParcelableArrayListExtra(EasyPhotos.RESULT_PHOTOS);
                boolean selectedOriginal = data.getBooleanExtra(EasyPhotos.RESULT_SELECTED_ORIGINAL, false);//判断用户是否选择了原图
                assert resultPhotos != null;
                if (resultPhotos.size() != 0) {
                    if (showOnePhoto.size() != 0) {
                        showOnePhoto.clear();
                    }
                }
                showOnePhoto.addAll(resultPhotos);//单个图片
                //处理数据
                for (int a = 0; a < resultPhotos.size(); a++) {
                    Photo mimageitem = resultPhotos.get(a);
                    newOnePhotoFiles.add(a, new File(mimageitem.path));
                }
                LubanUtils.getInstance().luBanMore(newOnePhotoFiles, this, new LubanUtils.NewPhotoFiles() {

                    @Override
                    public void lubanFiles(ArrayList<File> files) {
                        uploadDocRegInfoImg(files);
                    }
                });

            }
        } else if (RESULT_CANCELED == resultCode) {//没有数据的回传  显示默认的图片
            ivClose.setVisibility(View.GONE);
            Glide.with(OkGoUploadActivity.this)
                    .load(getResources().getDrawable(R.drawable.btn_add_pic))
                    .thumbnail(0.1f)
                    .into(ivItem0);
        }
    }

    //TODO 显示还是原来的图片 上传是压缩后的
    private void uploadDocRegInfoImg(ArrayList<File> newFiles) {
        ivClose.setVisibility(View.VISIBLE);
        Glide.with(OkGoUploadActivity.this)
                .load(newFiles.get(0).getAbsolutePath())
                .thumbnail(0.1f)
                .into(ivItem0);
        String file1 = String.valueOf(new File(showOnePhoto.get(0).path).length());//原始大小
        String file2 = String.valueOf(newFiles.get(0).length());//原始大小
        showToast("原始大小:" + file1 + "--------压缩后的大小:--" + file2);

        uploadRpImg(newFiles.get(0));//上传图片
    }


    /**
     * 上传图片
     */
    private void uploadRpImg(File photoPath) {

        OkGo.<String>post("您的URL地址/uploadRpImg")
                .tag(this)
                .params("image", photoPath)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        showToast("上传成功");
                        //成功后删除压缩后的文件
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                LubanUtils.getInstance().deleteFile(OkGoUploadActivity.this);
                            }
                        }).start();
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                    }

                });

    }

}
