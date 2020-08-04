package com.bodhi.mvvmgithub.bindingadapter;

import android.graphics.Color;
import android.text.TextUtils;
import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

import com.bodhi.mvvmgithub.R;
import com.squareup.picasso.Picasso;

/**
 * 自定义BindingAdapter 使布局文件可以使用image属性
 */
public class ImageViewBindingAdapter {

    @BindingAdapter("image")
    public static void setImage(ImageView imageView,String imageUrl){
        if(!TextUtils.isEmpty(imageUrl)){
            Picasso.get()
                    .load(imageUrl)
                    .placeholder(R.mipmap.ic_launcher)
                    .error(R.mipmap.ic_launcher_round)
                    .into(imageView);
        }else{
            imageView.setImageResource(R.mipmap.ic_launcher_round);
        }
    }



}
