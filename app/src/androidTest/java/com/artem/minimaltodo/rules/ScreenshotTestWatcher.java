package com.artem.minimaltodo.rules;

import android.graphics.Bitmap;

import androidx.test.runner.screenshot.ScreenCapture;
import androidx.test.runner.screenshot.Screenshot;

import org.junit.rules.TestWatcher;
import org.junit.runner.Description;

import java.io.IOException;

public class ScreenshotTestWatcher extends TestWatcher {
    /**
     * Invoked when a test method finishes (whether passing or failing)
     */
    @Override
    protected void failed(Throwable error, Description description) {
        super.failed(error, description);

        String className = (description != null) ? description.getTestClass().getSimpleName() : "NullClassname";
        String methodName = (description != null) ? description.getMethodName() : "NullMethodName";
        String filename = String.format("%s - %s", className, methodName);

        ScreenCapture capture = Screenshot.capture();
        capture.setName(filename);
        capture.setFormat(Bitmap.CompressFormat.PNG);

        try {
            capture.process();
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }
}
