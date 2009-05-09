/*
* Copyright 2004-2009 by dronten@gmail.com
*
* This source is distributed under the terms of the GNU PUBLIC LICENSE version 3
* http://www.gnu.org/licenses/gpl.html
*/

package com.googlepages.dronten.jripper.gui;

import com.googlepages.dronten.jripper.*;
import com.googlepages.dronten.jripper.cdda2wav.LoadCDPTask;
import com.googlepages.dronten.jripper.freedb.LoadCDTask;
import com.googlepages.dronten.jripper.gui.dialog.HelpDialog;
import com.googlepages.dronten.jripper.gui.dialog.setup.*;
import com.googlepages.dronten.jripper.music.Album;
import com.googlepages.dronten.jripper.music.Track;
import com.googlepages.dronten.jripper.util.Log;
import com.googlepages.dronten.jripper.util.Pref;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


/**
 * The button menu object.
 * And a combobox for selecting encoding type.
 */
public class MenuPanel extends JPanel implements ActionListener {
    private static final long   serialVersionUID = 666L;
    private static final int    COMPONENT_WIDTH = 175;
    private JButton             aViewLog;
    private JButton             aHelp;
    private JButton             aQuit;
    private JButton             aLoadCD;
    private JButton             aLoadCDDB;
    private JButton             aLoadCDDB2;
    private JButton             aLoadDir;
    private JButton             aConvertCD;
    private JButton             aConvertCD2;
    private JButton             aSetup;
    private JButton             aSelectAll = null;
    private JButton             aSelectNone = null;
    private JButton             aReset;
    private JComboBox           aRipperChoiceCombo;


    /**
     * Create menu panel.
     */
    public MenuPanel() {
        super();

        JPanel panel = new JPanel();

        setLayout(new BorderLayout());
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        aRipperChoiceCombo = ComponentFactory.createCombo(Constants.ENCODER_NAME_DEFAULTS, Pref.getPref(Constants.ENCODER_KEY, Constants.ENCODER_DEFAULT), "Select encoder type.", COMPONENT_WIDTH, COMPONENT_WIDTH);
        panel.add(aRipperChoiceCombo);
        panel.add(Box.createRigidArea(new Dimension(0, 3)));

        aConvertCD = ComponentFactory.createButton("&Encode Tracks", "<html>Start encoding of the selected tracks.</html>", this, COMPONENT_WIDTH, 0);
        panel.add(aConvertCD);
        panel.add(Box.createRigidArea(new Dimension(0, 6)));

        aConvertCD2 = ComponentFactory.createButton("Encode Complete Album", "<html>Encode all tracks from the CD.<br>The trackname will be the name of the album.<br><b>The track will end up in the artist directory!<br>Not in the directory choice set in the preferences</b></html>", this, COMPONENT_WIDTH, 0);
        panel.add(aConvertCD2);
        panel.add(Box.createRigidArea(new Dimension(0, 6)));

        panel.add(new JSeparator());
        panel.add(Box.createRigidArea(new Dimension(0, 6)));

        aLoadCD = ComponentFactory.createButton("Load &CD", "<html>Load tracks from CD.<br>If CD-Text is available on the CD it could be read by cdda2wav if the CD is an SCSI device.</html>", this, COMPONENT_WIDTH, 0);
        panel.add(aLoadCD);
        panel.add(Box.createRigidArea(new Dimension(0, 3)));

        aLoadCDDB = ComponentFactory.createButton("Load CD (http)", "<html>Load track names from freeDB server.<br>Using the http protocol.</html>", this, COMPONENT_WIDTH, 0);
        panel.add(aLoadCDDB);
        panel.add(Box.createRigidArea(new Dimension(0, 3)));

        aLoadCDDB2 = ComponentFactory.createButton("Load CD (cddbp)", "<html>Load track names from freeDB server.<br>Using the cddbp protocol.</html>", this, COMPONENT_WIDTH, 0);
        panel.add(aLoadCDDB2);
        panel.add(Box.createRigidArea(new Dimension(0, 3)));

        aLoadDir = ComponentFactory.createButton("Load &Directory", "Load tracks from a local directory.", this, COMPONENT_WIDTH, 0);
        panel.add(aLoadDir);
        panel.add(Box.createRigidArea(new Dimension(0, 6)));

        panel.add(new JSeparator());
        panel.add(Box.createRigidArea(new Dimension(0, 6)));

        aReset = ComponentFactory.createButton("C&lear Album", "Clear album data", this, 0, 0);
        panel.add(aReset);
        panel.add(Box.createRigidArea(new Dimension(0, 3)));

        aSelectAll = ComponentFactory.createButton("Select &All", "Select all tracks in track table", this, 0, 0);
        panel.add(aSelectAll);
        panel.add(Box.createRigidArea(new Dimension(0, 3)));

        aSelectNone = ComponentFactory.createButton("Select &None", "Select no tracks in track table", this, 0, 0);
        panel.add(aSelectNone);
        panel.add(Box.createRigidArea(new Dimension(0, 6)));

        panel.add(new JSeparator());
        panel.add(Box.createRigidArea(new Dimension(0, 6)));

        aSetup = ComponentFactory.createButton("&Setup", "Change jRipper Preference", this, COMPONENT_WIDTH, 0);
        panel.add(aSetup);
        panel.add(Box.createRigidArea(new Dimension(0, 3)));

        aViewLog = ComponentFactory.createButton("Show &Log", "Show work log", this, COMPONENT_WIDTH, 0);
        panel.add(aViewLog);
        panel.add(Box.createRigidArea(new Dimension(0, 3)));

        aHelp = ComponentFactory.createButton("&Help", "Show help screen", this, COMPONENT_WIDTH, 0);
        panel.add(aHelp);
        panel.add(Box.createRigidArea(new Dimension(0, 6)));

        panel.add(new JSeparator());
        panel.add(Box.createRigidArea(new Dimension(0, 6)));

        aQuit = ComponentFactory.createButton("&Quit", "Quit jRipper", this, COMPONENT_WIDTH, 0);
        panel.add(aQuit);
        panel.add(Box.createRigidArea(new Dimension(0, 3)));

        add(panel, BorderLayout.NORTH);
    }


    /**
     * Take care of button messages.
     *
     * @param actionEvent
     */
    public void actionPerformed(ActionEvent actionEvent) {
        if (actionEvent.getSource() == aViewLog) {
            HelpDialog help = new HelpDialog(JRipper.get(), "jRipper Log", "&Close");

            help.centerOnApplication();
            help.setVisible(true);
            help.setText(Log.get().getLogMessage());
        }
        else if (actionEvent.getSource() == aHelp) {
            String text = "<html><body>";

            text += "<h2>jRipper " + Constants.APP_VERSION + "</h2>";
            text += Constants.APP_COPYRIGHT + "<br>";
            text += Constants.APP_HOMEPAGE + "<br>";
            text += Constants.APP_LICENSE + "<br><br>";

            text += "<b>Setup</b><br>";
            text += "To use jRipper some criterions must be met.<br>";
            text += "jRipper uses <b>cdda2wav, lame, oggenc, oggdec, flac, faac and faad</b> programs.<br>";
            text += "If these programs are not in the path, you have to set them in the  <b>Program Paths</b> dialog.<br>";
            text += "You can skip to set the path for the program you don't intend to use.<br>";
            text += "Then the <b>base music directory</b> must be set in the <b>General Options</b> dialog.<br>";
            text += "And the last thing is to set the CD device in the <b>CD Reader Settings</b> dialog.<br>";
            text += "Some common devices are preset in the device list.<br>";
            text += "If you have an ide cd reader it can probably be found at <b>/dev/cdrom</b> in linux.<br>";
            text += "Typical values for SCSI drives are 1,0,0 or 2,0,0.<br>";
            text += "Windows is using ide drives in SCSI mode<br>";
            text += "Press the <b>Probe Devices</b> button to check for SCSI drives. Works only for root user.<br>";
            text += "There are more settings available for the cd reader but they are not necessary for using jRipper.<br><br>";
            text += "<b>Encoder</b><br>";
            text += "To change the encoder settings, read the manual pages for the encoder programs,<br>";
            text += "and change parameters in <b>Encoder Parameters</b> dialog.<br>";
            text += "The preset settings are a good choice for most users.<br><br>";
            text += "<b>CDDB</b><br>";
            text += "jRipper can read cd contents from a freeDB server by the http protocol.<br>";
            text += "Or by the cddbp protocol with help from cdda2wav.<br>";
            text += "It also possible that cdda2wav can read <b>CD-Text</b> information from the cd.<br><br>";
            text += "</body></html>";

            HelpDialog help = new HelpDialog(JRipper.get(), "jRipper Help", "&Close");
            help.centerOnApplication();
            help.setVisible(true);
            help.setHtml(text);
        }
        else if (actionEvent.getSource() == aSetup) {
            String[]            choices = Constants.SETUP_DIALOG_TABS;
            BaseSetupPanel[]    panels = {new General(), new FileNames(), new Tag(), new Program(), new FreeDB(), new CDReader(), new Encoder()};
            SetupDialog         setup = new SetupDialog(JRipper.get(), "jRipper Setup", "&Close", choices, panels);

            setup.centerOnApplication();
            setup.setVisible(true);
        }
        else if (actionEvent.getSource() == aLoadCD) {
            com.googlepages.dronten.jripper.cdda2wav.LoadCDTask task = new com.googlepages.dronten.jripper.cdda2wav.LoadCDTask();
            task.doTask();
        }
        else if (actionEvent.getSource() == aLoadCDDB) {
            LoadCDTask task = new LoadCDTask();
            task.doTask();
        }
        else if (actionEvent.getSource() == aLoadCDDB2) {
            LoadCDPTask task = new LoadCDPTask();
            task.doTask();
        }
        else if (actionEvent.getSource() == aLoadDir) {
            LoadDirectoryTask task = new LoadDirectoryTask();
            task.doTask();
        }
        else if (actionEvent.getSource() == aConvertCD) {
            Album album = JRipper.get().getWin().getAlbum();

            if (JRipper.get().getWin().getTrackPanel().getAlbumPanel().copyAlbum(album)) {
                RipperTask task = new RipperTask(album, aRipperChoiceCombo.getSelectedIndex());
                task.doTask();
            }
        }
        else if (actionEvent.getSource() == aConvertCD2) {
            Album album = JRipper.get().getWin().getAlbum();

            if (JRipper.get().getWin().getTrackPanel().getAlbumPanel().copyAlbum(album)) {
                WholeCDRipperTask task = new WholeCDRipperTask(album, aRipperChoiceCombo.getSelectedIndex());
                task.doTask();
            }
        }
        else if (actionEvent.getSource() == aReset) {
            JRipper.get().getWin().getTrackPanel().getAlbumPanel().reset();
        }
        else if (actionEvent.getSource() == aSelectAll) {
            Album album = JRipper.get().getWin().getAlbum();

            if (album == null) {
                return;
            }

            for (Track track : album.aTracks) {
                track.aSelected = true;
            }

            JRipper.get().getWin().getTrackPanel().fire();
        }
        else if (actionEvent.getSource() == aSelectNone) {
            Album album = JRipper.get().getWin().getAlbum();

            if (album == null) {
                return;
            }

            for (Track track : album.aTracks) {
                track.aSelected = false;
            }

            JRipper.get().getWin().getTrackPanel().fire();
        }
        else if (actionEvent.getSource() == aQuit) {
            JRipper.get().saveWindow();
        }

        enableUI();
    }


    /**
     * Enable or disable ui stuff in the mneu panel depending on status.
     */
    public void enableUI() {
        Album album = JRipper.get().getWin().getAlbum();

        if (album != null && album.aTracks.size() > 0) {
            aConvertCD.setEnabled(true);
            aConvertCD2.setEnabled(album.aCD);
            aSelectAll.setEnabled(true);
            aSelectNone.setEnabled(true);
        }
        else {
            aConvertCD.setEnabled(false);
            aConvertCD2.setEnabled(false);
            aSelectAll.setEnabled(false);
            aSelectNone.setEnabled(false);
        }
    }
}
