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
import android.widget.TextView;

public class PaiementDialog extends AppCompatDialogFragment {
    private EditText etNomProprietaire;
    private EditText etNumeroCarte;
    private EditText etDateFinValidite;
    private EditText etCryptoCarte;
    private TextView tvMsgPaiement;
    //
    private PaiementDialogListener listener;
    //
    private String montantAPayer;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            listener= (PaiementDialogListener) context;
        } catch (Exception e) { }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        Bundle args = getArguments();
        montantAPayer = args.getString("montantAPayer");

        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());

        LayoutInflater layoutInflater=getActivity().getLayoutInflater();
        View view=layoutInflater.inflate(R.layout.layout_dialog_paiement, null);

        etNomProprietaire=view.findViewById(R.id.etNomProprietaire);
        etNumeroCarte=view.findViewById(R.id.etNumeroCarte);
        etDateFinValidite=view.findViewById(R.id.etDateFinValidite);
        etCryptoCarte=view.findViewById(R.id.etCryptoCarte);
        tvMsgPaiement=view.findViewById(R.id.tvMsgPaiement);

        tvMsgPaiement.setText(tvMsgPaiement.getText().toString()+" "+montantAPayer+" DH");


        builder.setView(view)
                .setTitle("Ecrivez un message au offreur")
                .setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setPositiveButton("Payer", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String nomProprietaire = etNomProprietaire.getText().toString();
                        String numeroCarte = etNumeroCarte.getText().toString();
                        String dateFinValidite = etDateFinValidite.getText().toString();
                        String cryptoCarte = etCryptoCarte.getText().toString();

                        listener.effectuerPaiement(nomProprietaire, numeroCarte, dateFinValidite, cryptoCarte);
                    }
                });


        return builder.create();
    }

    public interface PaiementDialogListener
    {
        void effectuerPaiement(String nomProprietaire, String numeroCarte, String dateFinValidite, String cryptoCarte);
    }
}

