package com.sparidapps.musicplayer;

import android.content.ContentResolver;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import recyclerview_adapters.artists_rec_adapter;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link artist.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link artist#newInstance} factory method to
 * create an instance of this fragment.
 */
public class artist extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    // true by default until content provider and recyclerview adapter is ready
    private final boolean no_content = true;

    int exPermissionCheck;

    private TextView nocontnet_text;
    private RecyclerView recyclerView;

    SharedPreferences preferences;

    private List<ArtistModel> artists_list = new ArrayList<>();

    private OnFragmentInteractionListener mListener;

    public artist() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment artist.
     */
    // TODO: Rename and change types and number of parameters
    public static artist newInstance(String param1, String param2) {
        artist fragment = new artist();
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
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_artist, container, false);
        nocontnet_text = (TextView) view.findViewById(R.id.nocontent_artists);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerview_artists);
        exPermissionCheck = preferences.getInt("exPermissionCheck", PackageManager.PERMISSION_DENIED);

        RecyclerView.LayoutManager manager = new LinearLayoutManager(getActivity().getApplicationContext());
        recyclerView.setLayoutManager(manager);
        artists_rec_adapter adapter = new artists_rec_adapter(artists_list);
        recyclerView.setAdapter(adapter);

        if(exPermissionCheck == PackageManager.PERMISSION_GRANTED){
            getArtists();
            if(artists_list.size() > 0){
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

    private void getArtists() {
        ContentResolver resolver = getActivity().getContentResolver();
        Uri artists_uri = MediaStore.Audio.Artists.EXTERNAL_CONTENT_URI;
        Cursor cursor = resolver.query(artists_uri, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
            int artist_column = cursor.getColumnIndex(MediaStore.Audio.Artists.ARTIST);
            int id_column = cursor.getColumnIndex(MediaStore.Audio.Artists._ID);
            int albums_column = cursor.getColumnIndex(MediaStore.Audio.Artists.NUMBER_OF_ALBUMS);
            int tracks_column = cursor.getColumnIndex(MediaStore.Audio.Artists.NUMBER_OF_TRACKS);

            do {

                String artist_name = cursor.getString(artist_column);
                long artist_id = cursor.getLong(id_column);
                int albums_num = cursor.getInt(albums_column);
                int tracks_num = cursor.getInt(tracks_column);

                ArtistModel artistModel = new ArtistModel();
                artistModel.setName(artist_name);
                artistModel.setID(artist_id);
                artistModel.setAlbums_num(albums_num);
                artistModel.setTracks_num(tracks_num);
                artists_list.add(artistModel);

            } while (cursor.moveToNext());
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
        artists_list.clear();
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
