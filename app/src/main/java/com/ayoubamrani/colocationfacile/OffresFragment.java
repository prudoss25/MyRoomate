package com.ayoubamrani.colocationfacile;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
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

public class OffresFragment extends Fragment {

    FloatingActionButton btnAjouterOffre;

    ArrayList<Offre> offreArrayList;
    ListView lvOffres;
    OffreListAdapter offreListAdapter;
    DatabaseReference databaseReference;
    String idUserCourant;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.offres_fragment, container, false);

        databaseReference= FirebaseDatabase.getInstance().getReference("Offre");
        lvOffres=view.findViewById(R.id.lvOffresUserCourant);
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
                    if(idUserCourant.compareTo(offre.getIdOffreur())==0) offreArrayList.add(offre);
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

        btnAjouterOffre= view.findViewById(R.id.btnAddOffer);

        btnAjouterOffre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToAddOffer();
            }
        });

        return view;
    }

    public void goToAddOffer()
    {
        Intent intent=new Intent(getContext(), AjouterOffre.class);
        startActivity(intent);
    }

    private void goToOffer(String idOffre) {
        Intent intent=new Intent(getActivity().getBaseContext(), OffreActivity.class);
        intent.putExtra("idOffre", idOffre);
        getActivity().startActivity(intent);
    }
}
