package com.artem.minimaltodo.viewactions;

import org.hamcrest.Matcher;
import org.hamcrest.Matchers;

import android.graphics.Rect;
import android.util.Log;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.ScrollView;

import androidx.annotation.NonNull;
import androidx.core.widget.NestedScrollView;
import androidx.test.espresso.PerformException;
import androidx.test.espresso.UiController;
import androidx.test.espresso.ViewAction;
import androidx.test.espresso.action.ScrollToAction;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.espresso.util.HumanReadables;

import static androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static androidx.test.espresso.matcher.ViewMatchers.isDescendantOfA;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayingAtLeast;
import static androidx.test.espresso.matcher.ViewMatchers.withEffectiveVisibility;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.anyOf;

public final class ViewActions {

    private ViewActions() {
        throw new AssertionError("This constructor is not meant to be called!");
    }

    /**
     * This method allows to click on ActionBar elements which is not possible with the original
     * android.support.test.espresso.action.click() method.
     *
     * @return ViewAction object to be invoked
     */
    public static ViewAction safeClick() {
        return new ViewAction() {
            @Override
            public Matcher<View> getConstraints() {
                return Matchers.any(View.class);
            }

            @Override
            public String getDescription() {
                return this.getClass().getSimpleName();
            }

            @Override
            public void perform(@NonNull final UiController uiController, @NonNull final View view) {
                uiController.loopMainThreadUntilIdle();

                view.performClick();
            }
        };
    }

    /**
     * Click that will not be transformed into the long click as happens sometimes with
     * Espresso
     *
     * @return ViewAction object to be invoked
     */

    public static ViewAction shortClick() {
        return new ViewAction() {
            @Override
            public Matcher<View> getConstraints() {
                return Matchers.any(View.class);
            }

            @Override
            public String getDescription() {
                return this.getClass().getSimpleName();
            }

            @Override
            public void perform(@NonNull final UiController uiController, @NonNull final View view) {
                uiController.loopMainThreadUntilIdle();

                new Thread() {
                    public void run() {
                        SafeClickEvents.safeTap(view);
                    }
                }.start();
            }
        };
    }

    /**
     * Scroll that supports NestedScrollView
     *
     * @return ViewAction object to be invoked
     */

    public static ViewAction nestedScrollTo() {

        return new ViewAction() {
            private final String TAG = ScrollToAction.class.getSimpleName();

            @SuppressWarnings("unchecked")
            @Override
            public Matcher<View> getConstraints() {
                return allOf(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE), isDescendantOfA(anyOf(
                        isAssignableFrom(ScrollView.class), isAssignableFrom(HorizontalScrollView.class), isAssignableFrom(NestedScrollView.class))));
            }

            @Override
            public void perform(UiController uiController, View view) {
                if (isDisplayingAtLeast(90).matches(view)) {
                    Log.i(TAG, "View is already displayed. Returning.");
                    return;
                }
                Rect rect = new Rect();
                view.getDrawingRect(rect);
                if (!view.requestRectangleOnScreen(rect, true /* immediate */)) {
                    Log.w(TAG, "Scrolling to view was requested, but none of the parents scrolled.");
                }
                uiController.loopMainThreadUntilIdle();
                if (!isDisplayingAtLeast(90).matches(view)) {
                    throw new PerformException.Builder()
                            .withActionDescription(this.getDescription())
                            .withViewDescription(HumanReadables.describe(view))
                            .withCause(new RuntimeException(
                                    "Scrolling to view was attempted, but the view is not displayed"))
                            .build();
                }
            }

            @Override
            public String getDescription() {
                return "scroll to";
            }
        };
    }
}
