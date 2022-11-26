package com.example.nitjsrstudenthelper.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;

import com.example.nitjsrstudenthelper.adapters.DownloadsItemAdapter;
import com.example.nitjsrstudenthelper.adapters.childNoteAdapter;
import com.example.nitjsrstudenthelper.databinding.ActivityDownloadsBinding;
import com.example.nitjsrstudenthelper.models.ChildNoteItem;
import com.example.nitjsrstudenthelper.utils.pathUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class DownloadsActivity extends AppCompatActivity {

    ActivityDownloadsBinding binding;
    public DownloadsItemAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= ActivityDownloadsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setUpRecyclerView();
    }


    public void setUpRecyclerView(){
        List<ChildNoteItem> childNoteItemList = new ArrayList<>();
        File directory = new File(String.valueOf(pathUtil.rootDir));
        File[] files = directory.listFiles();
        for (int i = 0; i < files.length; i++)
        {
            childNoteItemList.add(new ChildNoteItem("Laplace Transform", files[i].getName(),"243kb",null,files[i].getAbsolutePath()));
        }

        adapter =new DownloadsItemAdapter(this,childNoteItemList);
        binding.downloadsRv.setLayoutManager(new LinearLayoutManager(this));
        binding.downloadsRv.setAdapter(adapter);

    }
}