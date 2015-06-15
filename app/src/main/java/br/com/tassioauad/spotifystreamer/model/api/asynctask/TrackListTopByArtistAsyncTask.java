package br.com.tassioauad.spotifystreamer.model.api.asynctask;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import br.com.tassioauad.spotifystreamer.model.api.ApiResultListener;
import br.com.tassioauad.spotifystreamer.model.entity.Artist;
import br.com.tassioauad.spotifystreamer.model.entity.Track;
import br.com.tassioauad.spotifystreamer.utils.exception.NotFoundException;
import kaaes.spotify.webapi.android.SpotifyApi;
import kaaes.spotify.webapi.android.SpotifyService;
import kaaes.spotify.webapi.android.models.Tracks;

public class TrackListTopByArtistAsyncTask extends GenericAsyncTask<Artist, Void, List<Track>> {

    public TrackListTopByArtistAsyncTask(ApiResultListener listener) {
        super(listener);
    }

    @Override
    protected AsyncTaskResult<List<Track>> doInBackground(Artist... params) {
        Map<String, Object> options = new HashMap<>();
        options.put(SpotifyService.COUNTRY, Locale.getDefault().getCountry());
        Tracks artistTopTracks = new SpotifyApi().getService().getArtistTopTrack(params[0].getId(), options);

        if(artistTopTracks.tracks.size() == 0) {
            return new AsyncTaskResult<>(new NotFoundException());
        } else {
            List<Track> trackList = new ArrayList<>();
            for(kaaes.spotify.webapi.android.models.Track track : artistTopTracks.tracks) {
                trackList.add(new Track(track));
            }

            return new AsyncTaskResult<>(trackList);
        }
    }
}
