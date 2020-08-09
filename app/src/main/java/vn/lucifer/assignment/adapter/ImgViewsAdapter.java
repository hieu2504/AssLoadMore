package vn.lucifer.assignment.adapter;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import java.util.List;

import vn.lucifer.assignment.fragment.ImgViewsFragment;
import vn.lucifer.assignment.model.Photo;

public class ImgViewsAdapter extends FragmentStatePagerAdapter {
    List<Photo> photoList;

    public ImgViewsAdapter(@NonNull FragmentManager fm,List<Photo> photoList) {
        super(fm);
        this.photoList=photoList;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        ImgViewsFragment imgViewsFragment=new ImgViewsFragment();
        Bundle bundle=new Bundle();
        bundle.putString("mess","Hello "+photoList.get(position).getTitle());
        bundle.putString("url",photoList.get(position).getUrlM());
        imgViewsFragment.setArguments(bundle);

        return imgViewsFragment;

    }

    @Override
    public int getCount() {
        return photoList.size();
    }
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }
}
