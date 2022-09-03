package org.valeryvash.util;

import org.hibernate.Session;
import org.hibernate.SessionException;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.valeryvash.model.Event;
import org.valeryvash.model.File;
import org.valeryvash.model.User;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.TimeZone;

public class HibernateSessionProvider {
    private static SessionFactory sessionFactory = null;

    static {
        // environment variable shall be presented in OS settings
        // you can find it (heroku) at your app settings -> Config vars -> press Reveal config vars
        final String CONNECTION_URL = System.getenv("CLEARDB_DATABASE_URL");

        // Strange behaviour occurred during JDBC connection create:
        // For deploy at heroku "hibernate.connection.url" shall contain "jdbc:" prefix.
        // "DEPLOYMENT_LOCATION" variable added to heroku for proper variable choose
        final String DEPLOYMENT_LOCATION = System.getenv("DEPLOYMENT_LOCATION");

        Map<String, Object> settings = new HashMap<>();

        if (Objects.equals(DEPLOYMENT_LOCATION, "heroku")) {
            // For deployment at heroku
            settings.put("hibernate.connection.url", "jdbc:" + CONNECTION_URL);
        } else {
            // For test runs at home station to local database
            settings.put("hibernate.connection.url", "jdbc:mysql://localhost:3306/crud_localhost_schema");
            settings.put("hibernate.connection.username", "root");
            settings.put("hibernate.connection.password", "root");
        }

        StandardServiceRegistry standardServiceRegistry = new StandardServiceRegistryBuilder()
                .configure() // load settings from hibernate.cfg.xml
                .applySettings(settings) // apply connection url
                .build();

        sessionFactory = new MetadataSources(standardServiceRegistry)
                .addAnnotatedClass(User.class)
                .addAnnotatedClass(Event.class)
                .addAnnotatedClass(File.class)
                .buildMetadata()
                .buildSessionFactory();

    }

    public static Session provideSession() {
        /*
         *  Time zones
         *  https://www.baeldung.com/hibernate-date-time#time-zone-setup
         *  Duplicates in hibernate.cfg.xml
         */
        Session sessionToBeReturned = sessionFactory
                .withOptions()
                .jdbcTimeZone(TimeZone.getTimeZone("UTC"))
                .openSession();
        if (sessionToBeReturned != null) {
            return sessionToBeReturned;
        } else {
            throw new SessionException("SessionProvider class doesn't provide a session");
        }
    }
}
