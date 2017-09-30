package com.robl2e.thistweet.data.remote;

import okhttp3.Response;

/**
 * Created by robl2e on 9/29/17.
 */

public interface AppResponseHandler<DATA> {
    void onFailure(Exception e);
    void onComplete(Response response, DATA data);
}
