package com.example.nitjsrstudenthelper.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.nitjsrstudenthelper.databinding.ActivityFacultyBinding;

public class FacultyActivity extends AppCompatActivity {
    ActivityFacultyBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= ActivityFacultyBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}