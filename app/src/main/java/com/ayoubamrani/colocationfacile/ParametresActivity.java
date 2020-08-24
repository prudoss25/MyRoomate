package com.ayoubamrani.colocationfacile;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class ParametresActivity extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST=1;
    //
    ImageView updatedProfilPicture;
    EditText etUpdatedEmail;
    EditText etUpdatedPassword;
    //
    Button btnUpdate;
    Button btnUploadUpdatedProfilPicture;
    ProgressBar progressBar;
    //
    FirebaseStorage firebaseStorage;
    StorageReference storageReference;
    DatabaseReference databaseReference;
    DatabaseReference dbReference;
    //
    String currentUserEmail;
    String currentUserProfilPicture;
    String currentUserPassword;
    //
    Uri profilePictureUri;
    String currentUserKey;
    //
    String updatedEmail;
    String updatedProfilPictureUrl;
    String updatedPassword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parametres);
        //
        updatedProfilPicture=findViewById(R.id.updatedProfilPicture);
        etUpdatedEmail=findViewById(R.id.etUpdatedEmail);
        etUpdatedPassword=findViewById(R.id.etUpdatedPassword);
        progressBar=findViewById(R.id.uploadUpdatedPB);
        btnUpdate=findViewById(R.id.btnUpdate);
        btnUploadUpdatedProfilPicture=findViewById(R.id.btnUpdateProfilPicture);
        //
        databaseReference= FirebaseDatabase.getInstance().getReference("Utilisateur");
        firebaseStorage= FirebaseStorage.getInstance();
        storageReference= FirebaseStorage.getInstance().getReference("ProfilPictures");
        dbReference= FirebaseDatabase.getInstance().getReference("Offre");
        // Get the current user email

        String MyPREFERENCES = "MyPrefs" ;
        SharedPreferences prefs = getSharedPreferences(MyPREFERENCES , MODE_PRIVATE);
        currentUserEmail = prefs.getString("Email", null);
        currentUserPassword = prefs.getString("MotDePasse", null);
        currentUserProfilPicture = prefs.getString("UrlPhotoProfil", null);

        Glide.with(getApplicationContext()).load(currentUserProfilPicture).into(updatedProfilPicture);
        etUpdatedEmail.setText(currentUserEmail);
        etUpdatedPassword.setText(currentUserPassword);

        progressBar.setVisibility(View.GONE);

        // Get current user Key in the DB
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot user : dataSnapshot.getChildren())
                {
                    Utilisateur utilisateur=user.getValue(Utilisateur.class);
                    if(utilisateur.getEmail().compareTo(currentUserEmail)==0) currentUserKey=user.getKey();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(), databaseError.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });

        btnUploadUpdatedProfilPicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickPictureFromGallery();
            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateAccount();
            }
        });
    }

    private String getFileExtension(Uri uri)
    {
        ContentResolver contentResolver=getContentResolver();
        MimeTypeMap mimeTypeMap=MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }

    private void updateAccount() {
        if(profilePictureUri!=null)
        {
            progressBar.setVisibility(View.VISIBLE);

            StorageReference imageRef = firebaseStorage.getReferenceFromUrl(currentUserProfilPicture);

            imageRef.delete();

            StorageReference fileReference=storageReference.child(System.currentTimeMillis()+"."+ getFileExtension(profilePictureUri));

            fileReference.putFile(profilePictureUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Handler handler=new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    progressBar.setProgress(0);
                                }
                            },5000);

                            updatedProfilPictureUrl=taskSnapshot.getDownloadUrl().toString();
                            updatedEmail=etUpdatedEmail.getText().toString();
                            updatedPassword=etUpdatedPassword.getText().toString();

                            databaseReference.child(currentUserKey).child("photoProfilUrl").setValue(updatedProfilPictureUrl);
                            databaseReference.child(currentUserKey).child("motDePasse").setValue(updatedPassword);
                            databaseReference.child(currentUserKey).child("email").setValue(updatedEmail);

                            // Some housekeeping
                            SharedPreferences sharedpreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);

                            SharedPreferences.Editor editor = sharedpreferences.edit();

                            editor.putString("Email",updatedEmail);
                            editor.putString("UrlPhotoProfil",updatedProfilPictureUrl);
                            editor.putString("MotDePasse",updatedPassword);

                            editor.commit();

                            // Updating Offre table

                            dbReference.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    for(DataSnapshot offer : dataSnapshot.getChildren())
                                    {
                                        Offre offre=offer.getValue(Offre.class);
                                        if(offre.getIdOffreur().compareTo(currentUserEmail)==0)
                                        {
                                            String offerKey=offer.getKey();
                                            dbReference.child(offerKey).child("idOffreur").setValue(updatedEmail);
                                            dbReference.child(offerKey).child("photoOffreur").setValue(updatedProfilPictureUrl);
                                        }
                                    }
                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {
                                    Toast.makeText(getApplicationContext(), databaseError.getMessage(), Toast.LENGTH_SHORT).show();

                                }
                            });

                            Toast.makeText(getApplicationContext(), "Votre compte est mis à jour avec succès !", Toast.LENGTH_SHORT).show();
                            returnToMainActivity();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress=(100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                            progressBar.setProgress((int)progress);
                }
            });
        }
        else
        {
            Toast.makeText(getApplicationContext(), "Autre !", Toast.LENGTH_SHORT).show();
        }
    }

    private void returnToMainActivity() {
        Intent intent=new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
    }

    public void pickPictureFromGallery(){
        //Create an Intent with action as ACTION_PICK
        Intent intent=new Intent();
        // Sets the type as image/*. This ensures only components of type image are selected
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_PICK);
        //We pass an extra array with the accepted mime types. This will ensure only components with these MIME types as targeted.
        //String[] mimeTypes = {"image/jpeg", "image/png"};
        //intent.putExtra(Intent.EXTRA_MIME_TYPES,mimeTypes);
        // Launching the Intent
        startActivityForResult(intent,PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Result code is RESULT_OK only if the user selects an Image
        if (resultCode == RESULT_OK)
        {
            switch (requestCode){
                case PICK_IMAGE_REQUEST:
                    //data.getData return the content URI for the selected Image
                    profilePictureUri= data.getData();
                    Glide.with(this).load(profilePictureUri).into(updatedProfilPicture);
                    break;
            }
        }
    }
}
