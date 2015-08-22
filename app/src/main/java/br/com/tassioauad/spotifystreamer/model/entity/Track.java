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

    private String previewUrl;

    public Track() {
    }

    public Track(kaaes.spotify.webapi.android.models.Track track) {
        id = track.id;
        name = track.name;
        album = new Album(track.album);
        for (kaaes.spotify.webapi.android.models.ArtistSimple artistSimple : track.artists) {
            artistList.add(new Artist(artistSimple));
        }
        previewUrl = track.preview_url;
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

    public String getPreviewUrl() {
        return previewUrl;
    }

    public void setPreviewUrl(String previewUrl) {
        this.previewUrl = previewUrl;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.name);
        dest.writeParcelable(this.album, 0);
        dest.writeTypedList(artistList);
        dest.writeString(this.previewUrl);
    }

    protected Track(Parcel in) {
        this.id = in.readString();
        this.name = in.readString();
        this.album = in.readParcelable(Album.class.getClassLoader());
        this.artistList = in.createTypedArrayList(Artist.CREATOR);
        this.previewUrl = in.readString();
    }

    public static final Creator<Track> CREATOR = new Creator<Track>() {
        public Track createFromParcel(Parcel source) {
            return new Track(source);
        }

        public Track[] newArray(int size) {
            return new Track[size];
        }
    };
}
