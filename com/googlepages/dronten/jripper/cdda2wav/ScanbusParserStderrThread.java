/*
* Copyright 2004-2009 by dronten@gmail.com
*
* This source is distributed under the terms of the GNU PUBLIC LICENSE version 3
* http://www.gnu.org/licenses/gpl.html
*/

package com.googlepages.dronten.jripper.cdda2wav;

import com.googlepages.dronten.jripper.util.Log;
import com.googlepages.dronten.jripper.util.Progress;
import com.googlepages.dronten.jripper.util.StreamThread;

import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Parse the content info from the cdda2aw program and set album information.
 */
public class ScanbusParserStderrThread extends StreamThread {
    private static final Pattern    DEVICE = Pattern.compile("\\s*(\\d,\\d,\\d)\\s+\\d\\d\\d\\)\\s(.*)([rR][oO][mM])(.*)");
    private Vector<String>          aDevices = new Vector<String>();


    /**
     * Create parser and set work message.
     */
    public ScanbusParserStderrThread(Log log, Progress progress) throws Exception {
        super(log, progress, ReadType.READ_STDERR_LINES, false);
        aProgress.appendTask(2000, "Trying to scan for cd devices...", "");
        aDevices.add("--- Probed Devices ---");
    }


    /**
     * Callback for the input data.
     *
     * @param line
     */
    public void data(String line) {
        Matcher m1 = DEVICE.matcher(line);

        addLog(1, line);
        if (m1.find()) {
            aDevices.add(m1.group(1) + " - " + m1.group(2) + m1.group(3) + m1.group(4));
        }
    }


    /**
     * Get all found devices.
     *
     * @return Array of devices
     */
    public Vector<String> getDevices() {
        return aDevices;
    }
}
