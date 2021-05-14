package com.artem.minimaltodo.waitviewconditions;

import android.view.View;

import org.hamcrest.Matcher;

public class WaitViewShownCondition extends WaitViewCondition {

    public WaitViewShownCondition(final Matcher<View> viewMatcher) {
        super(viewMatcher);
    }
}