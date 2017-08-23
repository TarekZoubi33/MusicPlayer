package recyclerview_adapters;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.sparidapps.musicplayer.AlbumModel;
import com.sparidapps.musicplayer.ArtistModel;
import com.sparidapps.musicplayer.MainActivity;
import com.sparidapps.musicplayer.R;
import com.sparidapps.musicplayer.album_activity;
import com.sparidapps.musicplayer.albums;

import java.util.List;

/**
 * Created by Tarek Zoubi on 16/8/2017.
 */

public class album_rec_adapter extends RecyclerView.Adapter<album_rec_adapter.MyViewHolder> {

    private List<AlbumModel> albums;
    String albumName, albumArtistName, albumArt;
    int  tracksNumber;
    private  View.OnClickListener mOnClickListener;

    public album_rec_adapter(List<AlbumModel> albumModels) {
       this.albums = albumModels;
    }



    class MyViewHolder extends RecyclerView.ViewHolder{
        TextView album_name, album_artistName, tracks_number;
        ImageView album_art;

        public MyViewHolder(View itemView) {
            super(itemView);
            album_name = (TextView) itemView.findViewById(R.id.album_title);
            album_artistName = (TextView) itemView.findViewById(R.id.album_artist_name);
            tracks_number = (TextView) itemView.findViewById(R.id.num_tracks);
            album_art = (ImageView) itemView.findViewById(R.id.albumArt_rec);
        }
    }

    public void setOnClickListener(View.OnClickListener callback){
        this.mOnClickListener = callback;
    }



    @Override
    public album_rec_adapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.rec_albums, parent, false);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnClickListener.onClick(v);
            }
        });
        MyViewHolder holder = new MyViewHolder(itemView);
        return holder;
    }



    @Override
    public void onBindViewHolder(album_rec_adapter.MyViewHolder holder, int position) {
        AlbumModel albumModel = albums.get(position);
        albumName = albumModel.get_AlbumName();
        albumArtistName = albumModel.get_ArtistName();
        tracksNumber = albumModel.getNumber_of_tracks();
        albumArt = albumModel.get_AlbumArt();

        holder.album_name.setText(albumName);
        holder.album_artistName.setText(albumArtistName);
        holder.tracks_number.setText(String.valueOf(tracksNumber));
        if(albumArt != null) {
            Glide.with(holder.itemView.getContext()).load(albumArt).into(holder.album_art);
        }
        else{
            Glide.with(holder.itemView.getContext()).load(R.drawable.album_art).into(holder.album_art);
        }

    }

    @Override
    public int getItemCount() {
        return albums.size();
    }


}
