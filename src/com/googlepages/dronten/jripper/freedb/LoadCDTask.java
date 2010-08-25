/*
* Copyright 2004-2010 by dronten@gmail.com
*
* This source is distributed under the terms of the GNU PUBLIC LICENSE version 3
* http://www.gnu.org/licenses/gpl.html
*/

package com.googlepages.dronten.jripper.freedb;

import com.googlepages.dronten.jripper.JRipper;
import com.googlepages.dronten.jripper.cdda2wav.ContentsParserThread;
import com.googlepages.dronten.jripper.gui.dialog.ProgressDialog;
import com.googlepages.dronten.jripper.util.*;

import java.util.Vector;


/**
 * Load track names from cddb server.
 */
public class LoadCDTask {
    /**
     *
     */
    public LoadCDTask() {
    }


    /**
     * Do the query.
     */
    public void doTask() {
        ProgressDialog          dlg = new ProgressDialog(JRipper.get(), "Retrieving Track Names", "&Cancel", true);
        ContentsParserThread    parserThread = null;
        QueryParserThread       cddbThreadQuery = null;
        ProcessRunner           loadCdProc = null;
        boolean                 failed = false;
        Vector<BaseThread>      threads = new Vector<BaseThread>();

        try {
            Progress.get().clear();
            Log.get().clear();
            JRipper.get().getWin().setAlbum(null);

            parserThread            = new ContentsParserThread(Log.get(), Progress.get());
            parserThread.aAlbum.aCD = true;
            loadCdProc              = new ProcessRunnerReadProc(Log.get(), null, com.googlepages.dronten.jripper.cdda2wav.Command.getTocDecoder(), parserThread);
            cddbThreadQuery         = new QueryParserThread(parserThread.aAlbum, Progress.get());
            
            threads.add(loadCdProc);
            threads.add(cddbThreadQuery);

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

            if (loadCdProc != null && cddbThreadQuery != null) {
                if (loadCdProc.isAlive() && cddbThreadQuery.isAlive()) {
                    JRipper.get().getThreadCollector().add(loadCdProc);
                    JRipper.get().getThreadCollector().add(cddbThreadQuery);
                }
                else if (cddbThreadQuery.isAlive()) {
                    JRipper.get().getThreadCollector().add(cddbThreadQuery);
                }
                else {
                    if (loadCdProc.hasFailed()) {
                        failed = true;
                        message = "Reading CD contents failed! Check the log for more information";
                        Log.get().addTime(1, message);
                    }
                    else if (cddbThreadQuery.hasFailed()) {
                        if (HTTPQuery.CDDB_MATCH == 0) {
                            failed = true;
                            message = "CDDB data was not found or the server was busy";
                            Log.get().addTime(1, message);
                        }
                        else if (HTTPQuery.CDDB_MATCH == 1) {
                            failed = true;
                            message = "CD was found but contents could not be retrieved";
                            Log.get().addTime(1, message);
                        }
                    }
                    else {
                        JRipper.get().getWin().setAlbum(cddbThreadQuery.aAlbum);
                    }
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