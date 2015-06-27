package br.com.tassioauad.spotifystreamer.model.api;

public interface ArtistApi extends Api {

    /**
     * Find artist by name
     * @param name Arist's name
     */
    void findByName(String name);

}
