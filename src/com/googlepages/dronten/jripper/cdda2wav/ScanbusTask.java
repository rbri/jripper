/*
* Copyright 2004-2009 by dronten@gmail.com
*
* This source is distributed under the terms of the GNU PUBLIC LICENSE version 3
* http://www.gnu.org/licenses/gpl.html
*/

package com.googlepages.dronten.jripper.cdda2wav;

import com.googlepages.dronten.jripper.JRipper;
import com.googlepages.dronten.jripper.gui.dialog.ProgressDialog;
import com.googlepages.dronten.jripper.util.Log;
import com.googlepages.dronten.jripper.util.ProcessRunner;
import com.googlepages.dronten.jripper.util.ProcessRunnerReadStdoutAndStderrProc;
import com.googlepages.dronten.jripper.util.Progress;

import java.util.Vector;


/**
 * Scan for available SCSI drives.
 */
public class ScanbusTask {
    public Vector<String> aDevices = null;


    /**
     *
     */
    public ScanbusTask() {
    }


    /**
     * Run cdda2wav for an scanbus operation.
     * Use two parser threads, one for stdin and one for stderr as is seems different versions of cdda2wav is using different streams for the output.
     */
    public void doTask() {
        ProgressDialog              dlg = new ProgressDialog(JRipper.get(), "Probing For SCSI Devices", "&Cancel", true);
        ScanbusParserStderrThread   parserStderrThread = null;
        ScanbusParserStdinThread    parserStdinThread = null;
        ProcessRunner               runner = null;

        JRipper.get().getWin().getStatusBar().setMessage("Probing after devices");

        try {
            Progress.get().clear();
            Log.get().clear();
            JRipper.get().getWin().setAlbum(null);

            parserStderrThread = new ScanbusParserStderrThread(Log.get(), Progress.get());
            parserStdinThread = new ScanbusParserStdinThread(Log.get(), Progress.get());
            runner = new ProcessRunnerReadStdoutAndStderrProc(Log.get(), null, com.googlepages.dronten.jripper.cdda2wav.Command.getScanbus(), parserStderrThread, parserStdinThread);

            dlg.centerOnApplication();
            dlg.show(runner);
        }
        catch (Exception e) {
            Log.get().addTime(1, e.getMessage());
        }
        finally {
            dlg.cancelTask();

            if (runner != null) {
                if (runner.isAlive()) {
                    JRipper.get().getThreadCollector().add(runner);
                }
                else if (runner.hasFailed()) {
                    JRipper.get().getWin().getStatusBar().setMessage("Something failed when probing for SCSI devices!");
                }
                else {
                    if (parserStderrThread.getDevices().size() > 1) {
                        aDevices = parserStderrThread.getDevices();
                    }
                    else {
                        aDevices = parserStdinThread.getDevices();
                    }
                }
            }
        }
    }
}