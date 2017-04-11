package com.example.mirza.restaurantfinder;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import com.example.mirza.restaurantfinder.Data.RestaurantsDB;
import java.util.ArrayList;

public class RestaurantsFragment extends Fragment {

    private SQLiteDatabase mDB = null;
    private RestaurantsDB restaurantsDB;
    private SimpleCursorAdapter mAdapter;
    private Context context;

    public RestaurantsFragment(Context context) {
        this.context = context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        // Create a new database
        restaurantsDB = new RestaurantsDB(context);

        // Get the underlying database for writing
        mDB = restaurantsDB.getWritableDatabase();

        if(readRestaurants().getCount() == 0){
            insertRestaurants();
        }

        Cursor c = readRestaurants();

        mAdapter = new SimpleCursorAdapter(context, R.layout.restaurant_item, c,
                RestaurantsDB.columns, new int[] { R.id._id, R.id.tvName, R.id.tvAddress, R.id.tvType, R.id.tvRating },
                0);

        View view = inflater.inflate(R.layout.fragment_restaurants, container, false);

        Button btnCreate = (Button) view.findViewById(R.id.btnCreate);
        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), UpdateActivity.class);
                startActivity(intent);
            }
        });

        AutoCompleteTextView autoName= (AutoCompleteTextView) view.findViewById(R.id.autoName);

        ArrayList<String> names = new ArrayList<>();

        if (c != null ) {
            while (c.moveToNext()) {
                names.add(c.getString(c.getColumnIndex("resName")));
            }

        }
        c.close();

        autoName.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Cursor cursor = filterRestaurants();
                mAdapter.changeCursor(cursor);
            }
        });

        autoName.setOnDismissListener(new AutoCompleteTextView.OnDismissListener() {
            @Override
            public void onDismiss() {
                Cursor cursor = filterRestaurants();
                mAdapter.changeCursor(cursor);
            }
        });

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(context,
                android.R.layout.select_dialog_item, names);
        autoName.setAdapter(adapter);

        final ListView lvRestaurants= (ListView)view.findViewById(R.id.lvRestaurants);

        lvRestaurants.setAdapter(mAdapter);
        lvRestaurants.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                int currentID = Integer.parseInt (((TextView) view.findViewById(R.id._id)).getText().toString()) ;

                Intent intent = new Intent(getActivity(), RestaurantActivity.class);
                intent.putExtra("rID", currentID);
                startActivity(intent);
            }
        });

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getActivity().setTitle("Restaurants");
    }

    @Override
    public void onResume() {
        super.onResume();

        mAdapter.changeCursor(readRestaurants());
    }

    private Cursor readRestaurants() {
        return mDB.query(RestaurantsDB.TABLE_NAME,
                RestaurantsDB.columns, null, new String[] {}, null, null,null);
    }

    //returns filtered restaurants
    private Cursor filterRestaurants() {
        AutoCompleteTextView autoName= (AutoCompleteTextView) getView().findViewById(R.id.autoName);
        return mDB.query(RestaurantsDB.TABLE_NAME,
                RestaurantsDB.columns, RestaurantsDB.RESTAURANT_NAME + " LIKE '%" + autoName.getText() + "%'", new String[] {}, null, null,null);
    }

// Insert several employee records

    private void insertRestaurants() {

        ContentValues values = new ContentValues();

        values.put(RestaurantsDB.RESTAURANT_NAME, "Basilic");
        values.put(RestaurantsDB.RESTAURANT_ADDRESS, "19, Amir Temur Avenue, Tashkent, Uzbekistan");
        values.put(RestaurantsDB.RESTAURANT_TYPE, "Mediterranean, European, Italian, French");
        values.put(RestaurantsDB.RESTAURANT_RATING, 5);
        values.put(RestaurantsDB.RESTAURANT_MENU, "Black cod in \"Miso\" sauce\n" +
                "Pizza \"Frutti de Mare\"");
        mDB.insert(RestaurantsDB.TABLE_NAME, null, values);


        values.put(RestaurantsDB.RESTAURANT_NAME, "Affresco");
        values.put(RestaurantsDB.RESTAURANT_ADDRESS, "Babur street 14, Tashkent 100100, Uzbekistan");
        values.put(RestaurantsDB.RESTAURANT_TYPE, "Italian, European");
        values.put(RestaurantsDB.RESTAURANT_RATING, 4);
        values.put(RestaurantsDB.RESTAURANT_MENU, "Beef Steak\n" +
                "Bolognese");
        mDB.insert(RestaurantsDB.TABLE_NAME, null, values);


        values.put(RestaurantsDB.RESTAURANT_NAME, "Shedevr Garden Restaurant");
        values.put(RestaurantsDB.RESTAURANT_ADDRESS, "R. Faizi St., 44, Tashkent, Uzbekistan");
        values.put(RestaurantsDB.RESTAURANT_TYPE, "European, Contemporary");
        values.put(RestaurantsDB.RESTAURANT_RATING, 5);
        values.put(RestaurantsDB.RESTAURANT_MENU, "Mushroom Bleu Top Sirloin\n" +
                "Palov");
        mDB.insert(RestaurantsDB.TABLE_NAME, null, values);



        values.put(RestaurantsDB.RESTAURANT_NAME, "12 Chairs");
        values.put(RestaurantsDB.RESTAURANT_ADDRESS, "89 Nukus street, Tashkent, Uzbekistan");
        values.put(RestaurantsDB.RESTAURANT_TYPE, "Russian, Eastern European, Israeli, Ukrainian");
        values.put(RestaurantsDB.RESTAURANT_RATING, 4);
        values.put(RestaurantsDB.RESTAURANT_MENU, "Fried Rice\n" +
                "Broiled Sirloin Tips");
        mDB.insert(RestaurantsDB.TABLE_NAME, null, values);


        values.put(RestaurantsDB.RESTAURANT_NAME, "Central Asian Plov Centre");
        values.put(RestaurantsDB.RESTAURANT_ADDRESS, "Ergashev & Abdurashidov, Tashkent, Uzbekistan");
        values.put(RestaurantsDB.RESTAURANT_TYPE, "Uzbek National");
        values.put(RestaurantsDB.RESTAURANT_RATING, 5);
        values.put(RestaurantsDB.RESTAURANT_MENU, "Andijan Palov\n" +
                "Samarkand Palov\n" +
                "Toy palov");
        mDB.insert(RestaurantsDB.TABLE_NAME, null, values);


        values.put(RestaurantsDB.RESTAURANT_NAME, "Yapona Mama");
        values.put(RestaurantsDB.RESTAURANT_ADDRESS, "Shota Rustaveli St., 69A/3, Tashkent 100071, Uzbekistan");
        values.put(RestaurantsDB.RESTAURANT_TYPE, "Japanese");
        values.put(RestaurantsDB.RESTAURANT_RATING, 5);
        values.put(RestaurantsDB.RESTAURANT_MENU, "Fried Rice\n" +
                "Sushi");
        mDB.insert(RestaurantsDB.TABLE_NAME, null, values);


        values.put(RestaurantsDB.RESTAURANT_NAME, "Irish Pub");
        values.put(RestaurantsDB.RESTAURANT_ADDRESS, "Taras Shevchenko, Tashkent, Uzbekistan");
        values.put(RestaurantsDB.RESTAURANT_TYPE, "Pub, European");
        values.put(RestaurantsDB.RESTAURANT_RATING, 5);
        values.put(RestaurantsDB.RESTAURANT_MENU, "Steak\n" +
                "Beer");
        mDB.insert(RestaurantsDB.TABLE_NAME, null, values);
    }

}
