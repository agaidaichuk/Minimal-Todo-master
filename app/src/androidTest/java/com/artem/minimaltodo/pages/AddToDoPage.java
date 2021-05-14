package com.artem.minimaltodo.pages;

import com.artem.minimaltodo.R;
import com.artem.minimaltodo.model.ToDoItem;
import com.artem.minimaltodo.views.CheckboxView;
import com.artem.minimaltodo.views.DateTimeView;
import com.artem.minimaltodo.views.MyView;

import java.time.LocalDateTime;

import static androidx.test.espresso.matcher.ViewMatchers.withId;

public class AddToDoPage extends AbstractPage {
    private final MyView fab = new MyView(withId(R.id.makeToDoFloatingActionButton));
    private final MyView toDoTitle = new MyView(withId(R.id.userToDoEditText));
    private final MyView toDoDescr = new MyView(withId(R.id.userToDoDescription));
    private final CheckboxView reminderCheckbox = new CheckboxView(withId(R.id.toDoHasDateSwitchCompat));
    private final MyView copyReminder = new MyView(withId(R.id.copyclipboard));
    private final DateTimeView reminderDate = new DateTimeView(withId(R.id.newTodoDateEditText));
    private final DateTimeView reminderTime = new DateTimeView(withId(R.id.newTodoTimeEditText));

    public AddToDoPage() {
        waitUntilDisplayed(fab);
    }

    public void clickFab() {
        fab.click();
    }

    public AddToDoPage enterTitle(String title) {
        toDoTitle.sendKeys(title);

        return this;
    }

    public AddToDoPage verifyTitleError(String error) {
        toDoTitle.hasError(error);

        return this;
    }

    public AddToDoPage enterDescr(String descr) {
        toDoDescr.sendKeys(descr);

        return this;
    }

    public AddToDoPage checkReminder(Boolean checked) {
        reminderCheckbox.check(checked);

        return this;
    }

    public AddToDoPage copyReminder() {
        copyReminder.click();

        return this;
    }

    public AddToDoPage setReminder(LocalDateTime reminAt) {
        if (reminAt != null) {
            checkReminder(true);
            reminderDate.setPickerDate(reminAt);
            reminderTime.setPickerTime(reminAt);
        }

        return this;
    }

    public AddToDoPage addToDoItemWithError(ToDoItem item) {
        setAllFields(item);
        clickFab();

        return new AddToDoPage();
    }

    public MainPage addToDoItem(ToDoItem item) {
        setAllFields(item);
        clickFab();

        return new MainPage();
    }

    private void setAllFields(ToDoItem item) {
        if (item.getTitle() != null)
            enterTitle(item.getTitle());
        if (item.getDescription() != null)
            enterDescr(item.getDescription());
        if (item.getRemindAt() != null)
            setReminder(item.getRemindAt());
    }

    public AddToDoPage verifyItem(ToDoItem item) {
        toDoTitle.isCorrectText(item.getTitle());
        toDoDescr.isCorrectText(item.getDescription());

        if (item.getRemindAt() != null) {
            reminderCheckbox.isChecked();
            reminderDate.verifyDate(item.getRemindAt());
            reminderTime.verifyTime(item.getRemindAt());
        }

        return this;
    }


}
