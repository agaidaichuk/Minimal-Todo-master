package com.artem.minimaltodo.idlingresource;

import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.test.espresso.IdlingResource;
import androidx.test.espresso.NoMatchingViewException;
import androidx.test.espresso.ViewFinder;
import androidx.test.espresso.ViewInteraction;

import org.hamcrest.Matcher;

import java.lang.reflect.Field;

import static androidx.test.espresso.Espresso.onView;


public class WaitViewIdlingResource implements IdlingResource {

    private static final String TAG = WaitViewIdlingResource.class.getSimpleName();
    private final Matcher<View> viewMatcher;
    private ResourceCallback resourceCallback;

    public WaitViewIdlingResource(@NonNull final Matcher<View> viewMatcher) {
        this.viewMatcher = viewMatcher;
    }

    @Override
    public boolean isIdleNow() {
        View view = getView(viewMatcher);
        boolean idle = view == null || view.isShown();

        if (idle && resourceCallback != null) {
            resourceCallback.onTransitionToIdle();
        }

        return idle;
    }

    private static View getView(Matcher<View> viewMatcher) {
        try {
            ViewInteraction viewInteraction = onView(viewMatcher);
            Field finderField = viewInteraction.getClass().getDeclaredField("viewFinder");
            finderField.setAccessible(true);
            ViewFinder finder = (ViewFinder) finderField.get(viewInteraction);
            return finder.getView();
        } catch (NoMatchingViewException e) {
            Log.e(TAG, e.getMessage());
        } catch (NoSuchFieldException e) {
            Log.e(TAG, "ViewFinder is not present");
        } catch (IllegalAccessException | IllegalStateException e) {
            Log.e(TAG, "ViewFinder is not accessible");
        }
        return null;
    }

    @Override
    public void registerIdleTransitionCallback(@NonNull final ResourceCallback resourceCallback) {
        this.resourceCallback = resourceCallback;
    }

    /**
     * This is needed to make sure the idling resource is reusable
     */
    @Override
    public String getName() {
        return this + viewMatcher.toString();
    }
}