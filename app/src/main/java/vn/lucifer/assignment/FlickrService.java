package vn.lucifer.assignment;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import vn.lucifer.assignment.model.CommentExample;
import vn.lucifer.assignment.model.Example;
import vn.lucifer.assignment.model.FavouriteExample;
import vn.lucifer.assignment.model.ImgExample;

public interface FlickrService {

    @GET("services/rest/")
    Call<FavouriteExample> getFavourite(@Query("extras") String extras,
                                        @Query("nojsoncallback") String nojsoncallback,
                                        @Query("user_id") String user_id,
                                        @Query("format") String format,
                                        @Query("api_key") String api_key,
                                        @Query("method") String method,
                                        @Query("page") int page,
                                        @Query("per_page") int per_page);

    @GET("services/rest/")
    Call<Example> getListGallery(@Query("user_id") String user_id,
                              @Query("api_key") String api_key,
                              @Query("nojsoncallback") String nojsoncallback,
                              @Query("format") String format,
                              @Query("method") String method,
                              @Query("page") int page,
                              @Query("per_page") int per_page);

    @GET("services/rest/")
    Call<ImgExample> getListImages(@Query("api_key") String api_key,
                                   @Query("gallery_id") String gallery_id,
                                   @Query("extras") String extras,
                                   @Query("nojsoncallback") String nojsoncallback,
                                   @Query("format") String format,
                                   @Query("method") String method,
                                   @Query("page") int page,
                                   @Query("per_page") int per_page);

    @GET("services/rest/")
    Call<CommentExample> getListComment(@Query("api_key") String api_key,
                                       @Query("photo_id") String photo_id,
                                       @Query("nojsoncallback") String nojsoncallback,
                                       @Query("format") String format,
                                       @Query("method") String method
                                       );
}
