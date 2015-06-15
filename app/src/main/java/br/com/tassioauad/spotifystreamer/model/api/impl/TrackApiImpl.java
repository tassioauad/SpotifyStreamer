package br.com.tassioauad.spotifystreamer.model.api.impl;

import android.os.AsyncTask;

import br.com.tassioauad.spotifystreamer.model.api.GenericApi;
import br.com.tassioauad.spotifystreamer.model.api.asynctask.ArtistFindByNameAsyncTask;
import br.com.tassioauad.spotifystreamer.model.api.asynctask.TrackListTopByArtistAsyncTask;
import br.com.tassioauad.spotifystreamer.model.entity.Artist;
import br.com.tassioauad.spotifystreamer.model.api.TrackApi;

public class TrackApiImpl extends GenericApi implements TrackApi {

    private TrackListTopByArtistAsyncTask trackListTopByArtistAsyncTask;

    public void findTopTenTrack(Artist artist) {
        verifyApiResultListenerIsSetted();
        trackListTopByArtistAsyncTask = new TrackListTopByArtistAsyncTask(getApiResultListener());
        trackListTopByArtistAsyncTask.execute(artist);
    }

    @Override
    public void stopExecution() {
        if (trackListTopByArtistAsyncTask != null &&  trackListTopByArtistAsyncTask.getStatus().equals(AsyncTask.Status.RUNNING)) {
            trackListTopByArtistAsyncTask.cancel(true);
        }
    }
}
