package br.com.tassioauad.spotifystreamer.model.api;

import br.com.tassioauad.spotifystreamer.model.entity.Artist;

public interface TrackApi extends Api {
    void findTopTenTrack(Artist artist);
}
