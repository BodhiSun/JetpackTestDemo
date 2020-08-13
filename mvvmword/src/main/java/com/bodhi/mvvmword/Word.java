package com.bodhi.mvvmword;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * @author : Sun
 * @version : 1.0
 * @time : 2020/8/13
 * desc :
 */
@Entity(tableName = "word_table")
public class Word {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "word")
    private String mWord;

    public Word(@NonNull String word){
        this.mWord = word;
    }

    public String getWord(){
        return this.mWord;
    }

}
