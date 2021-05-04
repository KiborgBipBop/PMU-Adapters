package com.example.adapters;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class FeedAdapter extends RecyclerView.Adapter<FeedAdapter.PostViewHolder>{

    class PostViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView header;
        private ImageView picture;
        private TextView description;
        OnClickListener listener;

        public PostViewHolder(View item, OnClickListener listener){
            super(item);
            header = item.findViewById(R.id.my_header);
            picture = item.findViewById(R.id.my_pic);
            description = item.findViewById(R.id.my_desc);
            this.listener = listener;

            item.setOnClickListener(this);
        }

        public void bind(Post post){
            header.setText(post.getHeader());
            picture.setImageURI(post.getImage());
            description.setText(post.getDescription());

            picture.setVisibility(post.getImage() != null ? View.VISIBLE : View.GONE);
        }

        @Override
        public void onClick(View v) {
            listener.onItemClick(getAdapterPosition());
        }
    }

    @NonNull
    @Override
    public PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull PostViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public interface OnClickListener
    {
        void onClick(View view);

        void onItemClick(int position);
    }
}
