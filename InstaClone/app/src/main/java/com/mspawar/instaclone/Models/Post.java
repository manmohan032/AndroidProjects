package com.mspawar.instaclone.Models;

public class Post {
    private String postUrl="";
    private String postTitle="";
    private String profileLink=null;

    private String time=null;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getProfileLink() {
        return profileLink;
    }

    public void setProfileLink(String profileLink) {
        this.profileLink = profileLink;
    }

    public Post()
    {

    }
    public Post(String postUrl,String postTitle){
        this.postTitle=postTitle;
        this.postUrl=postUrl;
    }
    public String getPostUrl() {
        return postUrl;
    }

    public void setPostUrl(String postUrl) {
        this.postUrl = postUrl;
    }

    public String getPostTitle() {
        return postTitle;
    }

    public void setPostTitle(String postTitle) {
        this.postTitle = postTitle;
    }
}
