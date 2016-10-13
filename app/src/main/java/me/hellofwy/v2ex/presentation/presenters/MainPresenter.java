package me.hellofwy.v2ex.presentation.presenters;

import java.util.List;

import me.hellofwy.v2ex.domain.model.TopicModel;
import me.hellofwy.v2ex.presentation.presenters.base.BasePresenter;
import me.hellofwy.v2ex.presentation.ui.BaseView;


public interface MainPresenter extends BasePresenter {

    interface View extends BaseView {
        void showLatestTopics(List<TopicModel> topics);

        void finishRefresh();
    }

    void getLatestTopics();

    void swipeRefresh();
}
