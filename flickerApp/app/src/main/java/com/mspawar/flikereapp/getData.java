package com.mspawar.flikereapp;
import android.os.Looper;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.Executor;
import android.os.Handler;
import android.widget.TextView;



public class getData implements Runnable{
    private static final String TAG = "getData";
    private  String strings=null;

    private DownloadStatus downloadStatus;
    interface OnDownloadData{
        public void onCompleteDownload(String data,DownloadStatus status);
    }
    OnDownloadData callBack;
    getData(String strings,    OnDownloadData callBack){
        this.strings=strings;
        this.callBack=callBack;
    }
    /**
     * Executes the given command at some time in the future.  The command
     * may execute in a new thread, in a pooled thread, or in the calling
     * thread, at the discretion of the {@code Executor} implementation.
     *
     * @param command the runnable task
     * @throws RejectedExecutionException if this task cannot be
     *                                    accepted for execution
     * @throws NullPointerException       if command is null
     */
    /**
     * When an object implementing interface <code>Runnable</code> is used
     * to create a thread, starting the thread causes the object's
     * <code>run</code> method to be called in that separately executing
     * thread.
     * <p>
     * The general contract of the method <code>run</code> is that it may
     * take any action whatsoever.
     *
     * @see Thread#run()
     */
    @Override
    public void run() {
        String ans=downloadData();
        Log.d(TAG, "run: "+", id "+Thread.currentThread().getId());
        callBack.onCompleteDownload(ans,downloadStatus);
    }
    private String downloadData(){
        HttpURLConnection connection=null;
        BufferedReader bufferedReader=null;
        if(strings==null)
        {
            downloadStatus=DownloadStatus.NOT_INITIALIZED;
        }
        try
        {
            downloadStatus=DownloadStatus.PROCESSING;
            URL url=new URL(strings);
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

            Log.d(TAG, "thread: data downloaded succesfully + id  : "+Thread.currentThread().getId() );
            return result.toString();
        }
        catch (Exception e)
        {
            Log.d(TAG, "thread have: exception  : "+e.getMessage());
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
                    Log.d(TAG, "thread: IOException occurred in closing the stream");
                }
            }
        }
        downloadStatus=DownloadStatus.FAILED_OR_EMPTY;
        return null;
    }
}
