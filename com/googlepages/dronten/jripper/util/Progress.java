/*
* Copyright 2004-2009 by dronten@gmail.com
*
* This source is distributed under the terms of the GNU PUBLIC LICENSE version 3
* http://www.gnu.org/licenses/gpl.html
*/

package com.googlepages.dronten.jripper.util;

import java.util.Vector;


/**
 * A progress object that can use one or two progress values and 1 to many work tasks.
 */
public class Progress {
    private static Progress     PROGRESS = null;
    private Vector<String>      aMinorMessageQue = new Vector<String>();
    private Vector<String>      aMajorMessageQue = new Vector<String>();
    private Vector<Long>        aMinorMaxValueQue = new Vector<Long>();
    private Integer             aMinorValue = 0;
    private int                 aQueIndex = 0;
    private double              aTotalValue = 0;
    private double              aTotalRunningValue = 0;
    public boolean              aIncreaseMode = false;
    public int                  aUglyFugly = 0;


    /**
     *
     */
    public Progress() {
    }


    /**
     * Append new single progress task with max value as 100
     *
     * @param minorMessage
     */
    public void appendTask(String minorMessage) {
        aMinorMaxValueQue.add(100l);
        aTotalValue += 100;
        aMinorMessageQue.add(minorMessage);
        aMajorMessageQue.add("");
    }


    /**
     * Append new single progress task
     *
     * @param minorMaxValue
     * @param minorMessage
     */
    public void appendTask(long minorMaxValue, String minorMessage) {
        aMinorMaxValueQue.add(minorMaxValue);
        aTotalValue += minorMaxValue;
        aMinorMessageQue.add(minorMessage);
        aMajorMessageQue.add("");
    }


    /**
     * Append progress task with max value as 100
     *
     * @param minorMessage
     * @param majorMessage
     */
    public void appendTask(String minorMessage, String majorMessage) {
        aMinorMaxValueQue.add(100l);
        aTotalValue += 100;
        aMinorMessageQue.add(minorMessage);
        aMajorMessageQue.add(majorMessage);
    }


    /**
     * Append progress task
     *
     * @param minorMaxValue
     * @param minorMessage
     * @param majorMessage
     */
    public void appendTask(long minorMaxValue, String minorMessage, String majorMessage) {
        aMinorMaxValueQue.add(minorMaxValue);
        aTotalValue += minorMaxValue;
        aMinorMessageQue.add(minorMessage);
        aMajorMessageQue.add(majorMessage);
    }


    /**
     * Clear values.
     * And set max to 100.
     */
    public void clear() {
        aIncreaseMode = false;
        aUglyFugly = 0;
        aMinorValue = 0;
        aQueIndex = 0;
        aTotalValue = 0;
        aTotalRunningValue = 0;
        aMinorMessageQue.clear();
        aMajorMessageQue.clear();
        aMinorMaxValueQue.clear();
    }


    /**
     * Get Progress object.
     *
     * @return Get global progress object
     */
    public static Progress get() {
        if (PROGRESS == null) {
            PROGRESS = new Progress();
        }
        return PROGRESS;
    }


    /**
     * Return work message.
     *
     * @return Major work message
     */
    public String getMajorMessage() {
        assert aMajorMessageQue.size() > aQueIndex;
        return aMajorMessageQue.elementAt(aQueIndex);
    }


    /**
     * Return work message.
     *
     * @return Minor work message
     */
    public String getMinorMessage() {
        assert aMinorMessageQue.size() > aQueIndex;
        return aMinorMessageQue.elementAt(aQueIndex);
    }


    /**
     * Get major progress value
     *
     * @return Total progress as a value between 0 and 100
     */
    public int getMajorProgress() {
        if (aTotalValue > 0) {
            return (int) (((aMinorValue + aTotalRunningValue) / aTotalValue) * 100d);
        }
        else {
            return aMinorValue;
        }
    }


    /**
     * Get minor progress value
     *
     * @return Minor progress value
     */
    public int getMinorProgress() {
        assert aMinorMaxValueQue.size() > aQueIndex;
        Double d = (aMinorValue.doubleValue() / aMinorMaxValueQue.elementAt(aQueIndex).doubleValue()) * 100d;
        return d.intValue();
    }


    /**
     * Go to next work task
     */
    public void nextWorkTask() {
        assert aMinorMaxValueQue.size() > aQueIndex;
        aTotalRunningValue += aMinorMaxValueQue.get(aQueIndex);
        aQueIndex++;
        aMinorValue = 0;
    }


    /**
     * Set minor progress value
     *
     * @param value
     */
    public void setMinorProgress(int value) {
        if (aIncreaseMode) {
            if ((value + aUglyFugly) < aMinorValue) {
                aUglyFugly += 100;
            }

            aMinorValue = value + aUglyFugly;
        }
        else {
            aMinorValue = value;
        }
    }
}
