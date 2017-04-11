package com.example.mirza.restaurantfinder.Data;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

/**
 * Created by mirza on 16.03.2017.
 */

public class RestaurantsDB extends SQLiteOpenHelper {

    public final static String TABLE_NAME = "Restaurants";
    public final static String RESTAURANT_NAME = "resName";
    public final static String RESTAURANT_ADDRESS = "resAddress";
    public final static String RESTAURANT_TYPE = "resType";
    public final static String RESTAURANT_RATING = "resRating";
    public final static String RESTAURANT_MENU = "resMenu";

    public final static String _ID = "_id";
    public final static String[] columns = { _ID, RESTAURANT_NAME, RESTAURANT_ADDRESS, RESTAURANT_TYPE, RESTAURANT_RATING, RESTAURANT_MENU };

    final private static String CREATE_CMD =

            "CREATE TABLE Restaurants (" + _ID
                    + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + RESTAURANT_NAME + " TEXT NOT NULL, "
                    + RESTAURANT_ADDRESS + " TEXT NOT NULL, "
                    + RESTAURANT_TYPE + " TEXT NOT NULL, "
                    + RESTAURANT_RATING + " INTEGER NOT NULL, "
                    + RESTAURANT_MENU + " TEXT NOT NULL)";

    final private static String NAME = "restaurants_db";
    final private static Integer VERSION = 1;
    final private Context mContext;

    public RestaurantsDB(Context context) {
        super(context, NAME, null, VERSION);
        this.mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_CMD);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

}
