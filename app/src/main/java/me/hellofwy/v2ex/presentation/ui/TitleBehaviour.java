package me.hellofwy.v2ex.presentation.ui;

import android.support.design.widget.CoordinatorLayout;
import android.view.View;
import android.widget.ImageView;

import me.hellofwy.v2ex.R;

/**
 * Created by fwy on 2016/10/7.
 */

public class TitleBehaviour extends CoordinatorLayout.Behavior<ImageView> {
    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, ImageView child, View dependency) {
        return dependency.getId() == R.id.back_button;
    }

    @Override
    public void onNestedScroll(CoordinatorLayout coordinatorLayout, ImageView child, View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed) {
        super.onNestedScroll(coordinatorLayout, child, target, dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed);
    }
}
