package com.robl2e.thistweet.data.local.user;


import android.util.Log;

import com.robl2e.thistweet.data.model.user.User;
import com.robl2e.thistweet.data.remote.AppResponseHandler;
import com.robl2e.thistweet.data.remote.ErrorCodes;
import com.robl2e.thistweet.data.remote.TwitterClient;
import com.robl2e.thistweet.util.JsonUtils;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by robl2e on 10/4/17.
 */

public class UserRepository {
    private static final String TAG = UserRepository.class.getSimpleName();
    private TwitterClient client;

    public UserRepository(TwitterClient client) {
        this.client = client;
    }

    public void getUser(String userId, final AppResponseHandler<User> responseHandler) {
        client.getUserRequest(userId, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e(TAG, Log.getStackTraceString(e));

                if (responseHandler != null) responseHandler.onFailure(e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String rawString = response.body().string();
                Log.d(TAG, "rawString = " + rawString);

                int code = response.code();
                if (code != 200) {
                    if (code == ErrorCodes.INVALID_OR_EXPIRED_TOKEN) {
                        client.clearAccessToken();
                    }
                    if (responseHandler != null) {
                        responseHandler.onFailure(new IOException(rawString));
                    }
                    return;
                }


                try {
                    User user = JsonUtils.fromJson(rawString, User.class);
                    if (responseHandler != null) {
                        responseHandler.onComplete(response, user);
                    }
                } catch (IllegalStateException ex) {
                    Log.e(TAG, Log.getStackTraceString(ex));
                    if (responseHandler != null) {
                        responseHandler.onFailure(ex);
                    }
                }
            }
        });
    }
}
