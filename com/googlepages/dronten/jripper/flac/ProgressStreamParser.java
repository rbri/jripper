/*
* Copyright 2004-2009 by dronten@gmail.com
*
* This source is distributed under the terms of the GNU PUBLIC LICENSE version 3
* http://www.gnu.org/licenses/gpl.html
*/

package com.googlepages.dronten.jripper.flac;

import com.googlepages.dronten.jripper.util.StreamParserThread;

import java.util.regex.Pattern;


/**
 * Parser for progress reading while reading flac data.
 */
public class ProgressStreamParser extends StreamParserThread {
    /**
     *
     */
    public ProgressStreamParser(boolean toStdout) {
        super(toStdout);
        aPattern = Pattern.compile(".*?: ?(\\d+)\\% complete");
        aMatchCount = 1;
    }
}
