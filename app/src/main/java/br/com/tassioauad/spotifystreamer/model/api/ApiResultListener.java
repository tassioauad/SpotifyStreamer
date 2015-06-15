package br.com.tassioauad.spotifystreamer.model.api;

public interface ApiResultListener<RESULT> {

    void onResult(RESULT result);

    void onException(Exception exception);

}