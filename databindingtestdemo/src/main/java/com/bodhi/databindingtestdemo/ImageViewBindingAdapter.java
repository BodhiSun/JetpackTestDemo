package com.bodhi.databindingtestdemo;

import android.graphics.Color;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

import com.squareup.picasso.Picasso;

/**
 * @author : Sun
 * @version : 1.0
 * @time : 2020/7/31
 * desc : 自定义BindingAdapter 设置image属性 用于ImageView加载图片
 */
public class ImageViewBindingAdapter {

    @BindingAdapter("image")
    public static void setImage(ImageView imageview, String imageurl){
        if(!TextUtils.isEmpty(imageurl)){
            //Picasso加载网络图片
            Picasso.get()
                    .load(imageurl)
                    .placeholder(R.mipmap.ic_launcher)
                    .error(R.mipmap.ic_launcher_round)
                    .into(imageview);
        }else{
            imageview.setBackgroundColor(Color.DKGRAY);
        }
    }

    @BindingAdapter("image")
    public static void setImage(ImageView imageView,int imageResource){
        //加载资源文件中的图片
        imageView.setImageResource(imageResource);
    }

    /**
     * 多参数方法重载
     * requireAll = false 表示image，defaultImageResource两个参数不是必须都赋值，默认为true
     * @param imageView
     * @param imageUrl
     * @param imageResource
     */
    @BindingAdapter(value = {"image","defaultImageResource"},requireAll = false)
    public static void setImage(ImageView imageView, String imageUrl,int imageResource){
        //当网络地址不空时加载网络图片 否则加载本地图片
        if(!TextUtils.isEmpty(imageUrl)){
            Picasso.get()
                    .load(imageUrl)
                    .placeholder(R.mipmap.ic_launcher)
                    .error(R.mipmap.ic_launcher_round)
                    .into(imageView);
        }else{
            imageView.setImageResource(imageResource);
        }
    }


    /**
     * 可选旧值 修改某个属性时 先获取到之前的旧值
     * @param view
     * @param oldPadding
     * @param newPadding
     */
    @BindingAdapter("padding")
    public static void setPadding(View view,int oldPadding,int newPadding){
        Log.e("bindingadapter","oldpadding:"+oldPadding+"   newpadding:"+newPadding);
        if(oldPadding!=newPadding){
            view.setPadding(newPadding,newPadding,newPadding,newPadding);
        }
    }




}
