package br.com.tassioauad.spotifystreamer;

import android.app.Application;

import br.com.tassioauad.spotifystreamer.dagger.AppModule;
import dagger.ObjectGraph;

public class SpotifyStreamerApplication extends Application {

    private ObjectGraph objectGraph;

    @Override
    public void onCreate() {
        super.onCreate();

        objectGraph = ObjectGraph.create(new AppModule(SpotifyStreamerApplication.this));
    }

    public ObjectGraph getObjectGraph() {
        return objectGraph;
    }

}
