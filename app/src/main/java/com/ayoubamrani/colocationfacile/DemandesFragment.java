package com.ayoubamrani.colocationfacile;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
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

public class DemandesFragment extends Fragment {
    ArrayList<Demande> demandesArrayList;
    ListView lvDemandes;
    DemandeListAdapter demandesListAdapter;
    DatabaseReference databaseReference;
    String idUserCourant;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.demandes_fragment, container, false);
        
        databaseReference= FirebaseDatabase.getInstance().getReference("Demande");
        lvDemandes=view.findViewById(R.id.lvDemandes);
        demandesArrayList=new ArrayList<Demande>();

        String MyPREFERENCES = "MyPrefs" ;
        SharedPreferences prefs = getActivity().getSharedPreferences(MyPREFERENCES , getActivity().MODE_PRIVATE);
        idUserCourant = prefs.getString("Email", null);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot postSnapshot : dataSnapshot.getChildren())
                {
                    Demande demande=postSnapshot.getValue(Demande.class);
                    if(demande.getEmailOffreur().compareTo(idUserCourant)==0) demandesArrayList.add(demande);
                }

                demandesListAdapter=new DemandeListAdapter(getContext(), demandesArrayList);
                lvDemandes.setAdapter(demandesListAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getContext(),databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        lvDemandes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView tvIdDemande=(TextView) view.findViewById(R.id.idDemande);
                String idDemande=tvIdDemande.getText().toString();

                goToDemande(idDemande);
            }
        });

        return view;
    }

    private void goToDemande(String idDemande) {
        Intent intent=new Intent(getActivity().getBaseContext(), DemandeActivity.class);
        intent.putExtra("idDemande", idDemande);
        getActivity().startActivity(intent);
    }
}
