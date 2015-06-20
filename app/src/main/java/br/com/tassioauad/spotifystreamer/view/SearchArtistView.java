package br.com.tassioauad.spotifystreamer.view;

import java.util.List;

import br.com.tassioauad.spotifystreamer.model.entity.Artist;


public interface SearchArtistView {
    void showArtists(List<Artist> artists);

    void anyArtistFounded();

    void lostConnection();

    void showLoadingWarn();

    void hideLoadingWarn();
}
