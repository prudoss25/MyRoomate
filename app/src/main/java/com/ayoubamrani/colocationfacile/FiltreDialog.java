package com.ayoubamrani.colocationfacile;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatDialogFragment;
import android.support.v7.widget.SwitchCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class FiltreDialog extends AppCompatDialogFragment {

    public interface FiltreDialogListener
    {
        void appliquerFiltre(final String localisation,final int prixMax,final int duree,final int nbrDeColocs,final int ageMin,final int ageMax,final String type,final boolean fumeur,final boolean etudiant,final boolean possedeAnimaux);
    }

    private EditText etLocalisationFiltre;
    private EditText etPrixFiltre;
    private EditText etDureeFiltre;
    private EditText etNbrColocsFiltre;
    private EditText etAgeMinFiltre;
    private EditText etAgeMaxFiltre;
    private Spinner spTypeFiltre;
    private SwitchCompat swFumeurFiltre;
    private SwitchCompat swEtudiantFiltre;
    private SwitchCompat swPossedeAnimauxFiltre;
    //
    private FiltreDialogListener listener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            listener= (FiltreDialogListener) context;
        } catch (Exception e) { }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener=null;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());

        LayoutInflater layoutInflater=getActivity().getLayoutInflater();
        View view=layoutInflater.inflate(R.layout.layout_dialog_filtre, null);

        etLocalisationFiltre=view.findViewById(R.id.etLocalisationFiltre);
        etPrixFiltre=view.findViewById(R.id.etPrixFiltre);
        etDureeFiltre=view.findViewById(R.id.etDureeFiltre);
        etNbrColocsFiltre=view.findViewById(R.id.etNbrDeColocsFiltre);
        etAgeMinFiltre=view.findViewById(R.id.etAgeMinFiltre);
        etAgeMaxFiltre=view.findViewById(R.id.etAgeMaxFiltre);
        swFumeurFiltre=view.findViewById(R.id.swColocFumeurFiltre);
        swEtudiantFiltre=view.findViewById(R.id.swColocEtudiantFiltre);
        swPossedeAnimauxFiltre=view.findViewById(R.id.swColocPossedeAnimauxFiltre);
        spTypeFiltre=view.findViewById(R.id.spTypeFiltre);

        builder.setView(view)
                .setTitle("Filtres")
                .setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setPositiveButton("Appliquer", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String localisation = etLocalisationFiltre.getText().toString();
                        int prixMax = Integer.parseInt(etPrixFiltre.getText().toString());
                        int duree = Integer.parseInt(etDureeFiltre.getText().toString());
                        int nbrDeColocs = Integer.parseInt(etNbrColocsFiltre.getText().toString());
                        int ageMin = Integer.parseInt(etAgeMinFiltre.getText().toString());
                        int ageMax = Integer.parseInt(etAgeMaxFiltre.getText().toString());
                        String type = spTypeFiltre.getSelectedItem().toString();
                        boolean fumeur = swFumeurFiltre.isChecked();
                        boolean etudiant = swEtudiantFiltre.isChecked();
                        boolean possedeAnimaux = swPossedeAnimauxFiltre.isChecked();

                        listener.appliquerFiltre(localisation,prixMax,duree,nbrDeColocs,ageMin,ageMax,type,fumeur,etudiant,possedeAnimaux);
                    }
                });

        return builder.create();
    }

}

