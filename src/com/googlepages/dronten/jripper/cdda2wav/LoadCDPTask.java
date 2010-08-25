/*
* Copyright 2004-2010 by dronten@gmail.com
*
* This source is distributed under the terms of the GNU PUBLIC LICENSE version 3
* http://www.gnu.org/licenses/gpl.html
*/

package com.googlepages.dronten.jripper.cdda2wav;

import com.googlepages.dronten.jripper.JRipper;
import com.googlepages.dronten.jripper.gui.dialog.ProgressDialog;
import com.googlepages.dronten.jripper.util.*;

import java.util.Vector;


/**
 * Load track names from freedb server.
 */
public class LoadCDPTask {
    /**
     *
     */
    public LoadCDPTask() {
    }


    /**
     * Encode selected tracks.
     */
    public void doTask() {
        ProgressDialog          dlg = new ProgressDialog(JRipper.get(), "Retrieving Track Names", "&Cancel", true);
        ContentsParserThread    parserThread = null;
        ProcessRunner cddbDecoder = null;
        boolean                 failed = false;
        Vector<BaseThread>      threads = new Vector<BaseThread>();
        
        JRipper.get().getWin().getStatusBar().setMessage("Contacting freedbp server...");

        try {
            Progress.get().clear();
            Log.get().clear();
            JRipper.get().getWin().setAlbum(null);

            parserThread = new ContentsParserThread(Log.get(), Progress.get());
            parserThread.aAlbum.aCD = true;
            cddbDecoder = new ProcessRunnerReadProc(Log.get(), null, com.googlepages.dronten.jripper.cdda2wav.Command.getFreeDBDecoder(), parserThread);
            threads.add(cddbDecoder);

            dlg.centerOnApplication();
            dlg.show(threads);
        }
        catch (Exception e) {
            Log.get().addTime(1, e.getMessage());
            failed = true;
        }
        finally {
            String message = "CD data downloaded from freeDB server";
            dlg.cancelTask();

            if (cddbDecoder != null) {
                if (cddbDecoder.isAlive()) {
                    JRipper.get().getThreadCollector().add(cddbDecoder);
                }
                else if (cddbDecoder.hasFailed() || parserThread.hasFailed()) {
                    failed = true;
                    message = "Reading CD contents failed! Check the log for more information";
                    Log.get().addTime(1, message);
                }
                else if (parserThread.aAlbum.aAlbum.length() == 0 && parserThread.aAlbum.aArtist.length() == 0) {
                    failed = true;
                    message = "Loading freeDB data failed! Check the log for more information";
                    Log.get().addTime(1, message);
                }
                else {
                    JRipper.get().getWin().setAlbum(parserThread.aAlbum);
                }
            }

            if (dlg.hasBeenStopped()) {
                JRipper.get().getWin().getStatusBar().setNotifyMessage("Retrieving track names has been stopped!");
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