package com.artem.minimaltodo.views;

import android.view.View;
import android.widget.DatePicker;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.test.espresso.assertion.ViewAssertions;
import androidx.test.espresso.contrib.PickerActions;

import com.artem.minimaltodo.pages.Dialog;

import org.hamcrest.Matcher;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.equalTo;

public class DateTimeView extends AbstractView {
    private static final DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("d MMM, yyyy");
    private static final DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("h:mm a");

    public DateTimeView(Matcher<View> matcher) {
        super(matcher);
    }

    /**
     * Set Date and Time by the Resource String ID of the view
     *
     * @param date : date and time to set
     */

    public void setDateTime(@NonNull final Calendar date) {
        onView(viewMatcher).perform(click());
        onView(withClassName(equalTo(DatePicker.class.getName())))
                .perform(PickerActions.setDate(date.get(Calendar.YEAR), date.get(Calendar.MONTH) + 1, date.get(Calendar.DAY_OF_MONTH)));
        new Dialog().confirmDialog();
        onView(withClassName(equalTo(TimePicker.class.getName())))
                .perform(PickerActions.setTime(date.get(Calendar.HOUR_OF_DAY), date.get(Calendar.MINUTE)));
        new Dialog().confirmDialog();
    }

    /**
     * Set Date by the Resource ID of the view
     *
     * @param date : date to set
     */

    public void setPickerDate(@NonNull final LocalDateTime date) {
        onView(viewMatcher).perform(click());
        onView(withClassName(equalTo(DatePicker.class.getName())))
                .perform(PickerActions.setDate(date.getYear(), date.getMonth().getValue(), date.getDayOfMonth()));
        new Dialog().confirmDialog();
    }

    /**
     * Set Time by the Resource ID of the view
     *
     * @param date : time to set
     */

    public void verifyDate(@NonNull final LocalDateTime date) {
        onView(viewMatcher).check(ViewAssertions.matches(withText(date.format(dateFormat))));
    }

    /**
     * Set Time by the Resource ID of the view
     *
     * @param date : time to set
     */

    public void setPickerTime(@NonNull final LocalDateTime date) {
        onView(viewMatcher).perform(click());
        onView(withClassName(equalTo(TimePicker.class.getName())))
                .perform(PickerActions.setTime(date.getHour(), date.getMinute()));
        new Dialog().confirmDialog();
    }

    /**
     * Set Time by the Resource ID of the view
     *
     * @param date : time to set
     */

    public void verifyTime(@NonNull final LocalDateTime date) {
        onView(viewMatcher).check(ViewAssertions.matches(withText(date.format(timeFormat))));
    }

}
