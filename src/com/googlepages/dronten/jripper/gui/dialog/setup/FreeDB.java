/*
* Copyright 2004-2010 by dronten@gmail.com
*
* This source is distributed under the terms of the GNU PUBLIC LICENSE version 3
* http://www.gnu.org/licenses/gpl.html
*/

package com.googlepages.dronten.jripper.gui.dialog.setup;

import com.googlepages.dronten.jripper.Constants;
import com.googlepages.dronten.jripper.gui.ComponentFactory;
import com.googlepages.dronten.jripper.util.Pref;

import javax.swing.*;
import java.awt.*;


/**
 * Set cddb options (server and port and default method).
 */
public class FreeDB extends BaseSetupPanel {
	private static final long serialVersionUID = -5687003939448814870L;
    //private JComboBox   aDefaultMethod = null;
    private JComboBox   aCDDBPServer = null;
    private JComboBox   aCDDBServer = null;
    private JTextField  aEmail = null;
    private JTextField  aCDDBPPort = null;
    private JTextField  aCDDBPort = null;


    /**
     *
     */
    public FreeDB() {
        String[] servers = Constants.FREEDB_OPTIONS;
        //String[] type = {"freeDB By http", "freeDB By CDDBP (cdda2av)"};

        //aDefaultMethod  = ComponentFactory.createCombo(type, Pref.getPref(Constants.FREEDB_KEY, Constants.FREEDB_DEFAULT), "Select default freedb access", 0, 0);
        aCDDBServer     = ComponentFactory.createCombo(servers, Pref.getPref(Constants.FREEDB_SERVER_KEY, Constants.FREEDB_SERVER_DEFAULT), "Select server to retrieve record information from.", 0, 0);
        aCDDBPort       = ComponentFactory.createInput(Pref.getPref(Constants.FREEDB_PORT_KEY, Constants.FREEDB_PORT_DEFAULT), "<html>Select freedb server port.<br>Default is 80.</html>", 0, 0);
        aCDDBPServer    = ComponentFactory.createCombo(servers, Pref.getPref(Constants.FREEDBP_SERVER_KEY, Constants.FREEDBP_SERVER_DEFAULT), "Select server to retrieve record information from.", 0, 0);
        aCDDBPPort      = ComponentFactory.createInput(Pref.getPref(Constants.FREEDBP_PORT_KEY, Constants.FREEDBP_PORT_DEFAULT), "<html>Select freedbp server port.<br>Default is 8880.</html>", 0, 0);
        aEmail          = ComponentFactory.createInput(Pref.getPref(Constants.FREEDB_EMAIL_KEY, Constants.FREEDB_EMAIL_DEFAULT), "<html>Set your email adress.</html>", 0, 0);

        //add(ComponentFactory.createTwoPanel(new JLabel("Set Access Method"), aDefaultMethod));
        add(Box.createRigidArea(new Dimension(0, 10)));

        add(ComponentFactory.createTwoPanel(new JLabel("FreeDB Server"), aCDDBServer));
        add(Box.createRigidArea(new Dimension(0, 5)));

        add(ComponentFactory.createTwoPanel(new JLabel("FreeDB Server Port"), aCDDBPort));
        add(Box.createRigidArea(new Dimension(0, 10)));

        add(ComponentFactory.createTwoPanel(new JLabel("FreeDBP Server"), aCDDBPServer));
        add(Box.createRigidArea(new Dimension(0, 5)));

        add(ComponentFactory.createTwoPanel(new JLabel("FreeDBP Server Port"), aCDDBPPort));
        add(Box.createRigidArea(new Dimension(0, 10)));

        add(ComponentFactory.createTwoPanel(new JLabel("Email"), aEmail));
    }


    /**
     * Save settings.
     */
    public void save() {
        //Pref.setPref(Constants.FREEDB_KEY, aDefaultMethod.getSelectedIndex());
        Pref.setPref(Constants.FREEDB_SERVER_KEY, (String) aCDDBServer.getSelectedItem());
        Pref.setPref(Constants.FREEDB_PORT_KEY, aCDDBPort.getText());
        Pref.setPref(Constants.FREEDBP_SERVER_KEY, (String) aCDDBPServer.getSelectedItem());
        Pref.setPref(Constants.FREEDBP_PORT_KEY, aCDDBPPort.getText());
        Pref.setPref(Constants.FREEDB_EMAIL_KEY, aEmail.getText());
    }


}
