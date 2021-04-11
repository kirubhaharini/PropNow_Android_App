package com.example.mainapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class HouseDatabaseHelper extends SQLiteOpenHelper {

    public static final String HOUSE_TABLE = "HOUSE_TABLE";
    public static final String COL_ID = "id";
    public static final String COL_TOWN = "town";
    public static final String COL_FLAT_TYPE = "flat_type";
    public static final String COL_BLOCK = "block";
    public static final String COL_STREET_NAME = "street_name";
    public static final String COL_STOREY_RANGE = "storey_range";
    public static final String COL_FLOOR_AREA_SQM = "floor_area_sqm";
    public static final String COL_FLAT_MODEL = "flat_model";
    public static final String COL_LEASE_COMMENCE_DATE = "lease_commence_date";
    public static final String COL_REMAINING_LEASE = "remaining_lease";
    public static final String COL_RESALE_PRICE = "resale_price";
    public static final String COL_AGENT_ID = "agent_id";

    public HouseDatabaseHelper(@Nullable Context context) {
        super(context, "House.db", null, 1);
    }

    //this is called the first time a database accessed. There should be code in here to create a new database
    @Override
    public void onCreate(SQLiteDatabase db) {

        String createTableStatement = "CREATE TABLE " + HOUSE_TABLE + "(" + COL_ID + " INT  NOT NULL PRIMARY KEY AUTOINCREMENT, " +
                COL_TOWN + " VARCHAR(15) NOT NULL , " + COL_FLAT_TYPE + " VARCHAR(16) NOT NULL, " + COL_BLOCK + " VARCHAR(4) NOT NULL, " + COL_STREET_NAME + " VARCHAR(20) NOT NULL," +
                COL_STOREY_RANGE + " VARCHAR(8) NOT NULL, " + COL_FLOOR_AREA_SQM + " NUMERIC(4,1) NOT NULL, " + COL_FLAT_MODEL + " VARCHAR(22) NOT NULL, " +
                COL_LEASE_COMMENCE_DATE + " INTEGER  NOT NULL, " + COL_REMAINING_LEASE + " VARCHAR(18) NOT NULL, " + COL_RESALE_PRICE + " NUMERIC(9,2) NOT NULL," +
                COL_AGENT_ID + " INTEGER NOT NULL)";

        db.execSQL(createTableStatement);


    }

    //this is used if the version number changes.
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public boolean addOne(House house) {


        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COL_TOWN, house.getTown());
        cv.put(COL_FLAT_TYPE, house.getFlat_type());
        cv.put(COL_BLOCK, house.getBlock());
        cv.put(COL_STREET_NAME, house.getStreet_name());
        cv.put(COL_STOREY_RANGE, house.getStorey_range());
        cv.put(COL_FLOOR_AREA_SQM, house.getFloor_area_sqm());
        cv.put(COL_FLAT_MODEL, house.getFlat_model());
        cv.put(COL_LEASE_COMMENCE_DATE, house.getLease_commence_date());
        cv.put(COL_REMAINING_LEASE, house.getRemaining_lease());
        cv.put(COL_RESALE_PRICE, house.getResale_price());
        cv.put(COL_AGENT_ID, house.getAgent_id());

        long insert = db.insert(HOUSE_TABLE, null, cv);
        if (insert == -1) {
            return false;
        } else {

            return true;
        }
    }
}
