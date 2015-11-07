package com.gandalf.tvprogramv2;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;

public class TimetableActivity extends SingleFragmentActivity {

    @Override
    public Fragment createFragment() {
        String tvUrl = getIntent().getStringExtra(TimetableFragment.EXTRA_TV_PROGRAM_URL);
        String tvName = getIntent().getStringExtra(TimetableFragment.EXTRA_TV_PROGRAM_NAME);
        return TimetableFragment.newInstance(tvUrl, tvName);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timetable);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_launcher_tv);
        setSupportActionBar(toolbar);
    }

}
