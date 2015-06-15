package br.com.tassioauad.spotifystreamer.model.api;

public abstract class GenericApi {

    private ApiResultListener listener;

    public ApiResultListener getApiResultListener() {
        return listener;
    }

    public void setApiResultListener(ApiResultListener listener) {
        this.listener = listener;
    }

    public void verifyApiResultListenerIsSetted() {
        if (getApiResultListener() == null) {
            throw new RuntimeException("You must set an ApiResultListener instance before");
        }
    }
}