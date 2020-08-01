package vn.lucifer.assignment.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Gallery {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("gallery_id")
    @Expose
    private String galleryId;
    @SerializedName("url")
    @Expose
    private String url;

    @SerializedName("title")
    @Expose
    private Title title;




    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGalleryId() {
        return galleryId;
    }

    public void setGalleryId(String galleryId) {
        this.galleryId = galleryId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }



    public Title getTitle() {
        return title;
    }

    public void setTitle(Title title) {
        this.title = title;
    }



}