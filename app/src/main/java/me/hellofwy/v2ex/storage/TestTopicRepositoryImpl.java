package me.hellofwy.v2ex.storage;

import android.content.Context;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import me.hellofwy.v2ex.R;
import me.hellofwy.v2ex.domain.model.TopicModel;
import me.hellofwy.v2ex.domain.repository.TopicRepository;
import me.hellofwy.v2ex.network.V2EXapi;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import timber.log.Timber;

import static android.R.attr.path;

/**
 * Created by fwy on 2016/9/28.
 */

public class TestTopicRepositoryImpl implements TopicRepository {
    private Context mContext;

    public TestTopicRepositoryImpl(Context context) {
        mContext = context;
    }

    @Override
    public List<TopicModel> getLatestTopics() {
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        InputStream file = mContext.getResources().openRawResource(R.raw.latest);
        TopicModel[] topics = new TopicModel[0];
        Gson gson = new Gson();
        try {
            topics = gson.fromJson(
                    new InputStreamReader(file, "UTF-8"),
                    TopicModel[].class);
        } catch (UnsupportedEncodingException e) {
            Timber.e(e.getClass().getSimpleName());
        }
        return Arrays.asList(topics);
    }
}
