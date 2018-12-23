package com.mysheng.office.kkanshop.entity;

import java.util.regex.Pattern;

public class ChatTools {
    private static Pattern webPattern = Pattern.compile("http[s]*://[[[^/:]&&[a-zA-Z_0-9]]\\.]+(:\\d+)?(/[a-zA-Z_0-9]+)*(/[a-zA-Z_0-9]*([a-zA-Z_0-9]+\\.[a-zA-Z_0-9]+)*)?(\\?(&?[a-zA-Z_0-9]+=[%[a-zA-Z_0-9]-]*)*)*(#[[a-zA-Z_0-9]|-]+)?(.jpg|.png|.gif|.jpeg)?");

    public static int VIEW_WIDTH=100;
    public static int VIEW_HEIGHT=160;
    public static boolean isNetUri(String url) {
        return webPattern.matcher(url).find();
    }

    public static final int RIGHT_TEXT=1;//右边文字
    public static final int LEFT_TEXT=2;//左边文字
    public static final int RIGHT_IMAGE=3;//右边图片
    public static final int LEFT_IMAGE=4;//左边图片
    public static final int RIGHT_AUDION=5;//右边语音
    public static final int LIFT_AUDIO=6;//左边语音
    public static final int SEND_TIME=7;//发送时间
    public static final int RIGHT_LOCATION=8;//右边位置
    public static final int LEFT_LOCATION=9;//左边位置
    public static final int RIGHT_VIDEO=10;//右边视频
    public static final int LEFT_VIDEO=11;//左边视频
    public static final int RIAGHT_VOICE=12;//右边实时语音
    public static final int LEFT_VOICE=13;//左边实时语音
    public static final int LEFT_GOODS=14;//左边实时语音
    public static final int RIGHT_GOODS=15;//左边实时语音

    public static String[] netImages = {
            "http://wx1.sinaimg.cn/woriginal/daaf97d2gy1fgsxkq8uc3j20dw0ku74x.jpg",
            "http://wx1.sinaimg.cn/woriginal/daaf97d2gy1fgsxkqm7b0j20dw0kut9h.jpg",
            "http://wx4.sinaimg.cn/woriginal/daaf97d2gy1fgsxks2l4ij20dw0kldhb.jpg",
            "http://wx2.sinaimg.cn/woriginal/daaf97d2gy1fgsxksskbkj20dw0kut9b.jpg",
            "https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=2966021298,3341101515&fm=23&gp=0.jpg",
            "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1496402134202&di=6c7f4a6afa5bdf02000c788f7a51e9c0&imgtype=0&src=http%3A%2F%2Fcdnq.duitang.com%2Fuploads%2Fitem%2F201506%2F23%2F20150623183946_iZtFs.jpeg",
            "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1496996892&di=ea1e213c8ddd4427c55f073db9bf91b7&imgtype=jpg&er=1&src=http%3A%2F%2Fpic27.nipic.com%2F20130323%2F9483785_182530048000_2.jpg",
            "http://wx1.sinaimg.cn/woriginal/daaf97d2gy1fgsxkq8uc3j20dw0ku74x.jpg",
            "http://wx1.sinaimg.cn/woriginal/daaf97d2gy1fgsxkqm7b0j20dw0kut9h.jpg",
            "http://wx4.sinaimg.cn/woriginal/daaf97d2gy1fgsxks2l4ij20dw0kldhb.jpg",
            "http://wx2.sinaimg.cn/woriginal/daaf97d2gy1fgsxksskbkj20dw0kut9b.jpg",
            "https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=2966021298,3341101515&fm=23&gp=0.jpg",
            "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1496402134202&di=6c7f4a6afa5bdf02000c788f7a51e9c0&imgtype=0&src=http%3A%2F%2Fcdnq.duitang.com%2Fuploads%2Fitem%2F201506%2F23%2F20150623183946_iZtFs.jpeg",
            "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1496996892&di=ea1e213c8ddd4427c55f073db9bf91b7&imgtype=jpg&er=1&src=http%3A%2F%2Fpic27.nipic.com%2F20130323%2F9483785_182530048000_2.jpg",
            "http://wx1.sinaimg.cn/woriginal/daaf97d2gy1fgsxkq8uc3j20dw0ku74x.jpg",
            "http://wx1.sinaimg.cn/woriginal/daaf97d2gy1fgsxkqm7b0j20dw0kut9h.jpg",
            "http://wx4.sinaimg.cn/woriginal/daaf97d2gy1fgsxks2l4ij20dw0kldhb.jpg",
            "http://wx2.sinaimg.cn/woriginal/daaf97d2gy1fgsxksskbkj20dw0kut9b.jpg",
            "https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=2966021298,3341101515&fm=23&gp=0.jpg",
            "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1496402134202&di=6c7f4a6afa5bdf02000c788f7a51e9c0&imgtype=0&src=http%3A%2F%2Fcdnq.duitang.com%2Fuploads%2Fitem%2F201506%2F23%2F20150623183946_iZtFs.jpeg",
            "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1496996892&di=ea1e213c8ddd4427c55f073db9bf91b7&imgtype=jpg&er=1&src=http%3A%2F%2Fpic27.nipic.com%2F20130323%2F9483785_182530048000_2.jpg",
            "http://wx1.sinaimg.cn/woriginal/daaf97d2gy1fgsxkq8uc3j20dw0ku74x.jpg",
            "http://wx1.sinaimg.cn/woriginal/daaf97d2gy1fgsxkqm7b0j20dw0kut9h.jpg",
            "http://wx4.sinaimg.cn/woriginal/daaf97d2gy1fgsxks2l4ij20dw0kldhb.jpg",
            "http://wx2.sinaimg.cn/woriginal/daaf97d2gy1fgsxksskbkj20dw0kut9b.jpg",
            "https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=2966021298,3341101515&fm=23&gp=0.jpg",
            "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1496402134202&di=6c7f4a6afa5bdf02000c788f7a51e9c0&imgtype=0&src=http%3A%2F%2Fcdnq.duitang.com%2Fuploads%2Fitem%2F201506%2F23%2F20150623183946_iZtFs.jpeg",
            "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1496996892&di=ea1e213c8ddd4427c55f073db9bf91b7&imgtype=jpg&er=1&src=http%3A%2F%2Fpic27.nipic.com%2F20130323%2F9483785_182530048000_2.jpg",
            "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1496996959&di=13c094ba73675a24df2ad1d2c730c02c&imgtype=jpg&er=1&src=http%3A%2F%2Fdasouji.com%2Fwp-content%2Fuploads%2F2015%2F07%2F%25E9%2595%25BF%25E8%258A%25B1%25E5%259B%25BE-6.jpg"
    };

}
