package me.hellofwy.v2ex.presentation.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import me.hellofwy.v2ex.R;
import me.hellofwy.v2ex.domain.executor.impl.ThreadExecutor;
import me.hellofwy.v2ex.domain.model.TopicModel;
import me.hellofwy.v2ex.network.TopicRepositoryImpl;
import me.hellofwy.v2ex.presentation.presenters.MainPresenter;
import me.hellofwy.v2ex.presentation.presenters.impl.MainPresenterImpl;
import me.hellofwy.v2ex.presentation.ui.adapter.ItemAdapter;
import me.hellofwy.v2ex.threading.MainThreadImpl;
import timber.log.Timber;

import static android.R.attr.id;

public class MainActivity extends AppCompatActivity
    implements MainPresenter.View,
        ItemAdapter.TopicsItemListener,
               SwipeRefreshLayout.OnRefreshListener {

    @Bind(R.id.recycler_view)
    RecyclerView recyclerView;

    @Bind(R.id.swipe_container)
    SwipeRefreshLayout swipeContainer;

    @Bind(R.id.tool_bar)
    Toolbar toolbar;

    private MainPresenter mMainPresenter;
    private ItemAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

//        setStatusBarTranslucentIfKitKatAbove(this, toolbar);

        mAdapter = new ItemAdapter(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(mAdapter);

        mMainPresenter = new MainPresenterImpl(
                ThreadExecutor.getInstance(),
                MainThreadImpl.getInstance(),
                this,
//                new TestTopicRepositoryImpl(this)
                new TopicRepositoryImpl()
        );

        setSupportActionBar(toolbar);

        swipeContainer.setOnRefreshListener(this);
        swipeContainer.setColorSchemeResources(R.color.colorAccent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Timber.v("main resumed");
        mMainPresenter.resume();
    }

    @Override
    public void showLatestTopics(List<TopicModel> topics) {
        if(topics == null) {
            Toast.makeText(this, "Get topics failed, may network failed!",
                    Toast.LENGTH_SHORT)
                    .show();
            Timber.e("api failed");
        } else {
            mAdapter.addTopics(topics);
            Timber.v("add topics");
        }
    }

    @Override
    public void finishRefresh() {
        swipeContainer.setRefreshing(false);
    }

    @Override
    public void showTopicDetailInWebView(String topicUrl) {
        Intent intent = new Intent(this, WebViewActivity.class);
        intent.putExtra("url", topicUrl);
        startActivity(intent);

    }

    @Override
    public void showMemberDetailUi(String memberId) {
        Intent intent = new Intent(this, MemberActivity.class);
        intent.putExtra(MemberActivity.MEMBER_ID, memberId);
        startActivity(intent);
    }

    @Override
    public void showNodeDetailUi(String nodeName) {
        Intent intent = new Intent(this, NodeActivity.class);
        intent.putExtra(NodeActivity.NODE_NAME, nodeName);
        startActivity(intent);
    }

    @Override
    public void showProgress() {
//        progressBar.setVisibility(View.VISIBLE);
        swipeContainer.setRefreshing(true);
    }

    @Override
    public void hideProgress() {
//        progressBar.setVisibility(View.INVISIBLE);
        swipeContainer.setRefreshing(false);
    }

    @Override
    public void showError(String message) {
        Toast.makeText(this, message,
                Toast.LENGTH_SHORT)
                .show();
    }

    @Override
    public void onRefresh() {
        mMainPresenter.swipeRefresh();
    }

    @Override
    public void onTitleClicked(TopicModel topic) {
        mMainPresenter.openTopic(topic);
    }

    @Override
    public void onMemberClicked(TopicModel topic) {
        mMainPresenter.openMember(topic);
    }

    @Override
    public void onNodeClicked(TopicModel topic) {
        mMainPresenter.openNode(topic);
    }
}
