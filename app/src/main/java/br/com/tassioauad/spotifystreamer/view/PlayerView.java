package br.com.tassioauad.spotifystreamer.view;

import br.com.tassioauad.spotifystreamer.model.entity.Track;

public interface PlayerView {
    void play(Track track, Boolean hasNext, Boolean hasPrevious);
}
