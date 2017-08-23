package recyclerview_adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sparidapps.musicplayer.ArtistModel;
import com.sparidapps.musicplayer.R;

import java.util.List;

/**
 * Created by Tarek Zoubi on 19/8/2017.
 */

public class artists_rec_adapter extends RecyclerView.Adapter<artists_rec_adapter.MyViewHolder> {

    private List<ArtistModel> artists;
    String artist_name;
    int albums_num, tracks_num;

    class MyViewHolder extends RecyclerView.ViewHolder{
        TextView artist, tv_albums, tv_tracks;
        public MyViewHolder(View itemView){
            super(itemView);
            artist = (TextView)itemView.findViewById(R.id.track_title);
            tv_albums = (TextView) itemView.findViewById(R.id.track_artist_name);
            tv_tracks = (TextView) itemView.findViewById(R.id.duration);
        }
    }

    public artists_rec_adapter(List <ArtistModel> artists_list){
        this.artists = artists_list;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.rec_tracks, parent, false);
        MyViewHolder holder = new MyViewHolder(itemView);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        ArtistModel artistModel = artists.get(position);
        artist_name = artistModel.getName();
        albums_num = artistModel.getAlbums_num();
        tracks_num = artistModel.getTracks_num();
        holder.artist.setText(artist_name);
        holder.tv_albums.setText(String.valueOf(albums_num) + " " +  "Albums");
        holder.tv_tracks.setText(String.valueOf(tracks_num) + " " +  "Tracks");

    }

    @Override
    public int getItemCount() {
        return artists.size();
    }
}
