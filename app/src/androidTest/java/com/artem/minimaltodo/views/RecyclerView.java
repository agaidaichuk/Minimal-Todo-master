package com.artem.minimaltodo.views;

import android.view.View;

import androidx.test.espresso.contrib.RecyclerViewActions;

import org.hamcrest.Matcher;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.matcher.ViewMatchers.hasDescendant;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static com.artem.minimaltodo.viewactions.ViewActions.nestedScrollTo;


public class RecyclerView extends AbstractView {

    public RecyclerView(Matcher<View> matcher) {
        super(matcher);
    }

    public void clickOnTheSpecificItemOfRecyclerView(int position) {
        onView(viewMatcher).perform(RecyclerViewActions.actionOnItemAtPosition(position, click()));
    }

    public void clickOnItemWithText(String text) {
        onView(viewMatcher)
                .perform(RecyclerViewActions.actionOnItem(hasDescendant(withText(text)), nestedScrollTo()));
        onView(viewMatcher)
                .perform(RecyclerViewActions.actionOnItem(hasDescendant(withText(text)), click()));
    }
}
