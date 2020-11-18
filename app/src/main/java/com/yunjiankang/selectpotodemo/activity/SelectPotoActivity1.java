package com.yunjiankang.selectpotodemo.activity;

import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.huantansheng.easyphotos.EasyPhotos;
import com.huantansheng.easyphotos.constant.Type;
import com.huantansheng.easyphotos.models.album.entity.Photo;
import com.stx.xhb.xbanner.XBanner;
import com.yunjiankang.selectpotodemo.R;
import com.yunjiankang.selectpotodemo.adapter.PhptpAdapter1;
import com.yunjiankang.selectpotodemo.base.BaseActivity;
import com.yunjiankang.selectpotodemo.utils.Divider;
import com.yunjiankang.selectpotodemo.utils.GlideEngine;
import com.yunjiankang.selectpotodemo.utils.LubanUtils;

import java.io.File;
import java.util.ArrayList;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
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
 * 类名称: SelectPotoActivity1
 * 类描述:
 * 创建人:zhengleilei.
 * 创建时间:2020/11/18 16:07
 * 邮箱:lifetime0911@163.com
 * 修改备注:
 * 版本号:V 1.0.0
 */
public class SelectPotoActivity1 extends BaseActivity {


    @BindView(R.id.iv_item0)
    ImageView ivItem0;
    @BindView(R.id.iv_close)
    ImageView ivClose;
    @BindView(R.id.rv_item0)
    RecyclerView rvItem0;
    @BindView(R.id.iv_item1)
    ImageView ivItem1;
    @BindView(R.id.tv_item0)
    TextView tvItem0;

    private ArrayList<Photo> showOnePhoto = new ArrayList<>();//原始单图片
    private ArrayList<Photo> showMorePhoto = new ArrayList<>();//原始多图片
    private ArrayList<Photo> showVideoPhoto = new ArrayList<>();//原始视频路径


    private ArrayList<File> newOnePhotoFiles = new ArrayList<>();//压缩后的图片
    private ArrayList<File> newMorePhotoFiles = new ArrayList<>();//压缩后的图片
    private ArrayList<File> newVideoFiles = new ArrayList<>();//压缩后的视频


    private PhptpAdapter1 photoAdapter;//图片的九宫格布局数据

    private int type;//选择是几个图片
    private PopupWindow popWindow;//显示图片

    @Override
    public int getViewId() {
        return R.layout.activity_select_poto1;
    }


    @Override
    public void initData() {
        showToast("回传做了清空处理可在212--222行代码作处理");
        rvItem0 = findViewById(R.id.rv_item0);

        rvItem0.setLayoutManager(new GridLayoutManager(this, 3));

        rvItem0.addItemDecoration(
                Divider.builder().
                        color(getResources().getColor(R.color.colorWhite))
                        .height(6)
                        .build()
        );
        photoAdapter = new PhptpAdapter1(this, showMorePhoto, (GridLayoutManager) rvItem0.getLayoutManager());
        rvItem0.setAdapter(photoAdapter);


        photoAdapter.setAddDrugreCordAdapterListener(new PhptpAdapter1.AddDrugreCordAdapterListener() {
            @Override
            public void itemClick(int position) {//设置添加
                openImage(9);
            }

            @Override
            public void itemShowImage(int position) {
                showPupop(showMorePhoto, position);
            }

            @Override
            public void itemClose(int position) {
                showMorePhoto.remove(position);
                photoAdapter.notifyDataSetChanged();
            }
        });
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.iv_item0, R.id.iv_close, R.id.iv_item1, R.id.tv_item0})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_item0:
                openImage(1);//选择图片单张
                break;
            case R.id.iv_close:
                showOnePhoto.clear();
                Glide.with(SelectPotoActivity1.this)
                        .load(getResources().getDrawable(R.drawable.btn_add_pic))
                        .thumbnail(0.1f)
                        .into(ivItem0);
                ivClose.setVisibility(View.GONE);
                break;
            case R.id.iv_item1://选择视频
                openImage(101);
                break;
            case R.id.tv_item0://删除
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        LubanUtils.getInstance().deleteFile(SelectPotoActivity1.this);
                    }
                }).start();

                break;
        }
    }

    //选择图片和视频
    private void openImage(int type) {
        this.type = type;
        if (type == 101) {
            EasyPhotos.createAlbum(this, true, GlideEngine.getInstance())
                    .setFileProviderAuthority("com.yunjiankang.selectpotodemo.fileprovider")
                    .setSelectedPhotos(showVideoPhoto)
                    .setCount(1)
                    .filter(Type.VIDEO)
                    .start(type);
        } else {
            EasyPhotos.createAlbum(this, true, GlideEngine.getInstance())
                    .setFileProviderAuthority("com.yunjiankang.selectpotodemo.fileprovider")
                    .setSelectedPhotos(type == 1 ? showOnePhoto : showMorePhoto)
                    .setCount(type)
                    .start(type);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (RESULT_OK == resultCode) {//是否已经选择
            if (requestCode == type && data != null) {//相机或相册回调
                ArrayList<Photo> resultPhotos = data.getParcelableArrayListExtra(EasyPhotos.RESULT_PHOTOS);
                boolean selectedOriginal = data.getBooleanExtra(EasyPhotos.RESULT_SELECTED_ORIGINAL, false);//判断用户是否选择了原图
                assert resultPhotos != null;
                if (resultPhotos.size() != 0) {
                    if (showOnePhoto.size() != 0) {
                        showOnePhoto.clear();
                    }
                    if (showMorePhoto.size() != 0) {
                        showMorePhoto.clear();
                    }
                    if (showVideoPhoto.size() != 0) {
                        showVideoPhoto.clear();
                    }
                }

                if (type == 1) {
                    showOnePhoto.addAll(resultPhotos);//单个图片
                    //处理数据
                    for (int a = 0; a < resultPhotos.size(); a++) {
                        Photo mimageitem = resultPhotos.get(a);
                        newOnePhotoFiles.add(a, new File(mimageitem.path));
                    }
                    LubanUtils.getInstance().luBanMore(newOnePhotoFiles, this, new LubanUtils.NewPhotoFiles() {

                        @Override
                        public void lubanFiles(ArrayList<File> files) {
                            uploadDocRegInfoImg(files, type);
                        }
                    });
                } else if (type == 9) {
                    showMorePhoto.addAll(resultPhotos);//多个图片
                    //处理数据
                    for (int a = 0; a < resultPhotos.size(); a++) {
                        Photo mimageitem = resultPhotos.get(a);
                        newMorePhotoFiles.add(a, new File(mimageitem.path));
                    }
                    LubanUtils.getInstance().luBanMore(newMorePhotoFiles, this, new LubanUtils.NewPhotoFiles() {

                        @Override
                        public void lubanFiles(ArrayList<File> files) {
                            uploadDocRegInfoImg(files, type);
                        }
                    });
                } else {
                    showVideoPhoto.addAll(resultPhotos);//视频
                    //处理数据
                    for (int a = 0; a < resultPhotos.size(); a++) {
                        Photo mimageitem = resultPhotos.get(a);
                        newVideoFiles.add(a, new File(mimageitem.path));
                    }
                    uploadDocRegInfoImg(newVideoFiles, type);
                }

            }
        } else if (RESULT_CANCELED == resultCode) {//没有数据的回传  显示默认的图片
            if (type == 1) {
                ivClose.setVisibility(View.GONE);
                Glide.with(SelectPotoActivity1.this)
                        .load(getResources().getDrawable(R.drawable.btn_add_pic))
                        .thumbnail(0.1f)
                        .into(ivItem0);
            } else if (type == 9) {
                photoAdapter.notifyDataSetChanged();
            } else {
                showToast("暂无视频文件");
            }
        }
    }


    //显示之前的图片文件
    private void showPupop(ArrayList<Photo> selectedPhotoList, int tion) {
        View popView = View.inflate(this, R.layout.layout_show_image, null);

        popView.setFocusable(true);
        popView.setFocusableInTouchMode(true);

        popWindow = new PopupWindow(popView, WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        popWindow.setAnimationStyle(R.style.AnimationImagePreview);
        popWindow.setOutsideTouchable(false);
        popWindow.setFocusable(false);
        popWindow.setBackgroundDrawable(new BitmapDrawable());
        popWindow.showAtLocation(popView, Gravity.CENTER, 0, 0);//中间显示
        bgAlpha(0.618f);//添加透明背景

        XBanner xbannerItem0 = popView.findViewById(R.id.xbanner_item0);//轮播图
        TextView tvItem0 = popView.findViewById(R.id.tv_item0);//关闭界面

        xbannerItem0.setData(selectedPhotoList, null);
        xbannerItem0.setBannerCurrentItem(tion);
        xbannerItem0.loadImage(new XBanner.XBannerAdapter() {

            @Override
            public void loadBanner(XBanner banner, Object model, View view, int position) {
                Glide.with(SelectPotoActivity1.this).load(selectedPhotoList.get(position).uri).into((ImageView) view);//设置图片为默认图片
            }
        });

        tvItem0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        popWindow.dismiss();
                        bgAlpha(1.0f);
                    }
                }, 200);
            }
        });
    }

    //TODO 显示还是原来的图片 上传是压缩后的
    private void uploadDocRegInfoImg(ArrayList<File> newFiles, int type) {
        if (type == 1) {//单图
            ivClose.setVisibility(View.VISIBLE);
            Glide.with(SelectPotoActivity1.this)
                    .load(newFiles.get(0).getAbsolutePath())
                    .thumbnail(0.1f)
                    .into(ivItem0);
            String file1 = String.valueOf(new File(showOnePhoto.get(0).path).length());//原始大小
            String file2 = String.valueOf(newFiles.get(0).length());//原始大小
            showToast("原始大小:" + file1 + "--------压缩后的大小:--" + file2);
        } else if (type == 9) {//多图
            int showPhotoMoreLength = 0;
            int newPhotoMoreLength = 0;
            for (int a = 0; a < showMorePhoto.size(); a++) {
                showPhotoMoreLength += new File(showMorePhoto.get(a).path).length();
            }
            for (int a = 0; a < newFiles.size(); a++) {
                newPhotoMoreLength += newFiles.get(a).length();
            }
            showToast("原始大小:" + showPhotoMoreLength + "--------压缩后的大小:--" + newPhotoMoreLength);
            photoAdapter.notifyDataSetChanged();
        } else if (type == 101) {//视频
            String file1 = String.valueOf(new File(showVideoPhoto.get(0).path).length());//原始大小
            String file2 = String.valueOf(newFiles.get(0).length());//原始大小
            showToast("这个没有压缩" + "原始大小:" + file1 + "--" + file2);
            Glide.with(SelectPotoActivity1.this)
                    .load(showVideoPhoto.get(0).uri)
                    .thumbnail(0.1f)
                    .into(ivItem1);
        }

    }

}
