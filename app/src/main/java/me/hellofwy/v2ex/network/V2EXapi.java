package me.hellofwy.v2ex.network;

import java.util.List;

import me.hellofwy.v2ex.domain.model.MemberModel;
import me.hellofwy.v2ex.domain.model.NodeModel;
import me.hellofwy.v2ex.domain.model.TopicModel;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by fwy on 2016/9/27.
 */

public interface V2EXapi {
    static String BASU_URL = "https://www.v2ex.com";

    @GET("api/topics/latest.json")
    Call<List<TopicModel>> getLatestTopics();

    @GET("api/topics/hot.json")
    Call<List<TopicModel>> getHotTopics();

    @GET("api/nodes/show.json")
    Call<NodeModel> getNodeInfo(@Query("name") String nodeName);

    @GET("api/members/show.json")
    Call<MemberModel> getMemberInfoByName(@Query("username") String userName);

    @GET("api/members/show.json")
    Call<MemberModel> getMemberInfoById(@Query("id") String userId);
}
