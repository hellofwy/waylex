package me.hellofwy.v2ex.domain.interactors.impl;

import java.util.List;

import me.hellofwy.v2ex.domain.executor.Executor;
import me.hellofwy.v2ex.domain.executor.MainThread;
import me.hellofwy.v2ex.domain.interactors.GetHotTopicsInteractor;
import me.hellofwy.v2ex.domain.interactors.GetNodeInfoInteractor;
import me.hellofwy.v2ex.domain.interactors.base.AbstractInteractor;
import me.hellofwy.v2ex.domain.model.NodeModel;
import me.hellofwy.v2ex.domain.model.TopicModel;
import me.hellofwy.v2ex.domain.repository.TopicRepository;

/**
 * This is an interactor boilerplate with a reference to a model repository.
 * <p/>
 */
public class GetNodeInfoInteractorImpl extends AbstractInteractor implements GetNodeInfoInteractor {

    private Callback mCallback;
    private TopicRepository mTopicRepository;
    private String mNodeName;

    public GetNodeInfoInteractorImpl(Executor threadExecutor,
                                     MainThread mainThread,
                                     Callback callback,
                                     TopicRepository topicRepository,
                                     String nodeName) {
        super(threadExecutor, mainThread);
        mCallback = callback;
        mTopicRepository = topicRepository;
        mNodeName = nodeName;
    }

    @Override
    public void run() {
        final NodeModel node = mTopicRepository.getNodeInfo(mNodeName);
        mMainThread.post(new Runnable() {
            @Override
            public void run() {
                mCallback.onGetNodeInfo(node);
            }
        });
     }
}