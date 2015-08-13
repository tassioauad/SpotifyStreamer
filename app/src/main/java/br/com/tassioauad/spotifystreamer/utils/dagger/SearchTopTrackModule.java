package br.com.tassioauad.spotifystreamer.utils.dagger;


import br.com.tassioauad.spotifystreamer.model.api.TrackApi;
import br.com.tassioauad.spotifystreamer.presenter.SearchTopTrackPresenter;
import br.com.tassioauad.spotifystreamer.view.SearchTopTrackView;
import br.com.tassioauad.spotifystreamer.view.fragment.SearchTopTrackFragment;
import dagger.Module;
import dagger.Provides;

@Module(library = true, includes = {ApiModule.class}, injects = SearchTopTrackFragment.class)
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
