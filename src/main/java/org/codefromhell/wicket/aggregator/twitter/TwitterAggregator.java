package org.codefromhell.wicket.aggregator.twitter;

import twitter4j.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class TwitterAggregator implements Serializable {

    private static final long serialVersionUID = 2L;

    public List<Tweet> searchTweets(String query) {
        List<Tweet> tweets = new ArrayList<Tweet>();
        try {
            Twitter twitter = new TwitterFactory().getInstance();
            Query twQuery = new Query(query);
            QueryResult result = twitter.search(twQuery);
            tweets = result.getTweets();
        } catch (TwitterException e) {
            e.printStackTrace();
        }
        return tweets;
    }
}
