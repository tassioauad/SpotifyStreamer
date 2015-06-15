package br.com.tassioauad.spotifystreamer.utils.dagger;


import br.com.tassioauad.spotifystreamer.model.api.ArtistApi;
import br.com.tassioauad.spotifystreamer.presenter.SearchArtistPresenter;
import br.com.tassioauad.spotifystreamer.view.SearchArtistView;
import br.com.tassioauad.spotifystreamer.view.activity.SearchArtistActivity;
import dagger.Module;
import dagger.Provides;

@Module(library = true, includes = {ApiModule.class}, injects = SearchArtistActivity.class)
public class SearchArtistModule {

    private SearchArtistView view;

    public SearchArtistModule(SearchArtistView view) {
        this.view = view;
    }

    @Provides
    public SearchArtistPresenter provideSearchArtistPresenter(ArtistApi artistApi) {
        return new SearchArtistPresenter(view, artistApi);
    }
}
