package com.robl2e.thistweet.data.local.timeline;

import android.content.res.Resources;
import android.util.Log;
import com.google.gson.reflect.TypeToken;
import com.robl2e.thistweet.R;
import com.robl2e.thistweet.data.model.timeline.Tweet;
import com.robl2e.thistweet.data.remote.AppResponseHandler;
import com.robl2e.thistweet.data.remote.ErrorCodes;
import com.robl2e.thistweet.data.remote.TwitterClient;
import com.robl2e.thistweet.util.JsonUtils;

import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.List;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Protocol;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by robl2e on 9/29/17.
 */

public class TimelineRepository {
    private static final String TAG = TimelineRepository.class.getSimpleName();

    private TwitterClient client;
    private Resources resources;

    public TimelineRepository(TwitterClient client, Resources resources) {
        this.client = client;
        this.resources = resources;
    }

    public void getTimeline(TimelineParams timelineParams, final AppResponseHandler<List<Tweet>> responseHandler) {
        //getTimeLineTest(responseHandler);
        _getTimeline(timelineParams, null, responseHandler);
    }

    public void getTimeline(TimelineParams timelineParams, Long maxId, final AppResponseHandler<List<Tweet>> responseHandler) {
        _getTimeline(timelineParams, maxId, responseHandler);
    }

    private void _getTimeline(TimelineParams timelineParams, Long maxId, final AppResponseHandler<List<Tweet>> responseHandler) {
        Long oneLessMaxId = maxId != null ? maxId - 1 : null;
        client.getTimelineRequest(timelineParams, oneLessMaxId, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e(TAG, Log.getStackTraceString(e));
                if (responseHandler != null) {
                    responseHandler.onFailure(e);
                }
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

                Type listType = new TypeToken<List<Tweet>>() {}.getType();
                List<Tweet> tweets = null;
                try {
                    tweets = JsonUtils.fromJson(rawString, listType);
                } catch (IllegalStateException ex) {
                    Log.e(TAG, Log.getStackTraceString(ex));
                    if (responseHandler != null) {
                        responseHandler.onFailure(ex);
                    }
                }

                if (responseHandler != null) {
                    responseHandler.onComplete(response, tweets);
                }
            }
        });
    }

    public void createTweet(String tweetText, final AppResponseHandler<Tweet> responseHandler) {
        client.postStatusUpdateRequest(tweetText, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                if (responseHandler != null) {
                    responseHandler.onFailure(e);
                }
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String rawString = response.body().string();
                Log.d(TAG, "postStatus Update rawString = " + rawString);

                Tweet tweet = JsonUtils.fromJson(rawString, Tweet.class);
                if (responseHandler != null) {
                    responseHandler.onComplete(response, tweet);
                }
            }
        });

    }

    // For testing
    private void getTimeLineTest(AppResponseHandler<List<Tweet>> responseHandler) {
        //Get Test data;
        InputStream inputStream = resources.openRawResource(R.raw.timeline);

        try {
            String jsonTimeLine = IOUtils.toString(inputStream, "UTF-8");
            Log.d(TAG, "jsonTimeline= " + jsonTimeLine);

            Type listType = new TypeToken<List<Tweet>>() {}.getType();
            List<Tweet> tweets = JsonUtils.fromJson(jsonTimeLine, listType);

            if (responseHandler != null) {
                Request.Builder requestBuilder = new Request.Builder();
                requestBuilder.url("http://www.twitter.com");
                Request request = requestBuilder.build();

                Response.Builder builder = new Response.Builder();
                builder.request(request);
                builder.protocol(Protocol.HTTP_1_0);
                builder.code(200);

                Response response = builder.build();
                responseHandler.onComplete(response, tweets);
            }
        } catch (IOException e) {
            Log.e(TAG, Log.getStackTraceString(e));
        }
    }

}
