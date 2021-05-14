package com.artem.minimaltodo.matchers;

import org.hamcrest.Description;
import org.hamcrest.Matcher;

import android.graphics.drawable.ColorDrawable;
import android.view.View;

import androidx.test.espresso.matcher.BoundedMatcher;

import static org.hamcrest.core.IsEqual.equalTo;

public class ColorMatcher {

    public static Matcher<Object> withColor(int expectedColor) {
        return withColor(equalTo(expectedColor));
    }

    private static Matcher<Object> withColor(final Matcher<Integer> expectedObject) {

        final int[] color = new int[1];

        return new BoundedMatcher<Object, View>(View.class) {
            @Override
            public boolean matchesSafely(final View actualObject) {

                color[0] = ((ColorDrawable) actualObject.getBackground()).getColor();

                return (expectedObject.matches(color[0]));

            }

            @Override
            public void describeTo(final Description description) {
                description.appendText("Color did not match " + color[0]);
            }
        };
    }
}
