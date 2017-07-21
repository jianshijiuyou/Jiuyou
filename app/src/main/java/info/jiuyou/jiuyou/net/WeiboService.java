package info.jiuyou.jiuyou.net;


import java.util.List;

import info.jiuyou.jiuyou.bean.Emotions;
import info.jiuyou.jiuyou.bean.weibo.StatusesResultBase;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.Url;

public interface WeiboService {


    @GET("statuses/home_timeline.json")
    Call<StatusesResultBase> homeTimeline(
            @Query("access_token") String access_token,
            @Query("since_id") String since_id,
            @Query("max_id") String max_id,
            @Query("count") String count
    );

    @GET("statuses/user_timeline.json")
    Call<StatusesResultBase> userTimeline(
            @Query("access_token") String access_token,
            @Query("uid") String uid,
            @Query("screen_name") String screen_name,
            @Query("since_id") String since_id,
            @Query("max_id") String max_id
    );


    @GET("emotions.json")
    Call<List<Emotions>> emotions(@Query("access_token") String access_token);

    @GET
    Call<ResponseBody> requestHtml(@Url String url);
}
