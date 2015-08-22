package br.com.tassioauad.spotifystreamer.presenter;

import java.util.ArrayList;
import java.util.Arrays;

import br.com.tassioauad.spotifystreamer.model.entity.Track;
import br.com.tassioauad.spotifystreamer.view.TrackView;

public class TrackPresenter {

    private TrackView view;

    public TrackPresenter(TrackView view) {
        this.view = view;
    }

    public void init(ArrayList<Track> trackList, int actualPosition) {
        if(trackList == null || trackList.size() == 0 || actualPosition == -1) {
            view.warnNoTracks();
        } else {
            view.showPlayer(trackList, actualPosition);
        }
    }
}
