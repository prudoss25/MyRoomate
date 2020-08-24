package com.ayoubamrani.colocationfacile;

import android.content.ContentResolver;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SwitchCompat;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
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

public class AjouterOffre extends AppCompatActivity {

    // Utilisateur courant
    String emailUtilisateurCourant;
    String photoUtilisateurCourant;
    String nomUtilisateurCourant;
    //
    private static final int PICK_IMAGE_REQUEST=1;
    //
    EditText etLocalisation;
    EditText etPrix;
    EditText etDuree;
    EditText etNbrDeColocs;
    EditText etDescription;
    EditText etAgeMin;
    EditText etAgeMax;
    Spinner spType;
    SwitchCompat swColocFumeur;
    SwitchCompat swColocEtudiantSeulement;
    SwitchCompat swColocPossedeAnimaux;
    ProgressBar uploadOfferProgressBar;
    //
    Button btnPosterOffre;
    Button btnAddOfferPicture;
    Uri offerPictureUri;
    ImageView ivOfferPicture;
    // Base de données & Stockage Firebase
    StorageReference storageReference;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajouter_offre);

        //
        String MyPREFERENCES = "MyPrefs" ;
        SharedPreferences prefs = getSharedPreferences(MyPREFERENCES , MODE_PRIVATE);
        emailUtilisateurCourant= prefs.getString("Email", null);
        nomUtilisateurCourant= prefs.getString("NomComplet", null);
        photoUtilisateurCourant= prefs.getString("UrlPhotoProfil", null);

        ivOfferPicture=findViewById(R.id.offerPicture);
        btnPosterOffre=findViewById(R.id.btnPosterOffre);
        btnAddOfferPicture=findViewById(R.id.btnAddOfferPicture);
        uploadOfferProgressBar=findViewById(R.id.uploadOfferProgressBar);
        //
        etLocalisation=findViewById(R.id.etLocalisation);
        spType=findViewById(R.id.spType);
        etPrix=findViewById(R.id.etPrix);
        etDuree=findViewById(R.id.etDuree);
        etNbrDeColocs=findViewById(R.id.etNbrDeColocs);
        etDescription=findViewById(R.id.etDescription);
        etAgeMin=findViewById(R.id.etAgeMin);
        etAgeMax=findViewById(R.id.etAgeMax);
        swColocFumeur=findViewById(R.id.swColocFumeur);
        swColocEtudiantSeulement=findViewById(R.id.swColocEtudiantSeulement);
        swColocPossedeAnimaux=findViewById(R.id.swColocPossedeAnimaux);
        //
        storageReference= FirebaseStorage.getInstance().getReference("HousesPictures");
        databaseReference = FirebaseDatabase.getInstance().getReference("Offre");

        ivOfferPicture.setVisibility(View.GONE);
        uploadOfferProgressBar.setVisibility(View.GONE);

        btnAddOfferPicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickPictureFromGallery();
            }
        });

        btnPosterOffre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                posterOffre();
            }
        });
    }

    private String getFileExtension(Uri uri)
    {
        ContentResolver contentResolver=getContentResolver();
        MimeTypeMap mimeTypeMap=MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }

    private void posterOffre() {
        if(offerPictureUri!=null)
        {
            uploadOfferProgressBar.setVisibility(View.VISIBLE);

            StorageReference fileReference=storageReference.child(System.currentTimeMillis()+"."+ getFileExtension(offerPictureUri));

            fileReference.putFile(offerPictureUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Handler handler=new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    uploadOfferProgressBar.setProgress(0);
                                }
                            },5000);


                            String offerId=databaseReference.push().getKey();
                            Offre offre=new Offre(offerId, emailUtilisateurCourant,etLocalisation.getText().toString(), spType.getSelectedItem().toString(),Integer.parseInt(etPrix.getText().toString()), Integer.parseInt(etDuree.getText().toString()),Integer.parseInt(etNbrDeColocs.getText().toString()), etDescription.getText().toString(),Integer.parseInt(etAgeMin.getText().toString()), Integer.parseInt(etAgeMax.getText().toString()), swColocFumeur.isChecked(), swColocPossedeAnimaux.isChecked(), swColocEtudiantSeulement.isChecked(), taskSnapshot.getDownloadUrl().toString(),nomUtilisateurCourant,photoUtilisateurCourant,false,false);

                            databaseReference.child(offerId).setValue(offre);
                            Toast.makeText(getApplicationContext(), "Offre ajoutée avec succès !", Toast.LENGTH_SHORT).show();
                            returnToAccueil();
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
                    uploadOfferProgressBar.setProgress((int)progress);
                }
            });
        }
        else
        {
            Toast.makeText(getApplicationContext(), "Sélectionner une photo !", Toast.LENGTH_SHORT).show();
}
    }

    private void returnToAccueil() {
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
                    offerPictureUri= data.getData();
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
                    ivOfferPicture.setVisibility(View.VISIBLE);
                    Glide.with(this).load(offerPictureUri).into(ivOfferPicture);
                    break;

            }
        }
    }
}
