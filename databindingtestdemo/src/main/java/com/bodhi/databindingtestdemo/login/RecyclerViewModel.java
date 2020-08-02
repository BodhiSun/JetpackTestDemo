package com.bodhi.databindingtestdemo.login;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : Sun
 * @version : 1.0
 * @time : 2020/8/2
 * desc :
 */
public class RecyclerViewModel {
    public List<Book2> getBooks(){
        List<Book2> book2s = new ArrayList<>();
        for(int i = 0; i<10;i++){
            Book2 book2= new Book2("Android开发手册"+i,"Google"+i);
            book2.image = "https://pics5.baidu.com/feed/5366d0160924ab1871d32c86c4cef6ca7a890b72.jpeg?token=46f78d47ee2a36dfbb4307f2f5c2aaed";
            book2s.add(book2);
        }
        return book2s;
    }


}
