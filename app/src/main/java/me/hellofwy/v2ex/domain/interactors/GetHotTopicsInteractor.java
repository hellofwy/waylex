package me.hellofwy.v2ex.domain.interactors;


import java.util.List;

import me.hellofwy.v2ex.domain.interactors.base.Interactor;
import me.hellofwy.v2ex.domain.model.TopicModel;


public interface GetHotTopicsInteractor extends Interactor {

    interface Callback {
        void onGetHotTopics(List<TopicModel> topics);
    }

    // TODO: Add interactor methods here
}
