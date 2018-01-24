package com.dell.sped;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileActivity extends AppCompatActivity {
    TextView textViewName;
    TextView textViewEmail;
    TextView textViewLogOut;
    private DatabaseReference databaseReference;
    private FirebaseUser firebaseUser;
    CircleImageView circleImageView;
    private static final int GALLERY_PICK = 1;
    private StorageReference mImageStorage;
    ProgressDialog progressDialog;
    Button buttonList;
    Button buttonMap;
    GPSTrack gpsTrack;

    @Override
    protected void onStart() {
        super.onStart();
        databaseReference.child("online").setValue("online");
        locChange();

    }

    @Override
    protected void onPause() {
        super.onPause();
        databaseReference.child("online").setValue("offline");
    }

    public void locChange(){
        gpsTrack = new GPSTrack(getApplicationContext());
        Location location = gpsTrack.getLocation();
        String lo = Double.toString(location.getLongitude());
        String lat = Double.toString(location.getLatitude());
        databaseReference.child("lo").setValue(lo);
        databaseReference.child("lat").setValue(lat);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        textViewEmail = (TextView)findViewById(R.id.textViewEmail);
        textViewName = (TextView)findViewById(R.id.textViewName);
        textViewLogOut = (TextView)findViewById(R.id.textViewLogOut);
        circleImageView = (CircleImageView)findViewById(R.id.imageViewProfile);
        buttonList = (Button)findViewById(R.id.buttonList);
        buttonMap = (Button)findViewById(R.id.buttonMap);

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        String current_uid = firebaseUser.getUid();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Users").child(current_uid);
        mImageStorage = FirebaseStorage.getInstance().getReference();
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String name = dataSnapshot.child("name").getValue().toString();
                String image = dataSnapshot.child("image").getValue().toString();
                String email = dataSnapshot.child("email").getValue().toString();
                textViewEmail.setText(email);
                textViewName.setText(name);
                Picasso.with(ProfileActivity.this).load(image).into(circleImageView);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        buttonMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProfileActivity.this,MapsActivity.class);
                startActivity(intent);
            }
        });

        buttonList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProfileActivity.this, UserListActivity.class);
                intent.putExtra("uid",firebaseUser.getUid());
                startActivity(intent);
            }
        });

        textViewLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(ProfileActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        circleImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CropImage.activity()
                        .setGuidelines(CropImageView.Guidelines.ON)
                        .setAspectRatio(1,1)
                        .start(ProfileActivity.this);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                progressDialog = new ProgressDialog(this);
                progressDialog.setTitle("Przesyłanie zdjęcia...");
                progressDialog.setMessage("Proszę czekać");
                progressDialog.setCanceledOnTouchOutside(false);
                progressDialog.show();
                Uri resultUri = result.getUri();
                StorageReference filepath = mImageStorage.child("profile_images").child(firebaseUser.getUid() + ".jpg");
                filepath.putFile(resultUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                        if(task.isSuccessful()){
                            String download_url = task.getResult().getDownloadUrl().toString();
                            databaseReference.child("image").setValue(download_url).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        progressDialog.dismiss();
                                    }
                                }
                            });
                        }else {
                            Toast.makeText(ProfileActivity.this,"Error",Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();

                        }
                    }
                });


            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }

    }


}
