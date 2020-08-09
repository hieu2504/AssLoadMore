package vn.lucifer.assignment.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.github.chrisbanes.photoview.PhotoView;

import vn.lucifer.assignment.R;
import vn.lucifer.assignment.model.Photo;


public class FavouriteImgViewsFragment extends Fragment {

    private PhotoView img;

    public FavouriteImgViewsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_favourite_img_views, container, false);
        img=view.findViewById(R.id.imgViewsF);
        String urlM=getArguments().getString("urlM","-M");
        String urlL=getArguments().getString("urlL","-L");
        if(urlL=="-L"){
            Glide.with(getActivity()).load(urlM).into(img);
        }else {
            Glide.with(getActivity()).load(urlL).into(img);
        }

        return view;
    }
}