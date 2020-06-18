package com.example.assignmentapplication;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.assignmentapplication.database.DBHelper;

import java.text.DateFormat;
import java.util.Date;
import java.util.List;


public class TodayAssignmentAdapter extends RecyclerView.Adapter<TodayAssignmentAdapter.ViewHolder>{
    private List<Assignments> data;
    private DBHelper helper;
    private static final int DATE_FORMAT = DateFormat.FULL;

    public TodayAssignmentAdapter(List<Assignments> data, DBHelper helper) {
        this.helper = helper;
        this.data = data;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.rec_today_assignment, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.name.setText(data.get(position).getTitle());
        Date hour = data.get(position).getReminderDate();
        String formattedDates = DateFormatter.formatDateAsString(DATE_FORMAT, hour);
        holder.hour.setText(formattedDates);
        holder.location.setText(data.get(position).getSubject());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        private TextView name, location, hour;
        private ViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.today_assignment_name);
            location = itemView.findViewById(R.id.today_class_location);
            hour = itemView.findViewById(R.id.today_class_hour);
        }
    }
}
