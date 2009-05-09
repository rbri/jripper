/*
* Copyright 2004-2009 by dronten@gmail.com
*
* This source is distributed under the terms of the GNU PUBLIC LICENSE version 3
* http://www.gnu.org/licenses/gpl.html
*/

package com.googlepages.dronten.jripper.util;

/**
 * Dummy stream reader.
 */
public class DummyParserThread extends StreamThread {
    /**
     * Create parser and set work message.
     */
    public DummyParserThread(ReadType readType) throws Exception {
        super(null, null, readType, false);
    }


    /**
     * Callback for the input data.
     *
     * @param line
     */
    public void data(String line) {
    }
}