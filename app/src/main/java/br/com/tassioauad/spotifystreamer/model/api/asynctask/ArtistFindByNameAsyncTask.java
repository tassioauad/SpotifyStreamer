package br.com.tassioauad.spotifystreamer.model.api.asynctask;

import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;

import br.com.tassioauad.spotifystreamer.model.api.ApiResultListener;
import br.com.tassioauad.spotifystreamer.model.api.exception.BadRequestException;
import br.com.tassioauad.spotifystreamer.model.api.exception.NotFoundException;
import br.com.tassioauad.spotifystreamer.model.entity.Artist;
import kaaes.spotify.webapi.android.SpotifyApi;
import kaaes.spotify.webapi.android.SpotifyService;
import kaaes.spotify.webapi.android.models.ArtistsPager;
import kaaes.spotify.webapi.android.models.Image;
import retrofit.RetrofitError;

public class ArtistFindByNameAsyncTask extends GenericAsyncTask<String, Void, List<Artist>> {

    public ArtistFindByNameAsyncTask(ApiResultListener listener) {
        super(listener);
    }

    @Override
    protected AsyncTaskResult<List<Artist>> doInBackground(String... params) {
        SpotifyService spotifyService = new SpotifyApi().getService();

        try {
            ArtistsPager results = spotifyService.searchArtists(params[0]);

            List<Artist> artistList = new ArrayList<>();
            for (kaaes.spotify.webapi.android.models.Artist spotifyWebApiArtist : results.artists.items) {
                artistList.add(new Artist(spotifyWebApiArtist));
            }

            return new AsyncTaskResult<>(artistList);

        } catch (RetrofitError error) {
            if(error.getResponse() == null) {
                return new AsyncTaskResult<>(new NotFoundException());
            } else {
                switch(error.getResponse().getStatus()) {
                    case HttpURLConnection.HTTP_BAD_REQUEST:
                        return new AsyncTaskResult<>(new BadRequestException());
                    case HttpURLConnection.HTTP_NOT_FOUND:
                        return new AsyncTaskResult<>(new NotFoundException());
                    default:
                        return new AsyncTaskResult<>(error);
                }
            }
        }


    }
}
