package com.artem.minimaltodo.views;

import android.os.SystemClock;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.test.espresso.matcher.ViewMatchers;

import com.artem.minimaltodo.viewactions.ViewActions;

import org.hamcrest.Matcher;

import static android.view.KeyEvent.KEYCODE_DEL;
import static androidx.test.espresso.Espresso.closeSoftKeyboard;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.pressKey;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.action.ViewActions.typeTextIntoFocusedView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.RootMatchers.withDecorView;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static com.artem.minimaltodo.utils.ActivityUtils.getCurrentActivity;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.isEmptyString;
import static org.hamcrest.Matchers.not;

public class MultiAutoSelectView extends AbstractView {

    public MultiAutoSelectView(Matcher<View> matcher) {
        super(matcher);
    }

    public void setValues(@NonNull final String... recipients) {
        onView(viewMatcher).perform(click());
        for (String value : recipients) {
            onView(viewMatcher).perform(typeTextIntoFocusedView(value));
            onView(viewMatcher).perform(typeTextIntoFocusedView(" "));
        }
        closeSoftKeyboard();
    }

    public void selectValue(@NonNull final String textToType, @NonNull final String value) {
        onView(viewMatcher).perform(click());
        onView(viewMatcher).perform(typeText(textToType));
        selectAutoComplete(value);
        closeSoftKeyboard();
    }

    public void removeValue() {
        onView(viewMatcher).perform(click()); // select view
        onView(viewMatcher).perform(pressKey(KEYCODE_DEL)); // Remove address
    }

    public void verifyText(@NonNull final String text) {
        if (text.isEmpty()) {
            onView(viewMatcher).check(matches(withText(isEmptyString())));
        } else {
            onView(viewMatcher).check(matches(allOf(withText(containsString(text)), ViewMatchers.isDisplayed())));
        }
    }

    /**
     * Select suggested value from the Auto-Complete drop-down
     *
     * @param value : text value to select from the Auto-Complete drop-down
     */

    private void selectAutoComplete(@NonNull final String value) {
        /*
        This sleep is needed because sometimes the app doesn't react when clicking on
        suggestion directly after typing text
         */
        SystemClock.sleep(500);

        onView(allOf(withText(value), ViewMatchers.isDisplayed()))
                .inRoot(withDecorView(not(is(getCurrentActivity().getWindow().getDecorView()))))
                .perform(ViewActions.shortClick());
    }
}
