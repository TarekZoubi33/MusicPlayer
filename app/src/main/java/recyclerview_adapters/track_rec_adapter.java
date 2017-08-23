package recyclerview_adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sparidapps.musicplayer.R;
import com.sparidapps.musicplayer.TrackModel;

import java.util.List;
import java.util.Locale;

/**
 * Created by Tarek Zoubi on 13/8/2017.
 */

public class track_rec_adapter extends RecyclerView.Adapter<track_rec_adapter.MyViewHolder> {


    private List<TrackModel> tracks;
    String trackName, artistName, trackDuration;


    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView track_title, artist_name, track_duration;
        public MyViewHolder(View itemView) {
            super(itemView);
            track_title = (TextView) itemView.findViewById(R.id.track_title);
            artist_name = (TextView) itemView.findViewById(R.id.track_artist_name);
            track_duration = (TextView) itemView.findViewById(R.id.duration);
        }
    }

    public track_rec_adapter(List<TrackModel> tracks){
        this.tracks = tracks;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.rec_tracks, parent, false);
        MyViewHolder holder = new MyViewHolder(itemView);

        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
       TrackModel trackModel = tracks.get(position);
        trackName = trackModel.getaName();
        artistName = trackModel.getaArtist();
        trackDuration = trackModel.getaDuration();
        int duration_s = Integer.parseInt(trackDuration) / 1000;
        int duration_m = duration_s / 60;
        duration_s = duration_s % 60;
        trackDuration = String.format(Locale.getDefault(), "%02d", duration_m) + ":" + String.format(Locale.getDefault(), "%02d", duration_s);
        holder.track_title.setText(trackName);
        holder.artist_name.setText(artistName);
        holder.track_duration.setText(trackDuration);

    }

    @Override
    public int getItemCount() {
        return tracks.size();
    }
}
