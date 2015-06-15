package br.com.tassioauad.spotifystreamer.model.api.impl;

import junit.framework.TestCase;

import java.util.List;
import java.util.concurrent.CountDownLatch;

import br.com.tassioauad.spotifystreamer.model.api.ApiResultListener;
import br.com.tassioauad.spotifystreamer.model.entity.Artist;

public class ArtistApiImplTest extends TestCase {

    ArtistApiImpl artistApi;

    public void setUp() throws Exception {
        super.setUp();

    }

    public void testFindByName() throws Exception {
        final CountDownLatch signal = new CountDownLatch(1);
        artistApi = new ArtistApiImpl();
        artistApi.setApiResultListener(new ApiResultListener<List<Artist>>() {

            @Override
            public void onResult(List<Artist> artistList) {
                assertNotNull("Artist list is null", artistList);
                assertTrue("Any artist found", artistList.size() > 0);
                signal.countDown();
            }

            @Override
            public void onException(Exception exception) {
                fail(exception.getMessage());
                signal.countDown();
            }
        });

        artistApi.findByName("Dave Matthews Band");
        signal.await();
    }
}