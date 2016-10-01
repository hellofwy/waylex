package me.hellofwy.v2ex.network;

import java.util.List;

import me.hellofwy.v2ex.domain.model.TopicModel;
import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by fwy on 2016/9/27.
 */

public interface V2EXapi {
    static String BASU_URL = "https://www.v2ex.com";
    @GET("api/topics/latest.json")
    Call<List<TopicModel>> getLatestTopics();
}
