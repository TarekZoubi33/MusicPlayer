package com.sparidapps.musicplayer;

/**
 * Created by Tarek Zoubi on 19/8/2017.
 */

public class ArtistModel {
    private String Name;
    private int albums_num, tracks_num;
    long artist_id;

    public void setName(String Name){
        this.Name = Name;
    }

    public void setAlbums_num(int num){
        this.albums_num = num;
    }

    public void setTracks_num(int num){
        this.tracks_num = num;
    }

    public void setID(long id){
        this.artist_id = id;
    }

    public String getName(){
        return Name;
    }

    public int getAlbums_num(){
        return albums_num;
    }

    public int getTracks_num(){
        return tracks_num;
    }

    public long getID(){
        return artist_id;
    }

}
