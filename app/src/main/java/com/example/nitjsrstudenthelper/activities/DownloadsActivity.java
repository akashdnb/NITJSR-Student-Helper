package com.example.nitjsrstudenthelper.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.nitjsrstudenthelper.databinding.ActivityDownloadsBinding;

public class DownloadsActivity extends AppCompatActivity {

    ActivityDownloadsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= ActivityDownloadsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}