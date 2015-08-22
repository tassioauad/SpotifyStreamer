package br.com.tassioauad.spotifystreamer.presenter;

import android.test.AndroidTestCase;

import junit.framework.TestCase;

import br.com.tassioauad.spotifystreamer.view.ArtistView;

import static org.mockito.Mockito.mock;

public class ArtistPresenterTest extends AndroidTestCase {

    ArtistPresenter presenter;
    ArtistView view;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        view = mock(ArtistView.class);
    }

    public void testInit() throws Exception {
        presenter = new ArtistPresenter(view);

        presenter.init();
    }
}