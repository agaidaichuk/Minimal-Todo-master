package com.artem.minimaltodo.views;

import org.hamcrest.Matcher;
import org.hamcrest.Matchers;

import android.view.View;

import androidx.test.espresso.NoMatchingViewException;
import androidx.test.espresso.ViewAction;

import com.artem.minimaltodo.matchers.ViewMatchers;
import com.artem.minimaltodo.viewactions.ViewActions;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.doesNotExist;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.not;

public abstract class AbstractView {

    final Matcher<View> viewMatcher;

    AbstractView(Matcher<View> matcher) {
        this.viewMatcher = Matchers.allOf(matcher, ViewMatchers.isShown());
    }

    public Matcher<View> getViewMatcher() {
        return viewMatcher;
    }

    /**
     * Verify if view is not displayed
     */

    public void isNotDisplayed() {
        onView(viewMatcher).check(doesNotExist());
    }

    /**
     * Verify if view is not displayed
     */

    public void isDisplayed() {
        onView(viewMatcher).check(matches(androidx.test.espresso.matcher.ViewMatchers.isDisplayed()));
    }

    /**
     * Is view is displayed
     *
     * @return : Return true if view with text value is displayed
     */

    public boolean isViewDisplayed() {
        try {
            onView(viewMatcher).check(matches(androidx.test.espresso.matcher.ViewMatchers.isDisplayed()));
            return true;
        } catch (NoMatchingViewException e) {
            return false;
        }
    }

    /**
     * Verify if view is selected
     */

    public void isSelected() {
        onView(viewMatcher).check(matches(androidx.test.espresso.matcher.ViewMatchers.isSelected()));
    }

    /**
     * Verify if view is selected
     */

    public boolean isViewSelected() {
        try {
            onView(allOf(viewMatcher, androidx.test.espresso.matcher.ViewMatchers.isSelected())).check(matches(androidx.test.espresso.matcher.ViewMatchers.isDisplayed()));
            return true;
        } catch (NoMatchingViewException e) {
            return false;
        }
    }

    /**
     * Verify if view is enabled
     */

    public void isEnabled() {
        onView(viewMatcher).check(matches(androidx.test.espresso.matcher.ViewMatchers.isEnabled()));
    }

    /**
     * Verify if view is disabled
     */

    public void isDisabled() {
        onView(viewMatcher).check(matches(not(androidx.test.espresso.matcher.ViewMatchers.isEnabled())));
    }

    /**
     * Scroll to the view inside ScrollView
     */

    public void scrollTo() {
        onView(viewMatcher).perform(ViewActions.nestedScrollTo());
    }

    public void performActions(ViewAction... viewActions) {
        onView(viewMatcher).perform(viewActions);
    }
}
