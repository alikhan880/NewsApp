package kz.kbtu.newsapp.Adapter;

import android.support.v7.widget.RecyclerView;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Date;

import kz.kbtu.newsapp.Models.Comment;
import kz.kbtu.newsapp.R;

/**
 * Created by abakh on 04-Oct-17.
 */

public class RecyclerCommentsAdapter extends RecyclerView.Adapter<RecyclerCommentsAdapter.ViewHolder> {
    private ArrayList<Comment> comments;

    public RecyclerCommentsAdapter(ArrayList<Comment> comments) {
        this.comments = comments;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycler_view_comments, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.message.setText(comments.get(position).getMessage());
        holder.author.setText(comments.get(position).getUser().getEmail());
        holder.timeStamp.setText(DateFormat.format("dd/MM/yyyy", new Date(comments.get(position).getTimestamp())));
    }

    @Override
    public int getItemCount() {
        return comments.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView message;
        TextView author;
        TextView timeStamp;
        public ViewHolder(View itemView) {
            super(itemView);
            message = (TextView) itemView.findViewById(R.id.item_text_recycler_comments);
            author = (TextView)itemView.findViewById(R.id.item_username_comments);
            timeStamp = (TextView)itemView.findViewById(R.id.item_timestamp_comments);
        }
    }
}
