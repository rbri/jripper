/*
* Copyright 2004-2010 by dronten@gmail.com
*
* This source is distributed under the terms of the GNU PUBLIC LICENSE version 3
* http://www.gnu.org/licenses/gpl.html
*/

package com.googlepages.dronten.jripper;

import com.googlepages.dronten.jripper.gui.dialog.HelpDialog;
import com.googlepages.dronten.jripper.gui.dialog.ProgressDialog;
import com.googlepages.dronten.jripper.music.Album;
import com.googlepages.dronten.jripper.music.Track;
import com.googlepages.dronten.jripper.util.BaseThread;
import com.googlepages.dronten.jripper.util.Log;
import com.googlepages.dronten.jripper.util.Progress;
import com.googlepages.dronten.jripper.util.StopWatch;

import java.awt.*;
import java.util.Vector;


/**
 * The audio converter task.<br>
 * Ripp a CD or transcode audio files between various audio formats.<br>
 */
public class RipperTask {
    private Album   aAlbum;
    private int     aEncoderIndex = 0;


    /**
     * @param album
     * @param encoderIndex
     */
    public RipperTask(Album album, int encoderIndex) {
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
        String              message = "";

        dlg.showMajorProgress();
        dlg.centerOnApplication();
        JRipper.get().getWin().getStatusBar().setMessage("Encoding selected tracks");

        Progress.get().clear();
        Log.get().clear();
        watch.start();

        try {
            for (Track track : aAlbum.aTracks) {
                if (track.aSelected) {
                    /*
                    * For every selected track create encoder/decoder threads and run them later in a progress dialog
                    */
                    track.setEncoderIndex(aEncoderIndex);
                    String fileName = track.createFileName(aAlbum, false);

                    if (track.getDecoder() != Constants.CD_TRACK && track.aFile.equalsIgnoreCase(fileName)) {
                        throw new Exception("The encoded file will have the same filename as the decoded.\nThat will not do!\nChange encoder type, titlename or artist/album.\n" + track.aFile);
                    }
                    else {
                        switch (track.getDecoder()) {
                            case Constants.CD_TRACK:
                                Command.createEncoderForCD(aAlbum, track, threads, fileName, false);
                                break;

                            case Constants.MP3_TRACK:
                            case Constants.OGG_TRACK:
                            case Constants.M4A_TRACK:
                            case Constants.FLAC_TRACK:
                                Command.createEncoderForFiles(aAlbum, track, threads, fileName);
                                break;

                            case Constants.WAV_TRACK:
                                Command.createEncoderForWavFiles(aAlbum, track, threads, fileName);
                                break;

                            default:
                                throw new Exception("Select another encoding type for selected source.\nYou can't convert from one format to the SAME format!");
                        }
                    }
                }
            }

            if (threads.size() < 1) {
                throw new Exception("Select at least one track to encode");

            }

            dlg.show(threads); // Run all threads one by one in the progress dialog

            if (dlg.hasBeenStopped()) {
                throw new Exception("Encoding/decoding work has been stopped!");

            }
            else if (dlg.hasFailed()) {
                throw new Exception("Encoding failed! Check the log for more information");

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