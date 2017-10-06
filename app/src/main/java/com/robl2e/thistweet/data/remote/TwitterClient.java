package com.robl2e.thistweet.data.remote;

import android.content.Context;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;

import com.codepath.oauth.OAuthBaseClient;
import com.github.scribejava.apis.TwitterApi;
import com.github.scribejava.core.builder.api.BaseApi;
import com.github.scribejava.core.model.OAuth1AccessToken;
import com.github.scribejava.core.model.Token;
import com.robl2e.thistweet.data.local.timeline.TimelineParams;
import com.robl2e.thistweet.data.local.timeline.TimelineType;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import se.akerfeldt.okhttp.signpost.OkHttpOAuthConsumer;
import se.akerfeldt.okhttp.signpost.SigningInterceptor;

/**
 * Created by robl2e on 9/27/17.
 */

public class TwitterClient extends OAuthBaseClient{
    private static final String TAG = TwitterClient.class.getSimpleName();

    public static final BaseApi REST_API_INSTANCE = TwitterApi.instance();
    public static final String REST_URL = "https://api.twitter.com/1.1";
    public static final String REST_CONSUMER_KEY = "sdB2Qe4dQTrmnyfvdNJu5nfKp";
    public static final String REST_CONSUMER_SECRET = "rXkMQGavvu4cP6m7l4Ow6qovw524UlrBr46wgSWJdaXX3V85xT";
    public static final String REST_CALLBACK_URL = "x-oauthflow-twitter://arbitraryname.com";

    private static final String HOME_TIMELINE_ENDPOINT = REST_URL + "/statuses/home_timeline.json";
    private static final String MENTIONS_TIMELINE_ENDPOINT = REST_URL + "/statuses/mentions_timeline.json";
    private static final String USER_TIMELINE_ENDPOINT = REST_URL + "/statuses/user_timeline.json";
    private static final String POST_STATUS_UPDATE_ENDPOINT = REST_URL + "/statuses/update.json";

    private static final String USER_ACCOUNT_ENDPOINT = REST_URL + "/account/verify_credentials.json";

    private static final String PARAM_MAX_ID = "max_id";
    private static final String PARAM_SINCE_ID = "since_id";
    private static final String PARAM_STATUS = "status";
    private static final String PARAM_USER_ID = "user_id";

    private OkHttpClient okHttpClient;

    public TwitterClient(Context context) {
        super(context, REST_API_INSTANCE, REST_URL,
                REST_CONSUMER_KEY, REST_CONSUMER_SECRET, REST_CALLBACK_URL);
        initializeOkHttpClient();
    }

    public TwitterClient(Context c, BaseApi apiInstance, String consumerUrl, String consumerKey, String consumerSecret, String callbackUrl) {
        super(c, apiInstance, consumerUrl, consumerKey, consumerSecret, callbackUrl);
        initializeOkHttpClient();
    }

    public void initialize() {
        if (okHttpClient == null) {
            initializeOkHttpClient();
        }
    }

    private void initializeOkHttpClient() {
        if (client == null) return;
        if (!isAuthenticated()) return;

        OkHttpOAuthConsumer consumer = new OkHttpOAuthConsumer(
                REST_CONSUMER_KEY, REST_CONSUMER_SECRET);

        if (client != null && isAuthenticated()) {
            Token token = client.getAccessToken();
            try {
                OAuth1AccessToken accessToken = (OAuth1AccessToken) token;
                consumer.setTokenWithSecret(accessToken.getToken(), accessToken.getTokenSecret());
            } catch (ClassCastException e) {
                Log.e(TAG, Log.getStackTraceString(e));
            }
        }

        okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(new SigningInterceptor(consumer))
                .build();
    }


    public void getTimelineRequest(@NonNull TimelineParams timelineParams, Long maxId, final Callback callback) {
        if (okHttpClient == null) return;

        String endpoint = resolveEndpoint(timelineParams.type);
        HttpUrl.Builder urlBuilder = HttpUrl
                .parse(endpoint).newBuilder();

        if (timelineParams.type == TimelineType.USER_TIMELINE
                && !TextUtils.isEmpty(timelineParams.id)) {
            urlBuilder.addQueryParameter(PARAM_USER_ID, timelineParams.id);
        }

        if (maxId != null) {
            urlBuilder.addQueryParameter(PARAM_MAX_ID, String.valueOf(maxId));
        }

        HttpUrl url = urlBuilder.build();
        Request request = new Request.Builder()
                .url(url)
                .build();

        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                if (callback != null) {
                    callback.onFailure(call, e);
                }
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (callback != null) {
                    callback.onResponse(call, response);
                }
            }
        });
    }

    private String resolveEndpoint(TimelineType from) {
        switch (from) {
            case HOME_TIMELINE:
                return HOME_TIMELINE_ENDPOINT;
            case MENTIONS_TIMELINE:
                return MENTIONS_TIMELINE_ENDPOINT;
            case USER_TIMELINE:
                return USER_TIMELINE_ENDPOINT;
        }
        return HOME_TIMELINE_ENDPOINT;
    }

    public void postStatusUpdateRequest(String tweetText, final Callback callback) {
        if (okHttpClient == null) return;

        HttpUrl.Builder urlBuilder = HttpUrl
                .parse(POST_STATUS_UPDATE_ENDPOINT).newBuilder();

        if (!TextUtils.isEmpty(tweetText)) {
            urlBuilder.addQueryParameter(PARAM_STATUS, tweetText);
        }

        // Empty POST body
        RequestBody body = RequestBody
                .create(null, new byte[]{});

        HttpUrl url = urlBuilder.build();
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();

        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                if (callback != null) {
                    callback.onFailure(call, e);
                }
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.code() != 200) {
                    if (callback != null) {
                        callback.onFailure(call, new IOException(response.message()));
                    }
                    return;
                }

                if (callback != null) {
                    callback.onResponse(call, response);
                }
            }
        });
    }

    public void getUserAccountRequest(final Callback callback) {
        if (okHttpClient == null) return;

        HttpUrl.Builder urlBuilder = HttpUrl
                .parse(USER_ACCOUNT_ENDPOINT).newBuilder();

        HttpUrl url = urlBuilder.build();
        Request request = new Request.Builder()
                .url(url)
                .build();

        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                if (callback != null) {
                    callback.onFailure(call, e);
                }
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (callback != null) {
                    callback.onResponse(call, response);
                }
            }
        });
    }
}
