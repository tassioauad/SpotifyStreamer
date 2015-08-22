package br.com.tassioauad.spotifystreamer.utils.dagger;


import br.com.tassioauad.spotifystreamer.presenter.TrackPresenter;
import br.com.tassioauad.spotifystreamer.view.TrackView;
import br.com.tassioauad.spotifystreamer.view.activity.TrackActivity;
import dagger.Module;
import dagger.Provides;

@Module(library = true, includes = {ApiModule.class}, injects = TrackActivity.class)
public class TrackModule {

    private TrackView view;

    public TrackModule(TrackView view) {
        this.view = view;
    }

    @Provides
    public TrackPresenter provideTrackPresenter() {
        return new TrackPresenter(view);
    }
}
