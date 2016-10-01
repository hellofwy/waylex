package me.hellofwy.v2ex.domain.interactors;

import java.util.List;

import me.hellofwy.v2ex.domain.interactors.base.Interactor;
import me.hellofwy.v2ex.domain.model.TopicModel;

/**
 * Created by fwy on 2016/9/29.
 */
public interface RefreshLatestTopicsInteractor extends Interactor {

    interface Callback {
        void onRefreshLatestTopics(List<TopicModel> topics);
    }
}
