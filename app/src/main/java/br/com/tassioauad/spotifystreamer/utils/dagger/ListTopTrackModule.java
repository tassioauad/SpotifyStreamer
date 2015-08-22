package br.com.tassioauad.spotifystreamer.utils.dagger;


import br.com.tassioauad.spotifystreamer.model.api.TrackApi;
import br.com.tassioauad.spotifystreamer.presenter.ListTopTrackPresenter;
import br.com.tassioauad.spotifystreamer.view.ListTopTrackView;
import br.com.tassioauad.spotifystreamer.view.fragment.ListTopTrackFragment;
import dagger.Module;
import dagger.Provides;

@Module(library = true, includes = {ApiModule.class}, injects = ListTopTrackFragment.class)
public class ListTopTrackModule {

    private ListTopTrackView view;

    public ListTopTrackModule(ListTopTrackView view) {
        this.view = view;
    }

    @Provides
    public ListTopTrackPresenter provideListTopTrackPresenter(TrackApi trackApi) {
        return new ListTopTrackPresenter(view, trackApi);
    }
}
