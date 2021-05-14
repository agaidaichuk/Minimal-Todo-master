package com.artem.minimaltodo.pages;

import org.hamcrest.Matcher;

import androidx.annotation.NonNull;
import androidx.annotation.StringRes;
import androidx.test.espresso.action.ViewActions;

import com.artem.minimaltodo.R;
import com.artem.minimaltodo.utils.ResourceUtils;
import com.artem.minimaltodo.views.AbstractView;
import com.artem.minimaltodo.views.MyView;
import com.artem.minimaltodo.waitviewconditions.WaitViewDisappearCondition;
import com.artem.minimaltodo.waitviewconditions.WaitViewShownCondition;

import static androidx.test.core.app.ApplicationProvider.getApplicationContext;
import static androidx.test.espresso.Espresso.closeSoftKeyboard;
import static androidx.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu;
import static androidx.test.espresso.Espresso.pressBack;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

public abstract class AbstractPage {

    protected void performAction(Action action) {
        MyView actionView = new MyView(action.getMatcher());
        if (!actionView.isViewDisplayed()) {
            openActionBarOverflowOrOptionsMenu(getApplicationContext());
        }
        actionView.click();
    }

    protected void verifyActionIsNotAvailable(Action action) {
        MyView actionView = new MyView(action.getMatcher());
        actionView.isNotDisplayed();
        openActionBarOverflowOrOptionsMenu(getApplicationContext());
        actionView.isNotDisplayed();
        pressBack();
    }

    protected AbstractPage verifySnackBar(@StringRes final int text) {
        return verifySnackBar(ResourceUtils.getStringFromResources(text));
    }

    protected AbstractPage verifySnackBar(final String text) {
        MyView snackBar = new MyView(allOf(withId(R.id.snackbar_text), withText(text)));
        waitUntilDisplayed(snackBar);
        snackBar.performActions(ViewActions.swipeRight());

        return this;
    }

    protected AbstractPage snackBarIsNotDisplayed(@StringRes final int text) {
        new MyView(allOf(withId(R.id.snackbar_text), withText(text))).isNotDisplayed();

        return this;
    }

    protected static void waitUntilDisplayed(@NonNull final Matcher<android.view.View> viewMatcher) {
        waitUntilDisplayed(new MyView(viewMatcher));
    }

    protected static void waitUntilDisplayed(final AbstractView view) {
        WaitViewShownCondition waitUntilDisplayedCondition = new WaitViewShownCondition(view.getViewMatcher());
        try {
            waitUntilDisplayedCondition.setUp();
            view.isDisplayed();
        } finally {
            waitUntilDisplayedCondition.tearDown();
            closeSoftKeyboard();
        }
    }

    protected static void waitUntilDisappear(@NonNull final Matcher<android.view.View> viewMatcher) {
        waitUntilDisappear(new MyView(viewMatcher));
    }

    protected static void waitUntilDisappear(final AbstractView view) {
        WaitViewDisappearCondition waitViewDisappearCondition = new WaitViewDisappearCondition(view.getViewMatcher());
        try {
            waitViewDisappearCondition.setUp();
            view.isNotDisplayed();
        } finally {
            waitViewDisappearCondition.tearDown();
            closeSoftKeyboard();
        }
    }
}