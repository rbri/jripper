/*
* Copyright 2004-2009 by dronten@gmail.com
*
* This source is distributed under the terms of the GNU PUBLIC LICENSE version 3
* http://www.gnu.org/licenses/gpl.html
*/

package com.googlepages.dronten.jripper.lame;

import com.googlepages.dronten.jripper.util.StreamParserThread;

import java.util.regex.Pattern;


/**
 * Parser for progress reading while reading MP3 data.
 */
public class ProgressStreamParser extends StreamParserThread {
    /**
     *
     */
    public ProgressStreamParser(boolean toStdout) {
        super(toStdout);
        aPattern = Pattern.compile("Frame#\\s*(\\d+)/(\\d+)\\.*");
        aMatchCount = 2;
    }
}
