package com.artem.minimaltodo.waitviewconditions;

import android.view.View;

import androidx.test.espresso.IdlingRegistry;

import com.artem.minimaltodo.idlingresource.WaitViewIdlingResource;

import org.hamcrest.Matcher;

abstract class WaitViewCondition {

    protected Matcher<View> viewMatcher;
    private WaitViewIdlingResource idlingResource;

    WaitViewCondition(final Matcher<View> viewMatcher) {
        this.viewMatcher = viewMatcher;
    }

    public void setUp() {
        idlingResource = new WaitViewIdlingResource(viewMatcher);
        IdlingRegistry.getInstance().register(idlingResource);
    }

    public void tearDown() {
        IdlingRegistry.getInstance().unregister(idlingResource);
        idlingResource = null;
    }
}