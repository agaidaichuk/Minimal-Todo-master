package com.artem.minimaltodo.utils;

import android.app.Activity;

import androidx.test.runner.lifecycle.ActivityLifecycleMonitorRegistry;
import androidx.test.runner.lifecycle.Stage;

import java.util.Collection;

import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;

/**
 * Useful activity utils for testing
 */
public final class ActivityUtils {

    public static Activity getCurrentActivity() {
        class CurrentActivity {

            private Activity instance;
        }
        final CurrentActivity currentActivity = new CurrentActivity();

        getInstrumentation().runOnMainSync(new Runnable() {
            @Override
            public void run() {
                Collection<Activity> activities = ActivityLifecycleMonitorRegistry.getInstance()
                        .getActivitiesInStage(Stage.RESUMED);

                if (activities != null && activities.size() > 0) {
                    currentActivity.instance = activities.iterator().next();
                }
            }
        });

        return currentActivity.instance;
    }
}
