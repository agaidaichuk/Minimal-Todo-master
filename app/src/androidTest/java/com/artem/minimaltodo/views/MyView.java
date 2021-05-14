package com.artem.minimaltodo.views;

import android.os.SystemClock;

import androidx.annotation.NonNull;
import androidx.annotation.StringRes;
import androidx.test.espresso.matcher.ViewMatchers;

import com.artem.minimaltodo.matchers.ColorMatcher;
import com.artem.minimaltodo.matchers.ImageMatchers;
import com.artem.minimaltodo.pages.Dialog;
import com.artem.minimaltodo.viewactions.ViewActions;

import org.hamcrest.Matcher;

import static androidx.test.espresso.Espresso.closeSoftKeyboard;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.clearText;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.hasErrorText;
import static androidx.test.espresso.matcher.ViewMatchers.withTagKey;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.endsWith;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.startsWith;

public class MyView extends AbstractView {

    public MyView(Matcher<android.view.View> matcher) {
        super(matcher);
    }

    /**
     * Click on the view with Resource ID
     */

    public void click() {
        onView(viewMatcher).perform(androidx.test.espresso.action.ViewActions.click());
    }

    /**
     * Click on the view if it's displayed
     */

    public void clickIfVisible() {
        if (isViewDisplayed()) {
            onView(viewMatcher).perform(androidx.test.espresso.action.ViewActions.click());
        }
    }

    /**
     * Wait 500 ms and click on the view
     */

    public void waitAndClick() {
        SystemClock.sleep(500);
        onView(viewMatcher).perform(androidx.test.espresso.action.ViewActions.click());
    }

    /**
     * Scroll to the view with Resource ID and click on it
     * Is valid only inside the ScrollView!!!
     */

    public void scrollToAndClick() {
        onView(viewMatcher).perform(ViewActions.nestedScrollTo(), androidx.test.espresso.action.ViewActions.click());
    }

    /**
     * Click on the view with Resource ID
     */

    public void shortClick() {
        onView(viewMatcher).perform(ViewActions.shortClick());
    }

    /**
     * Click on the view with Resource ID
     */

    public void clickSafely() {
        onView(viewMatcher).perform(ViewActions.safeClick());
    }

    /**
     * Click on the view with Resource ID
     * Click that will not be transformed into the long click as happens sometimes with Espresso
     */

    public void longClick() {
        onView(viewMatcher).perform(androidx.test.espresso.action.ViewActions.longClick());
    }

    /**
     * Type text into the view
     *
     * @param text : text to type
     */
    public void sendKeys(@NonNull final String text) {
        onView(viewMatcher).perform(clearText(), typeText(text));
        closeSoftKeyboard();
    }

    /**
     * Press Action key
     */
    public void pressActionKey() {
        onView(viewMatcher).perform(androidx.test.espresso.action.ViewActions.pressImeActionButton());
    }

    /**
     * Set text value into the view
     *
     * @param text : text to search
     */

    public void replaceText(@NonNull final String text) {
        onView(viewMatcher).perform(androidx.test.espresso.action.ViewActions.replaceText(text));
        closeSoftKeyboard();
    }

    /**
     * Verify if text value of the view with Resource ID is correct
     *
     * @param text : expected text value
     */

    public void isCorrectText(@NonNull final String text) {
        onView(viewMatcher).check(matches(allOf(withText(text), ViewMatchers.isDisplayed())));
    }

    /**
     * Verify if text value of the view with Resource ID is correct
     *
     * @param stringId : stringId of the expected text
     */

    public void isCorrectText(@StringRes final int stringId) {
        onView(viewMatcher).check(matches(allOf(withText(stringId), ViewMatchers.isDisplayed())));
    }

    /**
     * Verify if color of view with Resource ID is correct
     *
     * @param color : expected text value
     */
    public void isCorrectColor(final int color) {
        onView(viewMatcher).check(matches(ColorMatcher.withColor(color)));
    }

    /**
     * Verify if image of view with Resource ID is correct
     *
     * @param image : expected image value
     */
    public void isCorrectImage(final int image) {
        onView(viewMatcher).check(matches(ImageMatchers.withImageDrawable(image)));
    }

    /**
     * Verify if view with Resource ID contains a tag
     */
    public void verifyTag(@NonNull final int key, @NonNull final Object tag) {
        onView(viewMatcher).check(matches(withTagKey(key, equalTo(tag))));
    }

    /**
     * Verify if text value of the view with Resource ID ends correctly
     *
     * @param text : expected text value
     */

    public void endsWithText(@NonNull final String text) {
        onView(viewMatcher).check(matches(allOf(withText(endsWith(text)), ViewMatchers.isDisplayed())));
    }

    /**
     * Verify if textView contains an error
     *
     * @param error : expected text value of error
     */

    public void hasError(@NonNull final String error) {
        onView(viewMatcher).check(matches(hasErrorText(error)));
    }

    /**
     * Verify if text value of the view with Resource ID starts correctly
     *
     * @param text : expected text value
     */

    public void startsWithText(@NonNull final String text) {
        onView(viewMatcher).check(matches(allOf(withText(startsWith(text)), ViewMatchers.isDisplayed())));
    }

    /**
     * Verify if text value of the view with Resource ID contains a specific string
     *
     * @param text : expected subtext
     */

    public void containsText(@NonNull final String text) {
        onView(viewMatcher).check(matches(allOf(withText(containsString(text)), ViewMatchers.isDisplayed())));
    }

    /**
     * Set value to the view with Resource String ID via dialog
     *
     * @param text : value to set
     */

    public void enterValueViaDialog(@NonNull final String text) {
        click();
        new Dialog().enterValueToTheDialog(text);
    }
}
