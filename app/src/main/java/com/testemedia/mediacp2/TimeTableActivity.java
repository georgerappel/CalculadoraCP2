package com.testemedia.mediacp2;

/**
 * Created by Lucas and George Rappel on 09/07/2015.
 */
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import java.util.Locale;

public class TimeTableActivity extends ActionBarActivity {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link android.support.v4.app.FragmentPagerAdapter} derivative, which
     * will keep every loaded fragment in memory. If this becomes too memory
     * intensive, it may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    SectionsPagerAdapter mSectionsPagerAdapter;
    int label;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    ViewPager mViewPager;
    ListView lvItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.timetable_layout);


        // Create the adapter that will return a fragment for each of the three
        // primary sections of the app.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        lvItems = (ListView) findViewById(R.id.listatimetable);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.addmateria:
                Intent AddTimetable = new Intent(this, AddTimetable.class);
                this.startActivity(AddTimetable);
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
            // getItem is called to instantiate the fragment for the given page.
            // Return a DummySectionFragment (defined as a static inner class
            // below) with the page number as its lone argument.
            Fragment fragment = new DummySectionFragment();
            Bundle args = new Bundle();
            args.putInt(DummySectionFragment.ARG_SECTION_NUMBER, position + 1);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public int getCount() {
            // Show 7 total pages.
            return 7;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            Locale l = Locale.getDefault();
            switch (position) {
                case 0:
                    label = 0;
                    return getString(R.string.segunda).toUpperCase(l);
                case 1:
                    label = 1;
                    return getString(R.string.terca).toUpperCase(l);
                case 2:
                    label = 2;
                    return getString(R.string.quarta).toUpperCase(l);
                case 3:
                    label = 3;
                    return getString(R.string.quinta).toUpperCase(l);
                case 4:
                    label = 4;
                    return getString(R.string.sexta).toUpperCase(l);
                case 5:
                    label = 5;
                    return getString(R.string.sabado).toUpperCase(l);
                case 6:
                    label = 6;
                    return getString(R.string.domingo).toUpperCase(l);

            }
            return null;
        }
    }

    /**
     * A dummy fragment representing a section of the app, but that simply
     * displays dummy text.
     */
    public class DummySectionFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        public static final String ARG_SECTION_NUMBER = "section_number";

        public DummySectionFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            //View rootView = inflater.inflate(R.layout.fragment_main_dummy, container,  false);
            /*TextView dummyTextView = (TextView) rootView.findViewById(R.id.section_label);
            dummyTextView.setText(Integer.toString(getArguments().getInt(ARG_SECTION_NUMBER)));*/
            AddTimetable.diaSemana(getArguments().getInt(ARG_SECTION_NUMBER));
            AddTimetable.diaSemana(label);
            //return rootView;

            View rootView;

            // TodoDatabaseHandler is a SQLiteOpenHelper class connecting to SQLite
            SqlTimetable handler = new SqlTimetable(getActivity());
            // Get access to the underlying writeable database
            SQLiteDatabase db = handler.getWritableDatabase();
            // Query for items from the database and get a cursor back
            String query = "SELECT rowid _id,* FROM Lista WHERE DiaSemana = " + label;
            Cursor timeTableCursor = db.rawQuery(query, null);

            // Find ListView to populate
            lvItems = (ListView) findViewById(R.id.list_timetable);
            // Setup cursor adapter using cursor from last step
            TimeTableCursorAdapter todoAdapter = new TimeTableCursorAdapter(getActivity(), timeTableCursor, 0);
            // Attach cursor adapter to the ListView

            if (timeTableCursor.moveToFirst()){

                do {

                    rootView = todoAdapter.newView(getActivity(), timeTableCursor, container);
                    todoAdapter.bindView(rootView, getActivity(), timeTableCursor);
                    lvItems.setAdapter(todoAdapter);

                }while (timeTableCursor.moveToNext());

                return rootView;

            }


            else{

                rootView = inflater.inflate(R.layout.fragment_main_dummy, container,  false);
                AddTimetable.diaSemana(getArguments().getInt(ARG_SECTION_NUMBER));
                return rootView;

            }




        }



    }


}
