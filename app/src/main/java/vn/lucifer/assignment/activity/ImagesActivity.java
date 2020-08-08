package vn.lucifer.assignment.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.lucifer.assignment.EndlessRecyclerViewScrollListener;
import vn.lucifer.assignment.R;
import vn.lucifer.assignment.adapter.ImagesAdapter;
import vn.lucifer.assignment.model.ImgExample;
import vn.lucifer.assignment.model.MyRetrofitBuilder;
import vn.lucifer.assignment.model.Photo;

public class ImagesActivity extends AppCompatActivity {

    private static final String USER_ID = "186996527@N05";
    private static final String KEY_TOKEN = "807d0a50c895734934d348ee3a46a012";
    private static final String GET_IMAGES = "flickr.galleries.getPhotos";
    private static final String FULL_EXTRAS = "views,media,path_alias,url_sq,url_t,url_s,url_q,url_m,url_n,url_z,url_c,url_l,url_o";

    RecyclerView rvImages;
    ImagesAdapter imagesAdapter;
    private StaggeredGridLayoutManager staggeredGridLayoutManager;
    private SwipeRefreshLayout swipeRefreshLayout;
    private ProgressBar progressBar;
    private int pages = 1;
    private int per_page=10;
    public static List<Photo> photoList;
    private EndlessRecyclerViewScrollListener endlessRecyclerViewScrollListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_images);
        rvImages=findViewById(R.id.rvImages);
        swipeRefreshLayout=findViewById(R.id.srlRefesh);
        progressBar=findViewById(R.id.progressBar);

        RetrofitImages();

        setLayoutManager();
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                pages=1;
                progressBar.setVisibility(View.VISIBLE);
                RetrofitImages();

               setLayoutManager();
                // thực thi lệnh loadmore khi kéo xuống
                endlessRecyclerViewScrollListener = new EndlessRecyclerViewScrollListener(staggeredGridLayoutManager) {
                    @Override
                    public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                        pages++;
                        progressBar.setVisibility(View.VISIBLE);
                        RetrofitImages();


                    }
                };
                rvImages.addOnScrollListener(endlessRecyclerViewScrollListener);
                swipeRefreshLayout.setRefreshing(false);
            }
        });
        // thực thi lệnh loadmore khi kéo xuống
        endlessRecyclerViewScrollListener = new EndlessRecyclerViewScrollListener(staggeredGridLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                pages++;

                progressBar.setVisibility(View.VISIBLE);
                RetrofitImages();

            }
        };
        rvImages.addOnScrollListener(endlessRecyclerViewScrollListener);



    }
    private void RetrofitImages(){
        Intent intent = getIntent();
        Bundle  bundle = intent.getExtras();
        String galleryid = bundle.getString("galleryid");
        MyRetrofitBuilder.getInstance().
                getListImages(KEY_TOKEN, galleryid, FULL_EXTRAS, "1", "json", GET_IMAGES,pages,10)
                .enqueue(new Callback<ImgExample>() {
                    @Override
                    public void onResponse(Call<ImgExample> call, Response<ImgExample> response) {

                        photoList.addAll( response.body().getPhotos().getPhoto());


                        imagesAdapter.notifyItemInserted(photoList.size());
                        if (photoList.size() == 0) {
                            rvImages.addOnScrollListener(null);
                        }
                        progressBar.setVisibility(View.GONE);
                    }

                    @Override
                    public void onFailure(Call<ImgExample> call, Throwable t) {

                    }
                });
    }
    void setLayoutManager() {
        photoList=new ArrayList<>();
        imagesAdapter = new ImagesAdapter(photoList, ImagesActivity.this);
        rvImages.setHasFixedSize(true);
        rvImages.setAdapter(imagesAdapter);
        staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        //staggeredGridLayoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS);
        rvImages.setLayoutManager(staggeredGridLayoutManager);
    }
//    void setAdapter() {
//        imagesAdapter = new ImagesAdapter(photoList, ImagesActivity.this);
//        rvImages.setAdapter(imagesAdapter);
//    }
}