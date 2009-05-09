/*
* Copyright 2004-2009 by dronten@gmail.com
*
* This source is distributed under the terms of the GNU PUBLIC LICENSE version 3
* http://www.gnu.org/licenses/gpl.html
*/

package com.googlepages.dronten.jripper.cdda2wav;

import com.googlepages.dronten.jripper.Constants;
import com.googlepages.dronten.jripper.util.Pref;
import com.googlepages.dronten.jripper.util.ProcessParam;


/**
 * Commands for cdda2wav.
 */
public class Command {
    /**
     * Get params for reading wav data and save it to file or write to stdout.
     *
     * @param track       Track object
     * @param outFileName Name of destination file
     * @param wholecd     true if whole cd is read as one track
     * @return Command line parameters for reading cd
     */
    public static ProcessParam getDecoder(int track, String outFileName, boolean wholecd) {
        ProcessParam param = new ProcessParam();

        param.add(Pref.getPref(Constants.CD_READER_KEY, Constants.CD_READER_DEFAULT));
        param.add("-D");
        param.add(Pref.getPref(Constants.CD_DEVICE_KEY, Constants.CD_DEVICE_DEFAULT));

        if (Pref.getPref(Constants.CD_SPEED_KEY, 0) != 0) {
            param.add("-S", String.format("%d", Pref.getPref(Constants.CD_SPEED_KEY, 0)));
        }

        if (Pref.getPref(Constants.PARANOIA_KEY, Constants.PARANOIA_DEFAULT)) {
            param.add("-paranoia");
        }

        if (Pref.getPref(Constants.CD_MONO_KEY, Constants.CD_MONO_DEFAULT)) {
            param.add("-m");
        }

        param.add("-g");
        param.add("-H");

        if (wholecd) {
            param.add("-d");
            param.add("99999");
        }
        else {
            param.add("-t");
            param.add(track);
        }

        if (outFileName != null) {
            param.add(outFileName);
        }
        else {
            param.add("-");
        }

        return param;
    }


    /**
     * Get params for loading toc from cddb server.
     *
     * @return Command line parameters for reading cd tracks
     */
    public static ProcessParam getFreeDBDecoder() {
        ProcessParam param = new ProcessParam();

        param.add(Pref.getPref(Constants.CD_READER_KEY, Constants.CD_READER_DEFAULT));
        param.add("-D");
        param.add(Pref.getPref(Constants.CD_DEVICE_KEY, Constants.CD_DEVICE_DEFAULT));
        param.add("-g");
        param.add("-H");
        param.add("-J");
        param.add("-v");
        param.add("toc,title,sectors");
        param.add("-L");
        param.add("1");
        param.add("-cddbp-server=" + Pref.getPref(Constants.FREEDBP_SERVER_KEY, Constants.FREEDBP_SERVER_DEFAULT));
        param.add("-cddbp-port=" + Pref.getPref(Constants.FREEDBP_PORT_KEY, Constants.FREEDBP_PORT_DEFAULT));

        return param;
    }


    /**
     * Scan for scsi devices.
     *
     * @return Command line parameters for executin scanbus command for cdda2wav
     */
    public static ProcessParam getScanbus() {
        ProcessParam param = new ProcessParam();

        param.add(Pref.getPref(Constants.CD_READER_KEY, Constants.CD_READER_DEFAULT));
        param.add("-scanbus");

        return param;
    }

    
    /**
     * Get params for loading CD contents.
     *
     * @return Command line parameters for reading table of contents from an cd
     */
    public static ProcessParam getTocDecoder() {
        ProcessParam param = new ProcessParam();

        param.add(Pref.getPref(Constants.CD_READER_KEY, Constants.CD_READER_DEFAULT));
        param.add("-D");
        param.add(Pref.getPref(Constants.CD_DEVICE_KEY, Constants.CD_DEVICE_DEFAULT));
        param.add("-g");
        param.add("-H");
        param.add("-J");
        param.add("-v");
        param.add("toc,title,sectors");

        return param;
    }
}