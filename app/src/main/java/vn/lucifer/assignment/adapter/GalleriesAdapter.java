package vn.lucifer.assignment.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import vn.lucifer.assignment.R;
import vn.lucifer.assignment.activity.ImagesActivity;
import vn.lucifer.assignment.model.Gallery;

public class GalleriesAdapter extends RecyclerView.Adapter<GalleriesAdapter.GalleriesHollder>{

    private List<Gallery> galleryList;
    Context context;

    public GalleriesAdapter(List<Gallery> galleryList,Context context){
        this.galleryList=galleryList;
        this.context=context;
    }

    @NonNull
    @Override
    public GalleriesHollder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.row,parent,false);
        return new GalleriesHollder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GalleriesHollder holder, int position) {
        holder.tvTitle.setText(galleryList.get(position).getTitle().getContent());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context, ImagesActivity.class);
                Bundle bundle=new Bundle();

                bundle.putString("galleryid",galleryList.get(position).getGalleryId());
                bundle.putString("name",galleryList.get(position).getTitle().getContent());
                intent.putExtras(bundle);
                context.startActivity(intent);

            }
        });

    }

    @Override
    public int getItemCount() {
        return galleryList.size();
    }

    public class GalleriesHollder extends RecyclerView.ViewHolder {
        TextView tvTitle;
        public GalleriesHollder(@NonNull View itemView) {
            super(itemView);
            tvTitle=itemView.findViewById(R.id.tvTitle);
        }
    }
}
