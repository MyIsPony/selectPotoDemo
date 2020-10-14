package com.yunjiankang.selectpotodemo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.huantansheng.easyphotos.models.album.entity.Photo;

import java.util.ArrayList;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
 * 类名称: PhptpAdapter
 * 类描述:选择图片正方
 * 创建人:zhengleilei.
 * 创建时间:2020/3/10 11:21
 * 邮箱:zhengll@wbpharma.com
 * 修改备注:
 * 版本号:V 1.0.0
 */
public class PhptpAdapter extends RecyclerView.Adapter<PhptpAdapter.PhotoViewHolder> {

    private ArrayList<Photo> photoPaths;
    private LayoutInflater inflater;
    private GridLayoutManager gridLayoutManager;

    private Context mContext;

    public final static int TYPE_ADD = 1;
    public final static int TYPE_PHOTO = 2;

    public final static int MAX = 9;

    private AddDrugreCordAdapterListener addDrugreCordAdapterListener;

    public PhptpAdapter(Context mContext, ArrayList<Photo> photoPaths, GridLayoutManager gridLayoutManager) {
        this.photoPaths = photoPaths;
        this.mContext = mContext;
        inflater = LayoutInflater.from(mContext);
        this.gridLayoutManager = gridLayoutManager;
    }


    @Override
    public PhotoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = null;
        switch (viewType) {
            case TYPE_ADD:
                itemView = inflater.inflate(R.layout.layout_electronic_photo, parent, false);
                break;
            case TYPE_PHOTO:
                itemView = inflater.inflate(R.layout.layout_item_photo, parent, false);
                break;
        }

        final PhotoViewHolder viewHolder = new PhotoViewHolder(itemView);


        if (viewHolder.ivAdd != null) {
            viewHolder.ivAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //因为前面有个添加的图片 所以需要-1
                    addDrugreCordAdapterListener.itemClick(photoPaths.size() == 9 ? viewHolder.getAdapterPosition() : viewHolder.getAdapterPosition() - 1);
                }
            });
        }
        if (viewHolder.ivPhoto != null) {
            viewHolder.ivPhoto.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //因为前面有个添加的图片 所以需要-1
                    addDrugreCordAdapterListener.itemShowImage(photoPaths.size() == 9 ? viewHolder.getAdapterPosition() : viewHolder.getAdapterPosition() - 1);
                }
            });
        }
        if (viewHolder.ivClose != null) {
            viewHolder.ivClose.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //因为前面有个添加的图片 所以需要-1
                    addDrugreCordAdapterListener.itemClose(photoPaths.size() == 9 ? viewHolder.getAdapterPosition() : viewHolder.getAdapterPosition() - 1);
                }
            });
        }
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(final PhotoViewHolder holder, final int position) {
        if (getItemViewType(position) == TYPE_PHOTO) {
            Glide.with(mContext)
                    .load(photoPaths.get(photoPaths.size() == 9 ? position : position - 1).uri)
                    .thumbnail(0.1f)
                    .into(holder.ivPhoto);
        }
        ViewGroup.LayoutParams parm = holder.itemView.getLayoutParams();
        parm.height =
                gridLayoutManager.getWidth() / gridLayoutManager.getSpanCount()
                        - 2 * holder.itemView.getPaddingLeft() - 2 * ((ViewGroup.MarginLayoutParams) parm).leftMargin;
    }


    @Override
    public int getItemCount() {
        int count = photoPaths.size() + 1;
        if (count > MAX) {
            count = MAX;
        }
        return count;
    }

    @Override
    public int getItemViewType(int position) {
        if (photoPaths.size() == 9) {//说明所有的数据都需要填充
            return TYPE_PHOTO;
        } else {
            if (position == 0) {
                return TYPE_ADD;
            } else {
                return TYPE_PHOTO;
            }
        }
    }

    public static class PhotoViewHolder extends RecyclerView.ViewHolder {
        private ImageView ivPhoto;
        private ImageView ivAdd;
        private ImageView ivClose;

        public PhotoViewHolder(View itemView) {
            super(itemView);
            ivPhoto = (ImageView) itemView.findViewById(R.id.iv_item0);
            ivAdd = (ImageView) itemView.findViewById(R.id.iv_add);
            ivClose = (ImageView) itemView.findViewById(R.id.iv_close);
        }
    }


    public void setAddDrugreCordAdapterListener(AddDrugreCordAdapterListener addDrugreCordAdapterListener) {
        this.addDrugreCordAdapterListener = addDrugreCordAdapterListener;
    }

    public interface AddDrugreCordAdapterListener {
        void itemClick(int position);//点击添加

        void itemShowImage(int position);//点击查看

        void itemClose(int position);//点击删除
    }

}
