package util;

import model.User;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import org.hibernate.service.ServiceRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Util {
    //hibernate
    private static Logger logger = LoggerFactory.getLogger(Util.class);
    private static SessionFactory sessionFactory;
    //jdbc
    private static Connection conn = null;

    public Util() {
    }

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            try {
                Properties properties = new Properties();
                try (InputStream input = Util.class.getClassLoader().getResourceAsStream("hibernate.properties")) {
                    if (input == null) {
                        logger.error("Sorry, unable to find hibernate.properties");
                        return null;
                    }
                    properties.load(input);
                } catch (IOException ex) {
                    logger.error("IOException occurred while loading properties", ex);
                }

                Configuration configuration = new Configuration();
                configuration.setProperties(properties);

                configuration.addAnnotatedClass(User.class);

                ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                        .applySettings(configuration.getProperties()).build();
                sessionFactory = configuration.buildSessionFactory(serviceRegistry);
                logger.info("SessionFactory created successfully.");

            } catch (Exception e) {
                logger.error("Exception occurred while building SessionFactory", e);
            }
        }
        return sessionFactory;
    }
    public static void shutdown() {
        if (sessionFactory != null) {
            sessionFactory.close();
            logger.info("SessionFactory closed.");
        }
    }
    public static synchronized Connection getConnection() throws SQLException, IOException {
        if (conn == null || conn.isClosed()) {
            Properties props = getProps();
            conn = DriverManager.getConnection(
                    props.getProperty("db.url"),
                    props.getProperty("db.user"),
                    props.getProperty("db.password")
            );
        }
        return conn;
    }

    private static Properties getProps() throws IOException {
        Properties props = new Properties();
        try (InputStream in = Util.class.getClassLoader().getResourceAsStream("application.properties")) {
            props.load(in);
        } catch (IOException e) {
            throw new IOException("Database configuration file not found", e);
        }
        return props;
    }
}
