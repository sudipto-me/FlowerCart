package com.example.sudipto.hanselandgratel;

import android.app.ListActivity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v4.app.ListFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


import com.example.sudipto.hanselandgratel.FlowerAdapter;
import com.example.sudipto.hanselandgratel.R;
import com.example.sudipto.hanselandgratel.model.Flowers;
import com.example.sudipto.hanselandgratel.parsers.FlowerJSONParser;


import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static com.example.sudipto.hanselandgratel.R.*;


public class MainActivity extends AppCompatActivity {

    private static final String PHOTOS_BASE_URL =
            "http://services.hanselendpetal.com/photos/";

    TextView output;
    ProgressBar pb;

    List<myTask>tasks;

    List<Flowers>flowersList;
    private FlowerAdapter listAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout.activity_main);

        //output = (TextView)findViewById(id.textview);
        //output.setMovementMethod(new ScrollingMovementMethod());

        pb = (ProgressBar)findViewById(id.progressBar);
        pb.setVisibility(View.INVISIBLE);

        tasks = new ArrayList<>();


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == id.action_do_task){

            if(isOnline()){
                requestData("http://services.hanselandpetal.com/feeds/flowers.json");
            }
            else{
                Toast.makeText(this,"Network is not available",Toast.LENGTH_LONG).show();
            }


        }

        return false;
    }

    private void requestData(String uri) {
        myTask task = new myTask();
        task.execute(uri);
        //task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,"Param 1","Param 2","Param 3");
    }

    protected void updateDisplay(){

      /*
        if(flowersList!= null){
            for (Flowers flowers : flowersList){
                output.append(flowers.getName()+"\n");
            }


        }
        */

        FlowerAdapter adapter = new FlowerAdapter(this,R.layout.item_flower,flowersList);
       setListAdapter(adapter);

    }

    

    //check if the network is available or not
    protected boolean isOnline(){

        ConnectivityManager cm = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();

        if(networkInfo!=null && networkInfo.isConnectedOrConnecting()){

            return true;
        }
        else{

            return false;
        }


    }

    public void setListAdapter(FlowerAdapter listAdapter) {
        this.listAdapter = listAdapter;
    }


    //inner class to do work in the background

    private class myTask extends AsyncTask<String,String,List<Flowers>>{

        @Override
        protected void onPreExecute() {
            // updateDisplay("Starting Task");


// to keep the visibility the task bar until the execution
            if(tasks.size() == 0){
                pb.setVisibility(View.VISIBLE);
            }

            tasks.add(this);


        }

        @Override
        protected List<Flowers> doInBackground(String... params) {

            String content =  HttpManager.getData(params[0],"feed user","feedspassword");
            flowersList = FlowerJSONParser.parseFeed(content);

            for (Flowers flowers:flowersList){
                try{
                    String imageUrl = PHOTOS_BASE_URL + flowers.getPhoto();
                    InputStream in = (InputStream) new URL(imageUrl).getContent();
                    Bitmap bitmap = BitmapFactory.decodeStream(in);
                    flowers.setBitmap(bitmap);

                    in.close();




                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            return flowersList;


        }

        @Override
        protected void onPostExecute(List<Flowers>result) {


// to remove the progess bar after the final execution ends
            tasks.remove(this);
            if(tasks.size()==0){

                pb.setVisibility(View.INVISIBLE);
            }
            if(tasks == null){
                Toast.makeText(MainActivity.this, "Web service not available", Toast.LENGTH_LONG).show();
                return;
            }
            flowersList = result;

            updateDisplay();

        }

        /* to update the progress in the main thread while the task is executing in the background.*/

        @Override
        protected void onProgressUpdate(String... values) {
            //updateDisplay(values[0]);
        }
    }
}
