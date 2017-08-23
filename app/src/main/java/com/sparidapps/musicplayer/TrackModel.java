package com.sparidapps.musicplayer;

/**
 * Created by Tarek Zoubi on 4/8/2017.
 */

public class TrackModel {

    String aPath;
    String aName;
    String aAlbum;
    String aArtist;
    String aGenre;
    String aDuration;
    long track_id;


    public String getaPath(){
        return aPath;
    }

    public String getaName(){
        return aName;
    }

    public String getaAlbum(){
        return aAlbum;
    }
    public String getaArtist(){
        return aArtist;
    }
    public String getaGenre(){
        return aGenre;
    }

    public long getID(){
    return track_id;
    }
    public String getaDuration(){
        return aDuration;
    }
    public void setaPath(String aPath){
        this.aPath = aPath;
    }


   public void setID(long _id){
       this.track_id = _id;
   }

    public void setaName(String aName){
        this.aName = aName;
    }
    public void setaAlbum(String aAlbum){
        this.aAlbum = aAlbum;
    }
    public void setaArtist(String aArtist){
        this.aArtist = aArtist;
    }
    public void setaGenre(String aGenre){
        this.aGenre = aGenre;
    }
    public void setaDuration(String aDuration){
        this.aDuration =  aDuration;
    }

}
