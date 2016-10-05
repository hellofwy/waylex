package me.hellofwy.v2ex.presentation.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.Spanned;
import android.text.SpannedString;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import butterknife.Bind;
import butterknife.ButterKnife;
import me.hellofwy.v2ex.R;
import me.hellofwy.v2ex.domain.executor.impl.ThreadExecutor;
import me.hellofwy.v2ex.domain.model.NodeModel;
import me.hellofwy.v2ex.network.TopicRepositoryImpl;
import me.hellofwy.v2ex.presentation.presenters.NodePresenter;
import me.hellofwy.v2ex.presentation.presenters.impl.NodePresenterImpl;
import me.hellofwy.v2ex.threading.MainThreadImpl;

/**
 * Created by fwy on 2016/10/4.
 */

public class NodeActivity extends AppCompatActivity
        implements NodePresenter.View,
        SwipeRefreshLayout.OnRefreshListener {

    public static final String NODE_NAME = "node_name";
    @Bind(R.id.avatar)
    ImageView avatar;
    @Bind(R.id.name)
    TextView name;
    @Bind(R.id.title)
    TextView title;
    @Bind(R.id.title_alternative)
    TextView titleAlternative;
    @Bind(R.id.topics)
    TextView topics;
    @Bind(R.id.stars)
    TextView stars;
    @Bind(R.id.header)
    TextView header;
    @Bind(R.id.footer)
    TextView footer;
    @Bind(R.id.created)
    TextView created;

    @Bind(R.id.swipe_container)
    SwipeRefreshLayout swipeContainer;
    @Bind(R.id.tool_bar)
    Toolbar toolBar;

    private NodePresenter mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_node);
        ButterKnife.bind(this);

        setSupportActionBar(toolBar);
//        toolBar.setTitle("v2ex");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        init();
    }

    private void init() {
        String nodeName = "python";

        Intent intent = getIntent();
        String name = intent.getStringExtra(NODE_NAME);
        if (name != null) {
            nodeName = name;
        }

        mPresenter = new NodePresenterImpl(
                ThreadExecutor.getInstance(),
                MainThreadImpl.getInstance(),
                this,
//                new TestTopicRepositoryImpl(this),
                new TopicRepositoryImpl(),
                nodeName
        );

        swipeContainer.setOnRefreshListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.resume();
    }

    @Override
    public void showNode(NodeModel node) {
        bindNodeModel(node);
    }

    private void bindNodeModel(NodeModel node) {
        Picasso.with(this)
                .load("https:" + node.getAvatarLarge())
                .placeholder(R.drawable.avatar_default)
                .error(R.drawable.avatar_fail)
                .into(avatar);
        name.setText(node.getName());
        title.setText(node.getTitle());
        titleAlternative.setText(node.getTitleAlternative());
        header.setText(node.getHeader());
        footer.setText(fromHtml(node.getFooter()));
        stars.setText(String.valueOf(node.getStars()));
        topics.setText(String.valueOf(node.getTopics()));
        created.setText(String.valueOf(node.getCreated()));
    }

    public static Spanned fromHtml(String in) {
        if(in != null) {
            return Html.fromHtml(in);
        }
        return new SpannedString("");
    }
    @Override
    public void showProgress() {
        swipeContainer.setRefreshing(true);
    }

    @Override
    public void hideProgress() {
        swipeContainer.setRefreshing(false);
    }

    @Override
    public void showError(String message) {

    }

    @Override
    public void onRefresh() {
        //TODO
        swipeContainer.setRefreshing(false);
    }
}
