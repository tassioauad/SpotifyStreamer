package br.com.tassioauad.spotifystreamer.utils.dagger;


import br.com.tassioauad.spotifystreamer.presenter.ArtistPresenter;
import br.com.tassioauad.spotifystreamer.view.ArtistView;
import br.com.tassioauad.spotifystreamer.view.activity.ArtistActivity;
import dagger.Module;
import dagger.Provides;

@Module(library = true, includes = {ApiModule.class}, injects = ArtistActivity.class)
public class ArtistModule {

    private ArtistView view;

    public ArtistModule(ArtistView view) {
        this.view = view;
    }

    @Provides
    public ArtistPresenter provideArtistPresenter() {
        return new ArtistPresenter(view);
    }
}
