/*
* Copyright 2004-2010 by dronten@gmail.com
*
* This source is distributed under the terms of the GNU PUBLIC LICENSE version 3
* http://www.gnu.org/licenses/gpl.html
*/

package com.googlepages.dronten.jripper.gui.dialog;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.text.html.HTMLEditorKit;

import com.googlepages.dronten.jripper.gui.ComponentFactory;
import com.googlepages.dronten.jripper.util.Log;
import com.googlepages.dronten.jripper.util.Log.LogListener;


/**
 * A help dialog object.
 */
public class HelpDialog extends BaseDialog implements ActionListener, LogListener {
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
        Log.get().setLogListener(null);
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


	public void changed(Log aLog) {
		setText(aLog.getLogMessage());
	}
}