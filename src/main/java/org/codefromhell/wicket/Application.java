package org.codefromhell.wicket;

import org.apache.wicket.Page;
import org.apache.wicket.protocol.http.HttpSessionStore;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.session.ISessionStore;

/**
 * Application object for the webapp.
 *
 * @author Marco Grunert (magomi@gmail.com)
 */
public class Application extends WebApplication {

    private static final long serialVersionUID = 3L;

	/**
	 * Constructor.
	 */
	public Application() {
	}

    /**
     * Returns the class of the page that should be loaded on first access to the application.
     *
     * @return The class of the applications start page.
     */
	@Override
	public Class<? extends Page> getHomePage() {
		return HomePage.class;
	}

    @Override
	protected void init() {
		super.init();

		this.getResourceSettings().setResourcePollFrequency(null);
	}

    @Override
	protected ISessionStore newSessionStore() {	
		return new HttpSessionStore(this);
	}
}
