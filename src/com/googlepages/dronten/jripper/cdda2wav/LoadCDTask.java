/*
* Copyright 2004-2010 by dronten@gmail.com
*
* This source is distributed under the terms of the GNU PUBLIC LICENSE version 3
* http://www.gnu.org/licenses/gpl.html
*/

package com.googlepages.dronten.jripper.cdda2wav;

import com.googlepages.dronten.jripper.JRipper;
import com.googlepages.dronten.jripper.gui.dialog.ProgressDialog;
import com.googlepages.dronten.jripper.util.Log;
import com.googlepages.dronten.jripper.util.ProcessRunner;
import com.googlepages.dronten.jripper.util.ProcessRunnerReadProc;
import com.googlepages.dronten.jripper.util.Progress;


/**
 * Load tracks from CD using cddawav.
 */
public class LoadCDTask {
    /**
     *
     */
    public LoadCDTask() {
    }


    /**
     * Load tracks from cd device.
     */
    public void doTask() {
        ProgressDialog          dlg = new ProgressDialog(JRipper.get(), "Loading CD", "&Cancel", true);
        ContentsParserThread    parserThread = null;
        ProcessRunner runner = null;
        boolean                 failed = false;

        try {
            Progress.get().clear();
            Log.get().clear();
            JRipper.get().getWin().setAlbum(null);

            parserThread = new ContentsParserThread(Log.get(), Progress.get());
            parserThread.aAlbum.aCD = true;
            runner = new ProcessRunnerReadProc(Log.get(), null, com.googlepages.dronten.jripper.cdda2wav.Command.getTocDecoder(), parserThread);

            dlg.centerOnApplication();
            dlg.show(runner);
        }
        catch (Exception e) {
            Log.get().addTime(1, e.getMessage());
            failed = true;
        }
        finally {
            String message = "Tracks have been loaded";
            dlg.cancelTask();

            if (runner != null) {
                if (runner.isAlive()) {
                    JRipper.get().getThreadCollector().add(runner);
                    failed = true;
                }
                else if (runner.hasFailed()) {
                    message = "Loading CD failed! Check the log for more information.";
                    failed = true;
                }
                else {
                    JRipper.get().getWin().setAlbum(parserThread.aAlbum);
                }
            }

            if (dlg.hasBeenStopped()) {
                JRipper.get().getWin().getStatusBar().setNotifyMessage("Loading CD has been canceled!");
            }
            else if (failed) {
                JRipper.get().getWin().getStatusBar().setErrorMessage(message);
            }
            else {
                JRipper.get().getWin().getStatusBar().setMessage(message);
            }
        }
    }
}