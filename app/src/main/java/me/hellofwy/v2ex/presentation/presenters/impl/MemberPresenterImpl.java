package me.hellofwy.v2ex.presentation.presenters.impl;

import me.hellofwy.v2ex.domain.executor.Executor;
import me.hellofwy.v2ex.domain.executor.MainThread;
import me.hellofwy.v2ex.domain.interactors.GetMemberInfoInteractor;
import me.hellofwy.v2ex.domain.interactors.impl.GetMemberInfoInteractorImpl;
import me.hellofwy.v2ex.domain.model.MemberModel;
import me.hellofwy.v2ex.domain.repository.TopicRepository;
import me.hellofwy.v2ex.presentation.presenters.MemberPresenter;
import me.hellofwy.v2ex.presentation.presenters.base.AbstractPresenter;

public class MemberPresenterImpl extends AbstractPresenter
        implements MemberPresenter,
        GetMemberInfoInteractor.Callback {


    private final MemberPresenter.View mView;
    private final TopicRepository mRepository;
    private final String mIdOrName;
    private final MemberPresenter.Type type;
    private boolean isFirstTime;

    public MemberPresenterImpl(Executor executor,
                               MainThread mainThread,
                               MemberPresenter.View view,
                               TopicRepository repository,
                               String idOrName,
                                MemberPresenter.Type type
                               ) {
        super(executor, mainThread);
        this.mView = view;
        this.mRepository = repository;
        this.mIdOrName = idOrName;
        this.type = type;
        isFirstTime = true;
    }

    @Override
    public void getMemberInfo() {
        mView.hideProgress();

        GetMemberInfoInteractor.QueryType queryType;
        if(type == Type.ID) {
            queryType = GetMemberInfoInteractor.QueryType.ID;
        } else {
            queryType = GetMemberInfoInteractor.QueryType.NAME;
        }

        GetMemberInfoInteractor actor = new GetMemberInfoInteractorImpl(
                mExecutor,
                mMainThread,
                this,
                mRepository,
                mIdOrName,
                queryType
        );
        actor.execute();
    }

    @Override
    public void resume() {
        if(isFirstTime) {
            getMemberInfo();
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
    public void onGetMemberInfo(MemberModel member) {
        mView.showMember(member);
        mView.hideProgress();
    }

    @Override
    public void onGetMemberInfoError(String errorMessage) {
        mView.showError(errorMessage);
        mView.hideProgress();
    }
}
