package com.example.nitjsrstudenthelper.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.GridLayout;
import android.widget.Switch;
import android.widget.Toast;

import com.example.nitjsrstudenthelper.databinding.ActivityDashboardBinding;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.util.List;

public class DashboardActivity extends AppCompatActivity {

    private ActivityDashboardBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= ActivityDashboardBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        checkPermissions();
        manageClickEvent();
    }


    private void manageClickEvent() {
        for (int i = 0; i < binding.mainGrid.getChildCount(); i++) {
            CardView cardView = (CardView) binding.mainGrid.getChildAt(i);
            final int finalI = i;
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent= new Intent(DashboardActivity.this,ProfileActivity.class);

                    switch (finalI){
                        case 0: intent = new Intent(DashboardActivity.this,ProfileActivity.class); break;
                        case 1: intent = new Intent(DashboardActivity.this,DownloadsActivity.class); break;
                        case 2: intent = new Intent(DashboardActivity.this,NotesActivity.class); break;
                        case 3: intent = new Intent(DashboardActivity.this,PYQsActivity.class); break;
                        case 4: intent = new Intent(DashboardActivity.this,FacultyActivity.class); break;
                        case 5: intent = new Intent(DashboardActivity.this,AboutActivity.class); break;
                        case 6: intent = new Intent(DashboardActivity.this,SettingsActivity.class); break;
                        case 7: intent = new Intent(DashboardActivity.this,FAQSActivity.class); break;
                    }

                    startActivity(intent);
                }
            });
        }
    }

    private void checkPermissions() {
        Dexter.withContext(this).withPermissions(
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport multiplePermissionsReport) {
                        if(!multiplePermissionsReport.areAllPermissionsGranted()){
                            Toast.makeText(DashboardActivity.this, "Please allow permission", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> list, PermissionToken permissionToken) {

                    }
                }).check();
    }
}