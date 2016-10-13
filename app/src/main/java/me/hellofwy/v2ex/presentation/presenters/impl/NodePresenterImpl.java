package me.hellofwy.v2ex.presentation.presenters.impl;

import me.hellofwy.v2ex.domain.executor.Executor;
import me.hellofwy.v2ex.domain.executor.MainThread;
import me.hellofwy.v2ex.domain.interactors.GetNodeInfoInteractor;
import me.hellofwy.v2ex.domain.interactors.impl.GetNodeInfoInteractorImpl;
import me.hellofwy.v2ex.domain.model.NodeModel;
import me.hellofwy.v2ex.domain.repository.TopicRepository;
import me.hellofwy.v2ex.presentation.presenters.NodePresenter;
import me.hellofwy.v2ex.presentation.presenters.base.AbstractPresenter;

public class NodePresenterImpl extends AbstractPresenter
        implements NodePresenter,
        GetNodeInfoInteractor.Callback {


    private final View mView;
    private final TopicRepository mRepository;
    private final String mNodeName;

    private boolean isFirstTime;

    public NodePresenterImpl(Executor executor,
                             MainThread mainThread,
                             View view,
                             TopicRepository repository,
                             String nodeName
                             ) {
        super(executor, mainThread);
        this.mView = view;
        this.mRepository = repository;
        this.mNodeName = nodeName;

        isFirstTime = true;
    }

    @Override
    public void getNodeInfo() {
        mView.hideProgress();
        GetNodeInfoInteractor actor = new GetNodeInfoInteractorImpl(
                mExecutor,
                mMainThread,
                this,
                mRepository,
                mNodeName
        );
        actor.execute();
    }

    @Override
    public void resume() {
        if(isFirstTime) {
            getNodeInfo();
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
    public void onGetNodeInfo(NodeModel node) {
        mView.showNode(node);
        mView.hideProgress();
    }

    @Override
    public void onGetNodeInfoError(String errorMessage) {
        mView.showError(errorMessage);
        mView.hideProgress();
    }
}
