/*
* Copyright 2004-2010 by dronten@gmail.com
*
* This source is distributed under the terms of the GNU PUBLIC LICENSE version 3
* http://www.gnu.org/licenses/gpl.html
*/

package com.googlepages.dronten.jripper.cdda2wav;

import com.googlepages.dronten.jripper.music.Album;
import com.googlepages.dronten.jripper.util.Log;
import com.googlepages.dronten.jripper.util.Progress;
import com.googlepages.dronten.jripper.util.StreamThread;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Parse the content info from the cdda2wav program and set album information.
 */
public class ContentsParserThread extends StreamThread {
    private static final Pattern    TRACK   = Pattern.compile("T\\d+:\\s+(\\d*)\\s*(\\d*:\\d\\d.\\d\\d).*title.*?'(.*)' from '");
    private static final Pattern    DATA    = Pattern.compile("T\\d+:\\s+(\\d*)\\s*(\\d*:\\d\\d.\\d\\d)");
    private static final Pattern    ALBUM   = Pattern.compile("Album title: '(.*)'.*? from '(.*)'");
    private static final Pattern    CDDB    = Pattern.compile("CDDB discid: (.*)");
    private static final Pattern    TIME    = Pattern.compile("Tracks:(\\d*)\\s*(\\d*):(\\d*).(\\d*)");
    private static final Pattern    FAILED  = Pattern.compile("cddb connect failed:");
    public Album                    aAlbum  = new Album();


    /**
     * Create parser and set work message.
     *
     * @param log
     * @param progress
     * @throws Exception
     */
    public ContentsParserThread(Log log, Progress progress) throws Exception {
        super(log, progress, ReadType.READ_STDERR_LINES, false);
        aProgress.appendTask(2000, "Reading contents of the CD...", "");
    }


    /**
     * @return true if we has failed with reading disc toc
     */
    public boolean hasFailed() {
        if (aAlbum.aDiscID.length() == 0) {
            addLog(1, "Can't read disc CDDBI number!");
            return true;
        }
        return super.hasFailed();
    }


    /**
     * Callback for parsing data from cdda2wav program.
     *
     * @param line String from cdda2wav
     */
    public void data(String line) {
        Matcher m1 = TRACK.matcher(line);
        Matcher m2 = ALBUM.matcher(line);
        Matcher m3 = CDDB.matcher(line);
        Matcher m4 = TIME.matcher(line);
        Matcher m5 = FAILED.matcher(line);
        Matcher m6 = DATA.matcher(line);

        if (m1.find()) {
            aAlbum.addTrack(m1.group(3), m1.group(2), m1.group(1));
        }
        else if (m6.find() && aAlbum.aTracks.size() > 0) {
            /*
            * Set sector that belongs to data track, it must be used for cddb querys.
            */
            aAlbum.aDataSector = m6.group(1);
        }
        else if (m2.find()) {
            aAlbum.aAlbum = m2.group(1);
            aAlbum.aArtist = m2.group(2);
        }
        else if (m3.find()) {
            aAlbum.setDiscID(m3.group(1));
        }
        else if (m4.find()) {
            int min = 0;
            int sec = 0;

            try {
                min = Integer.valueOf(m4.group(2)) * 60;
                sec = Integer.valueOf(m4.group(3));
            }
            catch (NumberFormatException e) {
            }

            aAlbum.aTracksString = m4.group(1);
            aAlbum.aTimeString = m4.group(2) + ":" + m4.group(3) + "." + m4.group(4);
            aAlbum.aSeconds = String.valueOf(min + sec);
        }
        else if (m5.find()) {
            setFailed(true);
        }

        addLog(1, line);
    }


    /**
     * @return
     */
    public String toString() {
        if (aAlbum != null) {
            return "ContentsParserThread.toString() " + aAlbum.toString();
        }
        return "ContentsParserThread.toString() ";
    }
}
