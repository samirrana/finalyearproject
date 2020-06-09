package com.example.assignmentapplication;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class libraries_adapter extends RecyclerView.Adapter<libraries_adapter.ViewHolder>{
    private Context context; private List<library_source> list;

    public libraries_adapter(Context context, List<library_source> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.rec_open_source, parent, false));
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        library_source current = list.get(position);
        holder.name.setText(current.name);
        holder.name.setTag(current.website);
        holder.creator.setText("By "+ current.creator);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        TextView name, creator;
        ViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.library_name);
            creator = itemView.findViewById(R.id.library_creator);



        }
    }
}
