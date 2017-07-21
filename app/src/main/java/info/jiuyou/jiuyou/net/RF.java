package info.jiuyou.jiuyou.net;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import info.jiuyou.jiuyou.config.WeiboConstants;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RF {
    private static Retrofit retrofit;

    static {
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd hh:mm:ss")
                .create();

        retrofit = new Retrofit.Builder()
                .baseUrl("https://api.weibo.com/2/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

    }

    public static Retrofit getInstance() {
        return retrofit;
    }

    public static String weiboLink(String id) {
        return (WeiboConstants.INSTANCE.getWEIBO_DETIAL_BASE_URL() + id);
    }
}
