package br.com.tassioauad.spotifystreamer.utils.dagger;


import br.com.tassioauad.spotifystreamer.model.api.ArtistApi;
import br.com.tassioauad.spotifystreamer.presenter.ListArtistPresenter;
import br.com.tassioauad.spotifystreamer.presenter.PlayerPresenter;
import br.com.tassioauad.spotifystreamer.view.ListArtistView;
import br.com.tassioauad.spotifystreamer.view.PlayerView;
import br.com.tassioauad.spotifystreamer.view.fragment.ListArtistFragment;
import br.com.tassioauad.spotifystreamer.view.fragment.PlayerFragment;
import dagger.Module;
import dagger.Provides;

@Module(library = true, includes = {ApiModule.class}, injects = ListArtistFragment.class)
public class ListArtistModule {

    private ListArtistView view;

    public ListArtistModule(ListArtistView view) {
        this.view = view;
    }

    @Provides
    public ListArtistPresenter provideListArtistPresenter(ArtistApi artistApi) {
        return new ListArtistPresenter(view, artistApi);
    }
}
