package com.example.hotelsview;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.view.View;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class JsonParser extends AsyncTask<String,String,Void> {

    public List<Hotels> hoteldetails = new ArrayList<>();
    BufferedInputStream inputStream;
    JSONArray jsonArray;
    String result="";
    public ProgressDialog progressDialog;
    Activity activity;
    Context context;

    //constructor
    public JsonParser(Activity activty, Context context){
        this.activity=activty;
        this.context=context;
        this.progressDialog=new ProgressDialog(this.context);
    }

    protected void onPreExecute(){
        super.onPreExecute();
        progressDialog.dismiss();
        progressDialog.setMessage("Loading...");
        progressDialog.show();
        progressDialog.setOnCancelListener((dialogInterface)-> {
            JsonParser.this.cancel(true);
        });
    }

    @Override
    protected Void doInBackground(String... strings) {
        HttpURLConnection httpURLConnection=null;
        try{
            URL url = new URL(Url.fetchdata);
            httpURLConnection=(HttpURLConnection)url.openConnection();
            inputStream = new BufferedInputStream(httpURLConnection.getInputStream());
            result = readStream();
            result=result.substring(result.indexOf("(") + 1, result.lastIndexOf(")"));

        } catch (IOException e) {
            e.printStackTrace();
        }


        return null;
    }

    private String readStream() throws IOException{
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        StringBuilder stringBuilder = new StringBuilder();
        String line;
        try
        {
            while((line=bufferedReader.readLine())!=null){
                stringBuilder.append(line);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
            return stringBuilder.toString();

    }
    protected void onPostExecute(Void aVoid){
        try{
            jsonArray=new JSONArray(result);
            if(jsonArray!=null)
            {
                for(int index =0;index<jsonArray.length();index++){
                    Hotels hotels = new Hotels();
                    hotels.id=jsonArray.getJSONObject(index).getInt("id");
                    hotels.title=jsonArray.getJSONObject(index).getString("title");
                    hotels.description=jsonArray.getJSONObject(index).getString("description");
                    hotels.imageurl=Url.imagedata+hotels.id;
                    hoteldetails.add(hotels);
                }
            }
            android.widget.ListView listView;
            listView = (android.widget.ListView)this.activity.findViewById(R.id.listviewhoteldetails);
            CustomAdapter adapter = new CustomAdapter(this.context,hoteldetails);
            listView.setAdapter(adapter);

        } catch (JSONException e) {
            e.printStackTrace();
        }


    }
}
