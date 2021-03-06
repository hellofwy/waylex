package me.hellofwy.v2ex.domain.interactors;


import me.hellofwy.v2ex.domain.interactors.base.Interactor;
import me.hellofwy.v2ex.domain.model.MemberModel;


public interface GetMemberInfoInteractor extends Interactor {

    interface Callback {
        void onGetMemberInfo(MemberModel member);
        void onGetMemberInfoError(String errorMessage);
    }

    enum QueryType {
        ID,
        NAME
    }
}
