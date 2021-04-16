package com.example.mainapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.ToggleButton;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import static android.view.View.VISIBLE;
import static java.lang.Boolean.FALSE;

public class ResultsPage extends AppCompatActivity {

    SearchView mySearchView;
    ListView myList;
    DrawerLayout drawerLayout;
    //getting domain and loggedIn status
    String domain = MainActivity.getDomain();
    boolean loggedIn = MainActivity.setLoggedIn();
    //for sidebar - show options by domain
    LinearLayout mainmenu,viewgrants,viewagentinfo,homecalc,mylistings,inbox,settings;
    TextView username;
    ImageView picture,picture1,picture2;
    List<House> HouseList = new ArrayList<House>();
    List<Agent> AgentList = new ArrayList<>();
    ArrayAdapter<String> adapter;

    ImageButton ARcamera;
    Button SearchBar;
    TextView filters;
    ImageButton filters2;
    //ImageView houseInfo;
//    ToggleButton favorites;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results_page);
        drawerLayout = findViewById(R.id.drawer_layout);

        //ARcamera = findViewById(R.id.imageButton11);
        SearchBar = findViewById(R.id.button);
        filters = findViewById(R.id.textView3);
        filters2 = findViewById(R.id.imageButton3);
//        favorites = findViewById(R.id.toggleButton2);
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


        SearchBar.setOnClickListener(new View.OnClickListener() { //clicking on search bar
            @Override
            public void onClick(View v) {
                setContentView(R.layout.activity_search_bar);
            }
        });
        filters.setOnClickListener(new View.OnClickListener() { //clicking on advanced filters (text)
            @Override
            public void onClick(View v) {
                setContentView(R.layout.activity_advanced_filters);
            }
        });
        filters2.setOnClickListener(new View.OnClickListener() { //clicking on advanced filters(button)
            @Override
            public void onClick(View v) {
                setContentView(R.layout.activity_advanced_filters);
            }
        });
        /*houseInfo.setOnClickListener(new View.OnClickListener() { //clicking on house image
            @Override
            public void onClick(View v) {
                setContentView(R.layout.activity_house_info);
            }
        });*/

        readHouse();
        readAgentData();

        myList = (ListView)findViewById(R.id.MyList);

        ArrayList<String> newlist = (ArrayList<String>) getIntent().getSerializableExtra("thelist");
        //ArrayList<House> house = (ArrayList<House>) getIntent().getSerializableExtra("houselist");

        adapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,newlist);
        myList.setAdapter(adapter);

        myList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent;
                String address = parent.getAdapter().getItem(position).toString();
                int[] drawables = {R.drawable.pic,R.mipmap.ic_house,R.mipmap.ic_house2,R.mipmap.ic_house3};
                for(House b: HouseList) {
                    if (address.equals(b.getHouseId())) {
                        houseInfo.setStreet(b.getStreet_name());
                        houseInfo.setBedroom(b.getBedroom());
                        houseInfo.setMRT(b.getTown());  //CHECK
                        houseInfo.setAgent(getAgentName(b.getAgent_id()));
                        houseInfo.setPrice(b.getResale_price());
                        houseInfo.setImage(getRandomElement(drawables)); //setting a random image
                        break;
                    }
                }

                intent = new Intent(ResultsPage.this,houseInfo.class);
                startActivity(intent);
            }
        });

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
        //Intent intent = new Intent(ResultsPage.this, AdvancedFilters.class);
        //this.startActivity(intent);
        MainActivity.redirectActivity(this,AdvancedFilters.class);
        //this.finish();
    }
    public void ClickCamera(View view){ MainActivity.camera(this); }

    //VIEW GRANT INFO
    public void ClickViewGrantsInfo(View view){ MainActivity.redirectActivity(this,ViewGrantsInfo.class); }
    public void ClickEligibility(View view){
        if (loggedIn){MainActivity.redirectActivity(this,ViewEligibility.class);}
        else{MainActivity.login(this);}
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
        if (loggedIn){MainActivity.redirectActivity(this,HomeCalculator.class);}
        else{MainActivity.login(this);}
    }

    //MY LISTINGS
    public void ClickMyListings(View view){
        MainActivity.redirectActivity(this,MyListings.class);
    }

    //INBOX
    public void ClickInbox(View view){
        MainActivity.redirectActivity(this,Inbox.class);
    }
    public void ClickEditInbox(View view){
        MainActivity.redirectActivity(this,EditInbox.class);
    }

    //SETTINGS
    public void ClickSettings(View view){
        MainActivity.redirectActivity(this,Settings.class);
    }

    public String getAgentName(int agentID) {
        for (Agent a : AgentList) {
            if (a.getUserId() == agentID){
                return a.getUsername();
            }
        }
        return "";
    }

    private void readHouse(){
        InputStream isss = getResources().openRawResource(R.raw.house); //imp class
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(isss, Charset.forName("UTF-8")) //alt enter and import class charset
        );

        String line = "";
        try {
            reader.readLine();
            while ((line = reader.readLine()) != null) {
                Log.d("MyActivity", "Line: " + line);
                String[] tokens = line.split(",");

                House houses = new House();
                houses.setId(Integer.parseInt(tokens[0]));
                //   houses.setMonth(tokens[1]);
                houses.setTown(tokens[2]);
                houses.setFlat_type(tokens[3]);
                houses.setBlock(tokens[4]);
                houses.setStreet_name(tokens[5]);
                houses.setStorey_range(tokens[6]);
                houses.setFloor_area_sqm(Integer.parseInt(tokens[7]));
                houses.setFlat_model(tokens[8]);
                houses.setLease_commence_date(Integer.parseInt(tokens[9]));
                houses.setRemaining_lease(tokens[10]);
                houses.setResale_price(Integer.parseInt(tokens[11]));
                houses.setAgent_id(Integer.parseInt(tokens[0]));  //changed

                HouseList.add(houses);
                Log.d("MyActivity", "Just Created: " + houses);
            }
        } catch (IOException e) {
            Log.wtf("MyActivity", "Error reading on Line: " + line, e);
            e.printStackTrace();
        }
    }

    private void readAgentData() {
        InputStream iss = getResources().openRawResource(R.raw.agent); //imp class
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(iss, Charset.forName("UTF-8")) //alt enter and import class charset
        );

        String line = "";
        try {
            reader.readLine();
            while ((line = reader.readLine()) != null) {
                Log.d("MyActivity", "Line: " + line);
                String[] tokens = line.split(",");

                Agent agents = new Agent(-1,"","","","Agent","","");
                agents.setUserId(Integer.parseInt(tokens[0]));
                agents.setCompName(tokens[1]);
                agents.setUsername(tokens[2]);
                agents.setPassword(tokens[3]);
                agents.setDomain(tokens[4]);
                agents.setEmail(tokens[5]);
                agents.setNumber(tokens[6]);
                AgentList.add(agents);
                Log.d("MyActivity", "Just Created: " + agents);
            }
        } catch (IOException e) {
            Log.wtf("MyActivity", "Error reading on Line: " + line, e);
            e.printStackTrace();
        }


    }
    public static int getRandomElement(int[] arr){
        return arr[ThreadLocalRandom.current().nextInt(arr.length)];
    }
}