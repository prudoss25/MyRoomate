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

public class ContactInfosDialog extends AppCompatDialogFragment {
    String typeUtilisateur;
    String nomComplet;
    String email;
    //
    private TextView tvNomComplet;
    private TextView tvEmail;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        Bundle args = getArguments();
        typeUtilisateur = args.getString("typeUtilisateur");
        nomComplet = args.getString("nomComplet");
        email = args.getString("email");
        String dialogTitle="";

        if(typeUtilisateur.compareTo("offreur")==0) dialogTitle="Contact de l'offreur";
        else dialogTitle="Contact du demandeur";

        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());

        LayoutInflater layoutInflater=getActivity().getLayoutInflater();
        View view=layoutInflater.inflate(R.layout.layout_dialog_contact_infos, null);

        tvNomComplet = view.findViewById(R.id.nomCompletD);
        tvEmail = view.findViewById(R.id.emailD);

        tvNomComplet.setText(nomComplet);
        tvEmail.setText(email);

        builder.setView(view)
                .setTitle(dialogTitle)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

        return builder.create();
    }

}

