/*
* Copyright 2004-2009 by dronten@gmail.com
*
* This source is distributed under the terms of the GNU PUBLIC LICENSE version 3
* http://www.gnu.org/licenses/gpl.html
*/

package com.googlepages.dronten.jripper.gui;

import com.googlepages.dronten.jripper.Constants;
import com.googlepages.dronten.jripper.music.Album;
import com.googlepages.dronten.jripper.music.Track;

import javax.swing.table.AbstractTableModel;


/**
 * AlbumModel is a table model that contains all tracks that will be ripped or encoded/decoded.
 */
public class AlbumModel extends AbstractTableModel {
	private static final long serialVersionUID = 7898511494400030799L;
	private Album aAlbum = null;


    /**
     *
     */
    public AlbumModel() {
    }


    /**
     * Return current album.
     *
     * @return The current used Album object
     */
    public Album getAlbum() {
        return aAlbum;
    }


    /**
     * Get table column name.
     *
     * @param col - Column number
     * @return Name of column
     */
    @Override public String getColumnName(int col) {
        if (col == 0) {
            return "Convert";
        }
        else if (col == 1) {
            return "Track";
        }
        else if (col == 2) {
            return "Title";
        }
        else if (col == 3) {
            return "Type";
        }
        else {
            return "Time";
        }
    }


    /**
     * Return number of rows.
     *
     * @return Number of tracks
     */
    public int getRowCount() {
        if (aAlbum != null) {
            return aAlbum.aTracks.size();
        }
        else {
            return 0;
        }
    }


    /**
     * Return what type of object table is going to edit.
     *
     * @param column Column number
     * @return Class type
     */
    @Override public Class<?> getColumnClass(int column) {
        return getValueAt(0, column).getClass();
    }


    /**
     * 5 they are.
     *
     * @return Columns
     */
    public int getColumnCount() {
        return 5;
    }


    /**
     * @param row
     * @param col
     * @return Object to paint
     */
    public Object getValueAt(int row, int col) {
        Track track;

        try {
            track = aAlbum.aTracks.get(row);
        }
        catch (Exception e) {
            return null;
        }

        if (col == 0) {
            return track.aSelected;
        }
        else if (col == 1) {
            return track.aTrack;
        }
        else if (col == 2) {
            return track.aName;
        }
        else if (col == 3) {
            switch (track.getDecoder()) {
                case Constants.MP3_TRACK:
                    return "MP3";

                case Constants.OGG_TRACK:
                    return "Ogg";

                case Constants.FLAC_TRACK:
                    return "Flac";

                case Constants.M4A_TRACK:
                    return "M4A";

                case Constants.CD_TRACK:
                    return "CD";

                case Constants.WAV_TRACK:
                    return "Wav";

                default:
                    assert false;
                    return "";
            }
        }
        else {
            return track.aTime;
        }
    }


    /**
     * Only filename and select box can be edited.
     *
     * @param row   Row index
     * @param col   Column index
     * @return      true if cell can be edited
     */
    @Override public boolean isCellEditable(int row, int col) {
        switch (col) {
            case 0:
            case 2:
                return true;

            default:
                return false;
        }

    }


    /**
     * Set new album to display.
     *
     * @param album Album object
     */
    public void setAlbum(Album album) {
        aAlbum = album;
        fireTableDataChanged();
    }


    /**
     * Select/unselect track or edit track name.
     *
     * @param value Edited value
     * @param row   Row index
     * @param col   Column index
     */
    @Override public void setValueAt(Object value, int row, int col) {
        assert value != null;
        Track track;

        try {
            track = aAlbum.aTracks.get(row);
        }
        catch (Exception e) {
            return;
        }

        if (col == 0) {
            track.aSelected = (Boolean) value;
        }
        else if (col == 2) {
            track.aName = (String) value;
        }
        fireTableDataChanged();
    }
}
