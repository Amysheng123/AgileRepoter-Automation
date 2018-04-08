package com.lombardrisk.core.utils;

import org.apache.commons.dbutils.handlers.ArrayListHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;

/**
 * Created by amy sheng on 4/4/2018.
 */
public class DBHelper {
    private final static Logger logger = LoggerFactory.getLogger(DBHelper.class);
    private String dbms;
    private String dbmsDriver;
    private String host;
    private String ip;
    private String sid;
    private String port;
    private String db;
    private String user = "sa";
    private String password = "password";
    private Connection conn = null;

    protected DBHelper(String dbms, String host, String db)
    {
        this.dbms = dbms;
        fillDbmsDriver(dbms);
        this.host = host;
        fillDbmsPort(dbms);
        this.db = db;
    }

    private void fillDbmsDriver(String dbms)
    {
        if (dbms.equalsIgnoreCase("sqlServer"))
            dbmsDriver = "net.sourceforge.jtds.jdbc.Driver";
        else if (dbms.equalsIgnoreCase("oracle"))
            dbmsDriver = "oracle.jdbc.driver.OracleDriver";
    }

    protected void fillDbmsPort(String dbms)
    {
        if (dbms.equalsIgnoreCase("oracle"))
            port = "1521";
        else if (dbms.equalsIgnoreCase("sqlserver"))
            port = "1433";
    }

    protected void connect()
    {
        if (conn != null)
            return;

        String jdbcUrl = null;
        if (dbms.equalsIgnoreCase("oracle"))
            jdbcUrl = String.format("jdbc:oracle:thin:@%s:%s:%s", ip, port, sid);
        else if (dbms.equalsIgnoreCase("sqlserver"))
        {
            if (host.contains("\\"))
            {
                host = host.replace("\\", "#");
                jdbcUrl = String.format("jdbc:jtds:sqlserver://%s:%s/%s;instance=%s", host.split("#")[0], port, db, host.split("#")[1]);
            }
            else
            {
                jdbcUrl = String.format("jdbc:jtds:sqlserver://%s:%s/%s", host, port, db);
            }

        }
        else
        {
            jdbcUrl = String.format("jdbc:%s://%s:%s/%s", dbms, host, port, db);
        }

        DbUtils.loadDriver(dbmsDriver);
        try
        {
            if (dbms.equalsIgnoreCase("oracle"))
                conn = DriverManager.getConnection(jdbcUrl, db, password);
            else
                conn = DriverManager.getConnection(jdbcUrl, user, password);
        }
        catch (SQLException e)
        {
            logger.error("Database connection failed!");
            logger.error("error", e);
        }

    }

    protected void close()
    {
        try
        {
            DbUtils.close(conn);
            conn = null;
        }
        catch (SQLException e)
        {
            logger.error("Database close failed!");
            logger.error("error", e);
        }
    }

    protected String query(String sql) throws SQLException
    {
        String value = null;
        if (conn == null)
            return null;
        else
        {


        }
        return value;
    }

/*    protected List<String> queryRecords(String sql) throws SQLException
    {
        List<String> mapList = new ArrayList<>();
        try {
            QueryRunner qRunner = new QueryRunner();
            mapList = (List) qRunner.query(conn, sql, new ArrayListHandler());
            for (int i = 0; i < mapList.size(); i++) {
                //Map map = (Map) mapList.get(i);

            }
        }catch ()*/
}
