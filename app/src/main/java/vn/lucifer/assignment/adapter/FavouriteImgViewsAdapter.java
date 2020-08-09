package vn.lucifer.assignment.adapter;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import java.util.List;

import vn.lucifer.assignment.fragment.FavouriteImgViewsFragment;
import vn.lucifer.assignment.model.Photo;

public class FavouriteImgViewsAdapter extends FragmentStatePagerAdapter {
    List<Photo> photoListF;

    public FavouriteImgViewsAdapter(@NonNull FragmentManager fm,List<Photo> photoListF) {
        super(fm);
        this.photoListF=photoListF;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        FavouriteImgViewsFragment favouriteImgViewsFragment=new FavouriteImgViewsFragment();
        Bundle bundle=new Bundle();
        bundle.putString("urlM",photoListF.get(position).getUrlM());
        bundle.putString("urlL",photoListF.get(position).getUrlL());
        favouriteImgViewsFragment.setArguments(bundle);

        return favouriteImgViewsFragment;
    }

    @Override
    public int getCount() {
        return photoListF.size();
    }
    public int getItemPosition(Object object){
        return POSITION_NONE;
    }
}
