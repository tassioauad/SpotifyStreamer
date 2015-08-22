package br.com.tassioauad.spotifystreamer.view;

import java.util.List;

import br.com.tassioauad.spotifystreamer.model.entity.Track;

public interface TrackView {
    void warnNoTracks();

    void showPlayer(List<Track> trackList, int actualPosition);
}
