package com.artem.minimaltodo.views;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.test.espresso.ViewAction;
import androidx.test.espresso.matcher.ViewMatchers;

import com.artem.minimaltodo.viewactions.ViewActions;

import org.hamcrest.Matcher;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.longClick;
import static androidx.test.espresso.action.ViewActions.swipeDown;
import static androidx.test.espresso.action.ViewActions.swipeUp;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static com.artem.minimaltodo.matchers.ViewMatchers.withAdaptedData;
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.startsWith;

public class ListView extends AbstractView {

    public ListView(Matcher<View> matcher) {
        super(matcher);
    }

    public ListView() {
        super(ViewMatchers.isAssignableFrom(android.widget.ListView.class));
    }

    /**
     * Swipe to the Bottom
     */

    public void swipeToBottom() {
        onView(viewMatcher).perform(swipeUp());
    }

    /**
     * Swipe to the Top
     */

    public void swipeToTop() {
        onView(viewMatcher).perform(swipeDown());
    }

    /**
     * Swipe to the Top
     */

    public void swipeToParticularElementPositionInList(int position) {
        onData(anything()).inAdapterView(viewMatcher).atPosition(position).check(matches(ViewMatchers.isDisplayed()));
    }

    public void verifyTextInChildView(final int position, @NonNull final Matcher<View> childMatcher, @NonNull final String expectedText) {
        onData(anything()).inAdapterView(viewMatcher).atPosition(position).onChildView(childMatcher).check(matches(withText(startsWith(expectedText))));
    }

    /**
     * Click Item with Title
     *
     * @param matcher : matcher of the item
     */

    public void clickItem(@NonNull final Matcher<?> matcher) {
        onData(matcher).inAdapterView(viewMatcher).perform(ViewActions.shortClick());
    }

    /**
     * Click on the child view of the List Item
     *
     * @param matcher      : matcher of the row
     * @param childMatcher : matcher of the item on the child View
     */

    public void peformActionOnChildView(@NonNull final Matcher<?> matcher, @NonNull final Matcher<View> childMatcher, @NonNull ViewAction... actions) {
        onData(matcher).onChildView(childMatcher).inAdapterView(viewMatcher).perform(actions);
    }

    /**
     * Select Item with Title
     *
     * @param matcher : matcher of the item
     */

    public void selectItem(@NonNull final Matcher<?> matcher) {
        onData(matcher).inAdapterView(viewMatcher).perform(longClick());
    }

    /**
     * Is Item selected
     *
     * @param matcher : matcher of the item
     */

    public void isItemSelected(@NonNull final Matcher<?> matcher, @NonNull final Matcher<View> childMatcher) {
        onData(matcher).onChildView(childMatcher).inAdapterView(viewMatcher).check(matches(ViewMatchers.isSelected()));
    }

    /**
     * Click Item by ordinal
     *
     * @param position : ordinal position of the item
     */

    public void clickByOrdinal(final int position) {
        clickByOrdinal(position, false);
    }

    /**
     * Click Item by ordinal
     *
     * @param position   : ordinal position of the item
     * @param shortClick : perform shortClick or click
     */

    public void clickByOrdinal(final int position, final boolean shortClick) {
        onData(anything()).inAdapterView(viewMatcher).atPosition(position).perform(shortClick ? ViewActions.shortClick() : click());
    }

    /**
     * Verify if Item with matcher is displayed
     *
     * @param matcher : matcher of the item
     */

    public void containsItem(@NonNull final Matcher<?> matcher) {
        onView(viewMatcher).check(matches(withAdaptedData(matcher)));
    }
}
