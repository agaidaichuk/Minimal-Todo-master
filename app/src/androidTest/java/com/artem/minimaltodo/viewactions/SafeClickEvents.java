package com.artem.minimaltodo.viewactions;

import android.os.SystemClock;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.test.espresso.action.GeneralLocation;
import androidx.test.espresso.action.Press;

import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;

class SafeClickEvents {

    private static final long TAP_UP_EVENT_DELAY_IN_MS = 150;

    private static final float DEFAULT_PRESSURE = 1.0f;
    private static final float DEFAULT_SIZE = 1.0f;
    private static final int DEFAULT_META_STATE = 0;
    private static final int DEFAULT_DEVICE_ID = 0;
    private static final int DEFAULT_EDGE_FLAGS = 0;

    /**
     * Secure click that will not be transformed into the long click as happens sometimes with
     * Espresso
     */
    static void safeTap(@NonNull View view) {
        float[] screenCoordinates = GeneralLocation.VISIBLE_CENTER.calculateCoordinates(view);
        float[] precision = Press.FINGER.describePrecision();

        MotionEvent downEvent = sendDown(screenCoordinates, precision, SystemClock.uptimeMillis());

        sendUp(downEvent.getDownTime(),
                screenCoordinates, downEvent.getDownTime() + TAP_UP_EVENT_DELAY_IN_MS);
    }

    private static MotionEvent sendDown(@NonNull float[] touchPoint,
                                        @NonNull float[] precision, long downEventTime) {
        MotionEvent motionEvent = MotionEvent.obtain(downEventTime,
                SystemClock.uptimeMillis(),
                MotionEvent.ACTION_DOWN,
                touchPoint[0],
                touchPoint[1],
                DEFAULT_PRESSURE,
                DEFAULT_SIZE,
                DEFAULT_META_STATE,
                precision[0],
                precision[1],
                DEFAULT_DEVICE_ID,
                DEFAULT_EDGE_FLAGS);

        sendMotionEvent(motionEvent);

        return motionEvent;
    }

    private static void sendUp(final long downEventTime,
                               @NonNull float[] touchPoint, final long eventTime) {
        MotionEvent motionEvent = MotionEvent.obtain(downEventTime,
                eventTime,
                MotionEvent.ACTION_UP,
                touchPoint[0],
                touchPoint[1],
                DEFAULT_META_STATE);

        sendMotionEvent(motionEvent);
    }

    private static void sendMotionEvent(@NonNull MotionEvent motionEvent) {
        getInstrumentation().sendPointerSync(motionEvent);
        motionEvent.recycle();
    }
}
