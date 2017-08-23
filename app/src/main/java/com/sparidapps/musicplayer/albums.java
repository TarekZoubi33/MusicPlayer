package com.sparidapps.musicplayer;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
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

import recyclerview_adapters.album_rec_adapter;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link albums.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link albums#newInstance} factory method to
 * create an instance of this fragment.
 */
public class albums extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public albums() {
        // Required empty public constructor
    }
    // true by default until content provider and recyclerview adapter is ready
    private final boolean no_content = true;

    private TextView nocontnet_text;
    private RecyclerView recyclerView;

    int exPermissionCheck;

    SharedPreferences preferences;

    private List<AlbumModel> albums_list = new ArrayList<>();
    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment albums.
     */
    // TODO: Rename and change types and number of parameters
    public static albums newInstance(String param1, String param2) {
        albums fragment = new albums();
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_albums, container, false);
       nocontnet_text = (TextView) view.findViewById(R.id.nocontent_albums);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerview_albums);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(getActivity().getApplicationContext());
        album_rec_adapter adapter = new album_rec_adapter(albums_list);
        adapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = recyclerView.getChildAdapterPosition(v);
                AlbumModel albumModel = albums_list.get(position);
                String album_art = albumModel.get_AlbumArt();
                Intent intent = new Intent(getActivity(), album_activity.class);
                intent.putExtra("album_art", album_art);
                startActivity(intent);
            }
        });
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);

        exPermissionCheck = preferences.getInt("exPermissionCheck", PackageManager.PERMISSION_DENIED);
        if(exPermissionCheck == PackageManager.PERMISSION_GRANTED){
            getAlbums();
            if(albums_list.size() > 0){
                nocontnet_text.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);
                adapter.notifyDataSetChanged();
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

    private void getAlbums(){

        ContentResolver musicResolver = getActivity().getContentResolver();
        Uri albumUri = MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI;
        Cursor albumCursor = musicResolver.query(albumUri, null, null ,null, null);
        if(albumCursor !=null){
            albumCursor.moveToFirst();
            int id_column = albumCursor.getColumnIndex(MediaStore.Audio.Albums._ID);
            int title_column = albumCursor.getColumnIndex(MediaStore.Audio.Albums.ALBUM);
            int alb_artist_column = albumCursor.getColumnIndex(MediaStore.Audio.Albums.ARTIST);
            int alb_art_column = albumCursor.getColumnIndex(MediaStore.Audio.Albums.ALBUM_ART);
            int alb_tracks_column = albumCursor.getColumnIndex(MediaStore.Audio.Albums.NUMBER_OF_SONGS);

            do{
                String album_id = albumCursor.getString(id_column);
                String album_title = albumCursor.getString(title_column);
                String album_artist = albumCursor.getString(alb_artist_column);
                String album_art = albumCursor.getString(alb_art_column);
                Log.i("TAG", "Album art path: " + album_art);
                int tracks_num = albumCursor.getInt(alb_tracks_column);

                AlbumModel albumModel = new AlbumModel();
                albumModel.set_id(album_id);
                albumModel.set_AlbumName(album_title);
                albumModel.set_ArtistName(album_artist);
                albumModel.set_AlbumArt(album_art);
                albumModel.setNumber_of_tracks(tracks_num);
                albums_list.add(albumModel);
                Log.i("Tag", "List size is " + albums_list.size());

            }
            while(albumCursor.moveToNext());
        }
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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        albums_list.clear();
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
