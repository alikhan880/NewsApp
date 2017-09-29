package kz.kbtu.newsapp.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import kz.kbtu.newsapp.Models.Post;
import kz.kbtu.newsapp.R;

/**
 * Created by abakh on 28-Sep-17.
 */

public class RecyclerMainAdapter extends RecyclerView.Adapter<RecyclerMainAdapter.ViewHolder> {
    ArrayList<Post> list;
    private RecyclerMainListener listener;
    public RecyclerMainAdapter(ArrayList<Post> list, RecyclerMainListener listener) {
        this.list = list;
        this.listener = listener;
    }

    public interface RecyclerMainListener{
        void itemClicked(int position);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycler_view_main, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.message.setText(list.get(position).getMessage());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView message;
        public ViewHolder(View itemView) {
            super(itemView);
            message = (TextView) itemView.findViewById(R.id.item_text_recycler_main);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.itemClicked(getAdapterPosition());
                }
            });
        }
    }
}
