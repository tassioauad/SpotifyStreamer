package br.com.tassioauad.spotifystreamer.view;

import java.util.List;

import br.com.tassioauad.spotifystreamer.model.entity.Artist;


public interface SearchArtistView {

    /**
     * Show artists which were found in search
     * @param artists
     */
    void showArtists(List<Artist> artists);

    /**
     * Warn any artist was found!
     */
    void anyArtistFounded();

    /**
     * Warn the connection was lost
     */
    void lostConnection();

    /**
     * Warn searching
     */
    void showLoadingWarn();

    /**
     * Search was completed, stop to warn.
     */
    void hideLoadingWarn();
}
