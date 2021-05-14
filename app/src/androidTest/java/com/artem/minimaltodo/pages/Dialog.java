package com.artem.minimaltodo.pages;

import android.os.SystemClock;

import androidx.annotation.NonNull;

import com.artem.minimaltodo.utils.ResourceUtils;

import static androidx.test.espresso.Espresso.closeSoftKeyboard;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.RootMatchers.isDialog;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.endsWith;
import static org.hamcrest.Matchers.startsWith;

public class Dialog extends AbstractPage {

    public Dialog() {
        waitUntilDisplayed(withId(android.R.id.button1));
    }

    public Dialog(final int stringIdTitle) {
        waitUntilDisplayed(withId(android.R.id.title));
        onView(withId(android.R.id.title)).inRoot(isDialog()).check(matches(withText(stringIdTitle)));
    }

    public Dialog(final String stringIdTitle) {
        waitUntilDisplayed(withId(android.R.id.title));
        onView(withId(android.R.id.title)).inRoot(isDialog()).check(matches(withText(stringIdTitle)));
    }

    public Dialog(final int stringIdTitle, final int stringIdMessage) {
        this(stringIdTitle);
        onView(withId(android.R.id.message)).inRoot(isDialog()).check(matches(withText(startsWith(ResourceUtils.getStringFromResources(stringIdMessage)))));
    }

    /**
     * Click OK/Confirm/Save button on the confirmation pop-up
     */

    public void confirmDialog() {
        onView(withId(android.R.id.button1)).inRoot(isDialog()).perform(click());
        SystemClock.sleep(50);
    }

    /**
     * Click Cancel button on the confirmation pop-up
     */

    public void cancelDialog() {
        onView(withId(android.R.id.button2)).inRoot(isDialog()).perform(click());
    }

    public void pressNeutralButton() {
        onView(withId(android.R.id.button3)).inRoot(isDialog()).perform(click());
    }

    public void clickCertificateNameDisplayedWithinDialog(String certificateName) {
        onView(withText(certificateName)).inRoot(isDialog()).perform(click());
    }

    /**
     * Type text into the dialog and confirm it
     *
     * @param text : text to type
     */

    public void enterValueToTheDialog(@NonNull final String text) {
        onView(allOf(withClassName(endsWith("EditText")), isDisplayed())).perform(replaceText(text));
        closeSoftKeyboard();
        confirmDialog();
    }

}
