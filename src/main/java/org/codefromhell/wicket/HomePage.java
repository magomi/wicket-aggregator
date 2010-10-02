package org.codefromhell.wicket;


import com.sun.syndication.feed.synd.SyndEntry;
import org.apache.wicket.PageParameters;
import org.apache.wicket.ResourceReference;
import org.apache.wicket.ajax.AjaxEventBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormChoiceComponentUpdatingBehavior;
import org.apache.wicket.ajax.form.AjaxFormComponentUpdatingBehavior;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.markup.html.CSSPackageResource;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.RefreshingView;
import org.apache.wicket.markup.repeater.util.ModelIteratorAdapter;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.codefromhell.wicket.aggregator.twitter.RssAggregator;
import org.codefromhell.wicket.aggregator.twitter.TwitterAggregator;
import twitter4j.Tweet;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * The applications start page.
 *
 * @author Marco Grunert (magomi@gmail.com)
 *
 */
public class HomePage extends WebPage {

	private static final long serialVersionUID = 1L;

    private List<Tweet> tweets = new ArrayList<Tweet>();
    private String query = "#wicket";
    private WebMarkupContainer tweetsWmc;

    private List<SyndEntry> entries = new ArrayList<SyndEntry>();
    private String feed = "http://blog.chaosradio.ccc.de/index.php/feed/";
    private WebMarkupContainer feedWmc;

    /**
     * Constructor.
     *
     * @param parameters Additional url parameters that has been provided to the page.
     */
    public HomePage(final PageParameters parameters) {

        // add the style definions
        this.add(CSSPackageResource.getHeaderContribution(new ResourceReference(HomePage.class, "960.css")));

        Form form = new Form("form", new CompoundPropertyModel<HomePage>(this));

        ////////////////////////////////////////////////////////////
        // twitter
        ////////////////////////////////////////////////////////////

        form.add(new TextField("query"));

        // init the tweet list
        final TwitterAggregator ta = new TwitterAggregator();
        tweets = ta.searchTweets(query);

        form.add(new AjaxButton("search") {
            @Override
            protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
                tweets = ta.searchTweets(query);

                target.addComponent(tweetsWmc);
            }
        });

        tweetsWmc = new WebMarkupContainer("tweetsWmc");
        RefreshingView<Tweet> tweetsRV = new RefreshingView<Tweet>("tweets") {
            @Override
            protected Iterator<IModel<Tweet>> getItemModels() {
                return new ModelIteratorAdapter<Tweet>(tweets.iterator()) {
                    @Override
                    protected IModel<Tweet> model(Tweet object) {
                        return new CompoundPropertyModel<Tweet>(object);
                    }
                };
            }

            @Override
            protected void populateItem(final Item<Tweet> tweetItem) {
                tweetItem.add(new Label("fromUser"));
                tweetItem.add(new Label("text"));
            }
        };
        tweetsWmc.add(tweetsRV);
        tweetsWmc.setOutputMarkupId(true);
        form.add(tweetsWmc);

        ////////////////////////////////////////////////////////////
        // rss
        ////////////////////////////////////////////////////////////

        final RssAggregator rssAggregator = new RssAggregator();
        entries = rssAggregator.getFeedEntries(feed);
        
        List<String> feeds = new ArrayList<String>();
        feeds.add("http://blog.chaosradio.ccc.de/index.php/feed/");
        feeds.add("http://www.wicket-praxis.de/blog/feed/");
        feeds.add("http://www.heise.de/developer/rss/news-atom.xml");

        DropDownChoice feedChoice = new DropDownChoice("feed", feeds);
        feedChoice.add(new AjaxFormComponentUpdatingBehavior("onchange") {
            @Override
            protected void onUpdate(AjaxRequestTarget target) {
                entries = rssAggregator.getFeedEntries(feed);
                target.addComponent(feedWmc);
            }
        });
        form.add(feedChoice);

        feedWmc = new WebMarkupContainer("feedWmc");
        RefreshingView<SyndEntry> entriesRV = new RefreshingView<SyndEntry>("entries") {
            @Override
            protected Iterator<IModel<SyndEntry>> getItemModels() {
                return new ModelIteratorAdapter<SyndEntry>(entries.iterator()) {
                    @Override
                    protected IModel<SyndEntry> model(SyndEntry object) {
                        return new CompoundPropertyModel<SyndEntry>(object);
                    }
                };
            }

            @Override
            protected void populateItem(final Item<SyndEntry> entryItem) {
                Link entryLink = new Link("link") {
                    @Override
                    public void onClick() {
                        // TODO
                    }
                };
                entryLink.add(new Label("title"));

                entryItem.add(entryLink);
            }
        };
        feedWmc.add(entriesRV);
        feedWmc.setOutputMarkupId(true);
        form.add(feedWmc);

        add(form);
    }

}
