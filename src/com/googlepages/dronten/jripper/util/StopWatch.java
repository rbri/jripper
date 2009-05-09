/*
* Copyright 2004-2009 by dronten@gmail.com
*
* This source is distributed under the terms of the GNU PUBLIC LICENSE version 3
* http://www.gnu.org/licenses/gpl.html
*/

package com.googlepages.dronten.jripper.util;

import java.util.Calendar;


/**
 * Stop watch object.
 */
public class StopWatch {
    private long aStartTime = 0;
    private long aStopTime = 0;


    public long getStartTime() {
        return aStartTime;
    }


    /**
     * @return
     */
    public long getStopTime() {
        return aStopTime;
    }


    /**
     *
     */
    public void start() {
        aStartTime = Calendar.getInstance().getTimeInMillis();
    }


    /**
     *
     */
    public void stop() {
        aStopTime = Calendar.getInstance().getTimeInMillis();
    }


    /**
     * @return
     */
    public double getDiffTime() {
        return (aStopTime - aStartTime) / 1000d;
    }


    /**
     * @param start
     * @param stop
     * @return
     */
    public String toString() {
        long start = aStartTime / 1000;
        long stop = aStopTime / 1000;
        long min = (stop - start) / 60;
        long sec = (stop - start) - (min * 60);

        return String.format("%02d:%02d", min, sec);
    }
}
