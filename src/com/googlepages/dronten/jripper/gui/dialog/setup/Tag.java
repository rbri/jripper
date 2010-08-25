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
 * Set music title tag option.
 */
public class Tag extends BaseSetupPanel {
	private static final long serialVersionUID = -5748635179879855124L;
    private JComboBox aTitelTagChoices = null;
    private JCheckBox aRemoveNonLetters = null;


    /**
     *
     */
    public Tag() {
        aTitelTagChoices = ComponentFactory.createCombo(Constants.TITLE_OPTIONS, Pref.getPref(Constants.TITLE_KEY, Constants.TITLE_DEFAULT), "Set how title tag will be named.", 0, 0);
        aRemoveNonLetters = ComponentFactory.createCheck(Pref.getPref(Constants.TITLE_REMOVEPREFIX_KEY, Constants.TITLE_REMOVEPREFIX_DEFAULT), "Remove Prefix Number For Files", "<html>Check to remove all none letters first in the filename for the title tag when importing wav files.</html>", 0, 0);

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        add(ComponentFactory.createTwoPanel(new JLabel("Title Tag"), aTitelTagChoices));
        add(Box.createRigidArea(new Dimension(0, 5)));
        add(ComponentFactory.createOnePanel(aRemoveNonLetters));
    }


    /**
     * Save settings.
     */
    public void save() {
        Pref.setPref(Constants.TITLE_KEY, aTitelTagChoices.getSelectedIndex());
        Pref.setPref(Constants.TITLE_REMOVEPREFIX_KEY, aRemoveNonLetters.isSelected());
    }


}
