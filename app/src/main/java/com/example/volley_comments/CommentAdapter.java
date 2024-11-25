package com.example.volley_comments;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.CommentViewHolder> {

    private List<Comment> commentList;
    private OnItemClickListener onItemClickListener;

    public CommentAdapter(List<Comment> commentList, OnItemClickListener onItemClickListener) {
        this.commentList = commentList;
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public CommentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_comment, parent, false);
        return new CommentViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CommentViewHolder holder, int position) {
        Comment comment = commentList.get(position);
        holder.nameTextView.setText(comment.getName());
        holder.emailTextView.setText(comment.getEmail());

        holder.itemView.setOnClickListener(v -> onItemClickListener.onItemClick(comment));
    }

    @Override
    public int getItemCount() {
        return commentList.size();
    }

    public static class CommentViewHolder extends RecyclerView.ViewHolder {

        TextView nameTextView;
        TextView emailTextView;

        public CommentViewHolder(View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.comment_name);
            emailTextView = itemView.findViewById(R.id.comment_email);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(Comment comment);
    }
}
