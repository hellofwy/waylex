package me.hellofwy.v2ex.presentation.ui.activities;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

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
import me.hellofwy.v2ex.storage.TestTopicRepositoryImpl;
import me.hellofwy.v2ex.threading.MainThreadImpl;
import timber.log.Timber;

import static android.R.id.message;

public class MainActivity extends AppCompatActivity
            implements MainPresenter.View,
        SwipeRefreshLayout.OnRefreshListener {
    @Bind(R.id.progress_bar)
    ProgressBar progressBar;

    @Bind(R.id.recycler_view)
    RecyclerView recyclerView;

    @Bind(R.id.swipe_container)
    SwipeRefreshLayout swipeContainer;

    private MainPresenter mMainPresenter;
    private ItemAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

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

    }

    @Override
    public void onRefresh() {
        mMainPresenter.swipeRefresh();
    }
}
