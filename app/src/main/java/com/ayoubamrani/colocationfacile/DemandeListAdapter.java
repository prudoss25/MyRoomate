package com.ayoubamrani.colocationfacile;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class DemandeListAdapter extends BaseAdapter {

    ArrayList<Demande> mDemandes;
    Context mContext;

    public DemandeListAdapter(Context unContext, ArrayList<Demande> lesDemandes){
        this.mDemandes=lesDemandes;
        this.mContext=unContext;
    }

    @Override
    public int getCount() {
        return mDemandes.size();
    }

    @Override
    public Object getItem(int position) {
        return mDemandes.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    static class ViewHolder{
        CircleImageView PhotoProfilDemandeur;
        TextView NomCompletDemandeur;
        TextView MessageDemandeur;
        TextView IdDemande;
        TextView DateDemande;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v=convertView;
        ViewHolder vHolder;
        if (v==null) {
            v = LayoutInflater.from(mContext).inflate(R.layout.demande_layout, parent, false);
            vHolder=new ViewHolder();

            vHolder.PhotoProfilDemandeur=(CircleImageView) v.findViewById(R.id.cvPhotoProfilDemandeur);
            vHolder.IdDemande=(TextView) v.findViewById(R.id.idDemande);
            vHolder.NomCompletDemandeur=(TextView) v.findViewById(R.id.tvNomCompletDemandeur);
            vHolder.MessageDemandeur=(TextView) v.findViewById(R.id.tvMessageDemandeur);
            vHolder.DateDemande=(TextView) v.findViewById(R.id.tvDateDemande);

            v.setTag(vHolder);
        }
        else { vHolder=(ViewHolder)v.getTag(); }

        Demande demandeCourante=mDemandes.get(position);


        TextView textView=vHolder.NomCompletDemandeur;
        textView.setText(demandeCourante.getNomDemandeur());

        String photoDemandeurUrl=demandeCourante.getPhotoProfilDemandeur();
        CircleImageView circleImageView=vHolder.PhotoProfilDemandeur;
        Glide.with(mContext).load(photoDemandeurUrl).into(circleImageView);

        TextView textView4=vHolder.IdDemande;
        textView4.setText(demandeCourante.getIdDemande());

        TextView textView5=vHolder.MessageDemandeur;
        textView5.setText(demandeCourante.getMessageDemandeur());

        TextView textView6=vHolder.DateDemande;
        textView6.setText("Envoyé le : "+demandeCourante.getDateDemande());

        return v;
    }
}
