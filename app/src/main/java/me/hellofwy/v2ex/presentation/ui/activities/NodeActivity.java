package me.hellofwy.v2ex.presentation.ui.activities;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.graphics.Bitmap;
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
import android.text.Spanned;
import android.text.SpannedString;
import android.text.format.DateFormat;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
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
import me.hellofwy.v2ex.domain.model.NodeModel;
import me.hellofwy.v2ex.network.TopicRepositoryImpl;
import me.hellofwy.v2ex.presentation.presenters.NodePresenter;
import me.hellofwy.v2ex.presentation.presenters.impl.NodePresenterImpl;
import me.hellofwy.v2ex.threading.MainThreadImpl;
import timber.log.Timber;

import static me.hellofwy.v2ex.util.Convertor.dipToPixels;
import static me.hellofwy.v2ex.util.Convertor.setStatusBarTranslucentIfKitKatAbove;

/**
 * Created by fwy on 2016/10/4.
 */

public class NodeActivity extends AppCompatActivity
        implements NodePresenter.View,
        SwipeRefreshLayout.OnRefreshListener,
        AppBarLayout.OnOffsetChangedListener {

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
    @Bind(R.id.tool_bar_container)
    CollapsingToolbarLayout toolBarContainer;
    @Bind(R.id.title_container)
    LinearLayout titleContainer;
    @Bind(R.id.back_button)
    ImageButton backButton;
    @Bind(R.id.app_bar_layout)
    AppBarLayout appBarLayout;

    private NodePresenter mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_node);
        ButterKnife.bind(this);

//        toolBar.setTitle(R.string.node_activity_title);
        setSupportActionBar(toolBar);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);


        setStatusBarTranslucentIfKitKatAbove(this, appBarLayout);

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

        appBarLayout.addOnOffsetChangedListener(this);

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
        name.setText(node.getName());
        title.setText(node.getTitle());
        titleAlternative.setText(node.getTitleAlternative());

        setTextOrMakeGone(header, node.getHeader());
        setTextOrMakeGone(footer, node.getFooter());

        stars.setText(String.valueOf(node.getStars()));
        topics.setText(String.valueOf(node.getTopics()));

        created.setText(
                DateFormat.format("yyyy-MM-dd", node.getCreated() * 1000)
        );

//        toolBarContainer.setTitle(node.getTitle());
    }

    private void setTextOrMakeGone(TextView view, String text) {
        ViewParent parent = view.getParent();
        View p;
        if (parent instanceof View) {
            p = (View) parent;
        } else {
            return;
        }
        if (text != null && text.trim().length() > 0) {
            view.setText(fromHtml(text));
            p.setVisibility(View.VISIBLE);
        } else {
            p.setVisibility(View.GONE);
        }
    }

    private void setToolBarBackgroundAndTextColorWithPalette(Bitmap bitmap) {
        Palette p = Palette.from(bitmap).generate();
        Palette.Swatch swatch = null;
//        swatch = p.getMutedSwatch();
//        if (swatch == null) {
//            swatch = p.getDarkMutedSwatch();
//            if(swatch == null) {
//                swatch = p.getLightMutedSwatch();
//            }
//        }

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

    public static Spanned fromHtml(String in) {
        if (in != null) {
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
        float avatarStartHeight = dipToPixels(NodeActivity.this, 72);
        float avatarFinalHeight = dipToPixels(NodeActivity.this, 40);
        float avatarFinalScale = avatarFinalHeight / avatarStartHeight;
        float avatarScale = 1 - (1-avatarFinalScale) * percent;
        avatar.setScaleX(avatarScale);
        avatar.setScaleY(avatarScale);
    }
}
