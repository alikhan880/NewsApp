package kz.kbtu.newsapp.Adapter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.util.Vector;

import kz.kbtu.newsapp.Fragment.SearchFragment;
import kz.kbtu.newsapp.Models.Post;
import kz.kbtu.newsapp.R;

/**
 * Created by abakh on 21-Nov-17.
 */

public class RecyclerSearchAdapter extends RecyclerView.Adapter<RecyclerSearchAdapter.ViewHolder> {

    RecyclerSearchListener listener;
    Vector<Post> list;
    Vector<Post> favorites;

    public RecyclerSearchAdapter(RecyclerSearchListener listener, Vector<Post> list, Vector<Post> favorites) {
        this.listener = listener;
        this.list = list;
        this.favorites = favorites;
    }

    public interface RecyclerSearchListener{
        void itemClicked(int position);
        void likeClicked(int position);
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycler_view_search, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.message.setText(list.get(position).getMessage());
        holder.title.setText(list.get(position).getTitle());
        holder.tvDate.setText(list.get(position).getDate());
        Log.d("onBind", list.get(position).toString());
        if(list.get(position).getImageURL() != null && !list.get(position).getImageURL().equals("")){
            Log.d("onBind", list.get(position).getImageURL());
            Picasso.with(((SearchFragment)listener).getContext()).load(list.get(position).getImageURL())
                    .fit().centerCrop().memoryPolicy(MemoryPolicy.NO_CACHE).networkPolicy(NetworkPolicy.NO_CACHE).into(holder.ivPost);
            holder.ivPost.setVisibility(View.VISIBLE);
        }
        else{
            Picasso.with(((SearchFragment)listener).getContext()).cancelRequest(holder.ivPost);
            holder.ivPost.setVisibility(View.GONE);
        }
        if(favorites.contains(list.get(position))){
            holder.btnLike.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_heart, 0);
        }
        else{
            holder.btnLike.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_heart_without_color, 0);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        TextView message;
        Button btnLike;
        ImageView ivPost;
        TextView tvDate;
        public ViewHolder(View itemView) {
            super(itemView);
            title = (TextView)itemView.findViewById(R.id.item_title_recycler_main);
            message = (TextView) itemView.findViewById(R.id.item_text_recycler_main);
            btnLike = (Button)itemView.findViewById(R.id.item_btn_like);
            ivPost = (ImageView) itemView.findViewById(R.id.iv_item_recycler_main);
            tvDate = (TextView)itemView.findViewById(R.id.tv_date_item_recycler_main);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.itemClicked(getAdapterPosition());

                }
            });
            btnLike.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.likeClicked(getAdapterPosition());
                }
            });
        }
    }
}
