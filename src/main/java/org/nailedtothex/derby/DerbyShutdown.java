package org.nailedtothex.derby;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DerbyShutdown implements DerbyShutdownMBean {
    private static final Logger log = Logger.getLogger(DerbyShutdown.class.getName());

    public void create() throws Exception {
        log.finest("create DerbyShutdown MBean");
    }

    public void start() throws Exception {
        log.info("start DerbyShutdown MBean");
    }

    // stop() executes before destory().
    // it's better to execute shutdown as early as possible.
    public void stop() throws Exception {
        log.finest("stop DerbyShutdown MBean");
        shutdown("jdbc:derby:;shutdown=true");
    }

    public void destroy() throws Exception {
        log.finest("destroy DerbyShutdown MBean");
    }

    private void shutdown(String url) {
        Connection cn = null;
        try {
            cn = DriverManager.getConnection(url);
            log.log(Level.SEVERE, "Derby shutdown failed (no exception occurred).");
        } catch (SQLException e) {
            if ("XJ015".equals(e.getSQLState())) {
                log.log(Level.INFO, "Derby shutdown succeeded. SQLState={0}", e.getSQLState());
                log.log(Level.FINEST, "Derby shutdown exception", e);
                return;
            }
            log.log(Level.SEVERE, "Derby shutdown failed", e);
        } finally {
            if (cn != null) {
                try {
                    cn.close();
                } catch (Exception e) {
                    log.log(Level.WARNING, "Database closing error", e);
                }
            }
        }
    }
}
