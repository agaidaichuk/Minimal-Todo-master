package com.artem.minimaltodo.utils;

import android.content.res.Resources;

import androidx.core.content.ContextCompat;
import androidx.test.InstrumentationRegistry;

import static androidx.test.InstrumentationRegistry.getTargetContext;

public class ResourceUtils {

    public static String getStringFromResources(final int stringID) {
        return getResources().getString(stringID);
    }

    public static String getQuantityStringFromResources(int id, int quantity, Object... formatArgs) {
        return getResources().getQuantityString(id, quantity, formatArgs);
    }

    public static String[] getStringArrayFromResources(final int stringID) {
        return getResources().getStringArray(stringID);
    }

    public static int getColorFromResourses(int colorId) {
        return ContextCompat.getColor(getTargetContext(), colorId);
    }

    static int getScreenLayoutFromResources() {
        return getResources().getConfiguration().screenLayout;
    }

    private static Resources getResources() {
        return getTargetContext().getResources();
    }
}
