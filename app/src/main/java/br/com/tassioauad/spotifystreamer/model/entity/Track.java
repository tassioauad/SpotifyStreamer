package br.com.tassioauad.spotifystreamer.model.entity;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Track implements Parcelable {

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
        for (kaaes.spotify.webapi.android.models.ArtistSimple artistSimple : track.artists) {
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


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.name);
        dest.writeParcelable(this.album, flags);
        dest.writeTypedList(artistList);
    }

    private Track(Parcel in) {
        this.id = in.readString();
        this.name = in.readString();
        this.album = in.readParcelable(Album.class.getClassLoader());
        in.readTypedList(artistList, Artist.CREATOR);
    }

    public static final Parcelable.Creator<Track> CREATOR = new Parcelable.Creator<Track>() {
        public Track createFromParcel(Parcel source) {
            return new Track(source);
        }

        public Track[] newArray(int size) {
            return new Track[size];
        }
    };
}
