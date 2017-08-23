package com.sparidapps.musicplayer;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import recyclerview_adapters.track_rec_adapter;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link tracks.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link tracks#newInstance} factory method to
 * create an instance of this fragment.
 */
public class tracks extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";



    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private TextView nocontnet_text;
    private RecyclerView recyclerView;
    SharedPreferences preferences = null;

    int exPermissionCheck;

    private OnFragmentInteractionListener mListener;

    private List<TrackModel> track_list = new ArrayList<>();

    public tracks() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment tracks.
     */
    // TODO: Rename and change types and number of parameters
    public static tracks newInstance(String param1, String param2) {
        tracks fragment = new tracks();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        preferences = getActivity().getSharedPreferences("preferences", Context.MODE_PRIVATE);

    }


    public void getAllTracks(Context context) {
        ContentResolver musicresolver = getActivity().getContentResolver();
        Uri musicUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        Cursor musicCursor = musicresolver.query(musicUri, null, null, null, null);

        if (musicCursor != null) {
            musicCursor.moveToFirst();
                int title_column = musicCursor.getColumnIndex(MediaStore.Audio.Media.TITLE);
                int id_column = musicCursor.getColumnIndex(MediaStore.Audio.Media._ID);
                int artist_column = musicCursor.getColumnIndex(MediaStore.Audio.Media.ARTIST);
                int album_column = musicCursor.getColumnIndex(MediaStore.Audio.Media.ALBUM);

                MediaMetadataRetriever mr = new MediaMetadataRetriever();
                do {
                    long song_id = musicCursor.getLong(id_column);
                    String song_title = musicCursor.getString(title_column);
                    String song_artist = musicCursor.getString(artist_column);
                    String song_album = musicCursor.getString(album_column);
                    Log.i("TAG", "Song " + String.valueOf(song_title));

                    Uri genre_uri = ContentUris.withAppendedId(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, song_id);
                    mr.setDataSource(context, genre_uri);

                    String song_genre = mr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_GENRE);
                    String track_duration = mr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION);
                    TrackModel track = new TrackModel();
                    track.setaAlbum(song_album);
                    track.setaArtist(song_artist);
                    track.setaName(song_title);
                    track.setaGenre(song_genre);
                    track.setID(song_id);
                    track.setaDuration(track_duration);
                    track_list.add(track);

                }
                while (musicCursor.moveToNext());


        }
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_tracks, container, false);
        nocontnet_text = (TextView) view.findViewById(R.id.nocontent_tracks);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerview_tracks);
        nocontnet_text.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        track_rec_adapter rec_adapter = new track_rec_adapter(track_list);
        recyclerView.setAdapter(rec_adapter);
        exPermissionCheck = preferences.getInt("exPermissionCheck", PackageManager.PERMISSION_DENIED);
        if(exPermissionCheck == PackageManager.PERMISSION_GRANTED){
            getAllTracks(getActivity().getApplicationContext());
            if(track_list.size() > 0){
                nocontnet_text.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);
                rec_adapter.notifyDataSetChanged();
            }
            else{
                nocontnet_text.setVisibility(View.VISIBLE);
                recyclerView.setVisibility(View.GONE);
            }

        }
        else{
            nocontnet_text.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        }

        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        track_list.clear();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
