package me.hellofwy.v2ex.domain.interactors;


import me.hellofwy.v2ex.domain.interactors.base.Interactor;
import me.hellofwy.v2ex.domain.model.NodeModel;


public interface GetNodeInfoInteractor extends Interactor {

    interface Callback {
        void onGetNodeInfo(NodeModel node);
    }

    // TODO: Add interactor methods here
}
