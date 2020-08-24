package com.ayoubamrani.colocationfacile;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class OffreActivity extends AppCompatActivity implements DemandeDialog.DemandeDialogListener{

    Offre offreCourant;
    //
    String idOffre;
    //
    DatabaseReference databaseReference;
    DatabaseReference demandeReference;
    //
    ImageView ivPhotoOffre;
    TextView tvOffreur;
    TextView tvLocalisation;
    TextView tvType;
    TextView tvPrix;
    TextView tvDuree;
    TextView tvNbrDeColocs;
    TextView tvDescription;
    TextView tvAgeMin;
    TextView tvAgeMax;
    TextView tvColocFumeur;
    TextView tvColocEtudiantSeulement;
    TextView tvColocPossedeAnimaux;
    //
    Button btnDemanderOffre;
    //
    String emailDemandeur;
    String emailOffreur;
    String nomDemandeur;
    String idDemande;
    String dateDemande;
    String photoProfilDemandeur;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offre);

        Intent intent=getIntent();
        idOffre=intent.getStringExtra("idOffre");

        String MyPREFERENCES = "MyPrefs" ;
        SharedPreferences prefs = getSharedPreferences(MyPREFERENCES ,MODE_PRIVATE);
        emailDemandeur = prefs.getString("Email", null);
        nomDemandeur = prefs.getString("NomComplet", null);
        photoProfilDemandeur = prefs.getString("UrlPhotoProfil", null);

        offreCourant=new Offre();

        databaseReference= FirebaseDatabase.getInstance().getReference("Offre");
        demandeReference= FirebaseDatabase.getInstance().getReference("Demande");

        ivPhotoOffre=(ImageView)findViewById(R.id.ivPhotoOffre);
        tvOffreur=(TextView)findViewById(R.id.tvOffreur);
        tvLocalisation=(TextView)findViewById(R.id.tvLocalisation);
        tvType=(TextView)findViewById(R.id.tvType);
        tvPrix=(TextView)findViewById(R.id.tvPrix);
        tvDuree=(TextView)findViewById(R.id.tvDuree);
        tvNbrDeColocs=(TextView)findViewById(R.id.tvNbrColocs);
        tvDescription=(TextView)findViewById(R.id.tvDescription);
        tvAgeMin=(TextView)findViewById(R.id.tvAgeMin);
        tvAgeMax=(TextView)findViewById(R.id.tvAgeMax);
        tvColocFumeur=(TextView)findViewById(R.id.tvColocFumeur);
        tvColocPossedeAnimaux=(TextView)findViewById(R.id.tvColocPossedeAnimaux);
        tvColocEtudiantSeulement=(TextView)findViewById(R.id.tvColocEtudiantSeulement);
        btnDemanderOffre=(Button)findViewById(R.id.btnDemanderOffre);

        //
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for(DataSnapshot postSnapshot : dataSnapshot.getChildren())
                {
                    Offre offre=postSnapshot.getValue(Offre.class);
                    if(offre.getIdOffre().compareTo(idOffre)==0) offreCourant=offre;
                }

                Glide.with(getApplicationContext()).load(offreCourant.getPhotoOffreUrl()).into(ivPhotoOffre);

                emailOffreur=offreCourant.getIdOffreur();

                tvOffreur.setText(offreCourant.getNomOffreur());
                tvLocalisation.setText(offreCourant.getLocalisation());
                tvType.setText(offreCourant.getType());
                tvPrix.setText(Integer.toString(offreCourant.getPrix())+" DH");
                tvDuree.setText(Integer.toString(offreCourant.getDuree())+" mois");
                tvNbrDeColocs.setText(Integer.toString(offreCourant.getNbrDeColocs())+" colocs");
                tvDescription.setText(offreCourant.getDescription());
                tvAgeMin.setText(Integer.toString(offreCourant.getAgeMin()));
                tvAgeMax.setText(Integer.toString(offreCourant.getAgeMax()));
                tvColocFumeur.setText(offreCourant.isFumeurAccepte()?"Oui":"Non");
                tvColocEtudiantSeulement.setText(offreCourant.isEtudiantSeulement()?"Oui":"Non");
                tvColocPossedeAnimaux.setText(offreCourant.isAnimauxAcceptes()?"Oui":"Non");

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(),databaseError.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });

        btnDemanderOffre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog();
            }
        });
    }

    private void openDialog() {
        DemandeDialog demandeDialog=new DemandeDialog();
        demandeDialog.show(getSupportFragmentManager(),"Demande Dialog");
    }

    @Override
    public void envoyerDemande(String msgDemande) {

        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date date = new Date();
        dateDemande = dateFormat.format(date);

        idDemande = demandeReference.push().getKey();

        Demande demande = new Demande(emailDemandeur, emailOffreur, msgDemande, nomDemandeur, idDemande, idOffre, dateDemande, photoProfilDemandeur,false);

        demandeReference.child(idDemande).setValue(demande);

        Toast.makeText(getApplicationContext(), "Votre demande est envoyée avec succès !", Toast.LENGTH_SHORT).show();

        goToAccueil();


    }

    private void goToAccueil() {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
    }
}
