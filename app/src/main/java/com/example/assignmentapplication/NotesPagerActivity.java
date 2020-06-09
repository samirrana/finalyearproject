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
import static com.example.assignmentapplication.NotesFragment.isIsView;
import static com.example.assignmentapplication.NotesFragment.setIsView;

public class NotesPagerActivity extends AppCompatActivity {
    private static final String EXTRA_NOTES_ID =
            "com.example.assignmentapplication.notes_id";

    private ViewPager mViewPager;
    private List<Notes> mNotes;


    public static Intent newIntent(Context packageContext, UUID notesId) {
        Intent intent = new Intent(packageContext, NotesPagerActivity.class);
        intent.putExtra(EXTRA_NOTES_ID, notesId);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        final SharedPref sharedPref;
        sharedPref = new SharedPref(this);


        if (sharedPref.loadNightModeState()) {
            setTheme(R.style.darktheme);

        } else setTheme(R.style.AppTheme);


        isIsView();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes_pager);



        final UUID notesId = (UUID) getIntent()
                .getSerializableExtra(EXTRA_NOTES_ID);

        mViewPager = (ViewPager) findViewById(R.id.notes_view_pager);

        mNotes = NotesLab.get(this).getNotes();
        FragmentManager fragmentManager = getSupportFragmentManager();
        mViewPager.setAdapter(new FragmentStatePagerAdapter(fragmentManager) {

            @Override
            public Fragment getItem(int position) {
                Notes notes = mNotes.get(position);
                if (notes.getText() == null) {
                    return NotesFragment.newInstance(notes.getId());

                } else if (isIsView()) {
                    setIsView(false);
                    return NotesFragment.newInstance(notes.getId());

                }
                else {
                    return NotesFragment.newInstance(notes.getId());
                }
            }

            @Override
            public int getCount() {
                return mNotes.size();
            }
        });


        for (int i = 0; i < mNotes.size(); i++) {
            if (mNotes.get(i).getId().equals(notesId)) {
                mViewPager.setCurrentItem(i);
                break;
            }
        }

    }
}
