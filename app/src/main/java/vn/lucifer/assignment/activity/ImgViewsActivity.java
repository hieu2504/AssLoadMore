package vn.lucifer.assignment.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import androidx.viewpager.widget.ViewPager;

import android.Manifest;
import android.app.DownloadManager;
import android.app.WallpaperManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareDialog;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.lucifer.assignment.adapter.CommentAdapter;
import vn.lucifer.assignment.adapter.GalleriesAdapter;
import vn.lucifer.assignment.adapter.ImgViewsAdapter;
import vn.lucifer.assignment.R;
import vn.lucifer.assignment.model.Comment;
import vn.lucifer.assignment.model.CommentExample;
import vn.lucifer.assignment.model.Gallery;
import vn.lucifer.assignment.model.Images;
import vn.lucifer.assignment.model.ImgExample;
import vn.lucifer.assignment.model.MyRetrofitBuilder;
import vn.lucifer.assignment.model.Photo;
import vn.lucifer.assignment.model.ZoomOutPageTransformer;

public class ImgViewsActivity extends AppCompatActivity {

    private static final int PERMISSION_STORAGE_CODE1 = 1000;
    private static final int PERMISSION_STORAGE_CODE2 = 2000;
    private static final int PERMISSION_STORAGE_CODE3 = 3000;
    private ArrayList<Images> listUrl;
    FloatingActionButton action1G, action2G, action3G, action4G, action5G, action6G;
    private ViewPager viewPager;
    private ImgViewsAdapter imgViewsAdapter;
    private List<Photo> photoList;
    private int position;
    private CallbackManager callbackManager;
    private Images image;
    private static final String KEY_TOKEN = "807d0a50c895734934d348ee3a46a012";
    private static final String GET_COMMENT = "flickr.photos.comments.getList";
    private String ID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_img_views);
        viewPager=findViewById(R.id.viewpager);
        Intent intent=getIntent();
        position=intent.getIntExtra("position",-1);

        action1G = findViewById(R.id.action1G);
        action2G = findViewById(R.id.action2G);
        action3G = findViewById(R.id.action3G);
        action4G = findViewById(R.id.action4G);
        action5G = findViewById(R.id.action5G);
        action6G=findViewById(R.id.action6G);

        photoList= ImagesActivity.photoList;


        imgViewsAdapter=new ImgViewsAdapter(getSupportFragmentManager(),photoList);

        viewPager.setAdapter(imgViewsAdapter);

        viewPager.setCurrentItem(position);

        viewPager.setPageTransformer(true, new ZoomOutPageTransformer());
        check(position);
        action1G.setLabelText(listUrl.get(listUrl.size()-4).getHeight() + " x " + listUrl.get(listUrl.size()-4).getWidth());
        action2G.setLabelText(listUrl.get(listUrl.size()-3).getHeight() + " x " + listUrl.get(listUrl.size()-3).getWidth());
        action3G.setLabelText(listUrl.get(listUrl.size()-1).getHeight() + " x " + listUrl.get(listUrl.size()-1).getWidth());
        action4G.setLabelText("Share Facebook");
        action5G.setLabelText("Set Wallpaper");
        action6G.setLabelText("Comment");
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int positions) {
                check(positions);
                action1G.setLabelText(listUrl.get(listUrl.size()-4).getHeight() + " x " + listUrl.get(listUrl.size()-4).getWidth());
                action2G.setLabelText(listUrl.get(listUrl.size()-3).getHeight() + " x " + listUrl.get(listUrl.size()-3).getWidth());
                action3G.setLabelText(listUrl.get(listUrl.size()-1).getHeight() + " x " + listUrl.get(listUrl.size()-1).getWidth());
                //share face cân dùng
                position=positions;
                FloatingActionMenu floatingActionMenu=findViewById(R.id.floatingActionMenu);
                if(floatingActionMenu.isOpened()){
                    floatingActionMenu.close(false);
                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        action1G.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
                        //permission denied request it
                        String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
                        requestPermissions(permissions, PERMISSION_STORAGE_CODE1);
                    } else {
                        startDownLoading1();
                    }
                } else {
                    startDownLoading1();
                }
            }
        });
        action2G.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
                        //permission denied request it
                        String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
                        requestPermissions(permissions, PERMISSION_STORAGE_CODE2);
                    } else {
                        startDownLoading2();
                    }
                } else {
                    startDownLoading2();
                }
            }
        });

        action3G.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
                        //permission denied request it
                        String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
                        requestPermissions(permissions, PERMISSION_STORAGE_CODE3);
                    } else {
                        startDownLoading3();
                    }
                } else {
                    startDownLoading3();
                }
            }
        });

        // set images
        action5G.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                StrictMode.setThreadPolicy(policy);
                WallpaperManager wpm = WallpaperManager.getInstance(ImgViewsActivity.this);
                InputStream ins = null;

                try {
                    ins = new URL(listUrl.get(listUrl.size() - 1).getUrl()).openStream();
                    wpm.setStream(ins);
                    Toast.makeText(ImgViewsActivity.this, "Wallpaper Changed", Toast.LENGTH_SHORT).show();
                } catch (IOException e) {
                    e.printStackTrace();

                }
            }
        });

        // FB
        callbackManager = CallbackManager.Factory.create();
        action4G.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                share(position);
            }
        });


        action6G.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               final BottomSheetDialog bottomSheetDialog=new BottomSheetDialog(ImgViewsActivity.this,R.style.BottomSheetDialogTheme);
                View bottomSheetView= LayoutInflater.from(getApplicationContext())
                        .inflate(R.layout.layout_bottom_sheet
                        ,(LinearLayout)findViewById(R.id.bottomSheetContainer));

                ID=photoList.get(position).getId();
                RecyclerView rvComment;
                rvComment=bottomSheetView.findViewById(R.id.rvComment);
                MyRetrofitBuilder.getInstance().
                        getListComment(KEY_TOKEN, ID,  "1", "json",GET_COMMENT)
                        .enqueue(new Callback<CommentExample>() {
                            @Override
                            public void onResponse(Call<CommentExample> call, Response<CommentExample> response) {

                                if (response.body().getComments().getComment() != null) {
                                    List<Comment> commentList=response.body().getComments().getComment();

                                    CommentAdapter commentAdapter=new CommentAdapter(ImgViewsActivity.this,commentList);
                                    rvComment.setHasFixedSize(true);
                                    LinearLayoutManager linearLayoutManager=new LinearLayoutManager(ImgViewsActivity.this,RecyclerView.VERTICAL,false);
                                    rvComment.setAdapter(commentAdapter);
                                    rvComment.setLayoutManager(linearLayoutManager);
                                }else {
                                   Toast.makeText(ImgViewsActivity.this,"Không có bình luận nào",Toast.LENGTH_SHORT).show();
                                }




                            }

                            @Override
                            public void onFailure(Call<CommentExample> call, Throwable t) {

                            }
                        });

                bottomSheetDialog.setContentView(bottomSheetView);
                bottomSheetDialog.show();

            }

        });





    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }
    public void share(int position){
        ShareLinkContent content=new ShareLinkContent.Builder()
                .setContentUrl(Uri.parse(photoList.get(position).getUrlM())).build();

        ShareDialog shareDialog=new ShareDialog(this);
        shareDialog.show(content,ShareDialog.Mode.AUTOMATIC);
    }


    private void check(int position) {
        // photoListF=FavouriteActivity.photoListF;
        listUrl = new ArrayList<>();

        if (photoList.get(position).getUrlS() != null) {
            image = new Images();
            image.setUrl(photoList.get(position).getUrlS());
            image.setWidth(photoList.get(position).getWidthS().toString());
            image.setHeight(photoList.get(position).getHeightS().toString());
            image.setTitle(photoList.get(position).getTitle());
            listUrl.add(image);
        }
        if (photoList.get(position).getUrlQ() != null) {
            image = new Images();
            image.setUrl(photoList.get(position).getUrlQ());
            image.setWidth(photoList.get(position).getWidthQ().toString());
            image.setHeight(photoList.get(position).getHeightQ().toString());
            image.setTitle(photoList.get(position).getTitle());
            listUrl.add(image);
        }
        if (photoList.get(position).getUrlM() != null) {
            image = new Images();
            image.setUrl(photoList.get(position).getUrlM());
            image.setWidth(photoList.get(position).getWidthM().toString());
            image.setHeight(photoList.get(position).getHeightM().toString());
            image.setTitle(photoList.get(position).getTitle());
            listUrl.add(image);
        }
        if (photoList.get(position).getUrlN() != null) {
            image = new Images();
            image.setUrl(photoList.get(position).getUrlN());
            image.setWidth(photoList.get(position).getWidthN().toString());
            image.setHeight(photoList.get(position).getHeightN().toString());
            image.setTitle(photoList.get(position).getTitle());
            listUrl.add(image);
        }
        if (photoList.get(position).getUrlZ() != null) {
            image = new Images();
            image.setUrl(photoList.get(position).getUrlZ());
            image.setWidth(photoList.get(position).getWidthZ().toString());
            image.setHeight(photoList.get(position).getHeightZ().toString());
            image.setTitle(photoList.get(position).getTitle());
            listUrl.add(image);
        }
        if (photoList.get(position).getUrlC() != null) {
            image = new Images();
            image.setUrl(photoList.get(position).getUrlC());
            image.setWidth(photoList.get(position).getWidthC().toString());
            image.setHeight(photoList.get(position).getHeightC().toString());
            image.setTitle(photoList.get(position).getTitle());
            listUrl.add(image);
        }
        if (photoList.get(position).getUrlL() != null) {
            image = new Images();
            image.setUrl(photoList.get(position).getUrlL());
            image.setWidth(photoList.get(position).getWidthL().toString());
            image.setHeight(photoList.get(position).getHeightL().toString());
            image.setTitle(photoList.get(position).getTitle());
            listUrl.add(image);
        }
        if (photoList.get(position).getUrlO() != null) {
            image = new Images();
            image.setUrl(photoList.get(position).getUrlO());
            image.setWidth(photoList.get(position).getWidthO().toString());
            image.setHeight(photoList.get(position).getHeightO().toString());
            image.setTitle(photoList.get(position).getTitle());
            listUrl.add(image);
        }

    }

    private void startDownLoading1() {

        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(listUrl.get(listUrl.size() - 4).getUrl()));
        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE);
        request.setTitle("BackgroundHD" + listUrl.get(listUrl.size() - 4).getUrl().substring(35));
        request.setDescription(listUrl.get(listUrl.size() - 4).getUrl());
        Toast.makeText(this, "Downloading...!", Toast.LENGTH_SHORT).show();
        request.allowScanningByMediaScanner();
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "/lucifer" + System.currentTimeMillis());
        DownloadManager downloadManager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
        downloadManager.enqueue(request);
    }

    private void startDownLoading2() {
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(listUrl.get(listUrl.size() - 3).getUrl()));
        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE);
        request.setTitle("BackgroundHD" + listUrl.get(listUrl.size() - 3).getUrl().substring(35));
        request.setDescription(listUrl.get(listUrl.size() - 3).getUrl());
        Toast.makeText(this, "Downloading...!", Toast.LENGTH_SHORT).show();
        request.allowScanningByMediaScanner();
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "/lucifer" + System.currentTimeMillis());
        DownloadManager downloadManager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
        downloadManager.enqueue(request);
    }

    private void startDownLoading3() {

        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(listUrl.get(listUrl.size() - 1).getUrl()));
        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE);
        request.setTitle("BackgroundHD" + listUrl.get(listUrl.size() - 1).getUrl().substring(35));
        request.setDescription(listUrl.get(listUrl.size() - 1).getUrl());
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "/lucifer" + System.currentTimeMillis());
        Toast.makeText(this, "Downloading...!", Toast.LENGTH_SHORT).show();
        request.allowScanningByMediaScanner();
        DownloadManager downloadManager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
        downloadManager.enqueue(request);

    }


}