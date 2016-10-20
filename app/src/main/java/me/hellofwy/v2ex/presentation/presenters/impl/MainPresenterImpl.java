package me.hellofwy.v2ex.presentation.presenters.impl;

import java.util.List;

import me.hellofwy.v2ex.domain.executor.Executor;
import me.hellofwy.v2ex.domain.executor.MainThread;
import me.hellofwy.v2ex.domain.interactors.GetLatestTopicsInteractor;
import me.hellofwy.v2ex.domain.interactors.RefreshLatestTopicsInteractor;
import me.hellofwy.v2ex.domain.interactors.impl.GetLatestTopicsInteractorImpl;
import me.hellofwy.v2ex.domain.interactors.impl.RefreshLatestTopicsInteractorImpl;
import me.hellofwy.v2ex.domain.model.TopicModel;
import me.hellofwy.v2ex.domain.repository.TopicRepository;
import me.hellofwy.v2ex.presentation.presenters.MainPresenter;
import me.hellofwy.v2ex.presentation.presenters.base.AbstractPresenter;
import timber.log.Timber;

/**
 * Created by dmilicic on 12/13/15.
 */
public class MainPresenterImpl extends AbstractPresenter implements MainPresenter,
        GetLatestTopicsInteractor.Callback, RefreshLatestTopicsInteractor.Callback {

    private MainPresenter.View mView;
    private TopicRepository mRepository;

    private boolean isFirstTime;

    public MainPresenterImpl(Executor executor,
                             MainThread mainThread,
                             View view,
                             TopicRepository repository) {
        super(executor, mainThread);
        mView = view;
        mRepository = repository;
        isFirstTime = true;
    }

    @Override
    public void resume() {
        Timber.v("presator resume");
        if(isFirstTime) {
            getLatestTopics();
            isFirstTime = false;
        }
    }

    @Override
    public void pause() {

    }

    @Override
    public void stop() {

    }

    @Override
    public void destroy() {

    }

    @Override
    public void onError(String message) {

    }

    @Override
    public void onGetLatestTopics(List<TopicModel> topics) {
        mView.hideProgress();
        mView.showLatestTopics(topics);
    }

    @Override
    public void onGetLatestTopicsError(String errorMessage) {
        mView.hideProgress();
        mView.showError(errorMessage);
    }

    @Override
    public void getLatestTopics() {
        mView.showProgress();
        GetLatestTopicsInteractor interactor =
                new GetLatestTopicsInteractorImpl(
                        mExecutor,
                        mMainThread,
                        this,
                        mRepository
                );
        interactor.execute();
    }

    @Override
    public void swipeRefresh() {
        RefreshLatestTopicsInteractor interactor =
                new RefreshLatestTopicsInteractorImpl(
                        mExecutor,
                        mMainThread,
                        this,
                        mRepository
                );
        interactor.execute();
    }

    @Override
    public void openTopic(TopicModel topic) {
        mView.showTopicDetailInWebView(topic.getUrl());
    }

    @Override
    public void openMember(TopicModel topic) {
        mView.showMemberDetailUi(String.valueOf(
                topic.getMember().getId().toString()
        ));
    }

    @Override
    public void openNode(TopicModel topic) {
        mView.showNodeDetailUi(topic.getNode().getName());
    }

    @Override
    public void onRefreshLatestTopics(List<TopicModel> topics) {
        mView.showLatestTopics(topics);
        mView.finishRefresh();
    }

    @Override
    public void onRefreshLatestTopicsError(String message) {
        mView.showError(message);
        mView.finishRefresh();
    }
}
