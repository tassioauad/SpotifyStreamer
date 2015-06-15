package br.com.tassioauad.spotifystreamer.model.api.impl;

import android.os.AsyncTask;

import br.com.tassioauad.spotifystreamer.model.api.ArtistApi;
import br.com.tassioauad.spotifystreamer.model.api.GenericApi;
import br.com.tassioauad.spotifystreamer.model.api.asynctask.ArtistFindByNameAsyncTask;


public class ArtistApiImpl extends GenericApi implements ArtistApi {

    private ArtistFindByNameAsyncTask artistFindByNameAsyncTask;

    @Override
    public void findByName(String name) {
        verifyApiResultListenerIsSetted();
        artistFindByNameAsyncTask = new ArtistFindByNameAsyncTask(getApiResultListener());
        artistFindByNameAsyncTask.execute(name);
    }

    @Override
    public void stopExecution() {
        if (artistFindByNameAsyncTask != null &&  artistFindByNameAsyncTask.getStatus().equals(AsyncTask.Status.RUNNING)) {
            artistFindByNameAsyncTask.cancel(true);
        }
    }
}
