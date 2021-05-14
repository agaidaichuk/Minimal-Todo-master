package com.artem.minimaltodo;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner;

import com.artem.minimaltodo.Main.MainActivity;
import com.artem.minimaltodo.rules.ScreenshotTestWatcher;

import org.junit.Rule;
import org.junit.rules.RuleChain;
import org.junit.rules.TestRule;
import org.junit.runner.RunWith;


@RunWith(AndroidJUnit4ClassRunner.class)
public abstract class AbstractTest {

    @Rule
    public final TestRule activityAndScreenshotRule = RuleChain
            .outerRule(new ActivityScenarioRule(MainActivity.class))
            .around(new ScreenshotTestWatcher());
}
