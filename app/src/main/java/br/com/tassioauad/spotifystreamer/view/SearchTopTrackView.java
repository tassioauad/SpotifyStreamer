package br.com.tassioauad.spotifystreamer.view;

import java.util.List;

import br.com.tassioauad.spotifystreamer.model.entity.Track;

public interface SearchTopTrackView {
    void showTracks(List<Track> tracks);

    void anyTrackFounded();

    void lostConnection();

    void showLoadingWarn();

    void hideLoadingWarn();
}
