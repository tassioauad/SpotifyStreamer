package br.com.tassioauad.spotifystreamer.model.entity;

import android.os.Parcel;
import android.os.Parcelable;

import kaaes.spotify.webapi.android.models.AlbumSimple;
import kaaes.spotify.webapi.android.models.Image;

public class Album implements Parcelable {

    private String id;

    private String name;

    private String imageUrl;

    private String smallImageUrl;

    public Album() {
    }

    public Album(AlbumSimple album) {
        id = album.id;
        name = album.name;
        for (Image image : album.images) {
            if( (image.width >= 150) && (image.width <= 250) ){
                smallImageUrl = image.url;
            }
            else if( (image.width >= 600) && (image.width <= 700) ){
                imageUrl = image.url;
            }
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

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getSmallImageUrl() {
        return smallImageUrl;
    }

    public void setSmallImageUrl(String smallImageUrl) {
        this.smallImageUrl = smallImageUrl;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.name);
        dest.writeString(this.imageUrl);
        dest.writeString(this.smallImageUrl);
    }

    private Album(Parcel in) {
        this.id = in.readString();
        this.name = in.readString();
        this.imageUrl = in.readString();
        this.smallImageUrl = in.readString();
    }

    public static final Parcelable.Creator<Album> CREATOR = new Parcelable.Creator<Album>() {
        public Album createFromParcel(Parcel source) {
            return new Album(source);
        }

        public Album[] newArray(int size) {
            return new Album[size];
        }
    };
}
