package com.example.pagingtestdemo.paging_positional.paging;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pagingtestdemo.R;
import com.example.pagingtestdemo.paging_positional.model.Movie;
import com.squareup.picasso.Picasso;

public class MoviePagedListAdapter extends PagedListAdapter<Movie,MoviePagedListAdapter.MovieViewHolder>{

    private Context context;

    public MoviePagedListAdapter(Context context) {
        super(diffCallback);
        this.context = context;
    }

    //DiffUtil用于计算两个数据列表之间的差异
    private static DiffUtil.ItemCallback<Movie> diffCallback = new DiffUtil.ItemCallback<Movie>(){

        //检测两个对象是否代表同一个item
        @Override
        public boolean areItemsTheSame(@NonNull Movie oldItem, @NonNull Movie newItem) {
            return oldItem.id.equals(newItem.id);
        }

        //检测两个item是否存在不一样的数据
        @SuppressLint("DiffUtilEquals")
        @Override
        public boolean areContentsTheSame(@NonNull Movie oldItem, @NonNull Movie newItem) {
            return oldItem.equals(newItem);
        }
    };

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.item_movie, parent, false);
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
        //若getItem方法有数据将数据和UI控件绑定，无数据getItem()会通知PagedList获取下一页数据，PagedList再让DataSource执行具体的获取数据工作
        Movie movie = getItem(position);
        if(movie!=null){
            Picasso.get()
                    .load(movie.images.small)
                    .placeholder(R.mipmap.ic_launcher)
                    .error(R.mipmap.ic_launcher_round)
                    .into(holder.iv_icon);
            holder.tv_title.setText(movie.title);
            holder.tv_year.setText("上映年份："+movie.year);
        }else{
            holder.iv_icon.setImageResource(R.mipmap.ic_launcher);
            holder.tv_title.setText("");
            holder.tv_year.setText("");
        }
    }

    class MovieViewHolder extends RecyclerView.ViewHolder{
        ImageView iv_icon;
        TextView tv_title;
        TextView tv_year;

        public MovieViewHolder(@NonNull View itemView) {
            super(itemView);
            iv_icon = itemView.findViewById(R.id.iv_icon);
            tv_title = itemView.findViewById(R.id.tv_title);
            tv_year = itemView.findViewById(R.id.tv_year);
        }
    }
}
