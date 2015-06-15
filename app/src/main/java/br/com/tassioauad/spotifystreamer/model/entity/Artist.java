package br.com.tassioauad.spotifystreamer.model.entity;

import kaaes.spotify.webapi.android.models.ArtistSimple;
import kaaes.spotify.webapi.android.models.Image;

public class Artist {

    private String id;

    private String name;

    private String type;

    private String imageUrl;

    private String smallImageUrl;

    public Artist() {
    }

    public Artist(kaaes.spotify.webapi.android.models.Artist artist) {
        id = artist.id;;
        name = artist.name;
        type = artist.type;
        for (Image image : artist.images) {
            if( (image.width >= 150) && (image.width <= 250) ){
                smallImageUrl = image.url;
            }
            else if( (image.width >= 600) && (image.width <= 700) ){
                imageUrl = image.url;
            }
        }
    }

    public Artist(ArtistSimple artistSimple) {
        id = artistSimple.id;;
        name = artistSimple.name;
        type = artistSimple.type;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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
}
