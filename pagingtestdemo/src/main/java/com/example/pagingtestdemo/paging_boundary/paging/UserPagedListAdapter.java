package com.example.pagingtestdemo.paging_boundary.paging;

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
import com.example.pagingtestdemo.paging_boundary.model.User;
import com.squareup.picasso.Picasso;

/**
 * @author : Sun
 * @version : 1.0
 * @time : 2020/8/3
 * desc :
 */
public class UserPagedListAdapter extends PagedListAdapter<User, UserPagedListAdapter.UserViewHolder> {

    private Context context;

    public UserPagedListAdapter(Context context) {
        super(diffCallback);
        this.context = context;
    }

    private static DiffUtil.ItemCallback<User> diffCallback = new DiffUtil.ItemCallback<User>() {
        @Override
        public boolean areItemsTheSame(@NonNull User oldItem, @NonNull User newItem) {
            return oldItem.id==newItem.id;
        }

        @SuppressLint("DiffUtilEquals")
        @Override
        public boolean areContentsTheSame(@NonNull User oldItem, @NonNull User newItem) {
            return oldItem.equals(newItem);
        }
    };

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.item_user, parent, false);
        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        User user = getItem(position);
        if(user!=null){
            Picasso.get()
                    .load(user.avatar)
                    .placeholder(R.mipmap.ic_launcher)
                    .error(R.mipmap.ic_launcher_round)
                    .into(holder.iv_icon);

            holder.tv_title.setText(user.name);
            holder.tv_year.setText("id:"+user.id);
        }else{
            holder.iv_icon.setImageResource(R.mipmap.ic_launcher);
            holder.tv_title.setText("");
            holder.tv_year.setText("");
        }
    }

    class UserViewHolder extends RecyclerView.ViewHolder {

        ImageView iv_icon;
        TextView tv_title;
        TextView tv_year;

        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            iv_icon = itemView.findViewById(R.id.iv_icon);
            tv_title = itemView.findViewById(R.id.tv_title);
            tv_year = itemView.findViewById(R.id.tv_year);
        }
    }
}
