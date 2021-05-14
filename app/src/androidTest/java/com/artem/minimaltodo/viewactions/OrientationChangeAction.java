package com.artem.minimaltodo.viewactions;

import org.hamcrest.Matcher;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.view.View;

import androidx.test.espresso.UiController;
import androidx.test.espresso.ViewAction;
import androidx.test.runner.lifecycle.ActivityLifecycleMonitorRegistry;
import androidx.test.runner.lifecycle.Stage;

import java.util.Collection;

import static androidx.test.espresso.matcher.ViewMatchers.isRoot;

/**
 * An Espresso ViewAction that changes the orientation of the screen
 */
public class OrientationChangeAction implements ViewAction {

    private final int orientation;

    private OrientationChangeAction(int orientation) {
        this.orientation = orientation;
    }

    public static ViewAction orientationRight() {
        return new OrientationChangeAction(ActivityInfo.SCREEN_ORIENTATION_REVERSE_LANDSCAPE);
    }

    public static ViewAction orientationLeft() {
        return new OrientationChangeAction(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
    }

    public static ViewAction orientationPortrait() {
        return new OrientationChangeAction(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

    @Override
    public Matcher<View> getConstraints() {
        return isRoot();
    }

    @Override
    public String getDescription() {
        return "change orientation to " + orientation;
    }

    @Override
    public void perform(UiController uiController, View view) {
        uiController.loopMainThreadUntilIdle();
        final Activity activity = (Activity) view.getContext();
        activity.setRequestedOrientation(orientation);

        Collection<Activity> resumedActivities = ActivityLifecycleMonitorRegistry.getInstance().getActivitiesInStage(Stage.RESUMED);
        if (resumedActivities.isEmpty()) {
            throw new RuntimeException("Could not change orientation");
        }
    }
}