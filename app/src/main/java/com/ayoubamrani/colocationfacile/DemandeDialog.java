package com.ayoubamrani.colocationfacile;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

public class DemandeDialog extends AppCompatDialogFragment {
    private EditText etMessageDemande;
    private DemandeDialogListener listener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            listener= (DemandeDialogListener) context;
        } catch (Exception e) { }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());

        LayoutInflater layoutInflater=getActivity().getLayoutInflater();
        View view=layoutInflater.inflate(R.layout.layout_dialog_demande, null);

        builder.setView(view)
                .setTitle("Ecrivez un message au offreur")
                .setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setPositiveButton("Envoyer", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String msgDemande = etMessageDemande.getText().toString();
                        listener.envoyerDemande(msgDemande);
                    }
                });

        etMessageDemande=view.findViewById(R.id.etMessageAuOffreur);

        return builder.create();
    }

    public interface DemandeDialogListener
    {
        public void envoyerDemande(String msgDemande);
    }
}

