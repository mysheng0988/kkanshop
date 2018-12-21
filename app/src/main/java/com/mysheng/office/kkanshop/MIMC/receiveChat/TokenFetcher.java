package com.mysheng.office.kkanshop.MIMC.receiveChat;

import com.xiaomi.mimc.MIMCTokenFetcher;

import org.json.JSONObject;

import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by myaheng on 2018/12/21.
 */

public class TokenFetcher implements MIMCTokenFetcher {

    /**
     * appId/appKey/appSecret，小米开放平台(https://dev.mi.com/console/appservice/mimc.html)申请
     * 其中appKey和appSecret不可存储于APP端，应存储于APP自己的服务器，以防泄漏。
     *
     * 此处appId/appKey/appSec为小米MimcDemo所有，会在一定时间后失效，建议开发者自行申请
     **/
    // online
    private long appId = 2882303761517808316L;
    private String appKey = "5491780810316";
    private String appSecret = "3OOIj5pjUvyGmU0Nowctzw==";
    private String regionKey = "REGION_CN";
    private String domain = "https://mimc.chat.xiaomi.net/";
    // 用户登录APP的帐号
    private String appAccount = "";
    private String url;

    public TokenFetcher(String appAccount) {
        this.appAccount = appAccount;
    }

    @Override
    public String fetchToken() {
        /**
         * fetchToken()由SDK内部线程调用，获取小米Token服务器返回的JSON字符串
         * 本MimcDemo直接从小米Token服务器获取JSON串，只解析出键data对应的值返回即可，切记！！！
         * 强烈建议，APP从自己服务器获取data对应的JSON串，APP自己的服务器再从小米Token服务器获取，以防appKey和appSecret泄漏
         */

        url = domain + "api/account/token";
        String json = "{\"appId\":" + appId + ",\"appKey\":\"" + appKey + "\",\"appSecret\":\"" +
                appSecret + "\",\"appAccount\":\"" + appAccount + "\",\"regionKey\":\"" + regionKey + "\"}";
        MediaType JSON = MediaType.parse("application/json;charset=utf-8");
        OkHttpClient client = new OkHttpClient();
        Request request = new Request
                .Builder()
                .url(url)
                .post(RequestBody.create(JSON, json))
                .build();
        Call call = client.newCall(request);
        JSONObject data = null;
        try {
            Response response = call.execute();
            data = new JSONObject(response.body().string());
            int code = data.getInt("code");
            if (code != 200) {
                //logger.warn("Error, code = " + code);
                return null;
            }
        } catch (Exception e) {
            //logger.warn("Get token exception: " + e);
        }

        return data != null ? data.toString() : null;
    }
}
