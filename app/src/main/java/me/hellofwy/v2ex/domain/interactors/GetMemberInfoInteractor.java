package me.hellofwy.v2ex.domain.interactors;


import java.util.List;

import me.hellofwy.v2ex.domain.interactors.base.Interactor;
import me.hellofwy.v2ex.domain.model.MemberModel;
import me.hellofwy.v2ex.domain.model.TopicModel;


public interface GetMemberInfoInteractor extends Interactor {

    interface Callback {
        void onGetMemberInfo(MemberModel member);
    }

    // TODO: Add interactor methods here
}
