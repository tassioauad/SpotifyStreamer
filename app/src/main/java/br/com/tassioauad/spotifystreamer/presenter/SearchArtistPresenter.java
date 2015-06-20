package br.com.tassioauad.spotifystreamer.presenter;

import br.com.tassioauad.spotifystreamer.model.api.ArtistApi;
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

    }

    public void searchByName(String name) {

    }
}
