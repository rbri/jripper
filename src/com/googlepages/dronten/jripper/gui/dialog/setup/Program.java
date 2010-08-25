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
 * Set path to all encoder/decoder applications.
 */
public class Program extends BaseSetupPanel implements ActionListener {
	private static final long serialVersionUID = -5860710140617450387L;
    private JTextField aLameEncoder = null;
    private JTextField aOggEncoder = null;
    private JTextField aOggDecoder = null;
    private JTextField aAACEncoder = null;
    private JTextField aAACDecoder = null;
    private JTextField aFlacEncoder = null;
    private JTextField aCDReader = null;
    private JButton aBrowseCDReader = null;
    private JButton aBrowseLameEncoder = null;
    private JButton aBrowseOggEncoder = null;
    private JButton aBrowseOggDecoder = null;
    private JButton aBrowseAACEncoder = null;
    private JButton aBrowseAACDecoder = null;
    private JButton aBrowseFlacEncoder = null;


    /**
     *
     */
    public Program() {
        aScroll = true;

        aCDReader = ComponentFactory.createInput(Pref.getPref(Constants.CD_READER_KEY, Constants.CD_READER_DEFAULT), "Select cd reader program (cdda2wav).", 0, 0);
        aBrowseCDReader = ComponentFactory.createButton("&>>", "Browse for cdda2wav program.", this, 0, 0);
        aLameEncoder = ComponentFactory.createInput(Pref.getPref(Constants.MP3_ENCODER_KEY, Constants.MP3_ENCODER_DEFAULT), "Select MP3 encoder (lame).", 0, 0);
        aBrowseLameEncoder = ComponentFactory.createButton("&>>", "Browse for lame program.", this, 0, 0);
        aOggEncoder = ComponentFactory.createInput(Pref.getPref(Constants.OGG_ENCODER_KEY, Constants.OGG_ENCODER_DEFAULT), "Select Ogg encoder (oggenc).", 0, 0);
        aBrowseOggEncoder = ComponentFactory.createButton("&>>", "Browse for oggenc program.", this, 0, 0);
        aOggDecoder = ComponentFactory.createInput(Pref.getPref(Constants.OGG_DECODER_KEY, Constants.OGG_DECODER_DEFAULT), "Select Ogg decoder (oggdec).", 0, 0);
        aBrowseOggDecoder = ComponentFactory.createButton("&>>", "Browse for oggdec program.", this, 0, 0);
        aAACEncoder = ComponentFactory.createInput(Pref.getPref(Constants.AAC_ENCODER_KEY, Constants.AAC_ENCODER_DEFAULT), "Select AAC encoder (faac).", 0, 0);
        aBrowseAACEncoder = ComponentFactory.createButton("&>>", "Browse for faac program.", this, 0, 0);
        aAACDecoder = ComponentFactory.createInput(Pref.getPref(Constants.AAC_DECODER_KEY, Constants.AAC_DECODER_DEFAULT), "Select AAC decoder (faad).", 0, 0);
        aBrowseAACDecoder = ComponentFactory.createButton("&>>", "Browse for faad program.", this, 0, 0);
        aFlacEncoder = ComponentFactory.createInput(Pref.getPref(Constants.FLAC_ENCODER_KEY, Constants.FLAC_ENCODER_DEFAULT), "Select Flac encoder (flac).", 0, 0);
        aBrowseFlacEncoder = ComponentFactory.createButton("&>>", "Browse for flac program.", this, 0, 0);

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        add(ComponentFactory.createThreePanel(new JLabel("CD Reader"), aCDReader, aBrowseCDReader));
        add(Box.createRigidArea(new Dimension(0, 5)));
        add(ComponentFactory.createThreePanel(new JLabel("MP3 Encoder/Decoder"), aLameEncoder, aBrowseLameEncoder));
        add(Box.createRigidArea(new Dimension(0, 5)));
        add(ComponentFactory.createThreePanel(new JLabel("Ogg Encoder"), aOggEncoder, aBrowseOggEncoder));
        add(Box.createRigidArea(new Dimension(0, 5)));
        add(ComponentFactory.createThreePanel(new JLabel("Ogg Decoder"), aOggDecoder, aBrowseOggDecoder));
        add(Box.createRigidArea(new Dimension(0, 5)));
        add(ComponentFactory.createThreePanel(new JLabel("AAC Encoder"), aAACEncoder, aBrowseAACEncoder));
        add(Box.createRigidArea(new Dimension(0, 5)));
        add(ComponentFactory.createThreePanel(new JLabel("AAC Decoder"), aAACDecoder, aBrowseAACDecoder));
        add(Box.createRigidArea(new Dimension(0, 5)));
        add(ComponentFactory.createThreePanel(new JLabel("Flac Encoder/Decoder"), aFlacEncoder, aBrowseFlacEncoder));
    }


    /**
     * Browse for file.
     *
     * @param actionEvent
     */
    public void actionPerformed(ActionEvent actionEvent) {
        JFileChooser chooser = new JFileChooser();

        chooser.setDialogTitle("Select File");
        if (chooser.showOpenDialog(JRipper.get()) == JFileChooser.APPROVE_OPTION) {
            if (actionEvent.getSource() == aBrowseCDReader) {
                aCDReader.setText(chooser.getSelectedFile().getPath());
                Pref.setPref(Constants.CD_READER_KEY, aCDReader.getText());
            }
            else if (actionEvent.getSource() == aBrowseLameEncoder) {
                aLameEncoder.setText(chooser.getSelectedFile().getPath());
            }
            else if (actionEvent.getSource() == aBrowseOggEncoder) {
                aOggEncoder.setText(chooser.getSelectedFile().getPath());
            }
            else if (actionEvent.getSource() == aBrowseOggDecoder) {
                aOggDecoder.setText(chooser.getSelectedFile().getPath());
            }
            else if (actionEvent.getSource() == aBrowseAACEncoder) {
                aAACEncoder.setText(chooser.getSelectedFile().getPath());
            }
            else if (actionEvent.getSource() == aBrowseAACDecoder) {
                aAACDecoder.setText(chooser.getSelectedFile().getPath());
            }
            else if (actionEvent.getSource() == aBrowseFlacEncoder) {
                aFlacEncoder.setText(chooser.getSelectedFile().getPath());
            }
        }
    }


    /**
     * Save settings.
     */
    public void save() {
        Pref.setPref(Constants.CD_READER_KEY, aCDReader.getText());
        Pref.setPref(Constants.MP3_ENCODER_KEY, aLameEncoder.getText());
        Pref.setPref(Constants.OGG_ENCODER_KEY, aOggEncoder.getText());
        Pref.setPref(Constants.OGG_DECODER_KEY, aOggDecoder.getText());
        Pref.setPref(Constants.AAC_ENCODER_KEY, aAACEncoder.getText());
        Pref.setPref(Constants.AAC_DECODER_KEY, aAACDecoder.getText());
        Pref.setPref(Constants.FLAC_ENCODER_KEY, aFlacEncoder.getText());
    }


}
