package com.ayoubamrani.colocationfacile;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, FiltreDialog.FiltreDialogListener{

    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private Toolbar toolbar;
    private NavigationView navigationView;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private String userName;
    private String userEmail;
    private String userProfilPicture;
    // Les fragments
    AccueilFragment accueilFragment;
    OffresFragment offresFragment;
    DemandesFragment demandesFragment;
    ChatFragment chatFragment;
    // Notifications icon

    private int[] icons = {
            R.drawable.ic_home,
            R.drawable.ic_offer,
            R.drawable.ic_email,
            R.drawable.ic_chat
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String MyPREFERENCES = "MyPrefs" ;
        SharedPreferences prefs = getSharedPreferences(MyPREFERENCES , MODE_PRIVATE);
        userEmail= prefs.getString("Email", null);
        userName= prefs.getString("NomComplet", null);
        userProfilPicture= prefs.getString("UrlPhotoProfil", null);

        navigationView=(NavigationView)findViewById(R.id.drawerNavigationView);

        View drawerHeaderView = navigationView.getHeaderView(0);
        CircleImageView ivUserProfilePicture = (CircleImageView) drawerHeaderView.findViewById(R.id.profilePicture);
        TextView tvUserName = (TextView) drawerHeaderView.findViewById(R.id.userName);
        TextView tvUserEmail = (TextView) drawerHeaderView.findViewById(R.id.userEmail);

        tvUserName.setText(userName);
        tvUserEmail.setText(userEmail);
        //Picasso.get().load(userProfilPicture).fit().rotate(270).into(ivUserProfilePicture);
        Glide.with(this).load(userProfilPicture).into(ivUserProfilePicture);



        // The tablayout
        tabLayout=(TabLayout)findViewById(R.id.mainTabLayout);
        viewPager=(ViewPager) findViewById(R.id.mainViewPager);

        ViewPagerAdapter viewPagerAdapter=new ViewPagerAdapter(getSupportFragmentManager());

        // Les différents fragements
        accueilFragment = new AccueilFragment();
        offresFragment = new OffresFragment();
        demandesFragment = new DemandesFragment();
        chatFragment = new ChatFragment();

        viewPagerAdapter.addFragment(accueilFragment,"ACCUEIL");
        viewPagerAdapter.addFragment(offresFragment, "OFFRES");
        viewPagerAdapter.addFragment(demandesFragment, "DEMANDES");
        viewPagerAdapter.addFragment(chatFragment,"CHAT");


        viewPager.setOffscreenPageLimit(3);
        viewPager.setAdapter(viewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);


        for(int i=0; i<4; i++) tabLayout.getTabAt(i).setIcon(icons[i]);

        // The toolbar
        toolbar=(Toolbar)findViewById(R.id.toolbar);
        //toolbar.setTitle("Colocation Facile");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        // The actionBarDrawerToggle
        drawerLayout=(DrawerLayout)findViewById(R.id.drawerLayout);
        actionBarDrawerToggle=new ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        actionBarDrawerToggle.syncState();
        // Make dashboard fragment as a the default fragment when the app is opened for the 1st time
        if(savedInstanceState == null)
        {
            tabLayout.getTabAt(0).select();
        }
        // Navigation view
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_toolbar_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        /*switch (item.getItemId())
        {
            case R.id.search :
                //Toast.makeText(getApplicationContext(),"Search clicked", Toast.LENGTH_SHORT).show();
                break;

            default:
                break;
        }*/
        if(actionBarDrawerToggle.onOptionsItemSelected(item)) return true;
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if(drawerLayout.isDrawerOpen(GravityCompat.START)) drawerLayout.closeDrawer(GravityCompat.START);
        else super.onBackPressed();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId())
        {
            case R.id.accueil :
                tabLayout.getTabAt(0).select();
                break;
            case R.id.offres :
                tabLayout.getTabAt(1).select();
                //getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainerLayout, new OffresFragment()).commit();
                break;
            case R.id.demandes :
                tabLayout.getTabAt(2).select();
                break;
            case R.id.chat :
                tabLayout.getTabAt(3).select();
                break;
            case R.id.notifications :
                Intent notifsIntent=new Intent(this, NotificationsActivity.class);
                startActivity(notifsIntent);
                break;
            case R.id.parametres :
                Intent paramIntent=new Intent(this, ParametresActivity.class);
                startActivity(paramIntent);
                break;
            case R.id.aPropos :
                //openAProposDialog();
                break;
            case R.id.deconnexion :
                Intent intent=new Intent(this, Authentification.class);
                startActivity(intent);
                break;

        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void appliquerFiltre(String localisation, int prixMax, int duree, int nbrDeColocs, int ageMin, int ageMax, String type, boolean fumeur, boolean etudiant, boolean possedeAnimaux) {
            accueilFragment.appliquerFiltre(localisation,prixMax,duree,nbrDeColocs,ageMin,ageMax,type,fumeur,etudiant,possedeAnimaux);
    }

}
