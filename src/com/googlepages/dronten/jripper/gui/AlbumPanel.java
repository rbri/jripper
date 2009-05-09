/*
* Copyright 2004-2009 by dronten@gmail.com
*
* This source is distributed under the terms of the GNU PUBLIC LICENSE version 3
* http://www.gnu.org/licenses/gpl.html
*/

package com.googlepages.dronten.jripper.gui;

import com.googlepages.dronten.jripper.Constants;
import com.googlepages.dronten.jripper.JRipper;
import com.googlepages.dronten.jripper.music.Album;
import com.googlepages.dronten.jripper.music.MP3Genre;
import com.googlepages.dronten.jripper.util.Pref;

import javax.swing.*;
import java.awt.*;


/**
 * Show album data.<br>
 * It's a panel that contains a text fields for artist, album, year, comment, genre and time.<br>
 * These data will be the meta info that will be used for encoding the tracks.<br>
 */
public class AlbumPanel extends JPanel {
    private static final long   serialVersionUID = 666L;
    private JTextField          aArtistText = null;
    private JTextField          aAlbumText = null;
    private JTextField          aYearText = null;
    private JTextField          aCommentText = null;
    private JComboBox           aGenreChoice = null;
    private JLabel              aTimeLabel = null;


    /**
     * Create album panel.
     */
    public AlbumPanel() {
        super();

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        // Row 1
        JPanel tmp = new JPanel();
        tmp.setLayout(new BoxLayout(tmp, BoxLayout.X_AXIS));

        aArtistText = new JTextField("");
        aYearText   = new JTextField("");
        aTimeLabel  = new JLabel("00:00.00");

        aYearText.setPreferredSize(new Dimension(100, (int) aYearText.getSize().getHeight()));
        aYearText.setMaximumSize(new Dimension(200, 200));

        tmp.add(new JLabel("Artist"));
        tmp.add(Box.createRigidArea(new Dimension(3, 0)));
        tmp.add(aArtistText);
        tmp.add(Box.createRigidArea(new Dimension(10, 0)));
        tmp.add(new JLabel("Year"));
        tmp.add(Box.createRigidArea(new Dimension(3, 0)));
        tmp.add(aYearText);
        tmp.add(Box.createRigidArea(new Dimension(10, 0)));
        tmp.add(aTimeLabel);
        add(tmp);
        add(Box.createRigidArea(new Dimension(0, 3)));

        // Row 2
        tmp = new JPanel();
        tmp.setLayout(new BoxLayout(tmp, BoxLayout.X_AXIS));
        aAlbumText = new JTextField("");
        aGenreChoice = new JComboBox(MP3Genre.get().getGenres());
        aGenreChoice.setSelectedIndex(MP3Genre.get().getGenres().indexOf(MP3Genre.DEFAULT_STRING));
        aGenreChoice.setPreferredSize(new Dimension(150, (int) aGenreChoice.getSize().getHeight()));
        aGenreChoice.setMaximumSize(new Dimension(400, 200));

        tmp.add(new JLabel("Album"));
        tmp.add(Box.createRigidArea(new Dimension(3, 0)));
        tmp.add(aAlbumText);
        tmp.add(Box.createRigidArea(new Dimension(10, 0)));
        tmp.add(new JLabel("Genre"));
        tmp.add(Box.createRigidArea(new Dimension(3, 0)));
        tmp.add(aGenreChoice);
        add(tmp);
        add(Box.createRigidArea(new Dimension(0, 3)));

        // Row 3
        tmp = new JPanel();
        aCommentText = new JTextField("");
        tmp.setLayout(new BoxLayout(tmp, BoxLayout.X_AXIS));
        tmp.add(new JLabel("Comment"));
        tmp.add(Box.createRigidArea(new Dimension(3, 0)));
        tmp.add(aCommentText);
        add(tmp);

        add(Box.createRigidArea(new Dimension(0, 3)));
    }


    /**
     * Copy album data.
     *
     * @param album     The album data object
     * @return          true if data was ok
     */
    public boolean copyAlbum(Album album) {
        album.aArtist = aArtistText.getText();
        album.aAlbum = aAlbumText.getText();
        album.aGenre = (String) aGenreChoice.getSelectedItem();
        album.aYear = aYearText.getText();
        album.aComment = aCommentText.getText();

        if (album.aGenre == null) {
            album.aGenre = MP3Genre.DEFAULT_STRING;
        }

        if (Pref.getPref(Constants.ALBUM_DATA_KEY, Constants.ALBUM_DATA_DEFAULT) == false) {
            if (album.aArtist.length() == 0) {
                JRipper.get().getWin().getStatusBar().setErrorMessage("Set artist name before encoding");
                aArtistText.requestFocus();
                return false;
            }

            if (album.aAlbum.length() == 0) {
                JRipper.get().getWin().getStatusBar().setErrorMessage("Set album name before encoding");
                aAlbumText.requestFocus();
                return false;
            }

            if (album.aYear.length() == 0) {
                JRipper.get().getWin().getStatusBar().setErrorMessage("Set year before encoding");
                aYearText.requestFocus();
                return false;
            }
        }

        return true;
    }


    /**
     * Reset album data.
     */
    public void reset() {
        aArtistText.setText("");
        aAlbumText.setText("");
        aYearText.setText("");
        aTimeLabel.setText("00:00.00");
        aCommentText.setText("");
        aGenreChoice.setSelectedIndex(MP3Genre.DEFAULT_SORTED_NUM);
    }


    /**
     * Set album data.
     *
     * @param album - The album data object
     */
    public void setAlbum(Album album) {
        if (album != null) {
            if (album.aArtist.length() > 0) {
                aArtistText.setText(album.aArtist);
                aAlbumText.setText(album.aAlbum);
                aYearText.setText(album.aYear);
                aCommentText.setText(album.aComment);
                aGenreChoice.setSelectedIndex(MP3Genre.get().getGenres().indexOf(MP3Genre.get().parseGenre(album.aGenre)));
            }

            aTimeLabel.setText(album.aTimeString);
        }
    }
}