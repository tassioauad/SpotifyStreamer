package br.com.tassioauad.spotifystreamer;

import android.app.Application;

import br.com.tassioauad.spotifystreamer.utils.dagger.ApiModule;
import br.com.tassioauad.spotifystreamer.utils.dagger.AppModule;
import dagger.ObjectGraph;

public class SpotifyStreamerApplication extends Application {

    private ObjectGraph objectGraph;

    @Override
    public void onCreate() {
        super.onCreate();

        objectGraph = ObjectGraph.create(
                new Object[]{
                        new AppModule(SpotifyStreamerApplication.this),
                        new ApiModule()
                }
        );
    }

    public ObjectGraph getObjectGraph() {
        return objectGraph;
    }

}
