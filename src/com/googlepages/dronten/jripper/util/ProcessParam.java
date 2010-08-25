/*
* Copyright 2004-2010 by dronten@gmail.com
*
* This source is distributed under the terms of the GNU PUBLIC LICENSE version 3
* http://www.gnu.org/licenses/gpl.html
*/

package com.googlepages.dronten.jripper.util;

import java.util.ArrayList;


/**
 * Utility class for building a command for the shell.<br>
 * Get the command as a string or an vector.
 */
public class ProcessParam {
    private ArrayList<String> aParams = new ArrayList<String>();


    /**
     *
     */
    public ProcessParam() {
    }


    /**
     * Add param, but only if length is greater than 0.
     *
     * @param param
     */
    public void add(String param) {
        if (param.trim().length() > 0) {
            aParams.add(param.trim());
        }
    }


    /**
     * Add param, but only if length is greater than 0.
     *
     * @param param
     */
    public void addQuote(String param) {
        if (param.trim().length() > 0) {
            aParams.add("\"" + param.trim() + "\"");
        }
    }


    /**
     * Add param, but only if length is greater than 0.
     *
     * @param param
     */
    public void add(int param) {
        aParams.add(Integer.toString(param));
    }


    /**
     * Add param, but only if length is greater than 0.
     *
     * @param param1
     * @param param2
     */
    public void add(String param1, String param2) {
        if (param1.trim().length() > 0 && param2.trim().length() > 0) {
            aParams.add(param1.trim());
            aParams.add(param2.trim());
        }
    }


    /**
     * Add param, but only if length is greater than 0.
     *
     * @param param1
     * @param param2
     */
    public void add(String param1, int param2) {
        if (param1.trim().length() > 0) {
            aParams.add(param1.trim());
            aParams.add(Integer.toString(param2));
        }
    }


    /**
     * Add param, but only if length is greater than 0.
     *
     * @param param1
     * @param param2 String will be inside ""
     */
    public void addQuote(String param1, String param2) {
        if (param1.trim().length() > 0 && param2.trim().length() > 0) {
            aParams.add(param1.trim());
            aParams.add("\"" + param2.trim() + "\"");
        }
    }


    /**
     * Add param, but only if length is greater than 0.
     *
     * @param param1
     * @param param2
     */
    public void addQuote(String param1, int param2) {
        if (param1.trim().length() > 0) {
            aParams.add(param1.trim());
            aParams.add("\"" + Integer.toString(param2) + "\"");
        }
    }


    /**
     * Add param.
     * Split strings into sub strings and add each one as seperate param.
     *
     * @param param
     */
    public void addSplitString(String param) {
        String ve[] = param.split(" ");
        for (String s : ve) {
            if (s.trim().length() > 0) {
                aParams.add(s.trim());
            }
        }
    }


    /**
     * Get command as an array.
     *
     * @return
     */
    public String[] get() {
        String  ve[] = new String[aParams.size()];
        int     f = 0;

        for (String s : aParams) {
            ve[f++] = s;
        }

        return ve;
    }


    /**
     * Get command as an string.
     *
     * @param cmd
     * @return
     */
    public String toString() {
        String tmp = "";
        for (String s : aParams) {
            tmp += s + " ";
        }
        return tmp;
    }
}
