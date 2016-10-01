package me.kodelabs.v2ex;

import com.google.gson.Gson;

import org.junit.Ignore;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.util.List;

import me.hellofwy.v2ex.domain.model.TopicModel;
import me.hellofwy.v2ex.network.V2EXapi;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.R.attr.path;
import static org.junit.Assert.*;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class ExampleUnitTest {
    @Ignore
    @Test
    public void testGetAll() throws IOException {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(V2EXapi.BASU_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        V2EXapi service = retrofit.create(V2EXapi.class);
        Call<List<TopicModel>> topics_call = service.getLatestTopics();
        List<TopicModel> topics = topics_call.execute().body();
        assertEquals(30, topics.size());
    }

    @Test
    public void testGetAllLocal() throws IOException {
//        URL path = this.getClass().getResource("latest.json");
//        File file = new File(path.getFile());
//        BufferedReader reader = new BufferedReader(new FileReader(file));
//        InputStream path = this.getClass().getResourceAsStream("latest.json");
//        StringBuilder builder = new StringBuilder();
//        while(true) {
//            String line = reader.readLine();
//            if(line == null) break;
//            builder.append(line);
//            builder.append("\n");
//        }
//        String string = builder.toString();
//        assertEquals(300, string.length());
        InputStream path = this.getClass().getClassLoader()
                .getResourceAsStream("latest.json");
//        int count = 0;
//        while (path.read() != -1) {
//            count++;
//        }
//        assertEquals(100, count);
        Gson gson = new Gson();
        TopicModel[] topics =
                gson.fromJson(new InputStreamReader(path, "UTF-8"), TopicModel[].class);
        assertEquals(topics[0].getTitle(), "北京 Android 1.5 年 可独立开发 求个坑");
//        assertEquals(15, topics.length);
    }
}