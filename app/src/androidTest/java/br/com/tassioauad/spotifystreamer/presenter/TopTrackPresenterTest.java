package br.com.tassioauad.spotifystreamer.presenter;

import android.test.AndroidTestCase;

import junit.framework.TestCase;

import br.com.tassioauad.spotifystreamer.view.TopTrackView;

import static org.mockito.Mockito.mock;

public class TopTrackPresenterTest extends AndroidTestCase {

    TopTrackPresenter presenter;
    TopTrackView view;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        view = mock(TopTrackView.class);
    }


    public void testInit() throws Exception {
        presenter = new TopTrackPresenter(view);

        presenter.init();
    }
}