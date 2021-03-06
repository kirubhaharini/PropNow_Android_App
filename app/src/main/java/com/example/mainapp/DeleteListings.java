package com.example.mainapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import static android.view.View.VISIBLE;



public class DeleteListings extends AppCompatActivity {
    DrawerLayout drawerLayout;
    ImageButton button;
    //getting domain and loggedIn status
    String domain = MainActivity.getDomain();
    boolean loggedIn = MainActivity.setLoggedIn();
    //for sidebar - show options by domain
    LinearLayout mainmenu,viewgrants,viewagentinfo,homecalc,mylistings,inbox,settings;
    TextView username;
    ImageView picture,picture1,picture2;
    Button b;

    ListView lv_listings;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_listings);
        drawerLayout = findViewById(R.id.drawer_layout);
        lv_listings = findViewById(R.id.lv_listings);
        HouseMgr houseMgr = new HouseMgr(DeleteListings.this);
        List<House> all = houseMgr.getAll();
        ArrayAdapter houseArrayAdapter = new ArrayAdapter<House>(DeleteListings.this, android.R.layout.simple_list_item_1,all);
        lv_listings.setAdapter(houseArrayAdapter);

        //for sidebar - show options by domain
        mainmenu = findViewById(R.id.mainmenu);
        viewgrants = findViewById(R.id.viewgrants);
        viewagentinfo = findViewById(R.id.viewagentinfo);
        homecalc = findViewById(R.id.homecalc);
        mylistings = findViewById(R.id.mylistings);
        inbox = findViewById(R.id.inbox);
        settings = findViewById(R.id.settings);
        username = findViewById(R.id.username);
        picture = findViewById(R.id.picture);
        picture1 = findViewById(R.id.picture1);
        picture1.setVisibility(View.GONE);
        picture2 = findViewById(R.id.picture2);
        picture2.setVisibility(View.GONE);

        //set visibility according to domain
        if (domain=="AGENT"){  //for agents
            viewgrants.setVisibility(View.GONE);
            homecalc.setVisibility(View.GONE);
            viewagentinfo.setVisibility(View.GONE);
            username.setText("Francisca Grand");
            picture1.setVisibility(VISIBLE);
            picture.setVisibility(View.GONE);
        } else if (domain == "NON-AGENT") {  //for non-agents
            mylistings.setVisibility(View.GONE);
            username.setText("Ealasaid MacCarrane");
            picture2.setVisibility(VISIBLE);
            picture.setVisibility(View.GONE);
        } else{  //for general users
            mylistings.setVisibility(View.GONE);
            inbox.setVisibility(View.GONE);
        }


        lv_listings.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                House clickedHouse = (House) parent.getAdapter().getItem(position);
                HouseMgr houseMgr = new HouseMgr(DeleteListings.this);
                houseMgr.DeleteOne(clickedHouse);
                ArrayAdapter houseArrayAdapter = new ArrayAdapter<House>(DeleteListings.this, android.R.layout.simple_list_item_1, houseMgr.getAll());
                lv_listings.setAdapter(houseArrayAdapter);
                Toast.makeText(DeleteListings.this, "deleted house ID" + clickedHouse.getId(), Toast.LENGTH_SHORT).show();

            }
        });

    }

    public void ClickDone(View view){
        Intent intent = new Intent(DeleteListings.this, MyListings.class);
        startActivity(intent);
    }
    @Override
    protected void onPause() {
        super.onPause();
        //close drawer
        MainActivity.closeDrawer(drawerLayout);
    }

    //SIDEBAR OPTIONS - FUNCTIONS
    public void ClickMenu(View view){
        MainActivity.openDrawer(drawerLayout);
    }

    //MAIN MENU
    public void ClickHome(View view){ MainActivity.redirectActivity(this,MainActivity.class); }
    public void ClickAdvFilters(View view){
        MainActivity.redirectActivity(this,AdvancedFilters.class);
    }
    public void ClickCamera(View view){ MainActivity.camera(this); }

    //VIEW GRANT INFO
    public void ClickViewGrantsInfo(View view){ MainActivity.redirectActivity(this,ViewGrantsInfo.class); }
    public void ClickEligibility(View view){
        MainActivity.redirectActivity(this,ViewEligibility.class);
        //else{MainActivity.login(this);}
    }

    //VIEW AGENT INFO
    public void ClickViewAgentInfo(View view){ MainActivity.redirectActivity(this,ViewAgentInfo.class); }
    public void ClickAgent(View view){ MainActivity.redirectActivity(this,MonicaGeller.class); }

    //HOME CALC
    public void ClickHomeCalculator(View view){
        //this code below is correct
        if (domain == "AGENT" || domain == "NON-AGENT") {
            loggedIn = true;
        }
        else{ loggedIn=false;}
        MainActivity.redirectActivity(this,HomeCalculator.class);
        // else{MainActivity.login(this);}
    }

    //MY LISTINGS
    public void ClickMyListings(View view){
        MainActivity.redirectActivity(this,MyListings.class);
    }

    //INBOX
    public void ClickInbox(View view){
        recreate();
    }
    public void ClickEditInbox(View view){
        MainActivity.redirectActivity(this,EditInbox.class);
    }

    public void ClickChat(View view){
        MainActivity.redirectActivity(this, Chat.class);
    }

    //SETTINGS
    public void ClickSettings(View view){
        MainActivity.redirectActivity(this,Settings.class);
    }
}