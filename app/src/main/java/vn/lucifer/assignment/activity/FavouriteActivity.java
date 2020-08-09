package vn.lucifer.assignment.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.ProgressBar;
import android.widget.SearchView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.lucifer.assignment.EndlessRecyclerViewScrollListener;
import vn.lucifer.assignment.R;
import vn.lucifer.assignment.adapter.FavouriteAdapter;
import vn.lucifer.assignment.adapter.ImagesAdapter;
import vn.lucifer.assignment.model.FavouriteExample;
import vn.lucifer.assignment.model.Gallery;
import vn.lucifer.assignment.model.ImgExample;
import vn.lucifer.assignment.model.MyRetrofitBuilder;
import vn.lucifer.assignment.model.Photo;

public class FavouriteActivity extends AppCompatActivity {

    private static final String USER_ID = "186996527@N05";
    private static final String KEY_TOKEN = "807d0a50c895734934d348ee3a46a012";
    private static final String GET_FAVO = "flickr.favorites.getList";
    private static final String FULL_EXTRAS = "views,media,path_alias,url_sq,url_t,url_s,url_q,url_m,url_n,url_z,url_c,url_l,url_o";

    private StaggeredGridLayoutManager staggeredGridLayoutManager;
    private EndlessRecyclerViewScrollListener endlessRecyclerViewScrollListener;
    private SwipeRefreshLayout srlRefeshFavou;
    private RecyclerView rvFavourite;
    public static List<Photo> photoListF;
    private FavouriteAdapter favouriteAdapter;
    private ProgressBar progressBar;
    private int pages = 1;

    //Z-index
    //android:elevation="2dp"

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourite);
        srlRefeshFavou = findViewById(R.id.srlRefeshFavou);
        rvFavourite = findViewById(R.id.rvFavourite);
        progressBar = findViewById(R.id.progressBarF);
        String po = "null";
        try {
            Intent intent = getIntent();
            Bundle bundle = intent.getExtras();
             po = bundle.getString("position","1s");

        }catch (Exception e){
            Log.e("!@#@!!@$",""+po);
        }

        FavouriteRetrofitImages();

        setLayoutManager();
        srlRefeshFavou.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                pages=1;
                progressBar.setVisibility(View.VISIBLE);
                FavouriteRetrofitImages();

                setLayoutManager();
                // thực thi lệnh loadmore khi kéo xuống
                endlessRecyclerViewScrollListener = new EndlessRecyclerViewScrollListener(staggeredGridLayoutManager) {
                    @Override
                    public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                        pages++;
                        progressBar.setVisibility(View.VISIBLE);
                        FavouriteRetrofitImages();


                    }
                };
                rvFavourite.addOnScrollListener(endlessRecyclerViewScrollListener);
                srlRefeshFavou.setRefreshing(false);
            }
        });
        // thực thi lệnh loadmore khi kéo xuống
        endlessRecyclerViewScrollListener = new EndlessRecyclerViewScrollListener(staggeredGridLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                pages++;

                progressBar.setVisibility(View.VISIBLE);
                FavouriteRetrofitImages();

            }
        };
        rvFavourite.addOnScrollListener(endlessRecyclerViewScrollListener);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.favourite_menu, menu);
        MenuItem search = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) search.getActionView();
        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {

                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.itemGallery:
                Intent intent=new Intent(FavouriteActivity.this, GalleryActivity.class);
                startActivity(intent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void FavouriteRetrofitImages() {
        MyRetrofitBuilder.getInstance().
                getFavourite(FULL_EXTRAS, "1", USER_ID, "json", KEY_TOKEN, GET_FAVO, pages,
                        10)
                .enqueue(new Callback<FavouriteExample>() {
                    @Override
                    public void onResponse(Call<FavouriteExample> call, Response<FavouriteExample> response) {

                        photoListF.addAll(response.body().getPhotos().getPhoto());


                        favouriteAdapter.notifyItemInserted(photoListF.size());
                        if (photoListF.size() == 0) {
                            rvFavourite.addOnScrollListener(null);
                        }
                        progressBar.setVisibility(View.GONE);
                    }

                    @Override
                    public void onFailure(Call<FavouriteExample> call, Throwable t) {

                    }
                });
    }

    void setLayoutManager() {
        photoListF = new ArrayList<>();
        favouriteAdapter = new FavouriteAdapter(FavouriteActivity.this, photoListF);
        rvFavourite.setHasFixedSize(true);
        rvFavourite.setAdapter(favouriteAdapter);
        staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        //staggeredGridLayoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS);
        rvFavourite.setLayoutManager(staggeredGridLayoutManager);
    }
}