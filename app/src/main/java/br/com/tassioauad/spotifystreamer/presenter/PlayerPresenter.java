package br.com.tassioauad.spotifystreamer.presenter;

import java.util.List;

import br.com.tassioauad.spotifystreamer.model.entity.Track;
import br.com.tassioauad.spotifystreamer.view.PlayerView;

public class PlayerPresenter {

    private PlayerView view;
    private List<Track> trackList;
    private int actualPosition;

    public PlayerPresenter(PlayerView view) {
        this.view = view;
    }

    public void init(List<Track> trackList, int actualPosition) {
        this.trackList = trackList;
        this.actualPosition = actualPosition;
        view.play(trackList.get(actualPosition), actualPosition != (trackList.size() - 1), actualPosition != 0);
    }

    public void loadPrevious() {
        if (actualPosition > 0) {
            actualPosition--;
            view.play(trackList.get(actualPosition), actualPosition != (trackList.size() - 1), actualPosition != 0);
        }
    }

    public void loadNext() {
        if (actualPosition < (trackList.size() - 1)) {
            actualPosition++;
            view.play(trackList.get(actualPosition), actualPosition != (trackList.size() - 1), actualPosition != 0);
        }
    }


}
