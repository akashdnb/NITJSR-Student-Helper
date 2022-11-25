package com.example.nitjsrstudenthelper.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;

import com.example.nitjsrstudenthelper.adapters.childNoteAdapter;
import com.example.nitjsrstudenthelper.databinding.ActivityDownloadsBinding;
import com.example.nitjsrstudenthelper.models.ChildNoteItem;

import java.util.ArrayList;
import java.util.List;

public class DownloadsActivity extends AppCompatActivity {

    ActivityDownloadsBinding binding;
    childNoteAdapter childNoteAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= ActivityDownloadsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setUpRecyclerView();
    }


    private void setUpRecyclerView(){
        List<ChildNoteItem> childNoteItemList = new ArrayList<>();
        childNoteItemList.add(new ChildNoteItem("Laplace Transform", "lt-1.pdf","243kb",null,null));
        childNoteItemList.add(new ChildNoteItem("Laplace Transform", "lt-1.pdf","243kb",null,null));
        childNoteItemList.add(new ChildNoteItem("Laplace Transform", "lt-1.pdf","243kb",null,null));
        childNoteItemList.add(new ChildNoteItem("Laplace Transform", "lt-1.pdf","243kb",null,null));
        childNoteItemList.add(new ChildNoteItem("Laplace Transform", "lt-1.pdf","243kb",null,null));

        childNoteAdapter =new childNoteAdapter(this,childNoteItemList);
        binding.downloadsRv.setLayoutManager(new LinearLayoutManager(this));
        binding.downloadsRv.setAdapter(childNoteAdapter);
    }
}