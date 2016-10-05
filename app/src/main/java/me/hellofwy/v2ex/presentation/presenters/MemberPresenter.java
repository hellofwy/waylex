package me.hellofwy.v2ex.presentation.presenters;

import me.hellofwy.v2ex.domain.model.MemberModel;
import me.hellofwy.v2ex.presentation.presenters.base.BasePresenter;
import me.hellofwy.v2ex.presentation.ui.BaseView;


public interface MemberPresenter extends BasePresenter {

    interface View extends BaseView {
        void showMember(MemberModel member);
    }

    enum Type {
        ID,
        NAME
    }

    void getMemberInfo();
}
