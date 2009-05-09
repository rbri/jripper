/*
* Copyright 2004-2009 by dronten@gmail.com
*
* This source is distributed under the terms of the GNU PUBLIC LICENSE version 3
* http://www.gnu.org/licenses/gpl.html
*/

package com.googlepages.dronten.jripper.gui.dialog;

import com.googlepages.dronten.jripper.gui.ComponentFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;


/**
 * A list selection dialog object.
 */
public class ListDialog extends BaseDialog implements ActionListener {
    private JList       aList = null;
    private JButton     aOK = null;
    private boolean     aSelect = false;


    /**
     * @param owner         Dialog owner
     * @param titleText     Dialog title
     * @param labelText     Label text
     * @param okText        Ok button text
     * @param cancelText    Cancel button text
     * @param choices       Array of choices
     * @param fixed         true for fixed font
     * @throws HeadlessException
     */
    public ListDialog(JFrame owner, String titleText, String labelText, String okText, String cancelText, Vector choices, boolean fixed) throws HeadlessException {
        super(owner, titleText, true);

        aOK = ComponentFactory.createButton(okText, "", this, 0, 0);
        JButton cancelButton = ComponentFactory.createButton(cancelText, "", this, 0, 0);
        aList = new JList(choices);
        JScrollPane listScrollPane = new JScrollPane(aList);
        JPanel mainPanel = new JPanel();
        JLabel label = new JLabel(labelText);
        JPanel buttonPanel = new JPanel();

        if (fixed) {
            Font font = new Font("courier new", Font.TRUETYPE_FONT, 11);
            aList.setFont(font);
        }

        if (okText.length() == 0) {
            aOK.setVisible(false);
        }

        if (cancelText.length() == 0) {
            cancelButton.setVisible(false);
        }

        mainPanel.setLayout(ComponentFactory.createBorderLayout(5, 5));
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        mainPanel.setPreferredSize(new Dimension(700, 300));
        mainPanel.setMaximumSize(new Dimension(2000, 2000));

        buttonPanel.add(aOK);
        buttonPanel.add(cancelButton);
        mainPanel.add(label, BorderLayout.NORTH);
        mainPanel.add(listScrollPane, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        setContentPane(mainPanel);
        pack();
    }


    /**
     * @param actionEvent
     */
    public void actionPerformed(ActionEvent actionEvent) {
        setVisible(false);
        aSelect = actionEvent.getSource() == aOK;
    }


    /**
     * @return Is a row selected?
     */
    public boolean isSelected() {
        return aSelect;
    }


    /**
     * @return Index of selected row
     */
    public int selectedIndex() {
        return aList.getSelectedIndex();
    }
}