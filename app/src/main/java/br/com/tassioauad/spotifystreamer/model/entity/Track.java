package br.com.tassioauad.spotifystreamer.model.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Track {

    private String id;

    private String name;

    private Album album;

    public List<Artist> artistList = new ArrayList<>();

    public Track() {
    }

    public Track(kaaes.spotify.webapi.android.models.Track track) {
        id = track.id;
        name = track.name;
        album = new Album(track.album);
        for(kaaes.spotify.webapi.android.models.ArtistSimple artistSimple : track.artists) {
            artistList.add(new Artist(artistSimple));
        }

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Album getAlbum() {
        return album;
    }

    public void setAlbum(Album album) {
        this.album = album;
    }

    public List<Artist> getArtistList() {
        return artistList;
    }

    public void setArtistList(List<Artist> artistList) {
        this.artistList = artistList;
    }

}
