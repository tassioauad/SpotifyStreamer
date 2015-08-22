package br.com.tassioauad.spotifystreamer.view;

import java.util.ArrayList;
import java.util.List;

import br.com.tassioauad.spotifystreamer.model.entity.Track;

public interface ListTopTrackView {
    /**
     * Show tracks which were found in search
     * @param tracks
     * @param trackList
     */
    void showTracks(List<Track> trackList);

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
