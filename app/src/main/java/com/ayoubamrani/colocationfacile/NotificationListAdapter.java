package com.ayoubamrani.colocationfacile;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class NotificationListAdapter extends BaseAdapter {

    ArrayList<Notification> mNotifications;
    Context mContext;

    public NotificationListAdapter(Context unContext, ArrayList<Notification> lesNotifications){
        this.mNotifications=lesNotifications;
        this.mContext=unContext;
    }

    @Override
    public int getCount() {
        return mNotifications.size();
    }

    @Override
    public Object getItem(int position) {
        return mNotifications.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    static class ViewHolder{
        CircleImageView PhotoProfilEmetteur;
        TextView NomCompletEmetteur;
        TextView MessageNotification;
        TextView IdNotification;
        TextView DateNotification;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v=convertView;
        ViewHolder vHolder;
        if (v==null) {
            v = LayoutInflater.from(mContext).inflate(R.layout.notification_layout, parent, false);
            vHolder=new ViewHolder();

            vHolder.PhotoProfilEmetteur=(CircleImageView) v.findViewById(R.id.cvPhotoProfilEmetteur);
            vHolder.IdNotification=(TextView) v.findViewById(R.id.idNotification);
            vHolder.NomCompletEmetteur=(TextView) v.findViewById(R.id.tvNomCompletEmetteur);
            vHolder.MessageNotification=(TextView) v.findViewById(R.id.tvMsgNotification);
            vHolder.DateNotification=(TextView) v.findViewById(R.id.tvDateNotification);

            v.setTag(vHolder);
        }
        else { vHolder=(ViewHolder)v.getTag(); }

        Notification notificationCourante=mNotifications.get(position);


        TextView textView=vHolder.NomCompletEmetteur;
        textView.setText(notificationCourante.getNomEmetteur());

        String photoNotificationurUrl=notificationCourante.getPhotoProfilEmetteur();
        CircleImageView circleImageView=vHolder.PhotoProfilEmetteur;
        Glide.with(mContext).load(photoNotificationurUrl).into(circleImageView);

        TextView textView4=vHolder.IdNotification;
        textView4.setText(notificationCourante.getIdNotification());

        TextView textView5=vHolder.MessageNotification;
        textView5.setText(notificationCourante.getMessageNotification());

        TextView textView6=vHolder.DateNotification;
        textView6.setText("Envoyé le : "+notificationCourante.getDateNotification());

        return v;
    }
}
