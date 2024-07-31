package com.mspawar.flikereapp;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

enum DownloadStatus{IDLE,PROCESSING,NOT_INITIALIZED,FAILED_OR_EMPTY,OK}
public class GetRawData extends AsyncTask<String,Void,String> {
    private static final String TAG = "getRawData";

    interface OnDownloadData{
        public void onCompleteDownload(String data,DownloadStatus status);
    }

    private DownloadStatus downloadStatus;
    OnDownloadData callBack;
    public GetRawData(OnDownloadData callBack)
    {
        this.callBack=callBack;
        this.downloadStatus=DownloadStatus.IDLE;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);


//        Log.d(TAG, "onPostExecute: string downloaded : "+s);
        callBack.onCompleteDownload(s,downloadStatus);
    }


    @Override
    protected String doInBackground(String... strings) {
        HttpURLConnection connection=null;
        BufferedReader bufferedReader=null;
        if(strings==null)
        {
            downloadStatus=DownloadStatus.NOT_INITIALIZED;
            return null;
        }
        try
        {
            downloadStatus=DownloadStatus.PROCESSING;
            URL url=new URL(strings[0]);
            connection=(HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            int response=connection.getResponseCode();
            StringBuilder result=new StringBuilder();
            bufferedReader=new BufferedReader(new InputStreamReader(connection.getInputStream()));

            String line=bufferedReader.readLine();
            while (null!=line){

                result.append(line).append('\n');
                line=bufferedReader.readLine();
            }
            downloadStatus=DownloadStatus.OK;
            Log.d(TAG, "doInBackground: data downloaded succesfully" );
            return  result.toString();
        }
        catch (Exception e)
        {
            Log.d(TAG, "doInBackground: exception  : "+e.getMessage());
        }
        finally {
            if(connection!=null)
            {
                connection.disconnect();
            }
            if(bufferedReader!=null)
            {
                try {
                    bufferedReader.close();
                }
                catch (IOException e)
                {
                    Log.d(TAG, "doInBackground: IOException occurred in closing the stream");
                }
            }
        }
        downloadStatus=DownloadStatus.FAILED_OR_EMPTY;
        return null;
    }
}
