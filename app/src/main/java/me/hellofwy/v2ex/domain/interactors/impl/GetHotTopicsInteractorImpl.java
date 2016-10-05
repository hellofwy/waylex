package me.hellofwy.v2ex.domain.interactors.impl;

import java.util.List;

import me.hellofwy.v2ex.domain.executor.Executor;
import me.hellofwy.v2ex.domain.executor.MainThread;
import me.hellofwy.v2ex.domain.interactors.GetHotTopicsInteractor;
import me.hellofwy.v2ex.domain.interactors.base.AbstractInteractor;
import me.hellofwy.v2ex.domain.model.TopicModel;
import me.hellofwy.v2ex.domain.repository.TopicRepository;

/**
 * This is an interactor boilerplate with a reference to a model repository.
 * <p/>
 */
public class GetHotTopicsInteractorImpl extends AbstractInteractor implements GetHotTopicsInteractor {

    private Callback mCallback;
    private TopicRepository mTopicRepository;

    public GetHotTopicsInteractorImpl(Executor threadExecutor,
                                      MainThread mainThread,
                                      Callback callback, TopicRepository topicRepository) {
        super(threadExecutor, mainThread);
        mCallback = callback;
        mTopicRepository = topicRepository;
    }

    @Override
    public void run() {
        final List<TopicModel> topics = mTopicRepository.getHotTopics();
        mMainThread.post(new Runnable() {
            @Override
            public void run() {
                mCallback.onGetHotTopics(topics);
            }
        });
     }
}
