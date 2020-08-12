package vn.lucifer.assignment.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import vn.lucifer.assignment.R;
import vn.lucifer.assignment.model.Comment;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.CommentHolder> {

    private List<Comment> commentList;
    private Context context;
    public CommentAdapter(Context context,List<Comment> commentList){
        this.context=context;
        this.commentList=commentList;
    }

    @NonNull
    @Override
    public CommentHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.row_comment,parent,false);
        return new CommentHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CommentHolder holder, int position) {
        holder.tvName.setText(commentList.get(position).getRealname());
        holder.tvComment.setText(commentList.get(position).getContent());
    }

    @Override
    public int getItemCount() {
        return commentList.size();
    }

    public class CommentHolder extends RecyclerView.ViewHolder {
        TextView tvName,tvComment;
        public CommentHolder(@NonNull View itemView) {
            super(itemView);
            tvName=itemView.findViewById(R.id.tvName);
            tvComment=itemView.findViewById(R.id.tvComment);
        }
    }
}
