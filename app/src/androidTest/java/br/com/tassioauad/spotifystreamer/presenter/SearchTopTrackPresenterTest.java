package br.com.tassioauad.spotifystreamer.presenter;

import android.test.AndroidTestCase;

import org.mockito.ArgumentCaptor;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.util.ArrayList;
import java.util.List;

import br.com.tassioauad.spotifystreamer.model.api.ApiResultListener;
import br.com.tassioauad.spotifystreamer.model.api.TrackApi;
import br.com.tassioauad.spotifystreamer.model.api.exception.BadRequestException;
import br.com.tassioauad.spotifystreamer.model.api.exception.NotFoundException;
import br.com.tassioauad.spotifystreamer.model.entity.Artist;
import br.com.tassioauad.spotifystreamer.model.entity.Track;
import br.com.tassioauad.spotifystreamer.view.SearchTopTrackView;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyListOf;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class SearchTopTrackPresenterTest extends AndroidTestCase {

    SearchTopTrackPresenter presenter;
    SearchTopTrackView view;
    TrackApi trackApi;
    ArgumentCaptor<ApiResultListener> apiResultListenerArgumentCaptor;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        view = mock(SearchTopTrackView.class);
        trackApi = mock(TrackApi.class);
        apiResultListenerArgumentCaptor = ArgumentCaptor.forClass(ApiResultListener.class);
    }

    public void testSearchByArtist_ValidArtist() {
        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
                doAnswer(new Answer() {
                    @Override
                    public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
                        List<Track> trackList = new ArrayList<>();
                        trackList.add(new Track());
                        apiResultListenerArgumentCaptor.getValue().onResult(trackList);
                        return null;
                    }
                }).when(trackApi).findTopTenTrack(any(Artist.class));
                return null;
            }
        }).when(trackApi).setApiResultListener(apiResultListenerArgumentCaptor.capture());
        presenter = new SearchTopTrackPresenter(view, trackApi);

        presenter.searchByArtist(new Artist());

        verify(trackApi, times(1)).findTopTenTrack(any(Artist.class));
        verify(view, times(1)).showTracks(anyListOf(Track.class));
        verify(view, never()).anyTrackFounded();
        verify(view, never()).lostConnection();
        verify(view, times(1)).showLoadingWarn();
        verify(view, times(1)).hideLoadingWarn();
    }

    public void testSearchByArtist_InvalidArtist() {
        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
                doAnswer(new Answer() {
                    @Override
                    public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
                        List<Track> trackList = new ArrayList<>();
                        apiResultListenerArgumentCaptor.getValue().onResult(trackList);
                        return null;
                    }
                }).when(trackApi).findTopTenTrack(any(Artist.class));
                return null;
            }
        }).when(trackApi).setApiResultListener(apiResultListenerArgumentCaptor.capture());
        presenter = new SearchTopTrackPresenter(view, trackApi);

        presenter.searchByArtist(new Artist());

        verify(trackApi, times(1)).findTopTenTrack(any(Artist.class));
        verify(view, never()).showTracks(anyListOf(Track.class));
        verify(view, times(1)).anyTrackFounded();
        verify(view, never()).lostConnection();
        verify(view, times(1)).showLoadingWarn();
        verify(view, times(1)).hideLoadingWarn();
    }

    public void testSearchByArtist_BadRequest() {
        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
                doAnswer(new Answer() {
                    @Override
                    public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
                        apiResultListenerArgumentCaptor.getValue().onException(new BadRequestException());
                        return null;
                    }
                }).when(trackApi).findTopTenTrack(any(Artist.class));
                return null;
            }
        }).when(trackApi).setApiResultListener(apiResultListenerArgumentCaptor.capture());
        presenter = new SearchTopTrackPresenter(view, trackApi);

        presenter.searchByArtist(new Artist());

        verify(trackApi, times(1)).findTopTenTrack(any(Artist.class));
        verify(view, never()).showTracks(anyListOf(Track.class));
        verify(view, times(1)).anyTrackFounded();
        verify(view, never()).lostConnection();
        verify(view, times(1)).showLoadingWarn();
        verify(view, times(1)).hideLoadingWarn();
    }

    public void testSearchByArtist_NotFound() {
        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
                doAnswer(new Answer() {
                    @Override
                    public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
                        apiResultListenerArgumentCaptor.getValue().onException(new NotFoundException());
                        return null;
                    }
                }).when(trackApi).findTopTenTrack(any(Artist.class));
                return null;
            }
        }).when(trackApi).setApiResultListener(apiResultListenerArgumentCaptor.capture());
        presenter = new SearchTopTrackPresenter(view, trackApi);

        presenter.searchByArtist(new Artist());

        verify(trackApi, times(1)).findTopTenTrack(any(Artist.class));
        verify(view, never()).showTracks(anyListOf(Track.class));
        verify(view, never()).anyTrackFounded();
        verify(view, times(1)).lostConnection();
        verify(view, times(1)).showLoadingWarn();
        verify(view, times(1)).hideLoadingWarn();
    }

    public void testSearchByArtist_OtherException() {
        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
                doAnswer(new Answer() {
                    @Override
                    public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
                        apiResultListenerArgumentCaptor.getValue().onException(new Exception());
                        return null;
                    }
                }).when(trackApi).findTopTenTrack(any(Artist.class));
                return null;
            }
        }).when(trackApi).setApiResultListener(apiResultListenerArgumentCaptor.capture());
        presenter = new SearchTopTrackPresenter(view, trackApi);

        presenter.searchByArtist(new Artist());

        verify(trackApi, times(1)).findTopTenTrack(any(Artist.class));
        verify(view, never()).showTracks(anyListOf(Track.class));
        verify(view, times(1)).anyTrackFounded();
        verify(view, never()).lostConnection();
        verify(view, times(1)).showLoadingWarn();
        verify(view, times(1)).hideLoadingWarn();
    }

    public void testFinish() {
        presenter = new SearchTopTrackPresenter(view, trackApi);

        presenter.finish();

        verify(trackApi, times(1)).stopAnyExecution();
    }

}
