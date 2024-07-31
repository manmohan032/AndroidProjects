package com.mspawar.flikereapp;

import android.util.Log;

import java.net.URL;

public class Photos {
    private String title;
    private String author;
    private String authorID;
    private String tags;
    private String link;
    private String date;
    private String image;
    private static final String TAG = "Photos";
    public Photos(String title, String author, String authorID,String date, String tags, String link, String image) {
        Log.d(TAG, "Photos: constructer called");
        this.title = title;
        this.author = author;
        this.authorID = authorID;
        this.tags = tags;
        this.link = link;
        this.date=date;
        this.image = image;
    }


    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getAuthorID() {
        return authorID;
    }

    public String getTags() {
        return tags;
    }

    public String getLink() {
        return link;
    }

    public String getDate() {
        return date;
    }

    public String getImage() {
        return image;
    }

    @Override
    public String toString() {
        return "Photos{" +
                "title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", authorID='" + authorID + '\'' +
                ", tags='" + tags + '\'' +
                ", link='" + link + '\'' +
                ", image=" + image +
                '}';
    }
}
