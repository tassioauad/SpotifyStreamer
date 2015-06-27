package br.com.tassioauad.spotifystreamer.view;

import java.util.List;

import br.com.tassioauad.spotifystreamer.model.entity.Track;

public interface SearchTopTrackView {
    /**
     * Show tracks which were found in search
     * @param tracks
     */
    void showTracks(List<Track> tracks);

    /**
     * Warn any track was found!
     */
    void anyTrackFounded();

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
