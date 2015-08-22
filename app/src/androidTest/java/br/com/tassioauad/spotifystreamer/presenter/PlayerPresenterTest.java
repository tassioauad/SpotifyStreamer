package br.com.tassioauad.spotifystreamer.presenter;

import android.test.AndroidTestCase;

import junit.framework.TestCase;

import java.util.ArrayList;

import br.com.tassioauad.spotifystreamer.model.entity.Track;
import br.com.tassioauad.spotifystreamer.view.PlayerView;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyBoolean;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class PlayerPresenterTest extends AndroidTestCase {

    PlayerPresenter presenter;
    PlayerView view;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        view = mock(PlayerView.class);
    }

    public void testInit_HasNext() throws Exception {
        presenter = new PlayerPresenter(view);
        ArrayList<Track> trackArrayList = new ArrayList<>();
        Track trackOne = new Track();
        Track trackTwo = new Track();
        trackArrayList.add(trackOne);
        trackArrayList.add(trackTwo);
        int position = 0;

        presenter.init(trackArrayList, position);

        verify(view, times(1)).play(trackOne, true, false);
    }

    public void testInit_HasPrevious() throws Exception {
        presenter = new PlayerPresenter(view);
        ArrayList<Track> trackArrayList = new ArrayList<>();
        Track trackOne = new Track();
        Track trackTwo = new Track();
        trackArrayList.add(trackOne);
        trackArrayList.add(trackTwo);
        int position = 1;

        presenter.init(trackArrayList, position);

        verify(view, times(1)).play(trackTwo, false, true);
    }

    public void testLoadPrevious() throws Exception {
        presenter = new PlayerPresenter(view);
        ArrayList<Track> trackArrayList = new ArrayList<>();
        Track trackOne = new Track();
        Track trackTwo = new Track();
        trackArrayList.add(trackOne);
        trackArrayList.add(trackTwo);
        int position = 1;

        presenter.init(trackArrayList, position);
        presenter.loadPrevious();

        verify(view).play(trackOne, true, false);
    }

    public void testLoadNext() throws Exception {
        presenter = new PlayerPresenter(view);
        ArrayList<Track> trackArrayList = new ArrayList<>();
        Track trackOne = new Track();
        Track trackTwo = new Track();
        trackArrayList.add(trackOne);
        trackArrayList.add(trackTwo);
        int position = 0;

        presenter.init(trackArrayList, position);
        presenter.loadNext();

        verify(view).play(trackTwo, false, true);
    }
}