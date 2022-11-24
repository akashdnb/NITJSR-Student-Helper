package com.example.nitjsrstudenthelper.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nitjsrstudenthelper.R;
import com.example.nitjsrstudenthelper.models.NoteItem;

import java.util.List;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.viewHolder> {

    private Context context;
    private List<NoteItem> itemList;

    public NotesAdapter(Context context, List<NoteItem> itemList) {
        this.context = context;
        this.itemList = itemList;
    }

    @NonNull
    @Override
    public NotesAdapter.viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.notes_item, parent, false);
        return new NotesAdapter.viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NotesAdapter.viewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.title.setText(itemList.get(position).getTitle());
        holder.title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(context, "Clicked "+position, Toast.LENGTH_SHORT).show();
                handleClick(position, holder.recyclerView, holder.seperetorView);
            }
        });
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        TextView title;
        RecyclerView recyclerView;
        View seperetorView;
        public viewHolder(@NonNull View itemView) {
            super(itemView);
            title= itemView.findViewById(R.id.parent_item_title);
            recyclerView= itemView.findViewById(R.id.child_rv);
            seperetorView= itemView.findViewById(R.id.seperator_view);
        }
    }

    void handleClick(int position, RecyclerView recyclerView, View seperetorView){
        childNoteAdapter adapter= new childNoteAdapter(context, itemList.get(position).getChildItemList());
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(adapter);

        if(seperetorView.getVisibility()!=View.VISIBLE && recyclerView.getVisibility()!=View.VISIBLE){
            seperetorView.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.VISIBLE);
        }else{
            seperetorView.setVisibility(View.INVISIBLE);
            recyclerView.setVisibility(View.GONE);
        }
    }
}
