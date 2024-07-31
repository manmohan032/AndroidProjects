package com.mspawar.flikereapp;

import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class GetFlickerJsonData  implements Runnable,getData.OnDownloadData{
    private static final String TAG = "GetFlickerJsonData";

    private List<Photos> photosList=null;
    private String baseUrl;
    private String language;
    private String constrains;
    private boolean matchAll;

    private final OnDataAvailable callback;

    /**
     * If this thread was constructed using a separate
     * {@code Runnable} run object, then that
     * {@code Runnable} object's {@code run} method is called;
     * otherwise, this method does nothing and returns.
     * <p>
     * Subclasses of {@code Thread} should override this method.
     *
     * @see #start()
     * @see #stop()
     * @see #Thread(ThreadGroup, Runnable, String)
     */

    interface OnDataAvailable{
        public  void onDataAvailable(List<Photos> list,DownloadStatus downloadStatus);
    }

    public GetFlickerJsonData(String baseUrl, String language, boolean matchAll,String constrains, OnDataAvailable callback) {
        Log.d(TAG, "GetFlickerJsonData: constructor created");
        this.baseUrl = baseUrl;
        this.language = language;
        this.matchAll = matchAll;
        this.constrains=constrains;
        this.callback = callback;
    }
    @Override
    public void run() {
        Log.d(TAG, "run: "+", id "+Thread.currentThread().getId());
        executeOnSameThread();

    }

    public  void executeOnSameThread()
    {
        //this will download the raw data on same thread;
        Log.d(TAG, "executeOnSameThread: starts");
        String destinationUri=createUri(constrains,language,matchAll);

        getData ob=new getData(destinationUri,this);
        ob.run();
        Log.d(TAG, "executeOnSameThread: ends");

    }
    public  void executeOnDifferentThread()
    {
        Log.d(TAG, "executeOnSameThread: starts");
        String destinationUri=createUri(constrains,language,matchAll);
//        GetRawData ob=new GetRawData(this);
//        ob.execute(destinationUri);// this is for asyncTask
        new Thread(new getData(destinationUri,this)).start();
// if you want to make new thread for downloading the raw data;

        Log.d(TAG, "executeOnSameThread: ends");

    }
    String  createUri(String criteria,String lang ,boolean match)
    {
        return Uri.parse(baseUrl).buildUpon()
                .appendQueryParameter("tags",criteria)
            .appendQueryParameter("tagmode",match ? "ALL" :"ANY")
                .appendQueryParameter("lang",lang)
                .appendQueryParameter("format","json")
                .appendQueryParameter("nojsoncallback","1")
                .build().toString();

    }
    @Override
    public void onCompleteDownload(String data, DownloadStatus status) {

        if(status==DownloadStatus.OK)
        {
            Log.d(TAG, "onCompleteDownload: data is  1 "+", id "+Thread.currentThread().getId());
            photosList=new ArrayList<>();

            try {
                JSONObject jsonData=new JSONObject(data);
                JSONArray itemArray=jsonData.getJSONArray("items");
                for(int i=0;i<itemArray.length();i++)
                {
                    JSONObject onePhoto=itemArray.getJSONObject(i);
                    String title=onePhoto.getString("title");
                    String author=onePhoto.getString("author");
                    String author_id=onePhoto.getString("author_id");
                    String date_taken=onePhoto.getString("date_taken");
                    String tags=onePhoto.getString("tags");
//                    String link=onePhoto.getString("link");
                    String media=onePhoto.getJSONObject("media").getString("m");
                    String link =media.replaceFirst("_m","_b");

                    Photos photo=new Photos(title,author,author_id,date_taken,tags,link,media);
                    photosList.add(photo);

                    Log.d(TAG, "onCompleteDownload: download is complete a photo is : "+photo.toString() );
                }
            }
            catch (Exception e)
            {
                Log.d(TAG, "onCompleteDownload: error on json parsing");
            }

        }
        if(callback!=null)
        {
            Handler handler=new Handler(Looper.getMainLooper());
            handler.post(
                    ()->{
                        callback.onDataAvailable(photosList,status);
                    }
            );

        }
    }
}
