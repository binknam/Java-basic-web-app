package vn.kms.fundamentals.basicwebapp.web.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

public class SessionListener implements HttpSessionListener, HttpSessionAttributeListener {
    private static final Logger logger = LoggerFactory.getLogger(SessionListener.class);

    @Override
    public void sessionCreated(HttpSessionEvent se) {
        logger.debug("Session " + se.getSession().getId() + " is created.");
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        logger.debug("Session " + se.getSession().getId() + " is destroyed.");
    }

    @Override
    public void attributeAdded(HttpSessionBindingEvent event) {
        logger.debug("Session attribute " + event.getName() + " is added.");
    }

    @Override
    public void attributeReplaced(HttpSessionBindingEvent event) {
        logger.debug("Session attribute " + event.getName() + " is updated.");
    }

    @Override
    public void attributeRemoved(HttpSessionBindingEvent event) {
        logger.debug("Session attribute " + event.getName() + " is removed.");
    }
}
