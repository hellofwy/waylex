package me.hellofwy.v2ex.presentation.ui.activities;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.hellofwy.v2ex.R;
import me.hellofwy.v2ex.domain.executor.impl.ThreadExecutor;
import me.hellofwy.v2ex.domain.model.MemberModel;
import me.hellofwy.v2ex.network.TopicRepositoryImpl;
import me.hellofwy.v2ex.presentation.presenters.MemberPresenter;
import me.hellofwy.v2ex.presentation.presenters.impl.MemberPresenterImpl;
import me.hellofwy.v2ex.threading.MainThreadImpl;
import timber.log.Timber;

import static android.R.attr.animation;
import static me.hellofwy.v2ex.util.Convertor.dipToPixels;
import static me.hellofwy.v2ex.util.Convertor.setStatusBarTranslucentIfKitKatAbove;
import static me.hellofwy.v2ex.util.Convertor.utcToDate;

/**
 * Created by fwy on 2016/10/4.
 */

public class MemberActivity extends AppCompatActivity
        implements MemberPresenter.View,
        SwipeRefreshLayout.OnRefreshListener,
        AppBarLayout.OnOffsetChangedListener {

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

    @Bind(R.id.title_container)
    LinearLayout titleContainer;
    @Bind(R.id.back_button)
    ImageButton backButton;
    @Bind(R.id.tool_bar_container)
    CollapsingToolbarLayout toolBarContainer;
    @Bind(R.id.app_bar_layout)
    AppBarLayout appBarLayout;

    @Bind(R.id.title_after_collapse)
    TextView titleAfterCollapse;

    private MemberPresenter mPresenter;

    private InitPara mInitPara;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member);

        ButterKnife.bind(this);
        setStatusBarTranslucentIfKitKatAbove(this, appBarLayout);

        setSupportActionBar(toolBar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

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

        appBarLayout.addOnOffsetChangedListener(this);

        mInitPara = new InitPara();
        mInitPara.firstRun();

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
                .into(new Target() {
                    @Override
                    public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                        avatar.setImageBitmap(bitmap);
                        setToolBarBackgroundAndTextColorWithPalette(bitmap);
                    }

                    @Override
                    public void onBitmapFailed(Drawable errorDrawable) {
                        avatar.setImageResource(R.drawable.avatar_fail);

                    }

                    @Override
                    public void onPrepareLoad(Drawable placeHolderDrawable) {
                        avatar.setImageResource(R.drawable.avatar_default);
                    }
                });

        name.setText(member.getUsername());
        memberId.setText(String.format("第 %s 位会员", member.getId()));

        createTime.setText(String.format("%s 加入",
                utcToDate(member.getCreated()))
        );

        if (member.getWebsite() != null && member.getWebsite().trim().length() != 0) {
            website.setText(Html.fromHtml(member.getWebsite()));
        } else {
            setTextOrUndefined(website, null);
        }

        setTextOrUndefined(location, member.getLocation());
        setTextOrUndefined(twitter, member.getTwitter());
        setTextOrUndefined(psn, member.getPsn());
        setTextOrUndefined(github, member.getGithub());
        setTextOrUndefined(btc, member.getBtc());

        setTextOrUndefined(tagline, member.getTagline());
        setTextOrUndefined(bio, member.getBio());

    }

    private void setTextOrUndefined(TextView view, String text) {
        if(text != null && text.trim().length() != 0) {
            view.setText(text);
        } else {
            view.setText(R.string.undefined);
            view.setTextColor(getResources().getColor(R.color.m_material_grey_200));
        }
    }
    private void setToolBarBackgroundAndTextColorWithPalette(Bitmap bitmap) {
        Palette p = Palette.from(bitmap).generate();
        Palette.Swatch swatch = null;

        swatch = p.getDarkVibrantSwatch();
        if (swatch == null) {
            swatch = p.getVibrantSwatch();
            if (swatch == null) {
                swatch = p.getLightVibrantSwatch();
                if (swatch != null) {
                    Timber.v("getLightVibrantSwatch");
                } else {
                    Timber.v("No Vibrant");
                }
            } else {
                Timber.v("getVibrantSwatch");
            }
        } else {
            Timber.v("getDarkVibrantSwatch");
        }
        if (swatch != null) {
            appBarLayout.setBackgroundColor(swatch.getRgb());
            changeTitleColor(appBarLayout, swatch.getBodyTextColor());
            backButton.getDrawable()
                    .setColorFilter(swatch.getBodyTextColor(),
                            PorterDuff.Mode.SRC_ATOP);
        }
    }

    private void changeTitleColor(ViewGroup container, int color) {
        for (int i = 0; i < container.getChildCount(); i++) {
            View view = container.getChildAt(i);
            if (view instanceof TextView) {
                ((TextView) view).setTextColor(color);
            } else {
                if (view instanceof ViewGroup) {
                    if (((ViewGroup) view).getChildCount() > 0) {
                        changeTitleColor((ViewGroup) view, color);
                    }
                }
            }
        }
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @OnClick(R.id.back_button)
    public void backButtonClicked() {
        finish();
    }

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
        float total = appBarLayout.getTotalScrollRange();
        float percent = -verticalOffset / total;

        if(percent > 0.9 && titleContainer.getAlpha() > 0.2) {
            ObjectAnimator.ofFloat(titleContainer, View.ALPHA, 0)
                    .setDuration(100)
                    .start();
        }
            if(percent < 0.8 && titleContainer.getAlpha() < 0.01) {
                ObjectAnimator.ofFloat(titleContainer, View.ALPHA, 1)
                        .setDuration(100)
                        .start();
            }

//        titleContainer.setAlpha((1 - 1.3f * percent));
//
        float avatarFinalScale = mInitPara.avatarFinalHeight / mInitPara.avatarStartHeight;
        float avatarScale = 1 - (1-avatarFinalScale) * percent;
        avatar.setScaleX(avatarScale);
        avatar.setScaleY(avatarScale);
//
//        float avatarX = mInitPara.avatarStartX - (mInitPara.avatarStartX-mInitPara.avatarFinalX) * percent;
//        avatar.setX(avatarX);
    }

    private class InitPara {
        float avatarStartHeight;
        float avatarFinalHeight;
        float avatarStartX;
        float avatarFinalX;

        void firstRun() {
            avatarStartHeight = dipToPixels(MemberActivity.this, 72);
            avatarFinalHeight = dipToPixels(MemberActivity.this, 40);

//            avatarStartX = appBarLayout.getWidth() - avatar.getWidth()/2;
            avatarFinalX = backButton.getRight();

            Timber.v(String.format("X: %.1f %d", avatarStartX, appBarLayout.getWidth()));
        }
    }
}
