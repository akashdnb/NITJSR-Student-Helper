package com.example.nitjsrstudenthelper.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.nitjsrstudenthelper.R;
import com.example.nitjsrstudenthelper.databinding.ActivityProfileBinding;
import com.example.nitjsrstudenthelper.models.ChildNoteItem;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.Locale;
import java.util.Random;

public class ProfileActivity extends AppCompatActivity {
    ActivityProfileBinding binding;
    String email;
    private FirebaseAuth mAuth;
    GoogleSignInOptions gso;
    GoogleSignInClient googleSignInClient;
    DatabaseReference databaseReference;
    StorageReference storageReference;
    String branch=null, semester=null, subject=null, title=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= ActivityProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        mAuth=FirebaseAuth.getInstance();
        storageReference= FirebaseStorage.getInstance().getReference();
        databaseReference= FirebaseDatabase.getInstance().getReference("uploads");

        binding.uploadFiles.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createDialog();
            }
        });
        binding.signGoogleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signIn();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==101){
            Task<GoogleSignInAccount> task= GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account= task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account.getIdToken());
            }catch (Exception e){
                Toast.makeText(this, ""+e, Toast.LENGTH_SHORT).show();
            }
        }

        if(requestCode==111 && data!=null && data.getData()!=null){
            try {
                uploadFile(data.getData());
            }catch (Exception e){
                Log.d("bug", e.toString());
            }
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser!=null){
            binding.signGoogleBtn.setVisibility(View.GONE);
            binding.myRegistrationNoTv.setVisibility(View.VISIBLE);
            binding.pleaseLoginTv.setVisibility(View.GONE);
            binding.uploadFiles.setVisibility(View.VISIBLE);
            binding.myUploadsTv.setVisibility(View.VISIBLE);
            GoogleSignInAccount account= GoogleSignIn.getLastSignedInAccount(this);
            binding.myRegistrationNoTv.setText(account.getDisplayName());
            //Toast.makeText(this, ""+currentUser, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.profile_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()== R.id.log_out){
            AlertDialog.Builder builder = new AlertDialog.Builder(ProfileActivity.this);
            builder.setMessage("Do you want to sign out ?");
            builder.setPositiveButton("Log Out", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    mAuth.signOut();
                    gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                            .requestEmail()
                            .requestIdToken(getString(R.string.default_web_client_id))
                            .build();
                    googleSignInClient= GoogleSignIn.getClient(ProfileActivity.this,gso);
                    googleSignInClient.signOut();
                    Intent mIntent = getIntent();
                    startActivity(mIntent);
                    finish();

                }
            });
            builder.create().show();
        }
        return super.onOptionsItemSelected(item);
    }

    private void signIn() {
        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .requestIdToken(getString(R.string.default_web_client_id))
                .build();
        googleSignInClient= GoogleSignIn.getClient(this,gso);

        processLogin();
    }

    private void processLogin() {
        Intent signInIntent= googleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent,101);
    }

    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential= GoogleAuthProvider.getCredential(idToken,null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isComplete()){
                            FirebaseUser user= mAuth.getCurrentUser();
                            updateUser(user);
                        }else{
                            Toast.makeText(ProfileActivity.this, "Error in signing in !!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void updateUser(FirebaseUser user) {
        Intent mIntent = getIntent();
        startActivity(mIntent);
        finish();
    }

    private void manageUploads() {
        Intent intent= new Intent();
        intent.setType("application/pdf");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Choose pdf file..."), 111);
    }

    private void uploadFile(Uri data) {

        databaseReference= databaseReference.child(branch).child(semester);
        final ProgressDialog dialog= new ProgressDialog(this);
        dialog.setTitle("Uploading...");
        dialog.show();

        storageReference = storageReference.child("uploads/"+System.currentTimeMillis()+".pdf");
        storageReference.putFile(data)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Task<Uri> uriTask= taskSnapshot.getStorage().getDownloadUrl();
                        while(!uriTask.isComplete());
                        Uri uri= uriTask.getResult();

                        String key = getSaltString();
                        ChildNoteItem item= new ChildNoteItem(subject,title,"???kb",key,uri.toString());
                        //String key = databaseReference.push().getKey();
                        try {
                            databaseReference.child(subject).child(key).setValue(item);
                        }catch (Exception e){
                            Log.d("bug", e.toString());
                        }
                        dialog.dismiss();

                        Toast.makeText(ProfileActivity.this, "Uploaded", Toast.LENGTH_SHORT).show();
                    }
                }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                        //double progress =
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(ProfileActivity.this, "Failed \n"+e, Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                        Log.d("bug", e.toString());
                    }
                });
    }


    protected String getSaltString() {
        String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < 18) { // length of the random string.
            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
            salt.append(SALTCHARS.charAt(index));
        }
        String saltStr = salt.toString();
        return saltStr;
    }

    void createDialog() {
        AlertDialog.Builder builderSingle = new AlertDialog.Builder(ProfileActivity.this);
        builderSingle.setTitle("Select branch:-");

        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(ProfileActivity.this, android.R.layout.select_dialog_singlechoice);
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

                AlertDialog.Builder builderInner = new AlertDialog.Builder(ProfileActivity.this);
                builderInner.setTitle("Select Semester:-");
                final ArrayAdapter<String> semesterArrayList = new ArrayAdapter<String>(ProfileActivity.this, android.R.layout.select_dialog_singlechoice);
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
                        getData();
                        dialog.dismiss();
                    }
                });
                builderInner.show();
            }
        });
        builderSingle.show();
    }

    void getData(){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

        View promptView = LayoutInflater.from(this).inflate(R.layout.prompts, null);

        alertDialogBuilder.setView(promptView);

        final EditText subjectTv = (EditText) promptView.findViewById(R.id.subject_tv);
        final EditText titleTv = (EditText) promptView.findViewById(R.id.subject_tv);

        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        subject= subjectTv.getText().toString();
                        title= titleTv.getText().toString();
                        if(subject.length()==0 || title.length()==0){
                            Toast.makeText(ProfileActivity.this, "Enter subject and Title", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            manageUploads();
                        }
                    }
                })
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

        AlertDialog alertD = alertDialogBuilder.create();

        alertD.show();
    }

}