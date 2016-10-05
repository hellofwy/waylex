package me.hellofwy.v2ex.network;

import java.io.IOException;
import java.util.List;

import me.hellofwy.v2ex.domain.model.MemberModel;
import me.hellofwy.v2ex.domain.model.NodeModel;
import me.hellofwy.v2ex.domain.model.TopicModel;
import me.hellofwy.v2ex.domain.repository.TopicRepository;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by fwy on 2016/9/28.
 */

public class TopicRepositoryImpl implements TopicRepository {
    private static V2EXapi mV2EXapiService;

    public TopicRepositoryImpl() {
        if(mV2EXapiService == null) {
            mV2EXapiService = getV2EXapiService();
        }
    }

    private V2EXapi getV2EXapiService() {
        if(mV2EXapiService == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(V2EXapi.BASU_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            mV2EXapiService = retrofit.create(V2EXapi.class);
        }
        return mV2EXapiService;
    }

    @Override
    public List<TopicModel> getLatestTopics() {
        Call<List<TopicModel>> topics_call = mV2EXapiService.getLatestTopics();
        List<TopicModel> topics;
        try {
            topics = topics_call.execute().body();
        } catch (IOException e) {
            return null;
        }
        return topics;
    }

    @Override
    public List<TopicModel> getHotTopics() {
        Call<List<TopicModel>> topics_call = mV2EXapiService.getHotTopics();
        List<TopicModel> topics;
        try {
            topics = topics_call.execute().body();
        } catch (IOException e) {
            return null;
        }
        return topics;
    }

    @Override
    public NodeModel getNodeInfo(String nodeName) {
        Call<NodeModel> node_call = mV2EXapiService.getNodeInfo(nodeName);
        NodeModel node;
        try {
            node = node_call.execute().body();
        } catch (IOException e) {
            return null;
        }
        return node;
    }

    @Override
    public MemberModel getMemberInfoById(String memberId) {
        Call<MemberModel> member_call = mV2EXapiService.getMemberInfoById(memberId);
        MemberModel member;
        try {
            member = member_call.execute().body();
        } catch (IOException e) {
            return null;
        }
        return member;
    }

    @Override
    public MemberModel getMemberInfoByName(String memberName) {
        Call<MemberModel> member_call = mV2EXapiService.getMemberInfoByName(memberName);
        MemberModel member;
        try {
            member = member_call.execute().body();
        } catch (IOException e) {
            return null;
        }
        return member;
    }
}
