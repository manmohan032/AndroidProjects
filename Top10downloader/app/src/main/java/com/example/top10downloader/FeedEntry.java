package com.example.top10downloader;

public class FeedEntry {
    private String name;
    private String artist;
    private String imgUrl;
    private String title;

    private String summary;
    private String releaseDate;

    public String getName() {
        return name;
    }

    public String getArtist() {
        return artist;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public String getTitle() {
        return title;
    }

    public String getSummary() {
        return summary;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    @Override
    public String toString() {
        return
                "title="+title+"\n"+
                ", artist=" + artist + "\n" +
                ", imgUrl=" + imgUrl + "\n" +
                ", releaseDate=" + releaseDate + "\n" ;
    }
}
