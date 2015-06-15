package br.com.tassioauad.spotifystreamer.model.api;

import java.util.List;

public interface ArtistApi extends Api {

    void findByName(String name) throws Exception;

}
