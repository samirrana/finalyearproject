package com.example.assignmentapplication;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.DateFormat;
import java.util.Date;
import java.util.List;

public class AssignmentListFragment extends Fragment {


    private RecyclerView mAssignmentRecyclerView;
    private AssignmentAdapter mAdapter;
    private boolean mSubtitleVisible;
    private static final String SAVED_SUBTITLE_VISIBLE = "subtitle";


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }


    public static Fragment newInstance() {
        return new AssignmentListFragment();
    }

    private class AssignmentHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView mTitleTextView;
        private TextView mDateTextView;
        private Assignments mAssignments;
        private ImageView mSolvedImageView;
        private TextView mSubjectTextView;
        private TextView mReminderTextView;
        private TextView mTypeTextView;


        public AssignmentHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.list_item_assignment, parent, false));
            itemView.setOnClickListener(this);

            mTitleTextView = (TextView) itemView.findViewById(R.id.assignment_title);
            mDateTextView = (TextView) itemView.findViewById(R.id.assignment_date);
            mSolvedImageView = (ImageView) itemView.findViewById(R.id.assignment_solved);
            mSubjectTextView = (TextView) itemView.findViewById(R.id.assignment_subject);
            mReminderTextView = (TextView) itemView.findViewById(R.id.assignment_reminder);
            mTypeTextView = (TextView) itemView.findViewById(R.id.assignment_type);


        }

        public void bind(Assignments assignment) {
            mAssignments = assignment;
            mTitleTextView.setText(mAssignments.getTitle());
            Date date = mAssignments.getDate();
            String formattedDate = DateFormatter.formatDateAsString(DateFormat.LONG, date);
            String formattedTime = DateFormatter.formatDateAsTimeString(DateFormat.SHORT, date);
            mDateTextView.setText(formattedDate + " @ " + formattedTime);
            mSolvedImageView.setVisibility(assignment.isSolved() ? View.VISIBLE : View.GONE);
            mSubjectTextView.setText(mAssignments.getSubject());
            Date dates = mAssignments.getReminderDate();
            String formattedDates = DateFormatter.formatDateAsString(DateFormat.LONG, dates);
            String formattedTimes = DateFormatter.formatDateAsTimeString(DateFormat.SHORT, dates);
            mReminderTextView.setText(formattedDates + " @ " + formattedTimes);
            mTypeTextView.setText(mAssignments.getType());


        }

        @Override
        public void onClick(View view) {
            Intent intent = AssignmentPagerActivity.newIntent(getActivity(), mAssignments.getId());
            startActivity(intent);
        }
    }


    protected class AssignmentAdapter extends RecyclerView.Adapter<AssignmentHolder> {

        private List<Assignments> mAssignments;


        public AssignmentAdapter(List<Assignments> assignments) {
            mAssignments = assignments;
        }

        @NonNull
        @Override
        public AssignmentHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());

            return new AssignmentHolder(layoutInflater, parent);
        }

        @Override
        public void onBindViewHolder(@NonNull AssignmentHolder holder, int position) {
            Assignments assignment = mAssignments.get(position);
            holder.bind(assignment);

        }

        @Override
        public int getItemCount() {
            return mAssignments.size();
        }

        public void setAssignments(List<Assignments> assignments) {
            mAssignments = assignments;
        }


    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_assignment_list, container, false);

        mAssignmentRecyclerView = (RecyclerView) view
                .findViewById(R.id.assignment_recycler_view);
        mAssignmentRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        if (savedInstanceState != null) {
            mSubtitleVisible = savedInstanceState.getBoolean(SAVED_SUBTITLE_VISIBLE);
        }

        updateUI();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        updateUI();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(SAVED_SUBTITLE_VISIBLE, mSubtitleVisible);
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_assignment_list, menu);


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.new_show:
                Assignments assignment = new Assignments();
                AssignmentLab.get(getActivity()).addAssignments(assignment);
                Intent intent = AssignmentPagerActivity
                        .newIntent(getActivity(), assignment.getId());
                startActivity(intent);

                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void updateUI() {
        AssignmentLab assignmentLab = AssignmentLab.get(getActivity());
        List<Assignments> assignments = assignmentLab.getAssignments();


        if (mAdapter == null) {
            mAdapter = new AssignmentAdapter(assignments);
            mAssignmentRecyclerView.setAdapter(mAdapter);
        } else {
            mAdapter.setAssignments(assignments);
            mAdapter.notifyDataSetChanged();
        }

    }


}