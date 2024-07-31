package com.example.top10downloader;

import android.nfc.Tag;
import android.util.Log;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;

public class parse_application {
    private static final String TAG = "parse_application";
    private ArrayList<FeedEntry> app;

    public parse_application(){
        this.app =new ArrayList<>();
    }

    public ArrayList<FeedEntry> getApp() {
        return app;
    }

    public boolean parse(String xmlData){
        boolean status=true;
        FeedEntry currentFeed=null;
        boolean inEntry=false;
        String textValue="";

        try {
            XmlPullParserFactory factory=XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(true);
            XmlPullParser xmlPullParser= factory.newPullParser();
            xmlPullParser.setInput(new StringReader(xmlData));
            int event= xmlPullParser.getEventType();
            boolean rightSize=false;
            int c=0;
            while(event!=XmlPullParser.END_DOCUMENT)
            {
                String tagName=xmlPullParser.getName();
                switch (event)
                {
                    case XmlPullParser.START_TAG:
                        if("entry".equalsIgnoreCase(tagName))
                        {
                            Log.d(TAG, "parse: starting tag for : "+tagName+"  "+ c);
                            inEntry=true;
                            currentFeed=new FeedEntry();
                        }
                        else if("image".equalsIgnoreCase(tagName))
                        {
                            String resolution_53=xmlPullParser.getAttributeValue(null,"height");
                            if(resolution_53!=null)
                            {
                                rightSize="53".equalsIgnoreCase(resolution_53);
                            }
                        }
                        break;
                    case XmlPullParser.TEXT:
                        textValue=xmlPullParser.getText();
                        break;

                    case XmlPullParser.END_TAG:
                        if(inEntry)
                        {
                            if("entry".equalsIgnoreCase(tagName))
                            {
                                Log.d(TAG, "parse: ending tag for : "+tagName + " "+c);
                                c++;
                                app.add(currentFeed);
                                inEntry=false;
                            }
                            else if("title".equalsIgnoreCase(tagName))
                                currentFeed.setTitle(textValue);
                            else if("summary".equalsIgnoreCase(tagName))
                                currentFeed.setSummary(textValue);
                            else if("name".equalsIgnoreCase(tagName))
                                currentFeed.setName(textValue);
                            else if("artist".equalsIgnoreCase(tagName))
                                currentFeed.setArtist(textValue);
                            else if("image".equalsIgnoreCase(tagName) && rightSize) {
                                currentFeed.setImgUrl(textValue);
                                rightSize=false;
                            }
                            else if("releaseDate".equalsIgnoreCase(tagName) )
                                currentFeed.setReleaseDate(textValue);
                        }
                        break;
                    default:
                        //no code
                }

                event=xmlPullParser.next();
            }

        }
        catch (Exception e)
        {
            status=false;
            Log.d(TAG, "parse: error in parsing file + "+e.getMessage());
        }
//        for(FeedEntry ob : app)
//        {
//            Log.d(TAG, "\n****************\n");
//            Log.d(TAG, ob.toString());
//            Log.d(TAG, "\n****************\n");
//        }
        return  status;
    }
}
