package br.com.tassioauad.spotifystreamer.utils.dagger;


import br.com.tassioauad.spotifystreamer.presenter.TopTrackPresenter;
import br.com.tassioauad.spotifystreamer.view.TopTrackView;
import br.com.tassioauad.spotifystreamer.view.activity.TopTrackActivity;
import dagger.Module;
import dagger.Provides;

@Module(library = true, includes = {ApiModule.class}, injects = TopTrackActivity.class)
public class TopTrackModule {

    private TopTrackView view;

    public TopTrackModule(TopTrackView view) {
        this.view = view;
    }

    @Provides
    public TopTrackPresenter provideTopTrackPresenter() {
        return new TopTrackPresenter(view);
    }
}
