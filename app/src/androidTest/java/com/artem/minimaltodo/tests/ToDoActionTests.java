package com.artem.minimaltodo.tests;

import com.artem.minimaltodo.AbstractTest;
import com.artem.minimaltodo.model.ToDoItem;
import com.artem.minimaltodo.pages.MainPage;

import org.junit.Test;

import java.time.LocalDateTime;

public class ToDoActionTests extends AbstractTest {

    @Test
    public void addToDo() {
        ToDoItem testItem = ToDoItem.getBuilder()
                .setTitle("Interview at Peloton")
                .setDescription("Second round")
                .setReminder(LocalDateTime.of(2021, 4, 5, 12, 0))
                .build();

        new MainPage()
                .clickFab()
                .addToDoItem(testItem)
                .selectTask(testItem.getTitle())
                .verifyItem(testItem);

    }

    @Test
    public void tryToAddEmptyToDo() {
        ToDoItem testItem = ToDoItem.getBuilder()
                .setTitle("")
                .build();

        new MainPage()
                .clickFab()
                .addToDoItemWithError(testItem)
                .verifyTitleError("Please enter a Todo");

    }
}

