/*
* Copyright 2004-2009 by dronten@gmail.com
*
* This source is distributed under the terms of the GNU PUBLIC LICENSE version 3
* http://www.gnu.org/licenses/gpl.html
*/

package com.googlepages.dronten.jripper.util;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;


/**
 * A log object.
 */
public class Log {
    private static Log              LOG = null;
    private final ArrayList<String> aLog = new ArrayList<String>();
    private int                     aLogLevel = 0;
    private int                     aMaxSize = 0;
    private BufferedOutputStream    aOutputStream = null;


    /**
     * Create log object.
     *
     * @param maxSize   Max number of strings to store
     */
    public Log(int maxSize) {
        aMaxSize = maxSize;
    }


    /**
     * Create log object.
     *
     * @param maxSize   Max number of strings to store
     * @param fileName  Save also every line to logfile
     */
    public Log(int maxSize, String fileName) {
        aMaxSize = maxSize;

        try {
            aOutputStream = new BufferedOutputStream(new FileOutputStream(fileName));
        }
        catch (Exception e) {
            aOutputStream = null;
        }
    }


    /**
     * Add log message.
     *
     * @param logLevel  Log level
     * @param message   Message
     * @param add_time  true for inserting a time stamp
     */
    private void add(int logLevel, String message, boolean add_time) {
        if (logLevel <= aLogLevel) {
            String s = "";

            if (add_time) {
                GregorianCalendar cal = new GregorianCalendar();
                s = String.format("%02d:%02d:%02d %s", cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), cal.get(Calendar.SECOND), message);
            }
            else {
                s = message;
            }

            synchronized (aLog) {
                if (aOutputStream != null) {
                    try {
                        aOutputStream.write(s.getBytes());
                        aOutputStream.write("\n".getBytes());
                        aOutputStream.flush();
                    }
                    catch (Exception e) {
                    }
                }
                aLog.add(s);
                if (aLog.size() > aMaxSize) {
                    aLog.remove(0);
                }
            }
        }
    }


    /**
     * Add log message.
     *
     * @param logLevel
     * @param message
     */
    public void add(int logLevel, String message) {
        add(logLevel, message, false);
    }


    /**
     * Add log message with a time stamp first.
     *
     * @param logLevel
     * @param message
     */
    public void addTime(int logLevel, String message) {
        add(logLevel, message, true);
    }


    /**
     * Clear all log messages.
     */
    public void clear() {
        aLog.clear();
    }


    /**
     * Get global log. If it doesn't exist create it.
     * Maximum rows is 1000.
     *
     * @return
     */
    public static Log get() {
        if (LOG == null) {
            LOG = new Log(1000);
        }
        return LOG;
    }


    /**
     * Get global log. If it doesn't exist create it.
     * Maximum rows is 1000.
     *
     * @return
     */
    public static Log get(String fileName) {
        if (LOG == null) {
            LOG = new Log(1000, fileName);
        }
        return LOG;
    }


    /**
     * Add all log rows in one string with newlines between every row.
     *
     * @return
     */
    public String getLogMessage() {
        String message = "";

        for (String s : aLog) {
            message += s + "\n";
        }

        return message;
    }


    /**
     * Set current log level
     * s
     *
     * @param level
     */
    public void setLogLevel(int level) {
        aLogLevel = level;
    }


    /**
     * Print message to screen.
     *
     * @param verbose
     * @param message
     */
    public void print(int verbose, String message) {
        if (verbose <= aLogLevel) {
            String s = "";

            GregorianCalendar cal = new GregorianCalendar();
            System.out.println(String.format("%02d:%02d:%02d %s", cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), cal.get(Calendar.SECOND), message));
            System.out.flush();
        }
    }


    /**
     * Save log to file.
     *
     * @param fileName  Name of log file
     */
    public void save(String fileName) {
        BufferedOutputStream out = null;

        try {
            out = new BufferedOutputStream(new FileOutputStream(fileName));

            for (String line : aLog) {
                out.write(line.getBytes());
                out.write("\n".getBytes());
            }

            out.close();
        }
        catch (IOException e) {
        }
        finally {
            try {if (out != null) out.close();}
            catch (Exception e) {}
        }
    }


}
