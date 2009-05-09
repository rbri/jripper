/*
* Copyright 2004-2009 by dronten@gmail.com
*
* This source is distributed under the terms of the GNU PUBLIC LICENSE version 3
* http://www.gnu.org/licenses/gpl.html
*/

package com.googlepages.dronten.jripper.gui.dialog;

import com.googlepages.dronten.jripper.gui.ComponentFactory;

import javax.swing.*;
import javax.swing.text.html.HTMLEditorKit;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


/**
 * A help dialog object.
 */
public class HelpDialog extends BaseDialog implements ActionListener {
    private static final long serialVersionUID = 666L;
    private JEditorPane aEditor = null;


    /**
     * Create help dialog.
     *
     * @param owner      - Set to null to create a window that can hide below application.
     * @param title
     * @param close_text
     * @throws java.awt.HeadlessException
     */
    public HelpDialog(JFrame owner, String title, String close_text) throws HeadlessException {
        super(owner, title, false);

        aEditor = new JEditorPane();
        JButton close = ComponentFactory.createButton(close_text, "", this, 0, 0);
        JScrollPane editScrollPane = new JScrollPane(aEditor);
        JPanel mainPanel = new JPanel();

        aEditor.setEditable(false);
        mainPanel.setLayout(ComponentFactory.createBorderLayout(5, 5));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        mainPanel.setPreferredSize(new Dimension(640, 480));
        mainPanel.setMaximumSize(new Dimension(2000, 2000));

        mainPanel.add(editScrollPane, BorderLayout.CENTER);
        mainPanel.add(close, BorderLayout.SOUTH);
        setContentPane(mainPanel);
        pack();
    }


    /**
     * @param e
     */
    public void actionPerformed(ActionEvent e) {
        setVisible(false);
    }


    /**
     * Set text in html format.
     *
     * @param text
     */
    public void setHtml(String text) {
        aEditor.setEditorKit(new HTMLEditorKit());
        aEditor.setText(text);
        aEditor.setCaretPosition(0);
    }


    /**
     * Set plain text.
     *
     * @param text
     */
    public void setText(String text) {
        aEditor.setText(text);
    }
}