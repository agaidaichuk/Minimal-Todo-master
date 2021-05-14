package com.artem.minimaltodo.waitviewconditions;

import android.view.View;

import org.hamcrest.Matcher;

public class WaitViewDisappearCondition extends WaitViewCondition {

    public WaitViewDisappearCondition(final Matcher<View> viewMatcher) {
        super(viewMatcher);
    }
}
