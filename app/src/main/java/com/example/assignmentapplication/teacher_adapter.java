package com.example.assignmentapplication;

import android.annotation.SuppressLint;
import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.example.assignmentapplication.database.DBHelper;

import java.util.List;


public class teacher_adapter extends RecyclerView.Adapter<teacher_adapter.ViewHolder>{
    public Context context; private List<teacher_model> list;
    private MaterialDialog.Builder mdb, mad; private DBHelper database;

    public teacher_adapter(Context context, List<teacher_model> model) {
        this.context = context;
        this.list = model;
        database = new DBHelper(context);
        mdb = new MaterialDialog.Builder(context);
        mad = new MaterialDialog.Builder(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.rec_teacher, parent , false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        teacher_model current = list.get(position);
        holder.name.setText(current.name);
        holder.name.setTag(current.id);
        holder.phone.setText(current.phone);
        holder.initial.setText(current.name.substring(0, 1));
    }

    private teacher_model getItem(int position){
        return list.get(position);
    }

    private void removeItem(int position){
        list.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, list.size());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        LinearLayout layout;
        TextView name, phone, initial;
        ViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.rec_teacher_name);
            phone = itemView.findViewById(R.id.rec_teacher_phone);
            initial = itemView.findViewById(R.id.rec_teacher_initial);
            layout = itemView.findViewById(R.id.root_rec_teacher);
            @SuppressLint("InflateParams") final View v = LayoutInflater.from(context).inflate(R.layout.d_teacher, null);

            layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final int index = getAdapterPosition();
                    final teacher_model current = getItem(index);
                    ((TextView)v.findViewById(R.id.d_teacher_name)).setText(current.name);
                    ((TextView)v.findViewById(R.id.d_teacher_phone)).setText(current.phone);
                    mdb.title("Teacher's Data").customView(v, true)
                            .negativeText("Delete").positiveText("Edit").neutralText("Close").autoDismiss(true);

                    mdb.onPositive(new MaterialDialog.SingleButtonCallback() {
                        @Override
                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                            initMad(new String[]{current.id, current.name, current.phone}, index);
                        }
                    }).onNegative(new MaterialDialog.SingleButtonCallback() {
                        @Override
                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                            if(database.deleteTeacher(Integer.valueOf(current.id))){
                                removeItem(index);
                            }else{
                                Toast.makeText(context, "Delete Teacher's data failed!", Toast.LENGTH_SHORT).show();
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

        private void initMad(final String[] some, final int index) {
            @SuppressLint("InflateParams") final View add = LayoutInflater.from(context).inflate(R.layout.d_add_teacher, null);
            final EditText input_name = add.findViewById(R.id.input_name_teacher);
            final EditText input_phone = add.findViewById(R.id.input_phone_teacher);
            input_name.setText(some[1]); input_phone.setText(some[2]);
            mad.title("Edit Teacher's Data").customView(add, true)
                    .negativeText("Cancel").positiveText("Edit").autoDismiss(true)
                    .onPositive(new MaterialDialog.SingleButtonCallback() {
                        @Override
                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                            teacher_model newest = new teacher_model(input_name.getText().toString(), input_phone.getText().toString());
                            newest.setId(some[0]);
                            if(database.update_teacher(newest)){
                                list.set(index, newest); notifyDataSetChanged();
                            }else {
                                Toast.makeText(context, "Update Teacher's data Failed!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
            mad.build().show();
        }
    }
}
