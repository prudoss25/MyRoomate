package com.ayoubamrani.colocationfacile;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AccueilFragment extends Fragment{

    ArrayList<Offre> offreArrayList;
    ListView lvOffres;
    OffreListAdapter offreListAdapter;
    DatabaseReference databaseReference;
    String idUserCourant;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.accueil_fragment, container, false);

        setHasOptionsMenu(true);

        databaseReference= FirebaseDatabase.getInstance().getReference("Offre");
        lvOffres=view.findViewById(R.id.lvOffres);
        offreArrayList=new ArrayList<Offre>();

        String MyPREFERENCES = "MyPrefs" ;
        SharedPreferences prefs = getActivity().getSharedPreferences(MyPREFERENCES , getActivity().MODE_PRIVATE);
        idUserCourant = prefs.getString("Email", null);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot postSnapshot : dataSnapshot.getChildren())
                {
                    Offre offre=postSnapshot.getValue(Offre.class);
                    offreArrayList.add(offre);
                }

                offreListAdapter=new OffreListAdapter(getContext(), offreArrayList);
                lvOffres.setAdapter(offreListAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getContext(),databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        lvOffres.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView tvIdOffre=(TextView) view.findViewById(R.id.idOffre);
                String idOffre=tvIdOffre.getText().toString();

                goToOffer(idOffre);
            }
        });

        return view;
    }

    private void goToOffer(String idOffre) {
        Intent intent=new Intent(getActivity().getBaseContext(), OffreActivity.class);
        intent.putExtra("idOffre", idOffre);
        getActivity().startActivity(intent);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.search:
                openDialog();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void openDialog() {
        FiltreDialog filtreDialog=new FiltreDialog();
        filtreDialog.show(getActivity().getSupportFragmentManager(),"Filtre Dialog");
    }

    public void appliquerFiltre(final String localisation, final int prixMax,final int duree, final int nbrDeColocs, final int ageMin, final int ageMax, final String type, final boolean fumeur, final boolean etudiant, final boolean possedeAnimaux) {

        offreArrayList.clear();
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot postSnapshot : dataSnapshot.getChildren())
                {
                    Offre offre=postSnapshot.getValue(Offre.class);

                    if((offre.getLocalisation().toLowerCase().indexOf(localisation.trim().toLowerCase())!=-1 || localisation.trim().compareTo("")==0 )
                            && (offre.getPrix()<=prixMax || prixMax==0)
                            && (offre.getAgeMin()>=ageMin || ageMin==0)
                            && (offre.getAgeMax()<=ageMax || ageMax==0)
                            && (offre.getNbrDeColocs()==nbrDeColocs || nbrDeColocs==0)
                            && (offre.getDuree()==duree || duree==0)
                            && (offre.isFumeurAccepte()==fumeur || fumeur==false)
                            && (offre.isAnimauxAcceptes()==possedeAnimaux || possedeAnimaux==false)
                            && (offre.isEtudiantSeulement()==etudiant || etudiant==false)
                            && (offre.getType()==type || type.compareTo("Appartement")==0) )
                    {
                        offreArrayList.add(offre);
                    }
                }
                offreListAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getContext(),databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}
