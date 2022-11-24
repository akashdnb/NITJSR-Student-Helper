package com.example.nitjsrstudenthelper.activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.example.nitjsrstudenthelper.adapters.NotesAdapter;
import com.example.nitjsrstudenthelper.databinding.ActivityNotesBinding;
import com.example.nitjsrstudenthelper.models.ChildNoteItem;
import com.example.nitjsrstudenthelper.models.NoteItem;

import java.util.ArrayList;
import java.util.List;

public class NotesActivity extends AppCompatActivity {

    ActivityNotesBinding binding;
    NotesAdapter adapter;
    String branch=null ,semester=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= ActivityNotesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        createDialog();

        setupRecyclerView();

    }




    void createDialog() {
        AlertDialog.Builder builderSingle = new AlertDialog.Builder(NotesActivity.this);
        builderSingle.setTitle("Select branch:-");

        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(NotesActivity.this, android.R.layout.select_dialog_singlechoice);
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

                AlertDialog.Builder builderInner = new AlertDialog.Builder(NotesActivity.this);
                builderInner.setTitle("Select Semester:-");
                final ArrayAdapter<String> semesterArrayList = new ArrayAdapter<String>(NotesActivity.this, android.R.layout.select_dialog_singlechoice);
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
                        fetchData();
                        dialog.dismiss();
                    }
                });
                builderInner.show();
            }
        });
        builderSingle.show();
    }

    private void fetchData() {

    }

    private void setupRecyclerView(){
        List<NoteItem> noteItemList= new ArrayList<>();
        List<ChildNoteItem> childNoteItemList = new ArrayList<>();

        childNoteItemList.add(new ChildNoteItem("Maths", "mynotes.pdf","120kb",null,null));
        childNoteItemList.add(new ChildNoteItem("Phy", "mynotes1.pdf","440kb",null,null));
        childNoteItemList.add(new ChildNoteItem("Maths", "mynotes.pdf","120kb",null,null));
        childNoteItemList.add(new ChildNoteItem("Maths", "mynotes.pdf","120kb",null,null));

        NoteItem item1= new NoteItem("Maths", childNoteItemList);

        noteItemList.add(item1);
        noteItemList.add(item1);
        noteItemList.add(item1);
        noteItemList.add(item1);


        adapter= new NotesAdapter((Context) this, noteItemList);
        binding.notesRv.setLayoutManager(new LinearLayoutManager(this));
        binding.notesRv.setHasFixedSize(true);

        binding.notesRv.setAdapter(adapter);

    }
}