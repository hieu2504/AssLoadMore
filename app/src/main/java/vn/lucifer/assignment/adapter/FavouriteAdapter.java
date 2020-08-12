package vn.lucifer.assignment.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import vn.lucifer.assignment.R;
import vn.lucifer.assignment.activity.FavouriteImgViewsActivity;
import vn.lucifer.assignment.model.Photo;

public class FavouriteAdapter extends RecyclerView.Adapter<FavouriteAdapter.FavouriteHolder>{

    private List<Photo> photoListF;
    private List<Photo> photoListFAll;
    private Context context;
    public FavouriteAdapter(Context context,List<Photo> photoListF){
        this.context=context;
        this.photoListF=photoListF;
        photoListFAll=new ArrayList<>(photoListF);
    }
    @NonNull
    @Override
    public FavouriteHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.row_favourite,parent,false);
        return new FavouriteHolder(view);
    }

    private ConstraintSet set=new ConstraintSet();
    @Override
    public void onBindViewHolder(@NonNull FavouriteHolder holder, int position) {
        //Log.e("E<<<", photoListF.get(position).getUrlO()+"     "+position);
        holder.tvF.setText(photoListF.get(position).getViews());
        int width = Integer.parseInt(photoListF.get(position).getWidthM());
        int height = Integer.parseInt(photoListF.get(position).getHeightM());
        if (photoListF.get(position).getUrlM() == null) {
            int width1 = Integer.parseInt(photoListF.get(position).getWidthC());
            int height1 = Integer.parseInt(photoListF.get(position).getHeightC());
            String ratio1 = String.format("%d:%d", width1, height1);
            set.clone(holder.mConstraitLayoutF);
            set.setDimensionRatio(holder.imgF.getId(), ratio1);
            set.applyTo(holder.mConstraitLayoutF);
            Glide.with(context).load(photoListF.get(position).getUrlC()).into(holder.imgF);
        }
        String ratio = String.format("%d:%d", width, height);
        set.clone(holder.mConstraitLayoutF);
        set.setDimensionRatio(holder.imgF.getId(), ratio);
        set.applyTo(holder.mConstraitLayoutF);
        Glide.with(context).load(photoListF.get(position).getUrlM()).into(holder.imgF);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context, FavouriteImgViewsActivity.class);
                Bundle bundle=new Bundle();
                bundle.putInt("position",position);
                intent.putExtras(bundle);
                context.startActivity(intent);
                ((Activity)context).overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

            }


        });

    }

    @Override
    public int getItemCount() {
        return photoListF.size();
    }
    public void filterList(List<Photo> newList){
        photoListF = newList;
        notifyDataSetChanged();
    }



    public class FavouriteHolder extends RecyclerView.ViewHolder {
        private ConstraintLayout mConstraitLayoutF;
        private ImageView imgF;
        private TextView tvF;
        public FavouriteHolder(@NonNull View itemView) {
            super(itemView);
            mConstraitLayoutF=itemView.findViewById(R.id.mContraintLayoutF);
            imgF=itemView.findViewById(R.id.imgF);
            tvF=itemView.findViewById(R.id.tvTitleF);
        }
    }
}
