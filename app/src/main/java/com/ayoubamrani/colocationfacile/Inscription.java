package com.ayoubamrani.colocationfacile;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SwitchCompat;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.PicassoProvider;

import java.io.FileNotFoundException;

public class Inscription extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST=1;
    //
    EditText etNom;
    EditText etPrenom;
    Spinner spSexe;
    EditText etAge;
    EditText etEmail;
    EditText etMotDePasse;
    EditText etMotDePasseConfirme;
    SwitchCompat swEtudiant;
    SwitchCompat swFumeur;
    SwitchCompat swAnimaux;
    ProgressBar uploadProgressBar;
    //
    Button btnAddProfilePicture;
    Button btnInscrire;
    ImageView profilePicture;
    Uri profilePictureUri;
    // Base de données & Stockage Firebase
    StorageReference storageReference;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inscription);
        // Initialisation
        btnAddProfilePicture=findViewById(R.id.btnAddProfilePicture);
        btnInscrire=findViewById(R.id.btnSignUp);
        profilePicture=findViewById(R.id.profilePicture);
        //
        etNom=findViewById(R.id.etNom);
        etPrenom=findViewById(R.id.etPrenom);
        spSexe=findViewById(R.id.spSexe);
        etAge=findViewById(R.id.etAge);
        etEmail=findViewById(R.id.etUserEmail);
        etMotDePasse=findViewById(R.id.etUserPassword);
        etMotDePasseConfirme=findViewById(R.id.etConfirmedPassword);
        swEtudiant=findViewById(R.id.swEtudiant);
        swFumeur=findViewById(R.id.swFumeur);
        swAnimaux=findViewById(R.id.swAnimaux);
        uploadProgressBar=findViewById(R.id.uploadProgressBar);
        //
        storageReference= FirebaseStorage.getInstance().getReference("ProfilPictures");
        databaseReference = FirebaseDatabase.getInstance().getReference("Utilisateur");

        profilePicture.setVisibility(View.GONE);
        uploadProgressBar.setVisibility(View.GONE);


        btnAddProfilePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickPictureFromGallery();
            }
        });

        btnInscrire.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inscrireUtilisateur();
            }
        });

    }

    private String getFileExtension(Uri uri)
    {
        ContentResolver contentResolver=getContentResolver();
        MimeTypeMap mimeTypeMap=MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }

    private void inscrireUtilisateur() {
        if(profilePictureUri!=null)
        {
            uploadProgressBar.setVisibility(View.VISIBLE);

            StorageReference fileReference=storageReference.child(System.currentTimeMillis()+"."+ getFileExtension(profilePictureUri));

            fileReference.putFile(profilePictureUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Handler handler=new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    uploadProgressBar.setProgress(0);
                                }
                            },5000);

                            String motDePasse=etMotDePasse.getText().toString();
                            String motDePasseConfirme=etMotDePasseConfirme.getText().toString();
                            if(motDePasse.compareTo(motDePasseConfirme)==0)
                            {
                                Utilisateur utilisateur=new Utilisateur(etNom.getText().toString(),etPrenom.getText().toString(),
                                        spSexe.getSelectedItem().toString(), Integer.parseInt(etAge.getText().toString()),
                                        etEmail.getText().toString(),etMotDePasse.getText().toString(),
                                        swEtudiant.isChecked(),swFumeur.isChecked(), swAnimaux.isChecked(),
                                        taskSnapshot.getDownloadUrl().toString());

                                String userId=databaseReference.push().getKey();
                                databaseReference.child(userId).setValue(utilisateur);
                                Toast.makeText(getApplicationContext(), "T'es inscris avec succès !", Toast.LENGTH_SHORT).show();
                                returnToSignIn();
                            }else{Toast.makeText(getApplicationContext(), "Probleme !", Toast.LENGTH_SHORT).show(); }
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
                    uploadProgressBar.setProgress((int)progress);
                }
            });
        }
        else
        {
            Toast.makeText(getApplicationContext(), "Sélectionner une photo !", Toast.LENGTH_SHORT).show();
        }
    }

    private void returnToSignIn() {
        Intent intent=new Intent(getApplicationContext(), Authentification.class);
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
                    /*String[] filePathColumn = { MediaStore.Images.Media.DATA };
                    // Get the cursor
                    Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                    // Move to first row
                    cursor.moveToFirst();
                    //Get the column index of MediaStore.Images.Media.DATA
                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    //Gets the String value in the column
                    String imgDecodableString = cursor.getString(columnIndex);
                    cursor.close();
                    // Set the Image in ImageView after decoding the String
                    btnAddProfilePicture.setImageBitmap(Bitmap.createScaledBitmap(BitmapFactory.decodeFile(imgDecodableString), 700, 700, true));
                    */
                    //btnAddProfilePicture.setImageBitmap(decodeUri(getApplicationContext(), selectedImage, 200));
                    //show the profilePicture ImageView
                    profilePicture.setVisibility(View.VISIBLE);
                    Glide.with(this).load(profilePictureUri).into(profilePicture);
                    break;

            }
        }
    }

}
