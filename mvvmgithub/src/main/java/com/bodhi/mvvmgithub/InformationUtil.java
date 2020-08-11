package com.bodhi.mvvmgithub;

/**
 * @author : Sun
 * @version : 1.0
 * @time : 2020/7/29
 * desc :用于在布局中 引用的静态类
 */
public class InformationUtil {

    public static String getLocation(String area){
        return "location:"+area;
    }

    public static String getFollowers(int follower){
        return "followers:"+follower;
    }

    public static String getFollowing(int following){
        return "following:"+following;
    }
}
