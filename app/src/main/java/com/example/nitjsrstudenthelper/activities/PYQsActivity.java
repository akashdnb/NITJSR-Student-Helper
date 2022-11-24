package com.example.nitjsrstudenthelper.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.nitjsrstudenthelper.databinding.ActivityPyqsBinding;

public class PYQsActivity extends AppCompatActivity {
    ActivityPyqsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= ActivityPyqsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}