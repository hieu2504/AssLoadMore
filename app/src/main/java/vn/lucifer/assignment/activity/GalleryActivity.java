package vn.lucifer.assignment.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.os.Bundle;
import android.widget.ImageView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.lucifer.assignment.adapter.GalleriesAdapter;
import vn.lucifer.assignment.model.MyRetrofitBuilder;
import vn.lucifer.assignment.R;
import vn.lucifer.assignment.model.Example;
import vn.lucifer.assignment.model.Gallery;

public class GalleryActivity extends AppCompatActivity implements Callback<Example> {

    private static final String USER_ID = "186996527@N05";
    private static final String KEY_TOKEN = "807d0a50c895734934d348ee3a46a012";
    private static final String GET_GALLERIES = "flickr.galleries.getList";
    private int page = 1;
    GalleriesAdapter galleriesAdapter;
    RecyclerView rvGalleries;

    ImageView img;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);
        rvGalleries = findViewById(R.id.rvGalleries);

        MyRetrofitBuilder.getInstance().
                getListGallery(USER_ID, KEY_TOKEN, "1", "json", GET_GALLERIES, page, 20)
                .enqueue(this);
    }

    @Override
    public void onResponse(Call<Example> call, Response<Example> response) {
        List<Gallery> galleryList=response.body().getGalleries().getGallery();
        rvGalleries.setHasFixedSize(true);
        galleriesAdapter = new GalleriesAdapter(galleryList, GalleryActivity.this);
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);

        rvGalleries.setLayoutManager(staggeredGridLayoutManager);
        rvGalleries.setAdapter(galleriesAdapter);

    }

    @Override
    public void onFailure(Call<Example> call, Throwable t) {

    }
}