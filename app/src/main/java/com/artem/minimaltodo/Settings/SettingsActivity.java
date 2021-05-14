package com.artem.minimaltodo.Settings;

import android.app.FragmentManager;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NavUtils;

import com.artem.minimaltodo.Analytics.AnalyticsApplication;
import com.artem.minimaltodo.Main.MainFragment;
import com.artem.minimaltodo.R;

public class SettingsActivity extends AppCompatActivity {

    AnalyticsApplication app;

    @Override
    protected void onResume() {
        super.onResume();
        app.send(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        app = (AnalyticsApplication) getApplication();
        String theme = getSharedPreferences(MainFragment.THEME_PREFERENCES, MODE_PRIVATE).getString(MainFragment.THEME_SAVED, MainFragment.LIGHTTHEME);
        if (theme.equals(MainFragment.LIGHTTHEME)) {
            setTheme(R.style.CustomStyle_LightTheme);
        } else {
            setTheme(R.style.CustomStyle_DarkTheme);
        }
        super.onCreate(savedInstanceState);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_settings);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//        final Drawable backArrow = getResources().getDrawable(R.drawable.);
//        if (backArrow != null) {
//            backArrow.setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP);
//
//        }

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//            getSupportActionBar().setHomeAsUpIndicator(backArrow);
        }

        FragmentManager fm = getFragmentManager();
        fm.beginTransaction().replace(R.id.mycontent, new SettingsFragment()).commit();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                if (NavUtils.getParentActivityName(this) != null) {
                    NavUtils.navigateUpFromSameTask(this);
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}