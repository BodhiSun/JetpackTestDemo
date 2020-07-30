package com.bodhi.databindingtestdemo;

/**
 * @author : Sun
 * @version : 1.0
 * @time : 2020/7/29
 * desc :用于在布局中 引用的静态类
 */
public class BookRatingUtil {

    public static String getRatingString(int rating){
        switch (rating) {
            case 0:
                return "零星";
            case 1:
                return "一星";
            case 2:
                return "二星";
            case 3:
                return "三星";
            case 4:
                return "四星";
            case 5:
                return "五星";
        }
        return "";
    }

}
