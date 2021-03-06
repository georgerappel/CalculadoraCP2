package com.testemedia.mediacp2.timetable;

/**
 * Created by Lucas and George Rappel on 09/07/2015.
 */

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.testemedia.mediacp2.R;

import java.util.Locale;

public class TimeTableActivity extends AppCompatActivity {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link android.support.v4.app.FragmentPagerAdapter} derivative, which
     * will keep every loaded fragment in memory. If this becomes too memory
     * intensive, it may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    static int currentItem = 0;
    ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.timetable_layout);
        try {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        } catch (NullPointerException e){
            e.printStackTrace();
        }

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the app.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.pager);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mViewPager.setAdapter(mSectionsPagerAdapter);
        if(currentItem >= 0 && currentItem < 7)
            mViewPager.setCurrentItem(currentItem);
        else
            mViewPager.setCurrentItem(0);
    }

    @Override
    protected void onPause() {
        super.onPause();
        currentItem = mViewPager.getCurrentItem();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.timetable_actions, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            case R.id.addmateria:
                Intent addTimetableIntent = new Intent(this, AddTimetable.class);
                addTimetableIntent.putExtra("dia", mViewPager.getCurrentItem());
                this.startActivity(addTimetableIntent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // CHAMADO SOMENTE NA PRIMEIRA VEZ QUE O FRAGMENTO EH CRIADO
            TimetableDayFragment fragment = new TimetableDayFragment();
            Bundle args = new Bundle();
            args.putInt(TimetableDayFragment.ARG_SECTION_NUMBER, position);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public int getCount() {
            // Show 7 total pages.
            return 6;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            Locale l = Locale.getDefault();
            switch (position) {
                case 0:
                    return getString(R.string.segunda).toUpperCase(l);
                case 1:
                    return getString(R.string.terca).toUpperCase(l);
                case 2:
                    return getString(R.string.quarta).toUpperCase(l);
                case 3:
                    return getString(R.string.quinta).toUpperCase(l);
                case 4:
                    return getString(R.string.sexta).toUpperCase(l);
                case 5:
                    return getString(R.string.sabado).toUpperCase(l);

            }
            return null;
        }
    }
}
