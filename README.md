# Project 4 - *ThisTweet*

**ThisTweet** is an android app that allows a user to view home and mentions timelines, view user profiles with user timelines, as well as compose and post a new tweet. The app utilizes [Twitter REST API](https://developer.twitter.com/en/docs/api-reference-index).

Time spent: **1** hours spent in total

## User Stories

The following **required** functionality is completed:

* [x] The app includes **all required user stories** from Week 3 Twitter Client
* [ ] User can **switch between Timeline and Mention views using tabs**
  * [ ] User can view their home timeline tweets.
  * [ ] User can view the recent mentions of their username.
* [ ] User can navigate to **view their own profile**
  * [ ] User can see picture, tagline, # of followers, # of following, and tweets on their profile.
* [ ] User can **click on the profile image** in any tweet to see **another user's** profile.
 * [ ] User can see picture, tagline, # of followers, # of following, and tweets of clicked user.
 * [ ] Profile view includes that user's timeline
* [ ] User can [infinitely paginate](http://guides.codepath.com/android/Endless-Scrolling-with-AdapterViews-and-RecyclerView) any of these timelines (home, mentions, user) by scrolling to the bottom

The following **optional** features are implemented:

* [ ] User can view following / followers list through the profile
* [ ] Implements robust error handling, [check if internet is available](http://guides.codepath.com/android/Sending-and-Managing-Network-Requests#checking-for-network-connectivity), handle error cases, network failures
* [ ] When a network request is sent, user sees an [indeterminate progress indicator](http://guides.codepath.com/android/Handling-ProgressBars#progress-within-actionbar)
* [ ] User can **"reply" to any tweet on their home timeline**
  * [ ] The user that wrote the original tweet is automatically "@" replied in compose
* [ ] User can click on a tweet to be **taken to a "detail view"** of that tweet
 * [ ] User can take favorite (and unfavorite) or retweet actions on a tweet
* [ ] User can **search for tweets matching a particular query** and see results
* [ ] Usernames and hashtags are styled and clickable within tweets [using clickable spans](http://guides.codepath.com/android/Working-with-the-TextView#creating-clickable-styled-spans)

The following **bonus** features are implemented:

* [ ] Use Parcelable instead of Serializable using the popular [Parceler library](http://guides.codepath.com/android/Using-Parceler).
* [ ] Leverages the [data binding support module](http://guides.codepath.com/android/Applying-Data-Binding-for-Views) to bind data into layout templates.
* [ ] On the profile screen, leverage the [CoordinatorLayout](http://guides.codepath.com/android/Handling-Scrolls-with-CoordinatorLayout#responding-to-scroll-events) to [apply scrolling behavior](https://hackmd.io/s/SJyDOCgU) as the user scrolls through the profile timeline.
* [ ] User can view their direct messages (or send new ones)

The following **additional** features are implemented:

* [ ] List anything else that you can get done to improve the app functionality!

## Video Walkthrough

Here's a walkthrough of implemented user stories:

[Video via Dropbox Link](https://www.dropbox.com/s/b4phshtp282cobe/ThisTweet_2017_10_01_17_35_44.mp4?dl=0).

Video created with [AZ Screen Recorder](https://play.google.com/store/apps/details?id=com.hecorat.screenrecorder.free&hl=en).

## Notes

Describe any challenges encountered while building the app.

## Open-source libraries used

- [Okhttp](http://square.github.io/okhttp/) - An HTTP+HTTP/2 client for Android and Java applications.
- [Glide](http://bumptech.github.io/glide/) - An image loading and caching library for Android focused on smooth scrolling.
- [Gson](https://github.com/google/gson) - A Java serialization/deserialization library to convert Java Objects into JSON and back.
- [Apache Commons IO](https://github.com/apache/commons-io) - The Apache Commons IO library contains utility classes, stream implementations, file filters, file comparators, endian transformation classes, and much more.
- [BottomDialog](https://github.com/javiersantos/BottomDialogs) - An Android library that shows a customizable Material-based bottom sheet.
- [Date4J](http://www.date4j.net/) - Lightweight alternative to Java's built-in date-time classes.
- [glide-transformations](https://github.com/wasabeef/glide-transformations) - An Android transformation library providing a variety of image transformations for Glide.
- [okhttp-signpost](https://github.com/pakerfeldt/okhttp-signpost) - OAuth signing with signpost and okhttp
- [signpost](https://github.com/mttkay/signpost) - A light-weight client-side OAuth library for Java
- [android-oauth-handler](https://github.com/codepath/android-oauth-handler) - Android OAuth Wrapper makes authenticating with APIs simple

## License

    Copyright 2017 Robert Lee

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

        http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.