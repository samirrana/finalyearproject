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

public class NotesListFragment extends Fragment {


    private RecyclerView mNotesRecyclerView;
    private NotesAdapter mAdapter;
    private boolean mSubtitleVisible;
    private static final String SAVED_SUBTITLE_VISIBLE = "subtitle";
    private int index = 1;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }


    public static Fragment newInstance() {
        return new NotesListFragment();
    }

    private class NotesHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView mTextView;
        private Notes mNotes;



        public NotesHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.notes_list, parent, false));
            itemView.setOnClickListener(this);

            mTextView = (TextView) itemView.findViewById(R.id.notes_name);


        }

        public void bind(Notes notes) {
            mNotes = notes;
            mTextView.setText("\u2022 " + mNotes.getHeader());



        }

        @Override
        public void onClick(View view) {
            Intent intent = NotesPagerActivity.newIntent(getActivity(), mNotes.getId());
            startActivity(intent);
        }
    }


    protected class NotesAdapter extends RecyclerView.Adapter<NotesHolder> {

        private List<Notes> mNotes;


        public NotesAdapter(List<Notes> notes) {
            mNotes = notes;
        }

        @NonNull
        @Override
        public NotesHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());

            return new NotesHolder(layoutInflater, parent);
        }

        @Override
        public void onBindViewHolder(@NonNull NotesHolder holder, int position) {
            Notes notes = mNotes.get(position);
            holder.bind(notes);

        }

        @Override
        public int getItemCount() {
            return mNotes.size();
        }

        public void setNotes(List<Notes> notes) {
            mNotes = notes;
        }


    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notes_list, container, false);

        mNotesRecyclerView = (RecyclerView) view
                .findViewById(R.id.notes_recycler_view);
        mNotesRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

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
                Notes notes = new Notes();
                NotesLab.get(getActivity()).addNotes(notes);
                Intent intent = NotesPagerActivity
                        .newIntent(getActivity(), notes.getId());
                startActivity(intent);

                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void updateUI() {
        NotesLab notesLab = NotesLab.get(getActivity());
        List<Notes> notes = notesLab.getNotes();


        if (mAdapter == null) {
            mAdapter = new NotesAdapter(notes);
            mNotesRecyclerView.setAdapter(mAdapter);
        } else {
            mAdapter.setNotes(notes);
            mAdapter.notifyDataSetChanged();
        }

    }


}