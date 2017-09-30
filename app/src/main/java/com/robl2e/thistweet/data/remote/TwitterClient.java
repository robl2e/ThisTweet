package com.robl2e.thistweet.data.remote;

import android.content.Context;

import com.codepath.oauth.OAuthBaseClient;
import com.github.scribejava.apis.TwitterApi;
import com.github.scribejava.core.builder.api.BaseApi;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

/**
 * Created by robl2e on 9/27/17.
 */

public class TwitterClient extends OAuthBaseClient{
    public static final BaseApi REST_API_INSTANCE = TwitterApi.instance();
    public static final String REST_URL = "https://api.twitter.com/1.1";
    public static final String REST_CONSUMER_KEY = "sdB2Qe4dQTrmnyfvdNJu5nfKp";
    public static final String REST_CONSUMER_SECRET = "rXkMQGavvu4cP6m7l4Ow6qovw524UlrBr46wgSWJdaXX3V85xT";
    public static final String REST_CALLBACK_URL = "x-oauthflow-twitter://arbitraryname.com";


    public TwitterClient(Context context) {
        super(context, REST_API_INSTANCE, REST_URL,
                REST_CONSUMER_KEY, REST_CONSUMER_SECRET, REST_CALLBACK_URL);
    }

    public void getHomeTimeline(int page, AsyncHttpResponseHandler handler) {
        String apiUrl = getApiUrl("statuses/home_timeline.json");
        RequestParams params = new RequestParams();
        params.put("page", String.valueOf(page));
        client.get(apiUrl, params, handler);
    }

    public TwitterClient(Context c, BaseApi apiInstance, String consumerUrl, String consumerKey, String consumerSecret, String callbackUrl) {
        super(c, apiInstance, consumerUrl, consumerKey, consumerSecret, callbackUrl);
    }
}
