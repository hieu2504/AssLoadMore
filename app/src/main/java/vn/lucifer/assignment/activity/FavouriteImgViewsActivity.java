package vn.lucifer.assignment.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.Manifest;
import android.app.DownloadManager;
import android.app.WallpaperManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.util.Base64;
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
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.lucifer.assignment.R;
import vn.lucifer.assignment.adapter.CommentAdapter;
import vn.lucifer.assignment.adapter.FavouriteImgViewsAdapter;
import vn.lucifer.assignment.model.Comment;
import vn.lucifer.assignment.model.CommentExample;
import vn.lucifer.assignment.model.Images;
import vn.lucifer.assignment.model.MyRetrofitBuilder;
import vn.lucifer.assignment.model.Photo;
import vn.lucifer.assignment.model.ZoomOutPageTransformer;

public class FavouriteImgViewsActivity extends AppCompatActivity {

    private ViewPager viewPagerF;
    private static final int PERMISSION_STORAGE_CODE1 = 1000;
    private static final int PERMISSION_STORAGE_CODE2 = 2000;
    private static final int PERMISSION_STORAGE_CODE3 = 3000;
    private List<Photo> photoListF;
    private Images image;
    private ArrayList<Images> listUrl;
    private FavouriteImgViewsAdapter favouriteImgViewsAdapter;
    private int position;
    FloatingActionButton action1, action2, action3, action4, action5,action6;
    private CallbackManager callbackManager;
    private static final String KEY_TOKEN = "807d0a50c895734934d348ee3a46a012";
    private static final String GET_COMMENT = "flickr.photos.comments.getList";
    private String ID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourite_img_views);
        viewPagerF=findViewById(R.id.viewpagerF);
        Intent intent=getIntent();
        position=intent.getIntExtra("position",-1);

        action1 = findViewById(R.id.action1);
        action2 = findViewById(R.id.action2);
        action3 = findViewById(R.id.action3);
        action4 = findViewById(R.id.action4);
        action5 = findViewById(R.id.action5);
        action6 = findViewById(R.id.action6);

        photoListF=FavouriteActivity.photoListF;

        favouriteImgViewsAdapter=new FavouriteImgViewsAdapter(getSupportFragmentManager(),photoListF);

        viewPagerF.setAdapter(favouriteImgViewsAdapter);

        viewPagerF.setCurrentItem(position);

        viewPagerF.setPageTransformer(true,new ZoomOutPageTransformer());



        check(position);
        Log.e("ABCCC",listUrl.size()+"");
        action1.setLabelText(listUrl.get(listUrl.size()-4).getHeight() + " x " + listUrl.get(listUrl.size()-4).getWidth());
        action2.setLabelText(listUrl.get(listUrl.size()-3).getHeight() + " x " + listUrl.get(listUrl.size()-3).getWidth());
        action3.setLabelText(listUrl.get(listUrl.size()-1).getHeight() + " x " + listUrl.get(listUrl.size()-1).getWidth());
        action4.setLabelText("Share Facebook");
        action5.setLabelText("Set Wallpaper");
        action6.setLabelText("Comment");


        viewPagerF.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int positions) {
                check(positions);
                action1.setLabelText(listUrl.get(listUrl.size()-4).getHeight() + " x " + listUrl.get(listUrl.size()-4).getWidth());
                action2.setLabelText(listUrl.get(listUrl.size()-3).getHeight() + " x " + listUrl.get(listUrl.size()-3).getWidth());
                action3.setLabelText(listUrl.get(listUrl.size()-1).getHeight() + " x " + listUrl.get(listUrl.size()-1).getWidth());
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

        action1.setOnClickListener(new View.OnClickListener() {
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
        action2.setOnClickListener(new View.OnClickListener() {
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

        action3.setOnClickListener(new View.OnClickListener() {
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
        action5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                StrictMode.setThreadPolicy(policy);
                WallpaperManager wpm = WallpaperManager.getInstance(FavouriteImgViewsActivity.this);
                InputStream ins = null;

                try {
                    ins = new URL(listUrl.get(listUrl.size() - 1).getUrl()).openStream();
                    wpm.setStream(ins);
                    Toast.makeText(FavouriteImgViewsActivity.this, "Wallpaper Changed", Toast.LENGTH_SHORT).show();
                } catch (IOException e) {
                    e.printStackTrace();

                }
            }
        });

        // FB
        callbackManager = CallbackManager.Factory.create();
        action4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                share(position);
            }
        });


        action6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final BottomSheetDialog bottomSheetDialog=new BottomSheetDialog(FavouriteImgViewsActivity.this,R.style.BottomSheetDialogTheme);
                View bottomSheetView= LayoutInflater.from(getApplicationContext())
                        .inflate(R.layout.layout_bottom_sheet
                                ,(LinearLayout)findViewById(R.id.bottomSheetContainer));

                ID=photoListF.get(position).getId();
                RecyclerView rvComment;
                rvComment=bottomSheetView.findViewById(R.id.rvComment);
                MyRetrofitBuilder.getInstance().
                        getListComment(KEY_TOKEN, ID,  "1", "json",GET_COMMENT)
                        .enqueue(new Callback<CommentExample>() {
                            @Override
                            public void onResponse(Call<CommentExample> call, Response<CommentExample> response) {

                                if (response.body().getComments().getComment() != null) {
                                    List<Comment> commentList=response.body().getComments().getComment();

                                    CommentAdapter commentAdapter=new CommentAdapter(FavouriteImgViewsActivity.this,commentList);
                                    rvComment.setHasFixedSize(true);
                                    LinearLayoutManager linearLayoutManager=new LinearLayoutManager(FavouriteImgViewsActivity.this,RecyclerView.VERTICAL,false);
                                    rvComment.setAdapter(commentAdapter);
                                    rvComment.setLayoutManager(linearLayoutManager);
                                }else {
                                    Toast.makeText(FavouriteImgViewsActivity.this,"Không có bình luận nào",Toast.LENGTH_SHORT).show();
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }
    private void share(int position){
        ShareLinkContent content=new ShareLinkContent.Builder()
                .setContentUrl(Uri.parse(photoListF.get(position).getUrlM())).build();

        ShareDialog shareDialog=new ShareDialog(this);
        shareDialog.show(content,ShareDialog.Mode.AUTOMATIC);
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
    }


    private void check(int position) {
       // photoListF=FavouriteActivity.photoListF;
        listUrl = new ArrayList<>();

        if (photoListF.get(position).getUrlS() != null) {
            image = new Images();
            image.setUrl(photoListF.get(position).getUrlS());
            image.setWidth(photoListF.get(position).getWidthS().toString());
            image.setHeight(photoListF.get(position).getHeightS().toString());
            image.setTitle(photoListF.get(position).getTitle());
            listUrl.add(image);
        }
        if (photoListF.get(position).getUrlQ() != null) {
            image = new Images();
            image.setUrl(photoListF.get(position).getUrlQ());
            image.setWidth(photoListF.get(position).getWidthQ().toString());
            image.setHeight(photoListF.get(position).getHeightQ().toString());
            image.setTitle(photoListF.get(position).getTitle());
            listUrl.add(image);
        }
        if (photoListF.get(position).getUrlM() != null) {
            image = new Images();
            image.setUrl(photoListF.get(position).getUrlM());
            image.setWidth(photoListF.get(position).getWidthM().toString());
            image.setHeight(photoListF.get(position).getHeightM().toString());
            image.setTitle(photoListF.get(position).getTitle());
            listUrl.add(image);
        }
        if (photoListF.get(position).getUrlN() != null) {
            image = new Images();
            image.setUrl(photoListF.get(position).getUrlN());
            image.setWidth(photoListF.get(position).getWidthN().toString());
            image.setHeight(photoListF.get(position).getHeightN().toString());
            image.setTitle(photoListF.get(position).getTitle());
            listUrl.add(image);
        }
        if (photoListF.get(position).getUrlZ() != null) {
            image = new Images();
            image.setUrl(photoListF.get(position).getUrlZ());
            image.setWidth(photoListF.get(position).getWidthZ().toString());
            image.setHeight(photoListF.get(position).getHeightZ().toString());
            image.setTitle(photoListF.get(position).getTitle());
            listUrl.add(image);
        }
        if (photoListF.get(position).getUrlC() != null) {
            image = new Images();
            image.setUrl(photoListF.get(position).getUrlC());
            image.setWidth(photoListF.get(position).getWidthC().toString());
            image.setHeight(photoListF.get(position).getHeightC().toString());
            image.setTitle(photoListF.get(position).getTitle());
            listUrl.add(image);
        }
        if (photoListF.get(position).getUrlL() != null) {
            image = new Images();
            image.setUrl(photoListF.get(position).getUrlL());
            image.setWidth(photoListF.get(position).getWidthL().toString());
            image.setHeight(photoListF.get(position).getHeightL().toString());
            image.setTitle(photoListF.get(position).getTitle());
            listUrl.add(image);
        }
        if (photoListF.get(position).getUrlO() != null) {
            image = new Images();
            image.setUrl(photoListF.get(position).getUrlO());
            image.setWidth(photoListF.get(position).getWidthO().toString());
            image.setHeight(photoListF.get(position).getHeightO().toString());
            image.setTitle(photoListF.get(position).getTitle());
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