package com.gandalf.tvprogramv2;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;

import org.json.JSONException;

import java.io.IOException;

public class LauncherActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        TabLayout mTabLayout = (TabLayout) findViewById(R.id.tab_layout);
        mTabLayout.addTab(mTabLayout.newTab().setText("Programi"));
        mTabLayout.addTab(mTabLayout.newTab().setText("Omiljeni"));
        mTabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final ViewPager mPagerViewPager = (ViewPager) findViewById(R.id.pager_viewPager);
        final PagerAdapter mPagerAdapter = new PagerAdapter(getSupportFragmentManager(), mTabLayout.getTabCount());
        mPagerViewPager.setAdapter(mPagerAdapter);
        mPagerViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTabLayout));
        mTabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mPagerViewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        ImageButton mSearchImageButton = (ImageButton) findViewById(R.id.search_imageButton);
        mSearchImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getBaseContext(), SearchProgramsActivity.class);
                startActivity(i);
            }
        });
    }

    private class PagerAdapter extends FragmentStatePagerAdapter {
        private int mCount;

        public PagerAdapter(FragmentManager fm, int count) {
            super(fm);
            mCount = count;
        }

        @Override
        public android.support.v4.app.Fragment getItem(int position) {
            switch (position) {
                case 0:
                    ProgramsTabFragment tab1 = new ProgramsTabFragment();
                    return tab1;
                case 1:
                    FavoritesFragment tab2 = new FavoritesFragment();
                    return tab2;
                default:
                    return null;

            }
        }

        @Override
        public int getCount() {
            return mCount;
        }
    }

}
