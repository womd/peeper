package org.chk.peeper.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import org.chk.peeper.R;
import org.chk.peeper.dto.MediaItem;

import java.util.List;

public class SongViewAdapter extends RecyclerView.Adapter<SongViewAdapter.ViewHolder> {

    private List<MediaItem> mData;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;
    private ItemClickListener mLongClickListener;

    // data is passed into the constructor
    public SongViewAdapter(Context context, List<MediaItem> data) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
    }

    // inflates the row layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.songview_row, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        MediaItem mediaItem = mData.get(position);
        holder.songName.setText(mediaItem.getName());
        holder.songDuration.setText(  String.valueOf(mediaItem.getDuration() / 1000) + " sec" );
        holder.songSize.setText(String.valueOf(mediaItem.getSize() / 1024) + " kB");
        holder.songAlbum.setText(mediaItem.getAlbum());
        holder.songArtist.setText(mediaItem.getArtist());

    }

    // total number of rows
    @Override
    public int getItemCount() {
        return mData.size();
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener{
        TextView songName;
        TextView songDuration;
        TextView songSize;
        TextView songAlbum;
        TextView songArtist;

        ViewHolder(View itemView) {
            super(itemView);
            songName = itemView.findViewById(R.id.name);
            songDuration = itemView.findViewById(R.id.duration);
            songSize = itemView.findViewById(R.id.size);
            songArtist = itemView.findViewById(R.id.artist);
            songAlbum = itemView.findViewById(R.id.album);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null){
                mClickListener.onItemClick(view, getAdapterPosition());
            }
        }

        @Override
        public boolean onLongClick(View view){
            if(mLongClickListener != null){
                mLongClickListener.onItemLongClick(view, getAdapterPosition());
                return true;
            }
            return false;
        }
    }

    // convenience method for getting data at click position
    public MediaItem getItem(int id) {
        return mData.get(id);
    }

    // allows clicks events to be caught
    public void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }
    public boolean setLongClickListener(ItemClickListener itemLongClickListener) { this.mLongClickListener = itemLongClickListener; return true;}


    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
        boolean onItemLongClick(View view, int position);
    }
}
