package me.hellofwy.v2ex.domain.interactors;


import java.util.List;

import me.hellofwy.v2ex.domain.interactors.base.Interactor;
import me.hellofwy.v2ex.domain.model.NodeModel;
import me.hellofwy.v2ex.domain.model.TopicModel;


public interface GetNodeInfoInteractor extends Interactor {

    interface Callback {
        void onGetNodeInfo(NodeModel node);
    }

    // TODO: Add interactor methods here
}
