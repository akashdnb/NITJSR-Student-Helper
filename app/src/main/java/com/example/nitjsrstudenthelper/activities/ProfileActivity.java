package com.example.nitjsrstudenthelper.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= ActivityProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        mAuth=FirebaseAuth.getInstance();

        binding.uploadFiles.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                manageUploads();
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
        storageReference= FirebaseStorage.getInstance().getReference();
        databaseReference= FirebaseDatabase.getInstance().getReference("uploads");

        Intent intent= new Intent();
        intent.setType("application/pdf");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Choose pdf file..."), 111);
    }

    private void uploadFile(Uri data) {
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
                        ChildNoteItem item= new ChildNoteItem("myPdf","Pdf","123kb",key,uri.toString());
                        //String key = databaseReference.push().getKey();
                        try {
                            databaseReference.child(key).setValue(item);
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
}