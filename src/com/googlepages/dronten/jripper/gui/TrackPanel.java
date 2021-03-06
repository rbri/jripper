/*
* Copyright 2004-2010 by dronten@gmail.com
*
* This source is distributed under the terms of the GNU PUBLIC LICENSE version 3
* http://www.gnu.org/licenses/gpl.html
*/

package com.googlepages.dronten.jripper.gui;

import com.googlepages.dronten.jripper.music.Album;

import javax.swing.*;
import java.awt.*;


/**
 * The tack table panel with all tracks listed.
 * Can be tracks on a cd or from a directory.
 */
public class TrackPanel extends JPanel {
    private static final long   serialVersionUID = 666L;
    private AlbumPanel          aAlbumPanel = null;
    private JTable              aTrackTable = null;


    public TrackPanel() {
        super();
        aTrackTable = new JTable(new AlbumModel());
        JScrollPane scrollPane = new JScrollPane();
        aAlbumPanel = new AlbumPanel();

        aTrackTable.getColumnModel().getColumn(0).setMinWidth(50);
        aTrackTable.getColumnModel().getColumn(0).setPreferredWidth(100);
        aTrackTable.getColumnModel().getColumn(0).setMaxWidth(100);

        aTrackTable.getColumnModel().getColumn(1).setMinWidth(50);
        aTrackTable.getColumnModel().getColumn(1).setPreferredWidth(50);
        aTrackTable.getColumnModel().getColumn(1).setMaxWidth(100);

        aTrackTable.getColumnModel().getColumn(2).setMinWidth(100);
        aTrackTable.getColumnModel().getColumn(2).setPreferredWidth(500);
        aTrackTable.getColumnModel().getColumn(2).setMaxWidth(1000);

        aTrackTable.getColumnModel().getColumn(3).setMinWidth(50);
        aTrackTable.getColumnModel().getColumn(3).setPreferredWidth(100);
        aTrackTable.getColumnModel().getColumn(3).setMaxWidth(100);

        aTrackTable.getColumnModel().getColumn(4).setMinWidth(50);
        aTrackTable.getColumnModel().getColumn(4).setPreferredWidth(100);
        aTrackTable.getColumnModel().getColumn(4).setMaxWidth(100);

        aTrackTable.setShowGrid(false);

        setLayout(ComponentFactory.createBorderLayout(5, 5));
        setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        scrollPane.getViewport().setView(aTrackTable);

        add(scrollPane, BorderLayout.CENTER);
        add(aAlbumPanel, BorderLayout.SOUTH);
    }


    /**
     * Tell table that changes has been done to the album.
     */
    public void fire() {
        ((AlbumModel) aTrackTable.getModel()).fireTableDataChanged();
    }


    /**
     * Return album object.
     *
     * @return The current Album
     */
    public Album getAlbum() {
        return ((AlbumModel) aTrackTable.getModel()).getAlbum();
    }


    /**
     * Return album panel object.
     *
     * @return The AlbumPanel
     */
    public AlbumPanel getAlbumPanel() {
        return aAlbumPanel;
    }


    /**
     * Set new album.
     *
     * @param album
     */
    public void setAlbum(Album album) {
        ((AlbumModel) aTrackTable.getModel()).setAlbum(album);
        aAlbumPanel.setAlbum(album);
    }
}