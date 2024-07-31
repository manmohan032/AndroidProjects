package com.mspawar.instaclone.Models;

public class UserPost {
    private String postUrl="";
    private String postTitle="";
    private String profileLink="";

    public String getProfileLink() {
        return profileLink;
    }

    public void setProfileLink(String profileLink) {
        this.profileLink = profileLink;
    }

    public UserPost()
    {

    }
    public UserPost(String postUrl,String postTitle){
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
