package com.example.top10downloader;



import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;

import android.os.AsyncTask;
import android.os.Bundle;

import android.os.PersistableBundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.appcompat.widget.Toolbar;

import java.io.BufferedReader;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    private ArrayList<FeedEntry> app;
    private FeedEntry ob;
    private EditText editText;
    private EditText name;
    private EditText artist;
    private EditText summary;
    private static final String TAG = "MainActivity";
    private int i=0;
    private Button nextButton;
    private Button previousButton;
    private  Toolbar toolbar;
    private ListView listView;
    private String feedUrl="http://ax.itunes.apple.com/WebObjects/MZStoreServices.woa/ws/RSS/topfreeapplications/limit=%d/xml";
    private String feedCacheUrl="Invalid Url";
    public static final String State_url="Invalid Url";
    public static final String State_feedCacheLimit="jas";
    public static final String State_feedCacheUrl="invalid url";
    public static final String State_limit="invalid_limit";
    private int feedLimit=10;
    private int feedCacheLimit=-1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {



        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if(savedInstanceState!=null)
        {
//            feedCacheUrl=savedInstanceState.getString(State_feedCacheUrl);// as we are not saving the text if we uses cache then nothing will show as no text is restore
//  //and nothing is loaded as url and limits are same

//            feedCacheLimit=savedInstanceState.getInt(State_feedCacheLimit);

            feedUrl=savedInstanceState.getString(State_url);
            feedLimit=savedInstanceState.getInt(State_limit);
        }

//        name= (EditText) findViewById(R.id.name);
//        artist=(EditText)findViewById(R.id.artist);
//        summary=(EditText)findViewById(R.id.summary);
//
////        editText=(EditText) findViewById(R.id.textDisplay);
//         previousButton=(Button) findViewById(R.id.previous);
//         nextButton=(Button) findViewById(R.id.next);

        listView =(ListView) findViewById(R.id.xmlListView);

        toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        downloadUrl(feedUrl,feedLimit);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.feeds_menu, menu);
        if(feedLimit==10)
            menu.findItem(R.id.top10).setChecked(true);
        else
            menu.findItem(R.id.top25).setChecked(true);

        return true;
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id=item.getItemId();
        Log.d(TAG, "onOptionsItemSelected: was here");
        if (id==R.id.menuFree)
            feedUrl="http://ax.itunes.apple.com/WebObjects/MZStoreServices.woa/ws/RSS/topfreeapplications/limit=%d/xml";
        else if (id==R.id.menuPaid)
            feedUrl="http://ax.itunes.apple.com/WebObjects/MZStoreServices.woa/ws/RSS/toppaidapplications/limit=%d/xml";
        else if (id==R.id.menuSong)
            feedUrl="http://ax.itunes.apple.com/WebObjects/MZStoreServices.woa/ws/RSS/topsongs/limit=%d/xml";
        else if (id==R.id.top10) {
            item.setChecked(true);
            feedLimit=10;
        } else if (id==R.id.top25) {
            item.setChecked(true);
            feedLimit=25;
        } else if (id==R.id.refresh) {
            feedCacheLimit=-1;
            feedCacheUrl="Invalid Url";
        } else
            return super.onOptionsItemSelected(item);
        downloadUrl(feedUrl,feedLimit);
        return true;
    }
    private void downloadUrl(String feedUrl,int feedLimit)
    {
        if(!feedUrl.equalsIgnoreCase(feedCacheUrl) || (feedLimit!=feedCacheLimit)) {
            String url = String.format(feedUrl, feedLimit);
            downloadData ob = new downloadData();
            ob.execute(url);
            feedCacheUrl=feedUrl;
            feedCacheLimit=feedLimit;
            return;
        }
        Log.d(TAG, "downloadUrl: no change in url");
    }
    public void setApp(ArrayList<FeedEntry> app) {
        this.app = app;
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putInt(State_limit,feedLimit);
        outState.putString(State_url,feedUrl);
        outState.putString(State_feedCacheUrl,feedCacheUrl);
        outState.putInt(State_feedCacheLimit,feedCacheLimit);
        super.onSaveInstanceState(outState);
    }



    private class downloadData extends AsyncTask<String,Void,String> {
        private static final String TAG = "downlaodData";
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);


            parse_application parseApplication=new parse_application();
            parseApplication.parse(s);
            setApp(parseApplication.getApp());
//            createView();

            FeedAdapter arrayAdapter= new FeedAdapter(
                    MainActivity.this,R.layout.list_record,parseApplication.getApp()
            );
                listView.setAdapter(arrayAdapter);
        }

        @Override
        protected String doInBackground(String... strings) {
            String rssFeed=downloadXML(strings[0]);

            return rssFeed;
        }

        private String downloadXML(String urlPath)
        {
            StringBuilder xmlResult =new StringBuilder();

            try{
                URL url =new URL(urlPath);
                HttpURLConnection connection =(HttpURLConnection) url.openConnection();
                int response=connection.getResponseCode();
                Log.d(TAG, "downloadXML: response code is"+response);
                InputStream inputStream=connection.getInputStream();
                InputStreamReader inputStreamReader=new InputStreamReader(inputStream);
                BufferedReader bufferedReader =new BufferedReader(inputStreamReader);

                int charReaded;
                char[] destBuffer= new char[8000];
                while(true)
                {
                    charReaded = bufferedReader.read(destBuffer);
                    if(charReaded<0)
                        break;
                    if(charReaded>0)
                    {
                        xmlResult.append(String.copyValueOf(destBuffer,0,charReaded));
                    }
                }
                bufferedReader.close();
                return xmlResult.toString();
            }
            catch (MalformedURLException e)
            {
                Log.e(TAG, "downloadXML: exception is : "+e.getMessage() );
            }
            catch (SecurityException e)
            {
                Log.e(TAG, "downloadXML: security Exception : " +e.getMessage() );
            }
            catch (IOException e)
            {
                Log.e(TAG, "downloadXML: IOException :"+e.getMessage() );
//                e.printStackTrace();
            }

            return null;
        }
    }







    private void createView()//this layout is created by myself
    {

        if(app!=null ) {
            ob=app.get(0);
            artist.setText(ob.getArtist());
            name.setText(ob.getName());
            summary.setText(ob.getSummary());
        }

        View.OnClickListener next= new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i = (i+1)%app.size();
                ob=app.get(i);
                artist.setText(ob.getArtist());
                name.setText(ob.getName());
                summary.setText(ob.getSummary());
            }
        };
        View.OnClickListener previous= new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i = (i-1);
                if (i<0)
                    i=app.size()-1;
                ob=app.get(i);
                artist.setText(ob.getArtist());
                name.setText(ob.getName());
                summary.setText(ob.getSummary());
            }
        };
        previousButton.setOnClickListener(previous);
        nextButton.setOnClickListener(next);
    }
    //    private void createView() //this code is for creating a layout i used
//    {
//        if(app!=null ) {
//            Log.d(TAG, "onCreate: "+app.size());
//            editText.setText(i+"\n");
//            editText.append(app.get(0).toString());
//        }
//
//        View.OnClickListener next= new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                i = (i+1)%app.size();
//                editText.setText(i+"\n");
//                editText.append(app.get(i).toString());
//            }
//        };
//        View.OnClickListener previous= new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                i = (i-1);
//                if (i<0)
//                    i=app.size()-1;
//                editText.setText(i+"\n");
//                editText.append(app.get(i).toString());
//            }
//        };
//        editText.setOnClickListener(previous);
//        previousButton.setOnClickListener(previous);
//        nextButton.setOnClickListener(next);
//    }
}






