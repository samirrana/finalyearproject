package com.example.assignmentapplication;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.inputmethod.InputMethodManager;

import java.util.List;
import java.util.UUID;

public class AssignmentPagerActivity extends AppCompatActivity  {
    private static final String EXTRA_ASSIGNMNENT_ID =
            "com.example.android.tvshowapplication.assignment_id";

    private ViewPager mViewPager;
    private List<Assignments> mAssignments;


    public static Intent newIntent(Context packageContext, UUID assignmentID) {
        Intent intent = new Intent(packageContext, AssignmentPagerActivity.class);
        intent.putExtra(EXTRA_ASSIGNMNENT_ID, assignmentID);
        return intent;
    }




    @Override
    protected void onCreate(Bundle savedInstanceState) {

        final SharedPref sharedPref;
        sharedPref = new SharedPref(this);


        if(sharedPref.loadNightModeState()){
            setTheme(R.style.darktheme);

        } else setTheme(R.style.AppTheme);


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assignment_pager);

        UUID assignmentId = (UUID) getIntent()
                .getSerializableExtra(EXTRA_ASSIGNMNENT_ID);

        mViewPager = (ViewPager) findViewById(R.id.assignment_view_pager);

        mAssignments = AssignmentLab.get(this).getAssignments();
        FragmentManager fragmentManager = getSupportFragmentManager();
        mViewPager.setAdapter(new FragmentStatePagerAdapter(fragmentManager) {

            @Override
            public Fragment getItem(int position) {
                Assignments show = mAssignments.get(position);
                return AssignmentFragment.newInstance(show.getId());
            }

            @Override
            public int getCount() {
                return mAssignments.size();
            }
        });

        for (int i = 0; i < mAssignments.size(); i++) {
            if (mAssignments.get(i).getId().equals(assignmentId)) {
                mViewPager.setCurrentItem(i);
                break;
            }
        }

    }
}
