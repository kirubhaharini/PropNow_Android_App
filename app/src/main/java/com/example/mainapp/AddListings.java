package com.example.mainapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class AddListings extends AppCompatActivity {

    //references to buttons and other controls on layout
    Button btn_addListing;
    EditText et_blockNum, et_unitNum, et_street, et_bedroomNum, et_nearestMRT;
    ListView lv_houseList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_listings);


        btn_addListing = findViewById(R.id.btn_addListing);
        et_blockNum = findViewById(R.id.et_blockNum);
        et_unitNum = findViewById(R.id.et_unitNum);
        et_street = findViewById(R.id.et_street);
        et_bedroomNum = findViewById(R.id.et_bedroomNum);
        et_nearestMRT = findViewById(R.id.et_nearestMRT);

        //button listeners for the add and view all buttons
        btn_addListing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try{
                    House house = new House(-1,
                            Integer.parseInt(et_bedroomNum.getText().toString()),
                            et_blockNum.toString(),
                            et_unitNum.toString(),
                            et_nearestMRT.toString());


                    Toast.makeText(AddListings.this , house.toString(), Toast.LENGTH_SHORT).show();

                }
                catch (Exception e){
                    Toast.makeText(AddListings.this , "error adding house", Toast.LENGTH_SHORT).show();

                }
                
                
            }
        });






    }
}