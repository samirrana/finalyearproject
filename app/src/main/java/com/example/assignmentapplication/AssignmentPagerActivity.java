package com.example.assignmentapplication;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import java.util.List;
import java.util.UUID;

import static com.example.assignmentapplication.AssignmentDetailFragment.isIsEdit;
import static com.example.assignmentapplication.AssignmentDetailFragment.setIsEdit;

public class AssignmentPagerActivity extends AppCompatActivity {
    private static final String EXTRA_ASSIGNMNENT_ID =
            "com.example.assignmentapplication.assignment_id";

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


        if (sharedPref.loadNightModeState()) {
            setTheme(R.style.darktheme);

        } else setTheme(R.style.AppTheme);

        isIsEdit();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assignment_pager);



        final UUID assignmentId = (UUID) getIntent()
                .getSerializableExtra(EXTRA_ASSIGNMNENT_ID);

        mViewPager = (ViewPager) findViewById(R.id.assignment_view_pager);

        mAssignments = AssignmentLab.get(this).getAssignments();
        FragmentManager fragmentManager = getSupportFragmentManager();
        mViewPager.setAdapter(new FragmentStatePagerAdapter(fragmentManager) {

            @Override
            public Fragment getItem(int position) {
                Assignments assignment = mAssignments.get(position);
                if (assignment.getTitle() == null) {
                    return AssignmentFragment.newInstance(assignment.getId());

                } else if (isIsEdit()) {
                    setIsEdit(false);
                    return AssignmentFragment.newInstance(assignment.getId());

                }
                else {
                    return AssignmentDetailFragment.newInstance(assignment.getId());
                }
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
