package br.com.tassioauad.spotifystreamer.model.api;

public interface Api {

    ApiResultListener getApiResultListener();

    void setApiResultListener(ApiResultListener listener);

    void stopExecution();

}
