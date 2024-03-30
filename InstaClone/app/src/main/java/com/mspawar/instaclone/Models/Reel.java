package com.mspawar.instaclone.Models;

public class Reel {
    private String postUrl="";
    private String postTitle="";
//    private String profileImage
    public String getProfileLink() {
        return profileLink;
    }

    public void setProfileLink(String profileLink) {
        this.profileLink = profileLink;
    }

    private String profileLink="";
    public Reel()
    {

    }
    public Reel(String postUrl,String postTitle){
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
