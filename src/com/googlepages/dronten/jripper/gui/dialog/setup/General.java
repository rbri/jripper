/*
* Copyright 2004-2010 by dronten@gmail.com
*
* This source is distributed under the terms of the GNU PUBLIC LICENSE version 3
* http://www.gnu.org/licenses/gpl.html
*/

package com.googlepages.dronten.jripper.gui.dialog.setup;

import com.googlepages.dronten.jripper.Constants;
import com.googlepages.dronten.jripper.JRipper;
import com.googlepages.dronten.jripper.gui.ComponentFactory;
import com.googlepages.dronten.jripper.util.Pref;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


/**
 * Set some misc. options (lookefeel, allowempty, sleep, basedir).
 */
public class General extends BaseSetupPanel implements ActionListener {
    private static final long serialVersionUID = -2359626559691964307L;
    private JCheckBox   aEmptyAlbumData = null;
    private JCheckBox   aDumpCueSheet = null;
    private JCheckBox   aUnselectTrack = null;
    private String      aCurrentLF;
    private JTextField  aBaseDirectory = null;
    private JButton     aBrowseMusicDir = null;
    private JTextField  aCodepage = null;


    /**
     *
     */
    public General() {
        aEmptyAlbumData = ComponentFactory.createCheck(Pref.getPref(Constants.ALBUM_DATA_KEY, Constants.ALBUM_DATA_DEFAULT), "Allow Encoding Without Album Data", "Check to allow encoding tracks without album tags.", 0, 0);
        aDumpCueSheet   = ComponentFactory.createCheck(Pref.getPref(Constants.CUE_SHEET_KEY, Constants.CUE_SHEET_DEFAULT), "Create cue sheet when doing an complete album conversion", "<html>Create cue sheet when converting a complete CD into single track.<br>Cue sheet will end up in artist directory with \"album name\".cue as the filename.</html>", 0, 0);
        aUnselectTrack  = ComponentFactory.createCheck(Pref.getPref(Constants.UNSELECT_KEY, Constants.UNSELECT_DEFAULT), "Unselect track after successful encoding", "<html>Unselect track in the main view after each successful encoding.<br>If encoding has been canceled or something failed<br> then the remaining unencoded tracks are checked.</html>", 0, 0);
        aBaseDirectory  = ComponentFactory.createInput(Pref.getPref(Constants.BASE_DIRECTORY_KEY, ""), "Select root folder for the encoded tracks.", 0, 0);
        aBrowseMusicDir = ComponentFactory.createButton("&>>", "Select directory...", this, 0, 0);
        aCodepage       = ComponentFactory.createInput(Pref.getPref(Constants.CODEPAGE, ""), "<html>Set character codepage for mp3 tags.<br>If characters are looking strange you might have mp3 tags in a different codepage.<br><br><b>Changing this perhaps make things better. Or maybe not.</b></html>", 0, 0);

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        add(Box.createRigidArea(new Dimension(0, 5)));
        add(ComponentFactory.createOnePanel(aEmptyAlbumData));
        add(Box.createRigidArea(new Dimension(0, 5)));
        add(ComponentFactory.createOnePanel(aDumpCueSheet));
        add(Box.createRigidArea(new Dimension(0, 5)));
        add(ComponentFactory.createOnePanel(aUnselectTrack));
        add(Box.createRigidArea(new Dimension(0, 5)));
        add(ComponentFactory.createThreePanel(new JLabel("Music Folder"), aBaseDirectory, aBrowseMusicDir));
        add(Box.createRigidArea(new Dimension(0, 5)));
        add(ComponentFactory.createTwoPanel(new JLabel("Character Codepage"), aCodepage));
    }


    /**
     * Select base music directory.
     *
     * @param actionEvent
     */
    public void actionPerformed(ActionEvent actionEvent) {
        if (actionEvent.getSource() == aBrowseMusicDir) {
            JFileChooser chooser = new JFileChooser();

            chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            chooser.setDialogTitle("Select Directory");
            if (chooser.showOpenDialog(JRipper.get()) == JFileChooser.APPROVE_OPTION) {
                aBaseDirectory.setText(chooser.getSelectedFile().getPath());
            }
        }
    }


    /**
     * Save settings.
     */
    public void save() {
        Pref.setPref(Constants.ALBUM_DATA_KEY, aEmptyAlbumData.isSelected());
        Pref.setPref(Constants.CUE_SHEET_KEY, aDumpCueSheet.isSelected());
        Pref.setPref(Constants.UNSELECT_KEY, aUnselectTrack.isSelected());
        Pref.setPref(Constants.BASE_DIRECTORY_KEY, aBaseDirectory.getText());
        JRipper.get().setCodePage(aCodepage.getText());
    }
}
