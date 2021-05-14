package com.artem.minimaltodo.views;

import androidx.annotation.NonNull;
import androidx.test.espresso.DataInteraction;
import androidx.test.espresso.matcher.ViewMatchers;

import junit.framework.AssertionFailedError;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.PreferenceMatchers.withKey;
import static androidx.test.espresso.matcher.PreferenceMatchers.withTitle;
import static androidx.test.espresso.matcher.ViewMatchers.hasFocus;
import static androidx.test.espresso.matcher.ViewMatchers.isChecked;
import static androidx.test.espresso.matcher.ViewMatchers.isNotChecked;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.Matchers.allOf;

public class PreferenceListView extends ListView {

    public PreferenceListView() {
        super(allOf(withId(android.R.id.list), ViewMatchers.isDisplayed(), hasFocus()));
    }

    /**
     * Select Item with String Resource ID
     *
     * @param stringId : String Resource ID of the item
     */

    public void selectPreference(final int stringId) {
        onData(withTitle(stringId)).inAdapterView(viewMatcher).perform(click());
    }

    /**
     * Select Item with String Resource ID key value
     *
     * @param stringId : String Resource ID of the item
     */

    public void selectPreferenceByKey(@NonNull final String stringId) {
        onData(withKey(stringId)).inAdapterView(viewMatcher).perform(click());
    }

    /**
     * Set Checkbox Preference value by its title
     *
     * @param title     : title of the Checkbox Preference
     * @param isChecked : should we check/uncheck this preference
     */

    public void setPreferenceCheckBox(final int title, final boolean isChecked) {
        DataInteraction checkbox = onCheckboxPreference(title);
        try {
            checkbox.check(matches(isChecked ? isChecked() : isNotChecked()));
        } catch (AssertionFailedError e) {
            checkbox.perform(click());
        }
    }

    /**
     * Verify if the value of the Preference with Resource String ID is correct
     *
     * @param titleId : Resource String ID of the List Preference's title
     * @param text    : expected text value
     */

    public void isPreferenceValueCorrect(final int titleId, @NonNull final String text) {
        onPreference(titleId).check(matches(withText(text)));
    }

    /**
     * Verify if Checkbox Preference with Title is enabled
     *
     * @param title : Resource ID of the view
     */

    public void isCheckBoxPreferenceEnabled(final int title) {
        onCheckboxPreference(title).check(matches(ViewMatchers.isEnabled()));
    }

    /**
     * Verify if Checkbox Preference with Title is disabled
     *
     * @param title : Resource ID of the view
     */

    public void isCheckBoxPreferenceDisable(final int title) {
        onCheckboxPreference(title).check(matches(not(ViewMatchers.isEnabled())));
    }

    /**
     * Verify if Checkbox Preference with Title is checked
     *
     * @param title : Resource ID of the view
     */

    public void isCheckBoxPreferenceChecked(final int title) {
        onCheckboxPreference(title).check(matches(isChecked()));
    }

    /**
     * Verify if Checkbox Preference with Title is unchecked
     *
     * @param title : Resource ID of the view
     */

    public void isCheckBoxPreferenceUnchecked(final int title) {
        onCheckboxPreference(title).check(matches(isNotChecked()));
    }

    private DataInteraction onCheckboxPreference(final int title) {
        return getParent(title).onChildView(withId(android.R.id.checkbox));
    }

    private DataInteraction onPreference(final int title) {
        return getParent(title).onChildView(withId(android.R.id.summary));
    }

    private DataInteraction getParent(final int title) {
        return onData(withTitle(title)).inAdapterView(viewMatcher);
    }
}
