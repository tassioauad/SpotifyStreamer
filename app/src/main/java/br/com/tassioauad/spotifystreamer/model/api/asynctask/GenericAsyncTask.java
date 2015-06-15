package br.com.tassioauad.spotifystreamer.model.api.asynctask;

import android.os.AsyncTask;

import br.com.tassioauad.spotifystreamer.model.api.ApiResultListener;

public abstract class GenericAsyncTask<PARAM, POGRESS, RETURN> extends AsyncTask<PARAM, POGRESS, AsyncTaskResult<RETURN>> {

    private ApiResultListener listener;

    protected GenericAsyncTask(ApiResultListener listener) {
        this.listener = listener;
    }

    @Override
    protected void onPostExecute(AsyncTaskResult<RETURN> returnAsyncTaskResult) {
        if(returnAsyncTaskResult.getResult() != null) {
            listener.onResult(returnAsyncTaskResult.getResult());
        } else if(returnAsyncTaskResult.getError() != null) {
            listener.onException(returnAsyncTaskResult.getError());
        }

    }
}
