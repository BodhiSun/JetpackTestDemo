package com.bodhi.mvvmword;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

/**
 * @author : Sun
 * @version : 1.0
 * @time : 2020/8/13
 * desc :
 */
public class WordListAdapter extends RecyclerView.Adapter<WordListAdapter.WordViewHolder>{

    class WordViewHolder extends RecyclerView.ViewHolder{
        private final TextView wordItemView;

        public WordViewHolder(@NonNull View itemView) {
            super(itemView);
            wordItemView = itemView.findViewById(R.id.textView);
        }
    }

    private final LayoutInflater mInflater;
    private List<Word> mWords;

    WordListAdapter(Context context){mInflater = LayoutInflater.from(context);}

    @NonNull
    @Override
    public WordViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.recyclerview_item, parent, false);
        return new WordViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull WordViewHolder holder, int position) {
        if(mWords != null){
            Word current = mWords.get(position);
            holder.wordItemView.setText(current.getWord());
        }else{
            holder.wordItemView.setText("No Word");
        }
    }

    void setmWords(List<Word> words){
        mWords = words;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if(mWords != null)
            return mWords.size();
        else
            return 0;
    }


}
