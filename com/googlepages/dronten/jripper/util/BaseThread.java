/*
* Copyright 2004-2009 by dronten@gmail.com
*
* This source is distributed under the terms of the GNU PUBLIC LICENSE version 3
* http://www.gnu.org/licenses/gpl.html
*/

package com.googlepages.dronten.jripper.util;

/**
 * Base class for worker threads.
 */
public class BaseThread extends Thread {
    private Log         aLog = null;
    protected Progress  aProgress = null;
    protected boolean   aRunning = true;
    private boolean     aFailed = false;


    /**
     * Create thread and set log/progress object. Can be null.
     *
     * @param log
     * @param progress
     */
    public BaseThread(Log log, Progress progress) {
        super();
        aLog = log;
        aProgress = progress;
    }


    /**
     * Add log message.
     *
     * @param message
     */
    public void addLog(int verbose, String message) {
        if (aLog != null) {
            aLog.addTime(verbose, message);
        }
    }


    /**
     * isRunning is returning false after this call.
     */
    public void close() {
        aRunning = false;
    }


    /**
     * Get log object.
     *
     * @return Log object
     */
    public Log getLog() {
        return aLog;
    }


    /**
     * Has thread failed during running?
     *
     * @return true if something failed
     */
    public boolean hasFailed() {
        return aFailed;
    }


    /**
     * Set failed status.
     *
     * @param failed
     */
    public void setFailed(boolean failed) {
        aFailed = failed;
    }


    /**
     * Set log object.
     *
     * @param log
     */
    public void setLog(Log log) {
        aLog = log;
    }


    /**
     * Stop adding messages to the log.
     */
    public void stopLogging() {
        if (aLog != null) {
            synchronized (aLog) {
                aLog = null;
            }
        }
    }


    /**
     * Tell thread that it should break the loop.
     */
    public void stopRunning() {
        aRunning = false;
    }

}
