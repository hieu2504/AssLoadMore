package vn.lucifer.assignment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;


public class ImgViewsFragment extends Fragment {
private TextView textView;
private ImageView imageView;
    public ImgViewsFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_img_views, container, false);
        textView=view.findViewById(R.id.tvImgViews);
        imageView=view.findViewById(R.id.imgViews);
        String mess=getArguments().getString("mess");
        String url=getArguments().getString("url");
        Glide.with(getActivity()).load(url).into(imageView);


        textView.setText(mess);
        return view;
    }
}