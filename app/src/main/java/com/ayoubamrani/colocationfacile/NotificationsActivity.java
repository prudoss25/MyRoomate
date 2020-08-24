package com.ayoubamrani.colocationfacile;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
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

public class NotificationsActivity extends AppCompatActivity {

    ArrayList<Notification> notificationArrayList;
    ListView lvNotifications;
    NotificationListAdapter notificationListAdapter;
    DatabaseReference databaseReference;
    String idUserCourant;
    
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications);

        databaseReference= FirebaseDatabase.getInstance().getReference("Notification");
        lvNotifications=findViewById(R.id.lvNotifications);
        notificationArrayList=new ArrayList<Notification>();

        String MyPREFERENCES = "MyPrefs" ;
        SharedPreferences prefs = getSharedPreferences(MyPREFERENCES , MODE_PRIVATE);
        idUserCourant = prefs.getString("Email", null);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot postSnapshot : dataSnapshot.getChildren())
                {
                    Notification notification=postSnapshot.getValue(Notification.class);
                    if(notification.getEmailDestinataire().compareTo(idUserCourant)==0) notificationArrayList.add(notification);
                }

                notificationListAdapter=new NotificationListAdapter(getApplicationContext(), notificationArrayList);
                lvNotifications.setAdapter(notificationListAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(),databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        lvNotifications.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView tvIdNotification=(TextView) view.findViewById(R.id.idNotification);
                String idNotification=tvIdNotification.getText().toString();

                goToNotification(idNotification);
            }
        });
    }

    private void goToNotification(String idNotification) {
        Intent intent=new Intent(getApplicationContext(), NotificationActivity.class);
        intent.putExtra("idNotification", idNotification);
        startActivity(intent);
    }
}
