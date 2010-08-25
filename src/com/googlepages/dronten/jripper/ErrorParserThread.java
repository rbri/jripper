/*
* Copyright 2004-2010 by dronten@gmail.com
*
* This source is distributed under the terms of the GNU PUBLIC LICENSE version 3
* http://www.gnu.org/licenses/gpl.html
*/

package com.googlepages.dronten.jripper;

import com.googlepages.dronten.jripper.util.Log;
import com.googlepages.dronten.jripper.util.StreamThread;


/**
 * Save lines from stderr to the log object.
 */
public class ErrorParserThread extends StreamThread {
    /**
     * @param log - Log object
     */
    public ErrorParserThread(Log log) {
        super(log, null, ReadType.READ_STDERR_LINES, false);
    }


    /**
     * @param line
     */
    public void data(String line) {
        addLog(2, line);
    }
}
