package com.artem.minimaltodo.pages;

import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.contrib.RecyclerViewActions;

import com.artem.minimaltodo.R;
import com.artem.minimaltodo.views.MyView;
import com.artem.minimaltodo.views.RecyclerView;

import static androidx.test.espresso.matcher.ViewMatchers.withId;

public class MainPage extends AbstractPage {
    private final MyView fab = new MyView(withId(R.id.addToDoItemFAB));
    private final RecyclerView emptyToDoList = new RecyclerView(withId(R.id.toDoEmptyView));
    private final RecyclerView toDoList = new RecyclerView(withId(R.id.toDoRecyclerView));

    public MainPage() {
        waitUntilDisplayed(fab);
    }

    public AddToDoPage clickFab() {
        fab.click();

        return new AddToDoPage();
    }

    public AddToDoPage selectTask(String title) {
        toDoList.isDisplayed();
        toDoList.clickOnItemWithText(title);

        return new AddToDoPage();
    }
}
