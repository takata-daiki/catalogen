package org.bitbucket.ingvord.dirscanner;

import org.apache.log4j.Appender;
import org.apache.log4j.Logger;
import org.apache.log4j.Priority;
import org.apache.log4j.spi.LoggingEvent;

/**
 * A logger that does nothing.
 *
 * User: Ingvord
 * Date: 04.07.11
 */
public class NullLogger extends Logger {
    public static final NullLogger INSTANCE = new NullLogger("null");

    protected NullLogger(String name) {
        super(name);
    }

    @Override
    public void trace(Object message) {
    }

    @Override
    public void trace(Object message, Throwable t) {
    }

    @Override
    public void addAppender(Appender newAppender) {
    }

    @Override
    public void assertLog(boolean assertion, String msg) {
    }

    @Override
    public void callAppenders(LoggingEvent event) {
    }

    @Override
    public void debug(Object message) {
    }

    @Override
    public void debug(Object message, Throwable t) {
    }

    @Override
    public void error(Object message) {
    }

    @Override
    public void error(Object message, Throwable t) {
    }

    @Override
    public void fatal(Object message) {
    }

    @Override
    public void fatal(Object message, Throwable t) {
    }

    @Override
    protected void forcedLog(String fqcn, Priority level, Object message, Throwable t) {
    }

    @Override
    public void info(Object message) {
    }

    @Override
    public void info(Object message, Throwable t) {
    }

    @Override
    public void l7dlog(Priority priority, String key, Throwable t) {
    }

    @Override
    public void l7dlog(Priority priority, String key, Object[] params, Throwable t) {
    }

    @Override
    public void log(Priority priority, Object message, Throwable t) {
    }

    @Override
    public void log(Priority priority, Object message) {
    }

    @Override
    public void log(String callerFQCN, Priority level, Object message, Throwable t) {
    }

    @Override
    public void warn(Object message) {
    }

    @Override
    public void warn(Object message, Throwable t) {
    }
}
