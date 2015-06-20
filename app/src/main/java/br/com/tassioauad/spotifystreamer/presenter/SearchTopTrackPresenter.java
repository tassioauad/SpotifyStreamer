package br.com.tassioauad.spotifystreamer.presenter;

import java.util.List;

import br.com.tassioauad.spotifystreamer.model.api.ApiResultListener;
import br.com.tassioauad.spotifystreamer.model.api.TrackApi;
import br.com.tassioauad.spotifystreamer.model.api.exception.BadRequestException;
import br.com.tassioauad.spotifystreamer.model.api.exception.NotFoundException;
import br.com.tassioauad.spotifystreamer.model.entity.Artist;
import br.com.tassioauad.spotifystreamer.model.entity.Track;
import br.com.tassioauad.spotifystreamer.view.SearchTopTrackView;

public class SearchTopTrackPresenter {

    private SearchTopTrackView view;
    private TrackApi trackApi;

    public SearchTopTrackPresenter(SearchTopTrackView view, TrackApi trackApi) {
        this.view = view;
        this.trackApi = trackApi;
    }

    public void searchByArtist(Artist artist) {
        view.showLoadingWarn();
        trackApi.setApiResultListener(new ApiResultListener() {
            @Override
            public void onResult(Object object) {
                List<Track> artistList = (List<Track>) object;
                if(artistList.size() == 0) {
                    view.anyTrackFounded();
                } else {
                    view.showTracks(artistList);
                }
                view.hideLoadingWarn();
            }

            @Override
            public void onException(Exception exception) {
                if(exception instanceof NotFoundException) {
                    view.lostConnection();
                } else if(exception instanceof BadRequestException){
                    view.anyTrackFounded();
                } else {
                    view.anyTrackFounded();
                }
                view.hideLoadingWarn();
            }
        });

        trackApi.findTopTenTrack(artist);
    }

    public void finish() {
        trackApi.stopAnyExecution();
    }
}
