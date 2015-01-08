package org.nailedtothex.derby;

public interface DerbyShutdownMBean {
    void create() throws Exception;

    void start() throws Exception;

    void stop() throws Exception;

    void destroy() throws Exception;
}
