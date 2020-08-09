package vn.lucifer.assignment.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;

import java.util.List;

import vn.lucifer.assignment.adapter.ImgViewsAdapter;
import vn.lucifer.assignment.R;
import vn.lucifer.assignment.model.Photo;
import vn.lucifer.assignment.model.ZoomOutPageTransformer;

public class ImgViewsActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private ImgViewsAdapter imgViewsAdapter;
    private List<Photo> photoList;
    private int position;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_img_views);
        viewPager=findViewById(R.id.viewpager);
        Intent intent=getIntent();
        position=intent.getIntExtra("position",-1);

        photoList= ImagesActivity.photoList;

        imgViewsAdapter=new ImgViewsAdapter(getSupportFragmentManager(),photoList);

        viewPager.setAdapter(imgViewsAdapter);

        viewPager.setCurrentItem(position);

        viewPager.setPageTransformer(true, new ZoomOutPageTransformer());










    }

}