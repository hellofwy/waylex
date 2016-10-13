package me.hellofwy.v2ex.network;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import me.hellofwy.v2ex.domain.model.MemberModel;
import me.hellofwy.v2ex.domain.model.NodeModel;
import me.hellofwy.v2ex.domain.model.TopicModel;
import me.hellofwy.v2ex.domain.repository.TopicRepository;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static me.hellofwy.v2ex.R.id.topics;
import static me.hellofwy.v2ex.R.raw.member;
import static me.hellofwy.v2ex.R.raw.node;

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
        final OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .readTimeout(15, TimeUnit.SECONDS)
                .connectTimeout(15, TimeUnit.SECONDS)
                .build();

        if(mV2EXapiService == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .client(okHttpClient)
                    .baseUrl(V2EXapi.BASU_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            mV2EXapiService = retrofit.create(V2EXapi.class);
        }
        return mV2EXapiService;
    }

    @Override
    public List<TopicModel> getLatestTopics() throws IOException {
        Call<List<TopicModel>> topics_call = mV2EXapiService.getLatestTopics();
        return topics_call.execute().body();
    }

    @Override
    public List<TopicModel> getHotTopics() throws IOException {
        Call<List<TopicModel>> topics_call = mV2EXapiService.getHotTopics();
        return topics_call.execute().body();
    }

    @Override
    public NodeModel getNodeInfo(String nodeName) throws IOException {
        Call<NodeModel> node_call = mV2EXapiService.getNodeInfo(nodeName);
        return node_call.execute().body();
    }

    @Override
    public MemberModel getMemberInfoById(String memberId) throws IOException {
        Call<MemberModel> member_call = mV2EXapiService.getMemberInfoById(memberId);
        return member_call.execute().body();
    }

    @Override
    public MemberModel getMemberInfoByName(String memberName) throws IOException {
        Call<MemberModel> member_call = mV2EXapiService.getMemberInfoByName(memberName);
        return member_call.execute().body();
    }
}
