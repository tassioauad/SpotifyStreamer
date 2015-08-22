package br.com.tassioauad.spotifystreamer.presenter;

import android.test.AndroidTestCase;

import junit.framework.TestCase;

import java.util.ArrayList;

import br.com.tassioauad.spotifystreamer.model.entity.Track;
import br.com.tassioauad.spotifystreamer.view.TrackView;

import static org.mockito.Matchers.anyBoolean;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyListOf;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class TrackPresenterTest extends AndroidTestCase {

    TrackPresenter presenter;
    TrackView view;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        view = mock(TrackView.class);
    }

    public void testInit_TrackListNull() throws Exception {
        presenter = new TrackPresenter(view);
        int position = 0;

        presenter.init(null, position);

        verify(view, times(1)).warnNoTracks();
        verify(view, never()).showPlayer(anyListOf(Track.class), anyInt());
    }

    public void testInit_TrackListSizeZero() throws Exception {
        presenter = new TrackPresenter(view);
        ArrayList<Track> trackArrayList = new ArrayList<>();
        int position = 0;

        presenter.init(trackArrayList, position);

        verify(view, times(1)).warnNoTracks();
        verify(view, never()).showPlayer(anyListOf(Track.class), anyInt());
    }

    public void testInit_PositionNegative() throws Exception {
        presenter = new TrackPresenter(view);
        ArrayList<Track> trackArrayList = new ArrayList<>();
        Track trackOne = new Track();
        Track trackTwo = new Track();
        trackArrayList.add(trackOne);
        trackArrayList.add(trackTwo);
        int position = -1;

        presenter.init(trackArrayList, position);

        verify(view, times(1)).warnNoTracks();
        verify(view, never()).showPlayer(anyListOf(Track.class), anyInt());
    }

    public void testInit_TrackListWithItemPositionPositive() throws Exception {
        presenter = new TrackPresenter(view);
        ArrayList<Track> trackArrayList = new ArrayList<>();
        Track trackOne = new Track();
        Track trackTwo = new Track();
        trackArrayList.add(trackOne);
        trackArrayList.add(trackTwo);
        int position = 0;

        presenter.init(trackArrayList, position);

        verify(view, never()).warnNoTracks();
        verify(view, times(1)).showPlayer(trackArrayList, position);
    }
}