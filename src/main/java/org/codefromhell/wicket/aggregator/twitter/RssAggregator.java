package org.codefromhell.wicket.aggregator.twitter;

import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.io.FeedException;
import com.sun.syndication.io.SyndFeedInput;
import com.sun.syndication.io.XmlReader;
import twitter4j.*;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class RssAggregator {
    public List<SyndEntry> getFeedEntries(String feedUrl) {
        List<SyndEntry> entries = new ArrayList<SyndEntry>();

        try {
            URL url = new URL(feedUrl);
            XmlReader reader = null;

            try {

                reader = new XmlReader(url);

                SyndFeed feed = new SyndFeedInput().build(reader);

                entries = feed.getEntries();

            } finally {

                if (reader != null)

                    reader.close();

            }
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (FeedException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

        return entries;
    }
}
