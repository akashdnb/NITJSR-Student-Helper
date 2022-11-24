package com.example.nitjsrstudenthelper.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.nitjsrstudenthelper.databinding.ActivityFaqsactivityBinding;

public class FAQSActivity extends AppCompatActivity {

    ActivityFaqsactivityBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= ActivityFaqsactivityBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}