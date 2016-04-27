package com.dao;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.mchange.v2.c3p0.DataSources;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created by tzry on 16/2/15.
 */
public final class ConnectionManager {

    private static ConnectionManager instance;
    public ComboPooledDataSource ds;

    private static String c3p0Properties = "c3p0.properties";

    private ConnectionManager() throws Exception {
        //Properties p=new Properties();
        //p.load(this.getClass().getResourceAsStream(c3p0Properties));

        /*
        ds=new ComboPooledDataSource();
        ds.setUser(p.getProperty("user"));
        ds.setPassword(p.getProperty("password"));
        ds.setJdbcUrl(p.getProperty("jdbcurl"));
        ds.setDriverClass(p.getProperty("driverclass"));
        ds.setInitialPoolSize(Integer.parseInt(p.getProperty("initialPoolSize")));
        ds.setMinPoolSize(Integer.parseInt(p.getProperty("minPoolSize")));
        ds.setMaxPoolSize(Integer.parseInt(p.getProperty("maxPoolSize")));
        ds.setMaxStatements(Integer.parseInt(p.getProperty("maxStatements")));
        ds.setMaxIdleTime(Integer.parseInt(p.getProperty("maxdieTime")));
*/
        ds = new ComboPooledDataSource();
        ds.setUser("root");
        ds.setPassword("");
        ds.setJdbcUrl("jdbc:mysql://localhost:3306/security?characterEncoding=UTF8");
        ds.setDriverClass("com.mysql.jdbc.Driver");
        ds.setInitialPoolSize(10);
        ds.setMinPoolSize(5);
        ds.setMaxPoolSize(50);
        ds.setMaxStatements(200);
        ds.setMaxIdleTime(10000);
    }


    public static final ConnectionManager getInstance() {
        if (instance == null) {
            try {
                instance = new ConnectionManager();
            } catch (Exception e) {
            }
        }
        return instance;
    }

    public synchronized final Connection getConnection() {
        try {
            return ds.getConnection();
        } catch (SQLException e) {

        }
        return null;
    }


    protected void finalize() throws Throwable {
        DataSources.destroy(ds);
        super.finalize();
    }
}
