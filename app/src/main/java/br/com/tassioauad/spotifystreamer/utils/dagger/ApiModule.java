package br.com.tassioauad.spotifystreamer.utils.dagger;

import android.app.Application;

import br.com.tassioauad.spotifystreamer.model.api.ArtistApi;
import br.com.tassioauad.spotifystreamer.model.api.impl.ArtistApiImpl;
import dagger.Module;
import dagger.Provides;

@Module(library = true)
public class ApiModule {

    private static Application app;

    public ApiModule(Application app) {
        ApiModule.app = app;
    }

    public ApiModule() {
    }

    @Provides
    public ArtistApi provideArtistApi() {
        return new ArtistApiImpl();
    }

}
