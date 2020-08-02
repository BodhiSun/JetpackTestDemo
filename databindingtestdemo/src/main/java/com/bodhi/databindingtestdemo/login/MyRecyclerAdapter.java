package com.bodhi.databindingtestdemo.login;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;

import com.bodhi.databindingtestdemo.R;
import com.bodhi.databindingtestdemo.databinding.ItemLayoutBinding;

import java.util.List;

/**
 * @author : Sun
 * @version : 1.0
 * @time : 2020/8/1
 * desc :
 */
public class MyRecyclerAdapter extends RecyclerView.Adapter<MyRecyclerAdapter.MyViewHolder> {

    private List<Book2> book2s;

    public MyRecyclerAdapter(List<Book2> book2s){
        this.book2s = book2s;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
         ItemLayoutBinding itemLayoutBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.item_layout, parent, false);

        return new MyViewHolder(itemLayoutBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Book2 book2 = book2s.get(position);
        holder.itemLayoutBinding.setBook(book2);
    }

    @Override
    public int getItemCount() {
        return book2s.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        ItemLayoutBinding itemLayoutBinding;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        public MyViewHolder(ItemLayoutBinding itemLayoutBinding){
            super(itemLayoutBinding.getRoot());
            this.itemLayoutBinding = itemLayoutBinding;
        }
    }
}
