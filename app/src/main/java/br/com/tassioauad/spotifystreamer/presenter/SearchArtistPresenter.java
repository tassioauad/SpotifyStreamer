package br.com.tassioauad.spotifystreamer.presenter;

import java.util.List;

import br.com.tassioauad.spotifystreamer.model.api.ApiResultListener;
import br.com.tassioauad.spotifystreamer.model.api.ArtistApi;
import br.com.tassioauad.spotifystreamer.model.api.exception.BadRequestException;
import br.com.tassioauad.spotifystreamer.model.api.exception.NotFoundException;
import br.com.tassioauad.spotifystreamer.model.entity.Artist;
import br.com.tassioauad.spotifystreamer.view.SearchArtistView;

public class SearchArtistPresenter {

    private SearchArtistView view;
    private ArtistApi artistApi;

    public SearchArtistPresenter(SearchArtistView view, ArtistApi artistApi) {
        this.view = view;
        this.artistApi = artistApi;
    }

    public void init() {

    }

    public void finish() {
        artistApi.stopAnyExecution();
    }

    public void searchByName(String name) {
        artistApi.setApiResultListener(new ApiResultListener() {
            @Override
            public void onResult(Object object) {
                List<Artist> artistList = (List<Artist>) object;
                if(artistList.size() == 0) {
                    view.anyArtistFounded();
                } else {
                    view.showArtists(artistList);
                }
            }

            @Override
            public void onException(Exception exception) {
                if(exception instanceof NotFoundException) {
                    view.lostConnection();
                } else if(exception instanceof BadRequestException){
                    view.anyArtistFounded();
                } else {
                    view.anyArtistFounded();
                }
            }
        });

        artistApi.findByName(name);
    }
}
