/*
* Copyright 2004-2010 by dronten@gmail.com
*
* This source is distributed under the terms of the GNU PUBLIC LICENSE version 3
* http://www.gnu.org/licenses/gpl.html
*/

package com.googlepages.dronten.jripper.gui.dialog.setup;

import javax.swing.*;


/**
 * Set preference.
 */
public abstract class BaseSetupPanel extends JPanel {
    public boolean aScroll = false;


    /**
     *
     */
    public BaseSetupPanel() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
    }


    /**
     * Save settings.
     */
    public abstract void save();
}
