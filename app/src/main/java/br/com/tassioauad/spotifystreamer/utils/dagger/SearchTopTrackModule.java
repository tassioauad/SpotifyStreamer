package br.com.tassioauad.spotifystreamer.utils.dagger;


import br.com.tassioauad.spotifystreamer.model.api.ArtistApi;
import br.com.tassioauad.spotifystreamer.model.api.TrackApi;
import br.com.tassioauad.spotifystreamer.presenter.SearchTopTrackPresenter;
import br.com.tassioauad.spotifystreamer.view.SearchTopTrackView;
import br.com.tassioauad.spotifystreamer.view.activity.SearchArtistActivity;
import br.com.tassioauad.spotifystreamer.view.activity.SearchTopTrackActivity;
import dagger.Module;
import dagger.Provides;

@Module(library = true, includes = {ApiModule.class}, injects = SearchTopTrackActivity.class)
public class SearchTopTrackModule {

    private SearchTopTrackView view;

    public SearchTopTrackModule(SearchTopTrackView view) {
        this.view = view;
    }

    @Provides
    public SearchTopTrackPresenter provideSearchTopTrackPresenter(TrackApi trackApi) {
        return new SearchTopTrackPresenter(view, trackApi);
    }
}
