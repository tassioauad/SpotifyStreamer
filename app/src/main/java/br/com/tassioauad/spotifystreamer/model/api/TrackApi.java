package br.com.tassioauad.spotifystreamer.model.api;

import br.com.tassioauad.spotifystreamer.model.entity.Artist;

public interface TrackApi extends Api {

    /**
     * Find tracks by artist owner
     * @param artist Artist owner
     */
    void findTopTenTrack(Artist artist);
}
