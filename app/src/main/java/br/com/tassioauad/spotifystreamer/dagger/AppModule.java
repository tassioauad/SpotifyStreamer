package br.com.tassioauad.spotifystreamer.dagger;

import android.app.Application;
import android.content.Context;

import dagger.Module;
import dagger.Provides;

@Module(library = true)
public class AppModule {

    private static Application app;

    public AppModule(Application app) {
        AppModule.app = app;
    }

    public AppModule() {
    }

    @Provides
    public Application provideApplication() {
        return app;
    }

    @Provides
    public Context provideContext() {
        return app;
    }

}
