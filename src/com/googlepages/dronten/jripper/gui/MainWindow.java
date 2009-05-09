/*
* Copyright 2004-2009 by dronten@gmail.com
*
* This source is distributed under the terms of the GNU PUBLIC LICENSE version 3
* http://www.gnu.org/licenses/gpl.html
*/

package com.googlepages.dronten.jripper.gui;

import com.googlepages.dronten.jripper.music.Album;

import javax.swing.*;
import java.awt.*;


/**
 * JRipper window object.<br>
 * Create main gui stuff here.
 */
public class MainWindow extends JPanel {
    private static final long   serialVersionUID = 666L;
    private MenuPanel           aMenuPanel = null;
    private TrackPanel          aTrackPanel = null;
    private StatusBar           aStatusBar = null;


    /**
     * Create main application window.
     */
    public MainWindow() {
        super();
        aMenuPanel = new MenuPanel();
        aTrackPanel = new TrackPanel();
        aStatusBar = new StatusBar();

        setLayout(new BorderLayout());

        add(aTrackPanel, BorderLayout.CENTER);
        add(aMenuPanel, BorderLayout.WEST);
        add(aStatusBar, BorderLayout.SOUTH);

        aStatusBar.setMessage("Load a CD or tracks from a directory, then convert selected tracks to mp3/ogg/m4a/flac/wav files");
    }


    /**
     * Get current album.
     *
     * @return Get album object
     */
    public Album getAlbum() {
        return aTrackPanel.getAlbum();
    }

    /**
     *
     * @return
     */
    public MenuPanel getMenuPanel() {
        return aMenuPanel;
    }


    /**
     * Get jRippers statusbar.
     *
     * @return The application statusbar
     */
    public StatusBar getStatusBar() {
        return aStatusBar;
    }


    /**
     * Get jRippers track table panel.
     *
     * @return The track table panel
     */
    public TrackPanel getTrackPanel() {
        return aTrackPanel;
    }


    /**
     * Set new album to tracktable.
     *
     * @param album
     */
    public void setAlbum(Album album) {
        aTrackPanel.setAlbum(album);
    }
}
