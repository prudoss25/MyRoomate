package com.ayoubamrani.colocationfacile;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class OffreListAdapter extends BaseAdapter {

    ArrayList<Offre> mOffres;
    Context mContext;

    public OffreListAdapter(Context unContext, ArrayList<Offre> lesOffres){
        this.mOffres=lesOffres;
        this.mContext=unContext;
    }

    @Override
    public int getCount() {
        return mOffres.size();
    }

    @Override
    public Object getItem(int position) {
        return mOffres.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    static class ViewHolder{
        CircleImageView PhotoProfil;
        TextView NomComplet;
        ImageView PhotoLogement;
        TextView TypeLogement;
        TextView Localisation;
        TextView Prix;
        TextView IdOffre;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v=convertView;
        ViewHolder vHolder;
        if (v==null) {
            v = LayoutInflater.from(mContext).inflate(R.layout.offre_layout, parent, false);
            vHolder=new ViewHolder();

            vHolder.Localisation=(TextView)v.findViewById(R.id.tvLocalisationLogement);
            vHolder.NomComplet=(TextView)v.findViewById(R.id.nomComplet);
            vHolder.Prix=(TextView)v.findViewById(R.id.tvPrixLogement);
            vHolder.TypeLogement=(TextView)v.findViewById(R.id.tvTypeLogement);
            vHolder.PhotoLogement=(ImageView) v.findViewById(R.id.ivPhotoOffre);
            vHolder.PhotoProfil=(CircleImageView) v.findViewById(R.id.cvPhotoProfil);
            vHolder.IdOffre=(TextView) v.findViewById(R.id.idOffre);

            v.setTag(vHolder);
        }
        else { vHolder=(ViewHolder)v.getTag(); }

        Offre offreCourant=mOffres.get(position);

        String imageUrl=offreCourant.getPhotoOffreUrl();
        ImageView imageView=vHolder.PhotoLogement;
        //Picasso.get().load(imageUrl).into(imageView);
        Glide.with(mContext).load(imageUrl).into(imageView);


        TextView textView=vHolder.NomComplet;
        textView.setText(offreCourant.getNomOffreur());

        String photoOffreurUrl=offreCourant.getPhotoOffreur();
        CircleImageView circleImageView=vHolder.PhotoProfil;
        //Picasso.get().load(photoOffreurUrl).fit().into(circleImageView);
        Glide.with(mContext).load(photoOffreurUrl).into(circleImageView);


        TextView textView1=vHolder.TypeLogement;
        textView1.setText(offreCourant.getType());

        TextView textView2=vHolder.Localisation;
        textView2.setText(offreCourant.getLocalisation());

        TextView textView3=vHolder.Prix;
        textView3.setText(Integer.toString(offreCourant.getPrix())+"DH");

        TextView textView4=vHolder.IdOffre;
        textView4.setText(offreCourant.getIdOffre());

        return v;
    }
}
