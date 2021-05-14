package com.artem.minimaltodo.views;

import junit.framework.AssertionFailedError;

import org.hamcrest.Matcher;

import android.view.View;

import androidx.test.espresso.ViewInteraction;
import androidx.test.espresso.matcher.ViewMatchers;

import com.artem.minimaltodo.viewactions.ViewActions;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static org.hamcrest.Matchers.not;

public class CheckboxView extends AbstractView {

    public CheckboxView(Matcher<View> matcher) {
        super(matcher);
    }

    /**
     * Set Checkbox value
     *
     * @param isChecked : should we check/uncheck this preference
     */

    public void check(final boolean isChecked) {
        ViewInteraction checkbox = onView(viewMatcher);
        try {
            checkbox.check(matches(isChecked ? ViewMatchers.isChecked() : ViewMatchers.isNotChecked()));
        } catch (AssertionFailedError e) {
            checkbox.perform(click());
        }
    }

    /**
     * Set Checkbox value safely
     *
     * @param isChecked : should we check/uncheck this preference
     */

    public void checkSafely(final boolean isChecked) {
        ViewInteraction checkbox = onView(viewMatcher);
        try {
            checkbox.check(matches(isChecked ? ViewMatchers.isChecked() : ViewMatchers.isNotChecked()));
        } catch (AssertionFailedError e) {
            checkbox.perform(ViewActions.safeClick());
        }
    }

    /**
     * Verify if view is checked
     */

    public void isChecked() {
        onView(viewMatcher).check(matches(ViewMatchers.isChecked()));
    }

    /**
     * Verify if view is unchecked
     */

    public void isUnchecked() {
        onView(viewMatcher).check(matches(not(ViewMatchers.isChecked())));
    }
}
