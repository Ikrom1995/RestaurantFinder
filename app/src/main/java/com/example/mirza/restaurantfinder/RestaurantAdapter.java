package com.example.mirza.restaurantfinder;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by mirza on 16.03.2017.
 */

public class RestaurantAdapter extends BaseAdapter {
    Context context;
    List<Restaurants> allRestaurantsList;

    public RestaurantAdapter(Context context, List<Restaurants> allRestaurantsList) {
        this.context = context;
        this.allRestaurantsList = allRestaurantsList;
    }

    @Override
    public int getCount() {
        return allRestaurantsList.size();
    }

    @Override
    public Object getItem(int i) {
        return allRestaurantsList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View v = viewGroup.inflate(context, R.layout.restaurant_item, null);
        Restaurants restaurant = (Restaurants) getItem(i);

        ImageView imageView = (ImageView) v.findViewById(R.id.ivImage);
        imageView.setImageResource(restaurant.getImage());

        TextView tvName = (TextView) v.findViewById(R.id.tvName);
        tvName.setText(restaurant.getName());

        TextView tvAddress = (TextView) v.findViewById(R.id.tvAddress);
        tvAddress.setText(restaurant.getAddress());

        TextView tvType = (TextView) v.findViewById(R.id.tvType);
        tvType.setText(restaurant.getType());

        TextView tvRating = (TextView) v.findViewById(R.id.tvRating);
        tvRating.setText(String.valueOf(restaurant.getRating()));


        return v;

    }

}
