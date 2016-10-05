package me.hellofwy.v2ex.presentation.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import butterknife.Bind;
import butterknife.ButterKnife;
import me.hellofwy.v2ex.R;
import me.hellofwy.v2ex.domain.executor.impl.ThreadExecutor;
import me.hellofwy.v2ex.domain.model.MemberModel;
import me.hellofwy.v2ex.network.TopicRepositoryImpl;
import me.hellofwy.v2ex.presentation.presenters.MemberPresenter;
import me.hellofwy.v2ex.presentation.presenters.impl.MemberPresenterImpl;
import me.hellofwy.v2ex.threading.MainThreadImpl;

/**
 * Created by fwy on 2016/10/4.
 */

public class MemberActivity extends AppCompatActivity
        implements MemberPresenter.View,
        SwipeRefreshLayout.OnRefreshListener {

    public static final String MEMBER_ID = "member_id";
    public static final String MEMBER_NAME = "member_name";

    @Bind(R.id.avatar)
    ImageView avatar;
    @Bind(R.id.name)
    TextView name;
    @Bind(R.id.member_id)
    TextView memberId;
    @Bind(R.id.create_time)
    TextView createTime;
    @Bind(R.id.website)
    TextView website;
    @Bind(R.id.twitter)
    TextView twitter;
    @Bind(R.id.psn)
    TextView psn;
    @Bind(R.id.github)
    TextView github;
    @Bind(R.id.btc)
    TextView btc;
    @Bind(R.id.location)
    TextView location;
    @Bind(R.id.tagline)
    TextView tagline;
    @Bind(R.id.bio)
    TextView bio;

    @Bind(R.id.swipe_container)
    SwipeRefreshLayout swipeContainer;

    @Bind(R.id.tool_bar)
    Toolbar toolBar;

    private MemberPresenter mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member);
        ButterKnife.bind(this);

        setSupportActionBar(toolBar);
//        toolBar.setTitle("v2ex");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        init();
    }

    private void init() {
        String queryPara = "1";
        MemberPresenter.Type queryType = MemberPresenter.Type.ID;

        Intent intent = getIntent();
        String id = intent.getStringExtra(MEMBER_ID);
        if (id != null) {
            queryPara = id;
            queryType = MemberPresenter.Type.ID;
        } else {
            String name = intent.getStringExtra(MEMBER_NAME);
            if (name != null) {
                queryPara = name;
                queryType = MemberPresenter.Type.NAME;
            }
        }

        mPresenter = new MemberPresenterImpl(
                ThreadExecutor.getInstance(),
                MainThreadImpl.getInstance(),
                this,
//                new TestTopicRepositoryImpl(this),
                new TopicRepositoryImpl(),
                queryPara,
                queryType
        );

        swipeContainer.setOnRefreshListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.resume();

    }

    @Override
    public void showMember(MemberModel member) {
        bindModelToView(member);
    }

    private void bindModelToView(MemberModel member) {
        Picasso.with(this)
                .load("https:" + member.getAvatarLarge())
                .placeholder(R.drawable.avatar_default)
                .error(R.drawable.avatar_fail)
                .into(avatar);
        name.setText(member.getUsername());
        memberId.setText(String.format("第 %s 位会员", member.getId()));
        createTime.setText(String.format("%s 加入", member.getCreated()));
        website.setText(member.getWebsite());
        location.setText(member.getLocation());
        twitter.setText(member.getTwitter());
        psn.setText(member.getPsn());
        github.setText(member.getGithub());
        btc.setText(member.getBtc());
        tagline.setText(member.getTagline());
        bio.setText(member.getBio());
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
