package me.hellofwy.v2ex.presentation.presenters;

import me.hellofwy.v2ex.domain.model.NodeModel;
import me.hellofwy.v2ex.presentation.presenters.base.BasePresenter;
import me.hellofwy.v2ex.presentation.ui.BaseView;


public interface NodePresenter extends BasePresenter {

    interface View extends BaseView {
        void showNode(NodeModel node);
    }

    void getNodeInfo();
}
