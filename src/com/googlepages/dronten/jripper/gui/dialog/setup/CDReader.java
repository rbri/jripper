/*
* Copyright 2004-2010 by dronten@gmail.com
*
* This source is distributed under the terms of the GNU PUBLIC LICENSE version 3
* http://www.gnu.org/licenses/gpl.html
*/

package com.googlepages.dronten.jripper.gui.dialog.setup;

import com.googlepages.dronten.jripper.Constants;
import com.googlepages.dronten.jripper.cdda2wav.ScanbusTask;
import com.googlepages.dronten.jripper.gui.ComponentFactory;
import com.googlepages.dronten.jripper.util.Pref;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Set cd reader options (device, speed, paranoia).
 */
public class CDReader extends BaseSetupPanel implements ActionListener {
	private static final long serialVersionUID = -8131730144916093598L;
    private JComboBox   aCDDevice = null;
    private JComboBox   aCDSpeed = null;
    private JCheckBox   aCDParanoia = null;
    private JCheckBox   aCDMono = null;
    private JButton     aScanbus = null;


    /**
     *
     */
    public CDReader() {
        String[] speed = {"Default", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50"};

        aCDDevice = ComponentFactory.createCombo(new DeviceModel(), 0, "<html>Select cd device (common valuse are /dev/cdrom for ide drives or 1,0,0 for scsi or ide emulated as scsi)<br>You can run the command <b>cdda2wav -scanbus</b> as root to check available scsi cd readers.<br></html>", 0, 0);
        aScanbus = ComponentFactory.createButton("Probe Devices", "<html>Probe for SCSI devices.<br>Only works for <b>root</b> user on Unix.<br>Or on the <b>Windows</b> platform.</html>", this, 0, 0);
        aCDSpeed = ComponentFactory.createCombo(speed, Pref.getPref(Constants.CD_SPEED_KEY, Constants.CD_SPEED_DEFAULT), "Set CD reader speed.", 0, 0);
        aCDParanoia = ComponentFactory.createCheck(Pref.getPref(Constants.PARANOIA_KEY, Constants.PARANOIA_DEFAULT), "Use paranoia for slower but safer reading", "Check to use paranoia library when reading cd for safer but slower reading.", 0, 0);
        aCDMono = ComponentFactory.createCheck(Pref.getPref(Constants.CD_MONO_KEY, Constants.CD_MONO_DEFAULT), "Convert stereo to mono when reading CD", "Check to convert the stereo audiofiles on the CD to mono.", 0, 0);

        aCDDevice.setEditable(true);
        ((DeviceModel) aCDDevice.getModel()).addDevices(null);

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        add(ComponentFactory.createThreePanel(new JLabel("CD Device"), aCDDevice, aScanbus));
        add(Box.createRigidArea(new Dimension(0, 5)));
        add(ComponentFactory.createTwoPanel(new JLabel("CD Reading Speed"), aCDSpeed));
        add(Box.createRigidArea(new Dimension(0, 5)));
        add(ComponentFactory.createOnePanel(aCDParanoia));
        add(Box.createRigidArea(new Dimension(0, 5)));
        add(ComponentFactory.createOnePanel(aCDMono));
    }


    /**
     * Scan for devices.
     *
     * @param actionEvent
     */
    public void actionPerformed(ActionEvent actionEvent) {
        if (actionEvent.getSource() == aScanbus) {
            ScanbusTask task = new ScanbusTask();
            task.doTask();
            ((DeviceModel) aCDDevice.getModel()).addDevices(task.aDevices);
        }
    }


    /**
     * Save settings.
     */
    @Override public void save() {
        Pattern DEVICE = Pattern.compile("(\\D*)(\\d+,\\d+,\\d+) - (.*)");
        Matcher m1 = DEVICE.matcher((String) aCDDevice.getSelectedItem());

        if (m1.find()) {
            Pref.setPref(Constants.CD_DEVICE_KEY, m1.group(1) + m1.group(2));
        }
        else {
            Pref.setPref(Constants.CD_DEVICE_KEY, (String) aCDDevice.getSelectedItem());
        }

        Pref.setPref(Constants.CD_SPEED_KEY, aCDSpeed.getSelectedIndex());
        Pref.setPref(Constants.PARANOIA_KEY, aCDParanoia.isSelected());
        Pref.setPref(Constants.CD_MONO_KEY, aCDMono.isSelected());
    }


    /**
     * Data model for combobox with cd devices.
     */
    class DeviceModel extends DefaultComboBoxModel {
		private static final long serialVersionUID = -3813618980385028440L;

        /**
         *
         * @param ve2
         */
        public void addDevices(Vector<String> ve2) {
            String ve[] = Constants.SETUP_DEVICE_OPTIONS;
            String sel = Pref.getPref(Constants.CD_DEVICE_KEY, Constants.CD_DEVICE_DEFAULT);
            removeAllElements();

            for (String s : ve) {
                addElement(s);
            }

            if (ve2 != null) {
                for (String s2 : ve2) {
                    addElement(s2);
                }
            }

            aCDDevice.setSelectedItem(sel);
        }
    }
}
