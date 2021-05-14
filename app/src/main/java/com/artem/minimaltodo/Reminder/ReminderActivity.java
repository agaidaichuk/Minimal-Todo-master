package com.artem.minimaltodo.Reminder;

import android.os.Bundle;

import androidx.annotation.NonNull;

import com.artem.minimaltodo.AppDefault.AppDefaultActivity;
import com.artem.minimaltodo.R;

public class ReminderActivity extends AppDefaultActivity {

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected int contentViewLayoutRes() {
        return R.layout.reminder_layout;
    }

    @NonNull
    @Override
    protected ReminderFragment createInitialFragment() {
        return ReminderFragment.newInstance();
    }


}
