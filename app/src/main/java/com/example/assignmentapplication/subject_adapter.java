package com.example.assignmentapplication;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.example.assignmentapplication.database.DBHelper;

import java.util.List;


public class subject_adapter extends RecyclerView.Adapter<subject_adapter.ViewHolder>{
    public Context context; private List<subject_model> list;
    private MaterialDialog.Builder mdb; private DBHelper database;

    public subject_adapter(Context context, List<subject_model> list) {
        this.context = context;
        this.list = list;
        database = new DBHelper(context);
        mdb = new MaterialDialog.Builder(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.rec_subject, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        subject_model current = list.get(position);
        holder.name.setText(current.name);
        holder.name.setTag(current.note);
        holder.room.setText(current.room);
        holder.room.setTag(current.id);
        holder.teacher.setText(current.teacher);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

//    public subject_model getItem(int position){
//        return list.get(position);
//    }

    private void removeItem(int position){
        list.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, list.size());
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        TextView name, room, teacher; CardView cd;
        ViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.rec_subject_name);
            room = itemView.findViewById(R.id.rec_subject_room);
            teacher = itemView.findViewById(R.id.rec_subject_teacher);
            cd = itemView.findViewById(R.id.root_rec_subject);


            @SuppressLint("InflateParams") final View v = LayoutInflater.from(context).inflate(R.layout.d_subjects, null);
            cd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final int index = getAdapterPosition();
                    final subject_model current = list.get(index);
                    ((TextView)v.findViewById(R.id.d_subject_note)).setText(current.note);
                    ((TextView)v.findViewById(R.id.d_subject_room)).setText(current.room);
                    ((TextView)v.findViewById(R.id.d_subject_teacher)).setText(current.teacher);
                    mdb.title(name.getText().toString()).customView(v, true)
                            .negativeText("Delete").positiveText("Edit").neutralText("Close").autoDismiss(true);

                    mdb.onPositive(new MaterialDialog.SingleButtonCallback() {
                        @Override
                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                            Intent edit = new Intent(context, add_subject.class);
                            edit.putExtra("RequestCode", "12");
                            edit.putExtra("name", current.name);
                            edit.putExtra("room", current.room);
                            edit.putExtra("teacher", current.teacher);
                            edit.putExtra("note", current.note);
                            edit.putExtra("id", current.id);
                            edit.putExtra("index", String.valueOf(index));
                            ((Activity)context).startActivityForResult(edit, 12);
                        }
                    }).onNegative(new MaterialDialog.SingleButtonCallback() {
                        @Override
                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                            //TODO[Confirmation] Ask confirmation when delete
                            //That All the timetable for scheduled subject will also be deleted
                            if(database.deleteSubject(Integer.valueOf(current.id))){
                                subject_adapter.this.removeItem(index);
                            }else{
                                Toast.makeText(context, "Delete subject Failed!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }).onNeutral(new MaterialDialog.SingleButtonCallback() {
                        @Override
                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                            dialog.dismiss();
                        }
                    });

                    mdb.build().show();
                }
            });
        }
    }
}
