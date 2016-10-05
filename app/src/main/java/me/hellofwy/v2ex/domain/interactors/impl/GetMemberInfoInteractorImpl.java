package me.hellofwy.v2ex.domain.interactors.impl;

import me.hellofwy.v2ex.domain.executor.Executor;
import me.hellofwy.v2ex.domain.executor.MainThread;
import me.hellofwy.v2ex.domain.interactors.GetLatestTopicsInteractor;
import me.hellofwy.v2ex.domain.interactors.GetMemberInfoInteractor;
import me.hellofwy.v2ex.domain.interactors.base.AbstractInteractor;
import me.hellofwy.v2ex.domain.model.MemberModel;
import me.hellofwy.v2ex.domain.repository.TopicRepository;

/**
 * This is an interactor boilerplate with a reference to a model repository.
 * <p/>
 */
public class GetMemberInfoInteractorImpl extends AbstractInteractor
        implements GetMemberInfoInteractor {

    private final String mQueryParameter;
    private final QueryType mQueryType;

    private Callback mCallback;
    private TopicRepository mTopicRepository;

    public GetMemberInfoInteractorImpl(Executor threadExecutor,
                                       MainThread mainThread,
                                       Callback callback,
                                       TopicRepository topicRepository,
                                       String queryParameter,
                                       QueryType queryType) {
        super(threadExecutor, mainThread);
        mCallback = callback;
        mTopicRepository = topicRepository;
        mQueryParameter = queryParameter;
        mQueryType = queryType;
    }

    @Override
    public void run() {
        final MemberModel member;
        if(mQueryType == QueryType.ID) {
            member = mTopicRepository.getMemberInfoById(mQueryParameter);
        } else {
            member = mTopicRepository.getMemberInfoByName(mQueryParameter);
        }
        mMainThread.post(new Runnable() {
            @Override
            public void run() {
                mCallback.onGetMemberInfo(member);
            }
        });
     }
}
