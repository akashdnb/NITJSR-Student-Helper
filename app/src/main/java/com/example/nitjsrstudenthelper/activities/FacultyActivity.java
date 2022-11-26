package com.example.nitjsrstudenthelper.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;

import com.example.nitjsrstudenthelper.adapters.FacultyAdapter;
import com.example.nitjsrstudenthelper.databinding.ActivityFacultyBinding;
import com.example.nitjsrstudenthelper.models.FacultyModel;

import java.util.ArrayList;
import java.util.List;

public class FacultyActivity extends AppCompatActivity {
    ActivityFacultyBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= ActivityFacultyBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setUpRecyclerView();
    }

    private void setUpRecyclerView() {
        List<FacultyModel> facultyModelList= new ArrayList<>();
        facultyModelList.add(new FacultyModel("Om Hari Gupta","Electrical Department"));
        facultyModelList.add(new FacultyModel("Jitender Kuamr","Electrical Department"));
        facultyModelList.add(new FacultyModel("U K sinha","Electrical Department"));
        facultyModelList.add(new FacultyModel("Om Hari Gupta","Electrical Department"));
        facultyModelList.add(new FacultyModel("Om Hari Gupta","Electrical Department"));
        facultyModelList.add(new FacultyModel("Om Hari Gupta","Electrical Department"));
        facultyModelList.add(new FacultyModel("Om Hari Gupta","Electrical Department"));
        facultyModelList.add(new FacultyModel("Om Hari Gupta","Electrical Department"));

        FacultyAdapter adapter= new FacultyAdapter(this, facultyModelList);
        binding.facultyRv.setLayoutManager(new LinearLayoutManager(this));
        binding.facultyRv.setAdapter(adapter);


    }
}