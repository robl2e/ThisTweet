package com.robl2e.thistweet.data.local.timeline;

import android.content.res.Resources;
import android.util.Log;
import com.google.gson.reflect.TypeToken;
import com.robl2e.thistweet.R;
import com.robl2e.thistweet.data.model.timeline.Tweet;
import com.robl2e.thistweet.data.remote.AppResponseHandler;
import com.robl2e.thistweet.data.remote.TwitterClient;
import com.robl2e.thistweet.util.JsonUtils;

import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
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

    public void getTimeline(final AppResponseHandler<List<Tweet>> responseHandler) {
        _getTimeline(null, responseHandler);
    }

    public void getTimeline(Long maxId, final AppResponseHandler<List<Tweet>> responseHandler) {
        _getTimeline(maxId, responseHandler);
    }

    private void _getTimeline(Long maxId, final AppResponseHandler<List<Tweet>> responseHandler) {
        Long oneLessMaxId = maxId != null ? maxId - 1 : null;
        client.homeTimelineRequest(oneLessMaxId, new Callback() {
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

                Type listType = new TypeToken<List<Tweet>>() {}.getType();
                List<Tweet> tweets = JsonUtils.fromJson(rawString, listType);

                if (responseHandler != null) {
                    responseHandler.onComplete(response, tweets);
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
                responseHandler.onComplete(null, tweets);
            }
        } catch (IOException e) {
            Log.e(TAG, Log.getStackTraceString(e));
        }
    }
}
