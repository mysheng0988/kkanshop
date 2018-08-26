package com.mysheng.office.kkanshop.util;

import java.util.regex.Pattern;

/**
 * Created by myaheng on 2018/8/15.
 */

public class ChatTool {
    private static Pattern webPattern = Pattern.compile("http[s]*://[[[^/:]&&[a-zA-Z_0-9]]\\.]+(:\\d+)?(/[a-zA-Z_0-9]+)*(/[a-zA-Z_0-9]*([a-zA-Z_0-9]+\\.[a-zA-Z_0-9]+)*)?(\\?(&?[a-zA-Z_0-9]+=[%[a-zA-Z_0-9]-]*)*)*(#[[a-zA-Z_0-9]|-]+)?(.jpg|.png|.gif|.jpeg)?");

    public static int VIEW_WIDTH=100;
    public static int VIEW_HEIGHT=160;
    public static boolean isNetUri(String url) {
        return webPattern.matcher(url).find();
    }
}
