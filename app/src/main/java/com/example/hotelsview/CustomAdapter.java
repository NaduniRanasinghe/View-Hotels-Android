package com.example.hotelsview;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.io.IOException;
import java.net.URL;
import java.util.List;

import androidx.annotation.NonNull;

public class CustomAdapter extends ArrayAdapter<Hotels> {

    List<Hotels> hotellist;
    private Context context;

    public CustomAdapter(Context context,List<Hotels>hotellist){
        super(context,R.layout.activity_singlehotel,hotellist);
        this.hotellist=hotellist;
        this.context=context;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

        if(convertView==null){
            convertView=inflater.inflate(R.layout.activity_singlehotel,null);
        }
        TextView title = (TextView)convertView.findViewById(R.id.textViewhoteltitle);
        TextView description = (TextView)convertView.findViewById(R.id.textViewhoteldescription);
        ImageView image=(ImageView)convertView.findViewById(R.id.imageView);

        final Hotels hotels = hotellist.get(position);
        title.setText(hotels.getTitle());
        description.setText(hotels.getDescription());
        try{
            URL imageurl = new URL(hotels.getImageurl());
            Glide.with(context).load(imageurl).into(image);
        }catch(IOException e){
            e.printStackTrace();
        }
        return convertView;


    }
}
