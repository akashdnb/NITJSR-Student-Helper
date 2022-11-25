package com.example.nitjsrstudenthelper.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nitjsrstudenthelper.R;
import com.example.nitjsrstudenthelper.activities.DownloadsActivity;
import com.example.nitjsrstudenthelper.models.ChildNoteItem;
import com.example.nitjsrstudenthelper.models.NoteItem;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
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
                List<ChildNoteItem> childNoteItemList= new ArrayList<>();
                DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference("uploads")
                                .child(itemList.get(position).getBranch())
                                .child(itemList.get(position).getSemester())
                                .child(itemList.get(position).getTitle());

                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for(DataSnapshot dataSnapshot: snapshot.getChildren()){
                            try {
                                childNoteItemList.add(dataSnapshot.getValue(ChildNoteItem.class));
                            }catch (Exception e){
                                Log.d("bug", e+"");
                            }
                            childNoteAdapter adapter= new childNoteAdapter(context, childNoteItemList);
                            holder.recyclerView.setLayoutManager(new LinearLayoutManager(context));
                            holder.recyclerView.setAdapter(adapter);
                            handleClick(position, holder.recyclerView, holder.seperetorView);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
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
        if(seperetorView.getVisibility()!=View.VISIBLE && recyclerView.getVisibility()!=View.VISIBLE){
            seperetorView.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.VISIBLE);
        }else{
            seperetorView.setVisibility(View.INVISIBLE);
            recyclerView.setVisibility(View.GONE);
        }
    }
}
