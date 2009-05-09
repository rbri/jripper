/*
* Copyright 2004-2009 by dronten@gmail.com
*
* This source is distributed under the terms of the GNU PUBLIC LICENSE version 3
* http://www.gnu.org/licenses/gpl.html
*/

package com.googlepages.dronten.jripper;

import com.googlepages.dronten.jripper.gui.dialog.HelpDialog;
import com.googlepages.dronten.jripper.gui.dialog.ProgressDialog;
import com.googlepages.dronten.jripper.music.Album;
import com.googlepages.dronten.jripper.music.Track;
import com.googlepages.dronten.jripper.util.*;

import java.awt.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.Vector;


/**
 * Convert a CD into one audio track, wav file or encoded file.<br>
 */
public class WholeCDRipperTask {
    private Album   aAlbum;
    private int     aEncoderIndex = 0;


    /**
     * @param album
     * @param encoderIndex
     */
    public WholeCDRipperTask(Album album, int encoderIndex) {
        aEncoderIndex = encoderIndex;
        aAlbum = album;
    }


    /**
     * Encode/decode selected tracks.
     */
    public void doTask() {
        StopWatch           watch = new StopWatch();
        Vector<BaseThread>  threads = new Vector<BaseThread>();
        ProgressDialog      dlg = new ProgressDialog(JRipper.get(), "Encoding Tracks", "&Cancel", true);
        boolean             failed = false;
        boolean             first = true;
        String              message = "";
        String              fileName = "";

        dlg.centerOnApplication();
        JRipper.get().getWin().getStatusBar().setMessage("Encoding Complete Album Into One Track");

        Progress.get().clear();
        Progress.get().aIncreaseMode = true;
        Log.get().clear();
        watch.start();

        try {
            for (Track track : aAlbum.aTracks) {
                if (first) {
                    track.setEncoderIndex(aEncoderIndex);
                    fileName = track.createFileName(aAlbum, true);

                    switch (track.getDecoder()) {
                        case Constants.CD_TRACK:
                            Command.createEncoderForCD(aAlbum, track, threads, fileName, true);
                            break;

                        default:
                            throw new Exception("Select another encoding type for selected source.\nYou can't convert from one format to the SAME format!");
                    }
                    first = false;
                }
            }

            if (threads.size() < 1) {
                throw new Exception("Select one album to encode");
            }

            dlg.show(threads); // Run all threads one by one in the progress dialog

            if (dlg.hasBeenStopped()) {
                throw new Exception("Encoding/decoding work has been stopped!");

            }
            else if (dlg.hasFailed()) {
                throw new Exception("Encoding failed! Check the log for more information");

            }
            else if (Pref.getPref(Constants.CUE_SHEET_KEY, Constants.CUE_SHEET_DEFAULT)) {
                File file = new File(fileName);
                String fname = file.getName();
                String fpath = file.getParent();
                String shortName = fname;
                String extension = "";

                int dot = fname.lastIndexOf('.');
                if (dot > 0 && dot < fname.length() - 1) {
                    extension = fname.substring(dot + 1).toUpperCase();
                    shortName = fname.substring(0, dot);
                }

                /*
                * Create cue file that other application can use when using the track
                */
                aAlbum.calcRunningTime();
                BufferedWriter out = new BufferedWriter(new FileWriter(fpath + File.separator + shortName + ".cue"));
                out.write(String.format("TITLE \"%s\"", aAlbum.aAlbum));
                out.newLine();
                out.write(String.format("PERFORMER \"%s\"", aAlbum.aArtist));
                out.newLine();
                out.write(String.format("FILE \"%s\" %s", fname, extension));
                out.newLine();
                for (Track track : aAlbum.aTracks) {
                    out.write(String.format("  TRACK %02d AUDIO", track.aTrack));
                    out.newLine();
                    out.write(String.format("    TITLE \"%s\"", track.aName));
                    out.newLine();
                    out.write(String.format("    PERFORMER \"%s\"", aAlbum.aArtist));
                    out.newLine();
                    out.write(String.format("    INDEX 01 %s", track.timeToString()));
                    out.newLine();
                }
                out.close();
            }
        }
        catch (Exception e) {
            Log.get().addTime(1, e.getMessage());
            message = e.getMessage();
            failed = true;
        }
        finally {
            dlg.cancelTask();
            JRipper.get().getThreadCollector().add(threads);
            watch.stop();

            if (dlg.hasBeenStopped()) {
                JRipper.get().getWin().getStatusBar().setNotifyMessage(message);
            }
            else if (failed) {
                HelpDialog showLog = new HelpDialog(JRipper.get(), "jRipper Log", "&Close");
                showLog.setText(Log.get().getLogMessage());
                showLog.centerOnApplication();
                showLog.setVisible(true);
                JRipper.get().getWin().getStatusBar().setErrorMessage(message);
            }
            else {
                JRipper.get().getWin().getStatusBar().setNotifyMessage("The encoding took", watch.toString());
                Toolkit.getDefaultToolkit().beep();
            }
        }
    }
}
