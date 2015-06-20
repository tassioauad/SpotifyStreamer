package br.com.tassioauad.spotifystreamer.presenter;

import android.test.AndroidTestCase;

import org.mockito.ArgumentCaptor;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.util.ArrayList;
import java.util.List;

import br.com.tassioauad.spotifystreamer.model.api.ApiResultListener;
import br.com.tassioauad.spotifystreamer.model.api.ArtistApi;
import br.com.tassioauad.spotifystreamer.model.api.exception.BadRequestException;
import br.com.tassioauad.spotifystreamer.model.api.exception.NotFoundException;
import br.com.tassioauad.spotifystreamer.model.entity.Artist;
import br.com.tassioauad.spotifystreamer.view.SearchArtistView;

import static org.mockito.Matchers.anyListOf;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class SearchArtistPresenterTest extends AndroidTestCase {

    SearchArtistView view;
    ArtistApi artistApi;
    SearchArtistPresenter presenter;
    ArgumentCaptor<ApiResultListener> apiResultListenerArgumentCaptor;

    @Override
    public void setUp() throws Exception {
        super.setUp();
        view = mock(SearchArtistView.class);
        artistApi = mock(ArtistApi.class);
        apiResultListenerArgumentCaptor = ArgumentCaptor.forClass(ApiResultListener.class);
    }

    public void testInit()  {

    }

    public void testSearchByName_ValidName() {
        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
                doAnswer(new Answer() {
                    @Override
                    public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
                        List<Artist> artistList = new ArrayList<Artist>();
                        artistList.add(new Artist());
                        apiResultListenerArgumentCaptor.getValue().onResult(artistList);
                        return null;
                    }
                }).when(artistApi).findByName(anyString());
                return null;
            }
        }).when(artistApi).setApiResultListener(apiResultListenerArgumentCaptor.capture());
        presenter = new SearchArtistPresenter(view, artistApi);

        presenter.searchByName("Dave Matthews Band");

        verify(artistApi, times(1)).findByName(anyString());
        verify(view, times(1)).showArtists(anyListOf(Artist.class));
        verify(view, never()).anyArtistFounded();
        verify(view, never()).lostConnection();
        verify(view, times(1)).showLoadingWarn();
        verify(view, times(1)).hideLoadingWarn();
    }

    public void testSearchByName_InvalidName() {
        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
                doAnswer(new Answer() {
                    @Override
                    public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
                        List<Artist> artistList = new ArrayList<Artist>();
                        apiResultListenerArgumentCaptor.getValue().onResult(artistList);
                        return null;
                    }
                }).when(artistApi).findByName(anyString());
                return null;
            }
        }).when(artistApi).setApiResultListener(apiResultListenerArgumentCaptor.capture());
        presenter = new SearchArtistPresenter(view, artistApi);

        presenter.searchByName("##.*");

        verify(artistApi, times(1)).findByName(anyString());
        verify(view, never()).showArtists(anyListOf(Artist.class));
        verify(view, times(1)).anyArtistFounded();
        verify(view, never()).lostConnection();
        verify(view, times(1)).showLoadingWarn();
        verify(view, times(1)).hideLoadingWarn();
    }

    public void testSearchByName_BadRequest() {
        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
                doAnswer(new Answer() {
                    @Override
                    public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
                        apiResultListenerArgumentCaptor.getValue().onException(new BadRequestException());
                        return null;
                    }
                }).when(artistApi).findByName(anyString());
                return null;
            }
        }).when(artistApi).setApiResultListener(apiResultListenerArgumentCaptor.capture());
        presenter = new SearchArtistPresenter(view, artistApi);

        presenter.searchByName("##.*");

        verify(artistApi, times(1)).findByName(anyString());
        verify(view, never()).showArtists(anyListOf(Artist.class));
        verify(view, times(1)).anyArtistFounded();
        verify(view, never()).lostConnection();
        verify(view, times(1)).showLoadingWarn();
        verify(view, times(1)).hideLoadingWarn();
    }

    public void testSearchByName_NotFound() {
        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
                doAnswer(new Answer() {
                    @Override
                    public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
                        apiResultListenerArgumentCaptor.getValue().onException(new NotFoundException());
                        return null;
                    }
                }).when(artistApi).findByName(anyString());
                return null;
            }
        }).when(artistApi).setApiResultListener(apiResultListenerArgumentCaptor.capture());
        presenter = new SearchArtistPresenter(view, artistApi);

        presenter.searchByName("##.*");

        verify(artistApi, times(1)).findByName(anyString());
        verify(view, never()).showArtists(anyListOf(Artist.class));
        verify(view, never()).anyArtistFounded();
        verify(view, times(1)).lostConnection();
        verify(view, times(1)).showLoadingWarn();
        verify(view, times(1)).hideLoadingWarn();
    }

    public void testSearchByName_OtherException() {
        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
                doAnswer(new Answer() {
                    @Override
                    public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
                        apiResultListenerArgumentCaptor.getValue().onException(new Exception());
                        return null;
                    }
                }).when(artistApi).findByName(anyString());
                return null;
            }
        }).when(artistApi).setApiResultListener(apiResultListenerArgumentCaptor.capture());
        presenter = new SearchArtistPresenter(view, artistApi);

        presenter.searchByName("##.*");

        verify(artistApi, times(1)).findByName(anyString());
        verify(view, never()).showArtists(anyListOf(Artist.class));
        verify(view, times(1)).anyArtistFounded();
        verify(view, never()).lostConnection();
        verify(view, times(1)).showLoadingWarn();
        verify(view, times(1)).hideLoadingWarn();
    }

    public void testFinish() {
        presenter = new SearchArtistPresenter(view, artistApi);

        presenter.finish();

        verify(artistApi, times(1)).stopAnyExecution();
    }
}