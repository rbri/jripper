/*
* Copyright 2004-2010 by dronten@gmail.com
*
* This source is distributed under the terms of the GNU PUBLIC LICENSE version 3
* http://www.gnu.org/licenses/gpl.html
*/

package com.googlepages.dronten.jripper;

import com.googlepages.dronten.jripper.music.Album;
import com.googlepages.dronten.jripper.music.MP3Genre;
import com.googlepages.dronten.jripper.music.Track;
import com.googlepages.dronten.jripper.util.Pref;

import javax.swing.*;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;


/**
 * Load audio files from a local directory.
 */
public class LoadDirectoryTask {
    /**
     *
     */
    public LoadDirectoryTask() {
    }


    /**
     * Fire up the dir selection dialog and get all those wav/flac files.
     */
    public void doTask() {
        JFileChooser chooser = new JFileChooser();
        File currDir = new File(Pref.getPref(Constants.BASE_DIRECTORY_KEY, ""));

        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        chooser.setDialogTitle("Select Directory");
        if (currDir.isDirectory()) {
            chooser.setCurrentDirectory(currDir);
        }
        if (chooser.showOpenDialog(JRipper.get()) == JFileChooser.APPROVE_OPTION) {
            File dir = new File(chooser.getSelectedFile().getPath());
            File[] files = dir.listFiles();
            ArrayList<File> wavFiles = new ArrayList<File>();

            if (files != null) {
                for (File f : files) {
                    if (f.canRead()) {
                        if (f.isFile()) {
                            if (f.getName().toLowerCase().endsWith(".wav") ||
                                f.getName().toLowerCase().endsWith(".mp3") ||
                                f.getName().toLowerCase().endsWith(".ogg") ||
                                f.getName().toLowerCase().endsWith(".m4a") ||
                                f.getName().toLowerCase().endsWith(".flac")) {
                                wavFiles.add(new File(f.getPath()));
                            }
                        }
                    }
                }
            }

            if (wavFiles.size() > 0) {
                File albumFile = wavFiles.get(0).getParentFile();
                File artistFile = ((albumFile != null) ? albumFile.getParentFile() : albumFile);
                Album album = new Album(artistFile != null ? artistFile.getName() : "", albumFile != null ? albumFile.getName() : "", MP3Genre.DEFAULT_STRING, "", "");

                for (File f : wavFiles) {
                    album.addTrack(f.getName(), f);
                }

                Collections.sort(album.aTracks, new FileNameComparator());

                int count = 1;
                for (Track track : album.aTracks) {
                    track.aTrack = count;
                    count++;
                }

                JRipper.get().getWin().setAlbum(album);
            }
            else {
                Album album = new Album("", "", "", "", "");
                JRipper.get().getWin().setAlbum(album);
                JRipper.get().getWin().getStatusBar().setErrorMessage("No tracks have been found!");
            }
        }
    }


    /**
     * Compare track file names.
     */
    class FileNameComparator implements Comparator<Track> {
        /**
         * Compare two file names.
         *
         * @param track1
         * @param track2
         * @return
         */
        public int compare(Track track1, Track track2) {
            return track1.aFile.compareTo(track2.aFile);
        }
    }
}