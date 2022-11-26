package com.example.nitjsrstudenthelper.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nitjsrstudenthelper.R;
import com.example.nitjsrstudenthelper.models.FacultyModel;

import java.util.List;

public class FacultyAdapter extends RecyclerView.Adapter<FacultyAdapter.viewHolder> {

    Context context;
    List<FacultyModel> list;

    public FacultyAdapter(Context context, List<FacultyModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public FacultyAdapter.viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.faculty_item, parent, false);
        return new FacultyAdapter.viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FacultyAdapter.viewHolder holder, int position) {
        holder.name.setText(list.get(position).getName());
        holder.department.setText(list.get(position).getDepartment());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        TextView name, department;
        public viewHolder(@NonNull View itemView) {
            super(itemView);

            name= itemView.findViewById(R.id.faculty_name_tv);
            department= itemView.findViewById(R.id.faculty_department_tv);
        }
    }
}
