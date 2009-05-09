/*
* Copyright 2004-2009 by dronten@gmail.com
*
* This source is distributed under the terms of the GNU PUBLIC LICENSE version 3
* http://www.gnu.org/licenses/gpl.html
*/

package com.googlepages.dronten.jripper.aac;

import com.googlepages.dronten.jripper.Constants;
import com.googlepages.dronten.jripper.music.Album;
import com.googlepages.dronten.jripper.music.Track;
import com.googlepages.dronten.jripper.util.Pref;
import com.googlepages.dronten.jripper.util.ProcessParam;

/**
 * Command for generation flac files.
 */
public class Command {
    /**
     * Get params for decoding aac data.
     *
     * @param inFileName    Name of source file
     * @param outFileName   Name of destination file
     * @param format        WAV or PCM
     * @return              Command line parameters for decoding aac tracks
     */
    public static ProcessParam getDecoder(String inFileName, String outFileName, Constants.DataFormat format) {
        ProcessParam param = new ProcessParam();

        param.add(Pref.getPref(Constants.AAC_DECODER_KEY, Constants.AAC_DECODER_DEFAULT));

        if (outFileName != null) {
            param.add("-o");
            param.add(outFileName);
        }
        else {
            param.add("-w");
        }

        if (format == Constants.DataFormat.PCM) {
            param.add("-f");
            param.add("2");
        }
        else { // Microsoft WAV format
            param.add("-f");
            param.add("1");
        }

        param.add(inFileName);

        return param;
    }

    /**
     * Get params for encoding aac data.
     *
     * @param album     Album object
     * @param track     Track object
     * @param fileName  Name of destination file
     * @param useAlbum
     * @return Command line parameters for encoding aac
     * @throws Exception Throws exception for some error
     */
    public static ProcessParam getEncoder(Album album, Track track, String fileName, boolean useAlbum) throws Exception {
        ProcessParam    param = new ProcessParam();
        String          encoder;
        String          option;

        if (track.getEncoderIndex() < Constants.ENCODER_INDEX_COUNT) {
            encoder = Pref.getPref(Constants.AAC_ENCODER_KEY, Constants.AAC_ENCODER_DEFAULT);
            option = Pref.getPref(Constants.ENCODER_PARAM_KEYS[track.getEncoderIndex()], Constants.ENCODER_PARAM_DEFAULTS[track.getEncoderIndex()]);
        }
        else {
            throw new Exception("AAC ENCODER OUT OF RANGE, SHOULD NOT HAPPEN");
        }

        param.add(encoder);
        param.addSplitString(option);
        param.addQuote("--artist", album.aArtist);
        param.addQuote("--album", album.aAlbum);
        param.addQuote("--title", track.createTitleTag(album, useAlbum));
        param.addQuote("--year", album.aYear);
        param.addQuote("--track", track.aTrack);
        param.addQuote("--genre", album.aGenre);
        param.addQuote("--comment", album.aComment);
        param.add("-o");
        param.add(fileName);
        param.add("-");

        return param;
    }
}