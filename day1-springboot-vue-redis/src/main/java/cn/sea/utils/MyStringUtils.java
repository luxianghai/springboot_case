package cn.sea.utils;

import java.util.List;

public class MyStringUtils {

    /**
     * 判断字符串数组中是否有空串，有返回true,否则返回false
     * @param list
     * @return  true false
     */
    public static boolean checkStringsIsEmpty(List<String> list) {

        if ( list == null || list.size() == 0) return true;

        boolean isEmpty = false;

        for ( int i = 0 ; i < list.size(); i ++ ) {
            if( list.get(i) == null || list.get(i).trim().equals("")) {
                return true;
            }
        }

        return isEmpty;
    }

}
