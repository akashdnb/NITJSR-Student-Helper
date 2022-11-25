package com.example.nitjsrstudenthelper.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.example.nitjsrstudenthelper.adapters.NotesAdapter;
import com.example.nitjsrstudenthelper.databinding.ActivityPyqsBinding;
import com.example.nitjsrstudenthelper.models.ChildNoteItem;
import com.example.nitjsrstudenthelper.models.NoteItem;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class PYQsActivity extends AppCompatActivity {
    ActivityPyqsBinding binding;
    NotesAdapter adapter;
    DatabaseReference databaseReference, subReference;
    String branch=null, semester= null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= ActivityPyqsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        databaseReference= FirebaseDatabase.getInstance().getReference("uploads");
        subReference= FirebaseDatabase.getInstance().getReference("subjectList");

        createDialog();
    }


    void createDialog() {
        AlertDialog.Builder builderSingle = new AlertDialog.Builder(PYQsActivity.this);
        builderSingle.setTitle("Select branch:-");

        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(PYQsActivity.this, android.R.layout.select_dialog_singlechoice);
        arrayAdapter.add("CSE");
        arrayAdapter.add("ECE");
        arrayAdapter.add("EE");
        arrayAdapter.add("MECH");
        arrayAdapter.add("CIVIL");
        arrayAdapter.add("PI");

        builderSingle.setAdapter(arrayAdapter, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                branch = arrayAdapter.getItem(which);

                AlertDialog.Builder builderInner = new AlertDialog.Builder(PYQsActivity.this);
                builderInner.setTitle("Select Semester:-");
                final ArrayAdapter<String> semesterArrayList = new ArrayAdapter<String>(PYQsActivity.this, android.R.layout.select_dialog_singlechoice);
                semesterArrayList.add("1st Semester");
                semesterArrayList.add("2nd Semester");
                semesterArrayList.add("3rd Semester");
                semesterArrayList.add("4th Semester");
                semesterArrayList.add("5th Semester");
                semesterArrayList.add("6th Semester");
                semesterArrayList.add("7th Semester");
                semesterArrayList.add("8th Semester");

                builderInner.setAdapter(semesterArrayList, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        semester= semesterArrayList.getItem(which);
                        setupRecyclerView();
                        dialog.dismiss();
                    }
                });
                builderInner.show();
            }
        });
        builderSingle.show();
    }

    private void setupRecyclerView(){
        List<NoteItem> noteItemList= new ArrayList<>();
        List<ChildNoteItem> childNoteItemList = new ArrayList<>();

        databaseReference= databaseReference.child(branch).child(semester);

        subReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                try{
                    for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                        noteItemList.add(new NoteItem(dataSnapshot.getValue().toString(),branch,semester));
                    }
                    adapter= new NotesAdapter(PYQsActivity.this, noteItemList);
                    binding.pyqRv.setLayoutManager(new LinearLayoutManager(PYQsActivity.this));
                    binding.pyqRv.setHasFixedSize(true);
                    binding.pyqRv.setAdapter(adapter);
                }catch (Exception e){
                    Toast.makeText(PYQsActivity.this, "Something went wrong!!", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });



    }

}