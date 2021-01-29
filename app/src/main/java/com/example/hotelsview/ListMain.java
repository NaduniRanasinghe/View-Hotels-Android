package com.example.hotelsview;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class ListMain extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view);
        try{
            JsonParser jsonParser = (JsonParser) new JsonParser(this, this);
            jsonParser.execute();

        }catch(Exception e){
            e.printStackTrace();
        }
    }

}
