package com.sparidapps.musicplayer;

/**
 * Created by Tarek Zoubi on 16/8/2017.
 */

public class AlbumModel {
    String alb_name;
    String art_name;
    String genre;
    String alb_art;
    String alb_id;
    int number_of_tracks;

    public void set_AlbumName(String alb_name){
        this.alb_name = alb_name;
    }

    public void set_ArtistName(String art_name){
        this.art_name = art_name;
    }

    public void set_genre(String genre){
        this.genre = genre;
    }

    public void set_id(String id){
        this.alb_id = id;
    }
    public void set_AlbumArt(String alb_art){
        this.alb_art = alb_art;
    }
    public void setNumber_of_tracks(int number){
        this.number_of_tracks = number;
    }

    public String get_AlbumName(){
        return alb_name;
    }

    public String get_ArtistName(){
        return art_name;
    }

    public String getGenre(){
        return genre;
    }
    public String get_id(){
        return alb_id;
    }
    public String get_AlbumArt(){
        return alb_art;
    }
    public int getNumber_of_tracks(){
        return number_of_tracks;
    }
}
