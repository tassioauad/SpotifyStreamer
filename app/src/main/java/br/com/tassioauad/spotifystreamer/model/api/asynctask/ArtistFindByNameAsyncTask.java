package br.com.tassioauad.spotifystreamer.model.api.asynctask;

import java.util.ArrayList;
import java.util.List;

import br.com.tassioauad.spotifystreamer.model.api.ApiResultListener;
import br.com.tassioauad.spotifystreamer.model.entity.Artist;
import br.com.tassioauad.spotifystreamer.utils.exception.NotFoundException;
import kaaes.spotify.webapi.android.SpotifyApi;
import kaaes.spotify.webapi.android.SpotifyService;
import kaaes.spotify.webapi.android.models.ArtistsPager;
import kaaes.spotify.webapi.android.models.Image;

public class ArtistFindByNameAsyncTask extends GenericAsyncTask<String, Void, List<Artist>> {

    public ArtistFindByNameAsyncTask(ApiResultListener listener) {
        super(listener);
    }

    @Override
    protected AsyncTaskResult<List<Artist>> doInBackground(String... params) {
        SpotifyService spotifyService = new SpotifyApi().getService();
        ArtistsPager results = spotifyService.searchArtists(params[0]);

        if (results.artists.items.size() == 0) {
            return new AsyncTaskResult<>(new NotFoundException());

        } else {
            List<Artist> artistList = new ArrayList<>();
            for (kaaes.spotify.webapi.android.models.Artist spotifyWebApiArtist : results.artists.items) {
                artistList.add(new Artist(spotifyWebApiArtist));
            }

            return new AsyncTaskResult<>(artistList);
        }


    }
}
