package com.ayoubamrani.colocationfacile;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Authentification extends AppCompatActivity{

    EditText etEmail;
    EditText etPassword;
    Button btnSignIn;
    Button btnToSignUp;
    String email;
    String password;
    //
    DatabaseReference firebaseDatabase;
    //
    public static final String MyPREFERENCES = "MyPrefs" ;
    SharedPreferences sharedpreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authentification);

        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        // Initialisation
        etEmail=(EditText)findViewById(R.id.etEmail);
        etPassword=(EditText)findViewById(R.id.etPassword);
        btnSignIn=(Button)findViewById(R.id.btnSignIn);
        btnToSignUp=(Button)findViewById(R.id.btnToSignUp);
        //
        firebaseDatabase=FirebaseDatabase.getInstance().getReference("Utilisateur");

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email=etEmail.getText().toString();
                password=etPassword.getText().toString();

                firebaseDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for(DataSnapshot user : dataSnapshot.getChildren())
                        {
                            Utilisateur utilisateur=user.getValue(Utilisateur.class);
                            if(email.compareTo(utilisateur.getEmail())==0 && password.compareTo(utilisateur.getMotDePasse())==0)
                            {
                                Toast.makeText(getApplicationContext(), "Salut "+utilisateur.getPrenom()+" "+utilisateur.getNom()+"!", Toast.LENGTH_SHORT).show();

                                SharedPreferences.Editor editor = sharedpreferences.edit();

                                editor.putString("Email",email);
                                editor.putString("NomComplet",utilisateur.getPrenom()+" "+utilisateur.getNom());
                                editor.putString("UrlPhotoProfil",utilisateur.getPhotoProfilUrl());
                                editor.putString("MotDePasse",utilisateur.getMotDePasse());

                                editor.commit();

                                goToAccueil();
                            }
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Toast.makeText(getApplicationContext(), databaseError.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                });
            }
        });

        btnToSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(), Inscription.class);
                startActivity(intent);
            }
        });
    }

    private void goToAccueil() {
        Intent intent=new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
    }
}
