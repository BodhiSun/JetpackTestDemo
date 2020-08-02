package com.bodhi.databindingtestdemo.login;

import android.graphics.Color;
import android.text.TextUtils;
import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

import com.bodhi.databindingtestdemo.R;
import com.squareup.picasso.Picasso;

/**
 * @author : Sun
 * @version : 1.0
 * @time : 2020/8/1
 * desc :
 */
public class RecyclerViewImageBindingAdapter {
    @BindingAdapter("itemImage")
    public static void setImage(ImageView imageView,String imageUrl){
        if(!TextUtils.isEmpty(imageUrl)){
            Picasso.get()
                    .load(imageUrl)
                    .placeholder(R.mipmap.ic_launcher)
                    .error(R.mipmap.ic_launcher_round)
                    .into(imageView);
        }else{
            imageView.setBackgroundColor(Color.DKGRAY);
        }
    }

}
