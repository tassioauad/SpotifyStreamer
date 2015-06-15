package br.com.tassioauad.spotifystreamer.presenter;

import android.test.AndroidTestCase;

import org.mockito.Mock;

import br.com.tassioauad.spotifystreamer.model.api.ArtistApi;
import br.com.tassioauad.spotifystreamer.model.entity.Artist;
import br.com.tassioauad.spotifystreamer.view.SearchArtistView;

import static org.mockito.Matchers.anyListOf;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class SearchArtistPresenterTest extends AndroidTestCase {

    @Mock
    SearchArtistView view;
    @Mock
    ArtistApi artistApi;
    SearchArtistPresenter presenter;

    @Override
    public void setUp() throws Exception {
        super.setUp();
        view = mock(SearchArtistView.class);
        artistApi = mock(ArtistApi.class);
    }

    @Override
    public void tearDown() throws Exception {

    }

    public void testInit() throws Exception {

        presenter = new SearchArtistPresenter(view, artistApi);

        presenter.init();

        verify(artistApi, times(1)).findByName(anyString());
        verify(view, times(1)).showArtists(anyListOf(Artist.class));
    }

    public void testFinish() throws Exception {

    }
}