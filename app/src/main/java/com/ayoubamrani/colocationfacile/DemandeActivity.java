package com.ayoubamrani.colocationfacile;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
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

import de.hdodenhof.circleimageview.CircleImageView;

public class DemandeActivity extends AppCompatActivity {

    Demande demandeCourante;
    Utilisateur demandeur;
    Offre offreConcernee;
    //
    DatabaseReference demandeReference;
    DatabaseReference offreReference;
    DatabaseReference utilisateurReference;
    DatabaseReference notificationReference;
    //
    String idDemande;
    String idOffre;
    String idDemandeur;
    // Champs Offre concernée
    CircleImageView cvOffreur;
    TextView tvNomOffreur;
    ImageView ivOffre;
    TextView tvTypeOffre;
    TextView tvLocalisationOffre;
    TextView tvPrixOffre;
    // Champs Demandeur
    ImageView ivDemandeur;
    TextView tvDemandeur;
    TextView tvAgeDemandeur;
    TextView tvEstDemandeurFumeur;
    TextView tvEstDemandeurPossedeAnimaux;
    TextView tvEstDemandeurEtudiant;
    TextView tvMsgDemandeur;
    //
    Button btnAccepterDemande;
    //
    LinearLayout offreConcerneeContainer;
    //
    String dateNotification;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demande);

        Intent intent=getIntent();
        idDemande=intent.getStringExtra("idDemande");


        demandeCourante=new Demande();
        demandeur=new Utilisateur();
        offreConcernee=new Offre();

        demandeReference= FirebaseDatabase.getInstance().getReference("Demande");
        offreReference= FirebaseDatabase.getInstance().getReference("Offre");
        utilisateurReference= FirebaseDatabase.getInstance().getReference("Utilisateur");
        notificationReference= FirebaseDatabase.getInstance().getReference("Notification");
        btnAccepterDemande=findViewById(R.id.btnAccepterDemande);

        // Initialisation des différents champs

        // Offre Courante
        offreConcerneeContainer=findViewById(R.id.offreContainer);
        cvOffreur=findViewById(R.id.cvPhotoProfilOffreurCourant);
        tvNomOffreur=findViewById(R.id.tvNomOffreur);
        ivOffre=findViewById(R.id.ivPhotoOffreConcernee);
        tvTypeOffre=findViewById(R.id.tvTypeLogementOffre);
        tvLocalisationOffre=findViewById(R.id.tvLocalisationLogementOffre);
        tvPrixOffre=findViewById(R.id.tvPrixLogementOffre);
        // Demandeur
        ivDemandeur=findViewById(R.id.ivProfilPictureDemandeur);
        tvDemandeur=findViewById(R.id.tvDemandeur);
        tvAgeDemandeur=findViewById(R.id.tvAgeDemandeur);
        tvEstDemandeurFumeur=findViewById(R.id.tvDemandeurFumeur);
        tvEstDemandeurPossedeAnimaux=findViewById(R.id.tvDemandeurPossedeAnimaux);
        tvEstDemandeurEtudiant=findViewById(R.id.tvDemandeurEstEtudiant);
        tvMsgDemandeur=findViewById(R.id.tvMsgDemandeur);


        //
        demandeReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot postSnapshot : dataSnapshot.getChildren())
                {
                    Demande demande=postSnapshot.getValue(Demande.class);
                    if(demande.getIdDemande().compareTo(idDemande)==0) demandeCourante=demande;
                }

                tvMsgDemandeur.setText(demandeCourante.getMessageDemandeur());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(),databaseError.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });

        //
        offreReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot postSnapshot : dataSnapshot.getChildren())
                {
                    Offre offre=postSnapshot.getValue(Offre.class);

                    if(offre.getIdOffre().compareTo(demandeCourante.getIdOffre())==0) offreConcernee=offre;
                }

                idOffre=offreConcernee.getIdOffre();

                Glide.with(getApplicationContext()).load(offreConcernee.getPhotoOffreur()).into(cvOffreur);
                Glide.with(getApplicationContext()).load(offreConcernee.getPhotoOffreUrl()).into(ivOffre);

                tvNomOffreur.setText(offreConcernee.getNomOffreur());
                tvTypeOffre.setText(offreConcernee.getType());
                tvPrixOffre.setText(Integer.toString(offreConcernee.getPrix()));
                tvLocalisationOffre.setText(offreConcernee.getLocalisation());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(),databaseError.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
        //
        utilisateurReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for(DataSnapshot postSnapshot : dataSnapshot.getChildren())
                {
                    Utilisateur utilisateur=postSnapshot.getValue(Utilisateur.class);
                    if(utilisateur.getEmail().compareTo(demandeCourante.getEmailDemandeur())==0) demandeur=utilisateur;
                }

                Glide.with(getApplicationContext()).load(demandeur.getPhotoProfilUrl()).into(ivDemandeur);

                tvDemandeur.setText(demandeur.getPrenom()+" "+demandeur.getNom());
                tvAgeDemandeur.setText(Integer.toString(demandeur.getAge())+" ans");
                tvEstDemandeurFumeur.setText(demandeur.isEstFumeur()?"Oui":"Non");
                tvEstDemandeurEtudiant.setText(demandeur.isEstEtudiant()?"Oui":"Non");
                tvEstDemandeurPossedeAnimaux.setText(demandeur.isPossedAnimaux()?"Oui":"Non");

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(),databaseError.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });

        offreConcerneeContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToOffre(idOffre);
            }
        });

        btnAccepterDemande.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                demandeReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for(DataSnapshot postSnapshot : dataSnapshot.getChildren())
                        {
                            Demande demande=postSnapshot.getValue(Demande.class);
                            String demandeKey=postSnapshot.getKey();

                            if(demande.getIdDemande().compareTo(idDemande)==0)
                            {
                                demandeReference.child(demandeKey).child("estAcceptee").setValue(true);
                                // Envoi du notification

                                DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                                Date date = new Date();
                                dateNotification = dateFormat.format(date);

                                String notificationKey = notificationReference.push().getKey();
                                String notificationKey1 = notificationReference.push().getKey();

                                Notification notification = new Notification(offreConcernee.getIdOffreur(), demandeur.getEmail(), "Félicitations "+offreConcernee.getNomOffreur()+" a accepté votre demande !", offreConcernee.getNomOffreur(), notificationKey, offreConcernee.getIdOffre(), dateNotification, offreConcernee.getPhotoOffreur(),idDemande);
                                Notification notification1 = new Notification(offreConcernee.getIdOffreur(), offreConcernee.getIdOffreur(), "Félicitations tu a accepté la demande de "+demandeCourante.getNomDemandeur()+" !", offreConcernee.getNomOffreur(),notificationKey1, offreConcernee.getIdOffre(), dateNotification, offreConcernee.getPhotoOffreur(), idDemande);
                                notificationReference.child(notificationKey).setValue(notification);
                                notificationReference.child(notificationKey1).setValue(notification1);

                                Toast.makeText(getApplicationContext(),"Vous avez accepté avec succès la demande de "+demandeur.getPrenom()+" "+demandeur.getNom()+" !"
                                        +" Une notification sera envoyée à lui !", Toast.LENGTH_LONG).show();
                            }
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Toast.makeText(getApplicationContext(),databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    private void goToOffre(String id) {
        Intent intent=new Intent(getApplicationContext(), OffreActivity.class);
        intent.putExtra("idOffre", id);
        startActivity(intent);
    }

}
