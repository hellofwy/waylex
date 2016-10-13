package me.hellofwy.v2ex.domain.interactors.impl;

import java.io.IOException;
import java.util.List;

import me.hellofwy.v2ex.domain.executor.Executor;
import me.hellofwy.v2ex.domain.executor.MainThread;
import me.hellofwy.v2ex.domain.interactors.GetLatestTopicsInteractor;
import me.hellofwy.v2ex.domain.interactors.base.AbstractInteractor;
import me.hellofwy.v2ex.domain.model.TopicModel;
import me.hellofwy.v2ex.domain.repository.TopicRepository;

/**
 * This is an interactor boilerplate with a reference to a model repository.
 * <p/>
 */
public class GetLatestTopicsInteractorImpl extends AbstractInteractor implements GetLatestTopicsInteractor {

    private GetLatestTopicsInteractor.Callback mCallback;
    private TopicRepository mTopicRepository;

    public GetLatestTopicsInteractorImpl(Executor threadExecutor,
                                         MainThread mainThread,
                                         Callback callback, TopicRepository topicRepository) {
        super(threadExecutor, mainThread);
        mCallback = callback;
        mTopicRepository = topicRepository;
    }

    @Override
    public void run() {
        try {
            final List<TopicModel> topics = mTopicRepository.getLatestTopics();
            mMainThread.post(new Runnable() {
                @Override
                public void run() {
                    mCallback.onGetLatestTopics(topics);
                }
            });
        } catch (final IOException e) {
            mMainThread.post(new Runnable() {
                @Override
                public void run() {
                    mCallback.onGetLatestTopicsError(e.getMessage());
                }
            });
        }
     }
}
