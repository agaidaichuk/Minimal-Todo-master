package com.artem.minimaltodo.pages;

import org.hamcrest.Matcher;

import android.view.View;

public interface Action {

    Matcher<View> getMatcher();
}
