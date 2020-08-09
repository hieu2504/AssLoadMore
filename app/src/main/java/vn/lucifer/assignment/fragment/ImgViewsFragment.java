package vn.lucifer.assignment.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.github.chrisbanes.photoview.PhotoView;

import vn.lucifer.assignment.R;


public class ImgViewsFragment extends Fragment {
private TextView textView;
private PhotoView imageView;
    public ImgViewsFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_img_views, container, false);
        imageView=view.findViewById(R.id.imgViews);

        String url=getArguments().getString("url");
        Glide.with(getActivity()).load(url).into(imageView);



        return view;
    }
}