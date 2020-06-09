package com.example.assignmentapplication;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.assignmentapplication.database.DBHelper;

import java.util.List;


public class TodayClassAdapter extends RecyclerView.Adapter<TodayClassAdapter.ViewHolder>{
    private List<timetable_model> data;
    private DBHelper helper;

    public TodayClassAdapter(List<timetable_model> data, DBHelper helper) {
        this.helper = helper;
        this.data = data;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.rec_today_class, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.name.setText(data.get(position).name);
        String hour = data.get(position).start_Hour+"-"+data.get(position).end_Hour;
        holder.hour.setText(hour);
        holder.location.setText(helper.subject_location(data.get(position).name));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        private TextView name, location, hour;
        private ViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.today_class_name);
            location = itemView.findViewById(R.id.today_class_location);
            hour = itemView.findViewById(R.id.today_class_hour);
        }
    }
}
