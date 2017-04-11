package com.example.mirza.restaurantfinder;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mirza.restaurantfinder.Data.RestaurantsDB;

public class RestaurantActivity extends AppCompatActivity {

    private SQLiteDatabase mDB = null;

    @Override
    public void onRestart() {
        super.onRestart();
        //When BACK BUTTON is pressed, the activity on the stack is restarted
        finish();
        startActivity(getIntent());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant);

        int resID = getIntent().getIntExtra("rID", 0);

        TextView nameText = (TextView) findViewById(R.id.myImageViewText);
        TextView addressText = (TextView) findViewById(R.id.rAddress);
        TextView typeText = (TextView) findViewById(R.id.rType);
        TextView ratingText = (TextView) findViewById(R.id.rRating);
        TextView menuItems = (TextView) findViewById(R.id.rMenu);
        Button btnUpdate, btnDelete;

        btnUpdate = (Button) findViewById(R.id.btnUpdate);
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RestaurantActivity.this, UpdateActivity.class);
                int urID = getIntent().getIntExtra("rID", 0);
                intent.putExtra("uRID", urID);
                startActivity(intent);
            }
        });

        btnDelete = (Button) findViewById(R.id.btnDelete);
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int urID = getIntent().getIntExtra("rID", 0);
                mDB.execSQL("DELETE FROM " + RestaurantsDB.TABLE_NAME + " WHERE " + RestaurantsDB._ID + "='" + urID + "'");
                mDB.close();

                onBackPressed();
            }
        });

        mDB = this.openOrCreateDatabase("restaurants_db", 0, null);

        Cursor cursor = mDB.rawQuery("SELECT * FROM Restaurants WHERE _id = " + resID, null);

        if (cursor != null ) {
            if  (cursor.moveToFirst()) {
                do {
                    nameText.setText(cursor.getString(cursor.getColumnIndex("resName")));
                    addressText.setText(addressText.getText() + cursor.getString(cursor.getColumnIndex("resAddress")));
                    typeText.setText(typeText.getText() + cursor.getString(cursor.getColumnIndex("resType")));
                    ratingText.setText(ratingText.getText() + cursor.getString(cursor.getColumnIndex("resRating")));
                    menuItems.setText(cursor.getString(cursor.getColumnIndex("resMenu")));

                }while (cursor.moveToNext());
            }
        }
        cursor.close();
    }

}
