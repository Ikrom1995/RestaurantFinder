package com.example.mirza.restaurantfinder;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mirza.restaurantfinder.Data.RestaurantsDB;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

public class UpdateActivity extends AppCompatActivity {

    private SQLiteDatabase mDB = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        int urID = getIntent().getIntExtra("uRID", 0);

        mDB = this.openOrCreateDatabase("restaurants_db", 0, null);

        Cursor cursor = mDB.rawQuery("SELECT * FROM Restaurants WHERE _id = " + urID, null);

        final EditText rName, rAddress, rType, rRating, rMenu;
        Button save, cancel;

        rName = (EditText) findViewById(R.id.upName);
        rAddress = (EditText) findViewById(R.id.upAddress);
        rType = (EditText) findViewById(R.id.upType);
        rRating = (EditText) findViewById(R.id.upRating);
        rMenu = (EditText) findViewById(R.id.upMenu);

        if (cursor != null ) {
            if  (cursor.moveToFirst()) {
                do {
                    rName.setText(cursor.getString(cursor.getColumnIndex("resName")));
                    rAddress.setText(cursor.getString(cursor.getColumnIndex("resAddress")));
                    rType.setText(cursor.getString(cursor.getColumnIndex("resType")));
                    rRating.setText(cursor.getString(cursor.getColumnIndex("resRating")));
                    rMenu.setText(cursor.getString(cursor.getColumnIndex("resMenu")));

                }while (cursor.moveToNext());
            }
        } else {

        }
        cursor.close();

        save = (Button) findViewById(R.id.btnSave);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (    rName.getText().toString().isEmpty() ||
                        rAddress.getText().toString().isEmpty() ||
                        rType.getText().toString().isEmpty() ||
                        rRating.getText().toString().isEmpty() ||
                        rMenu.getText().toString().isEmpty()){

                    MessageBox("Fill in all fields");

                } else {
                    ContentValues cv = new ContentValues();
                    cv.put("resName", rName.getText().toString()); //These Fields should be your String values of actual column names
                    cv.put("resAddress", rAddress.getText().toString());
                    cv.put("resType", rType.getText().toString());
                    cv.put("resRating", Integer.parseInt(rRating.getText().toString()));
                    cv.put("resMenu", rMenu.getText().toString());

                    int urID = getIntent().getIntExtra("uRID", 0);
                    if(urID != 0){
                        mDB.update(RestaurantsDB.TABLE_NAME, cv, "_id="+ urID, null);
                    } else {
                        mDB.insert(RestaurantsDB.TABLE_NAME, null, cv);
                    }

                    onBackPressed();
                }
            }
        });

        cancel = (Button) findViewById(R.id.btnCancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    public void MessageBox(String message)
    {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

}
