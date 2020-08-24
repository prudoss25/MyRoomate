package com.ayoubamrani.colocationfacile;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

import de.hdodenhof.circleimageview.CircleImageView;

public class NotificationActivity extends AppCompatActivity implements PaiementDialog.PaiementDialogListener{

    Notification notificationCourante;
    Offre offreConcernee;

    //
    DatabaseReference offreReference;
    DatabaseReference notificationReference;
    DatabaseReference demandeReference;
    String idOffre;
    String idNotification;
    // Champs Offre concernée
    CircleImageView cvOffreur;
    TextView tvNomOffreur;
    ImageView ivOffre;
    TextView tvTypeOffre;
    TextView tvLocalisationOffre;
    TextView tvPrixOffre;
    //
    LinearLayout offreConcerneeContainer;
    //
    Button btnAfficherOffreur;
    Button btnAfficherContactOffreur;
    //
    String typeUtilisateur;
    String nomDemandeur;
    String emailDemandeur;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        Intent intent=getIntent();
        idNotification=intent.getStringExtra("idNotification");


        notificationCourante=new Notification();
        offreConcernee=new Offre();

        notificationReference= FirebaseDatabase.getInstance().getReference("Notification");
        demandeReference= FirebaseDatabase.getInstance().getReference("Demande");
        offreReference= FirebaseDatabase.getInstance().getReference("Offre");

        btnAfficherOffreur=findViewById(R.id.btnAfficherOffreur);
        btnAfficherContactOffreur=findViewById(R.id.btnAfficherContactoffreur);


        typeUtilisateur = "";
        emailDemandeur = "";
        nomDemandeur = "";

        // Initialisation des différents champs

        // Offre Courante
        offreConcerneeContainer=findViewById(R.id.offreConcerneeContainer);
        cvOffreur=findViewById(R.id.cvPhotoProfilEmetteurCourant);
        tvNomOffreur=findViewById(R.id.tvNomEmetteurCourant);
        ivOffre=findViewById(R.id.ivPhotoOffreConcerneeCourante);
        tvTypeOffre=findViewById(R.id.tvTypeLogementOffreConcernee);
        tvLocalisationOffre=findViewById(R.id.tvLocalisationLogementOffreConcernee);
        tvPrixOffre=findViewById(R.id.tvPrixLogementOffreConcernee);
        //
        notificationReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot postSnapshot : dataSnapshot.getChildren())
                {
                    Notification notification=postSnapshot.getValue(Notification.class);
                    if(notification.getIdNotification().compareTo(idNotification)==0) notificationCourante=notification;
                }
                if(notificationCourante.getEmailDestinataire().compareTo(notificationCourante.getEmailEmetteur())==0) typeUtilisateur="demandeur";
                else typeUtilisateur="offreur";

                if(typeUtilisateur.compareTo("offreur")==0) btnAfficherOffreur.setText("Contacter l'offreur");
                else btnAfficherOffreur.setText("Contacter le demandeur");
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(),databaseError.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });


        //
        demandeReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot postSnapshot : dataSnapshot.getChildren())
                {
                    Demande demande = postSnapshot.getValue(Demande.class);
                    if(demande.getIdDemande().compareTo(notificationCourante.getIdDemande())==0)
                    {
                        nomDemandeur = demande.getNomDemandeur();
                        emailDemandeur = demande.getEmailDemandeur();
                    }
                }
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

                    if(offre.getIdOffre().compareTo(notificationCourante.getIdOffre())==0) offreConcernee=offre;
                }

                idOffre=offreConcernee.getIdOffre();

                Glide.with(getApplicationContext()).load(offreConcernee.getPhotoOffreur()).into(cvOffreur);
                Glide.with(getApplicationContext()).load(offreConcernee.getPhotoOffreUrl()).into(ivOffre);

                tvNomOffreur.setText(offreConcernee.getNomOffreur());
                tvTypeOffre.setText(offreConcernee.getType());
                tvPrixOffre.setText(Integer.toString(offreConcernee.getPrix()));
                tvLocalisationOffre.setText(offreConcernee.getLocalisation());

                if((typeUtilisateur.compareTo("offreur")==0 && offreConcernee.isDemandeurAPaye()==true)
                        || (typeUtilisateur.compareTo("demandeur")==0 && offreConcernee.isOffreurAPaye()==true))
                {
                    btnAfficherOffreur.setVisibility(View.GONE);
                }else
                {
                    btnAfficherContactOffreur.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(),databaseError.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });

        //
        offreConcerneeContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToOffre(idOffre);
            }
        });
        //
        btnAfficherOffreur.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openPaiementDialog();
            }
        });
        //
        btnAfficherContactOffreur.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openInfosContactDialog();
            }
        });
    }

    private void openPaiementDialog() {
        PaiementDialog paiementDialog = new PaiementDialog();

        double montantAPayer = 0.05 * offreConcernee.getPrix();

        Bundle args = new Bundle();
        args.putString("montantAPayer", Double.toString(montantAPayer));

        paiementDialog.setArguments(args);

        paiementDialog.show(getSupportFragmentManager(),"Paiement Dialog");

    }

    private void goToOffre(String id) {
        Intent intent=new Intent(getApplicationContext(), OffreActivity.class);
        intent.putExtra("idOffre", id);
        startActivity(intent);
    }

    @Override
    public void effectuerPaiement(String nomProprietaire, String numeroCarte, String dateFinValidite, String cryptoCarte) {
        /*
            Traitement de paiement
         */
        boolean resultatPaiement = true;

        if(resultatPaiement==true)
        {
            Toast.makeText(getApplicationContext(),"Votre paiement est effectué avec succès !", Toast.LENGTH_SHORT).show();
            btnAfficherContactOffreur.setVisibility(View.VISIBLE);

            offreReference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for(DataSnapshot postSnapshot : dataSnapshot.getChildren())
                    {
                        Offre offre=postSnapshot.getValue(Offre.class);
                        String offreKey = postSnapshot.getKey();

                        int x=0;

                        if(idOffre.compareTo(offre.getIdOffre())==0)
                        {
                            if(typeUtilisateur.compareTo("offreur")==0)
                            {
                                offreReference.child(offreKey).child("demandeurAPaye").setValue(true);
                            }else
                            {
                                offreReference.child(offreKey).child("offreurAPaye").setValue(true);
                            }

                        }

                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }else
        {
            Toast.makeText(getApplicationContext(),"Une erreur est produite lors du paiement ! Veuillez réessayer svp !", Toast.LENGTH_SHORT).show();
        }
    }

    private void openInfosContactDialog() {
        ContactInfosDialog contactInfosDialog = new ContactInfosDialog();


        Bundle args = new Bundle();
        args.putString("typeUtilisateur", typeUtilisateur);
        args.putString("nomComplet", typeUtilisateur.compareTo("offreur")==0?offreConcernee.getNomOffreur():nomDemandeur);
        args.putString("email", typeUtilisateur.compareTo("offreur")==0?offreConcernee.getIdOffreur():emailDemandeur);

        contactInfosDialog.setArguments(args);

        contactInfosDialog.show(getSupportFragmentManager(),"Contact Dialog");
    }
}
