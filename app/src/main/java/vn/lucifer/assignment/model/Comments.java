package vn.lucifer.assignment.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Comments {
    @SerializedName("photo_id")
    @Expose
    private String photoId;
    @SerializedName("comment")
    @Expose
    private List<Comment> comment = null;

    public String getPhotoId() {
        return photoId;
    }

    public void setPhotoId(String photoId) {
        this.photoId = photoId;
    }

    public List<Comment> getComment() {
        return comment;
    }

    public void setComment(List<Comment> comment) {
        this.comment = comment;
    }
}
