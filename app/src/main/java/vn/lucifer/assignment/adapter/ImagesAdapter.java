package vn.lucifer.assignment.adapter;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.recyclerview.widget.RecyclerView;


import com.bumptech.glide.Glide;

import java.util.List;
import java.util.Locale;

import vn.lucifer.assignment.R;
import vn.lucifer.assignment.model.Photo;

public class ImagesAdapter extends RecyclerView.Adapter<ImagesAdapter.ImagesHolder> {

    private List<Photo> photoList;
    Context context;
    Photo photo;

    public ImagesAdapter(List<Photo> photoList,Context context){
        this.photoList=photoList;
        this.context=context;
    }

    @NonNull
    @Override
    public ImagesHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.rowimg,parent,false);
        return new ImagesHolder(view);
    }
    public ConstraintSet set= new ConstraintSet();
    @Override
    public void onBindViewHolder(@NonNull ImagesHolder holder, int position) {



        holder.tvTitleimg.setText(photoList.get(position).getViews());



        //Set size


       // String ratio = String.format(Locale.getDefault(), "%d:%d", options.outWidth, options.outHeight);
       // set.clone(holder.mContraintLayout);
       // set.setDimensionRatio(holder.imgImages.getId(), ratio);
     //   set.applyTo(holder.mContraintLayout);



        int width = Integer.parseInt(photoList.get(position).getWidthM());
        int height=Integer.parseInt(photoList.get(position).getHeightM());
        if (photoList.get(position).getUrlL()==""){
            int width1 = Integer.parseInt(photoList.get(position).getWidthC());
            int height1=Integer.parseInt(photoList.get(position).getHeightC());
            String  ratio1 =String.format("%d:%d",width1,height1);
            set.clone(holder.mContraintLayout);
            set.setDimensionRatio(holder.imgImages.getId(), ratio1);
            set.applyTo(holder.mContraintLayout);
            Glide.with(context).load(photoList.get(position).getUrlC()).into(holder.imgImages);
        }
       String  ratio =String.format("%d:%d",width,height);
        set.clone(holder.mContraintLayout);
        set.setDimensionRatio(holder.imgImages.getId(), ratio);
      set.applyTo(holder.mContraintLayout);
        Glide.with(context).load(photoList.get(position).getUrlM()).into(holder.imgImages);


    }

    @Override
    public int getItemCount() {
        return photoList.size();
    }
    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    public class ImagesHolder extends RecyclerView.ViewHolder {
        ImageView imgImages;
        TextView tvTitleimg;
        ConstraintLayout mContraintLayout;
        public ImagesHolder(@NonNull View itemView) {
            super(itemView);
            imgImages=itemView.findViewById(R.id.imgImages);
            tvTitleimg=itemView.findViewById(R.id.tvTitleimg);
            mContraintLayout=itemView.findViewById(R.id.mContraintLayout);

        }
    }


}
