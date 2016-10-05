package me.hellofwy.v2ex.domain.interactors;


import java.util.List;

import me.hellofwy.v2ex.domain.interactors.base.Interactor;
import me.hellofwy.v2ex.domain.model.TopicModel;


public interface GetLatestTopicsInteractor extends Interactor {

    interface Callback {
        void onGetLatestTopics(List<TopicModel> topics);
    }
}
