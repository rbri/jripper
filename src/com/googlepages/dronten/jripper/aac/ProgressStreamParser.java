/*
* Copyright 2004-2010 by dronten@gmail.com
*
* This source is distributed under the terms of the GNU PUBLIC LICENSE version 3
* http://www.gnu.org/licenses/gpl.html
*/

package com.googlepages.dronten.jripper.aac;

import com.googlepages.dronten.jripper.util.StreamParserThread;

import java.util.regex.Pattern;


/**
 * Parser for progress reading while reading M4A data.
 */
public class ProgressStreamParser extends StreamParserThread {
    /**
     *
     */
    public ProgressStreamParser(boolean toStdout) {
        super(toStdout);
        aPattern = Pattern.compile("(\\d+)\\% decoding\\.*");
        aMatchCount = 1;
    }
}
