package br.com.tassioauad.spotifystreamer.utils.dagger;


import br.com.tassioauad.spotifystreamer.presenter.PlayerPresenter;
import br.com.tassioauad.spotifystreamer.view.PlayerView;
import br.com.tassioauad.spotifystreamer.view.fragment.PlayerFragment;
import dagger.Module;
import dagger.Provides;

@Module(library = true, includes = {ApiModule.class}, injects = PlayerFragment.class)
public class PlayerModule {

    private PlayerView view;

    public PlayerModule(PlayerView view) {
        this.view = view;
    }

    @Provides
    public PlayerPresenter providePlayerPresenter() {
        return new PlayerPresenter(view);
    }
}
