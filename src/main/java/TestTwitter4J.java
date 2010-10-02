import twitter4j.*;

/**
 */
public class TestTwitter4J {
    public static void main(String[] args) {
        // The factory instance is re-useable and thread safe.
        try {
            Twitter twitter = new TwitterFactory().getInstance();
            Query query = new Query("#onedaytalk");
            QueryResult result = twitter.search(query);
            // System.out.println("hits:" + result. getTotal());
            for (Tweet tweet : result.getTweets()) {
                System.out.println(tweet.getFromUser() + ": " + tweet.getText());
            }
        } catch (TwitterException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }
}
