package com.example.nitjsrstudenthelper.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nitjsrstudenthelper.models.NoteItem;

import java.util.List;

public class DownloadsItemAdapter extends RecyclerView.Adapter<DownloadsItemAdapter.viewHolder> {
    private Context context;
    private List<NoteItem> itemList;

    @NonNull
    @Override
    public DownloadsItemAdapter.viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull DownloadsItemAdapter.viewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        public viewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
