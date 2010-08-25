/*
* Copyright 2004-2010 by dronten@gmail.com
*
* This source is distributed under the terms of the GNU PUBLIC LICENSE version 3
* http://www.gnu.org/licenses/gpl.html
*/

package com.googlepages.dronten.jripper.ogg;

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
     * Get params for converting mp3 to wav file.
     *
     * @param inFileName  Source filename
     * @param outFileName Destination file or null for stdout
     * @return Params for decoding mp3 file to wav
     * @throws Exception Throws exception if error occured
     */
    public static ProcessParam getDecoder(String inFileName, String outFileName) throws Exception {
        ProcessParam param = new ProcessParam();

        param.add(Pref.getPref(Constants.OGG_DECODER_KEY, Constants.OGG_DECODER_DEFAULT));
        param.add(inFileName);
        param.add("-o");
        if (outFileName != null) {
            param.add(outFileName);
        }
        else {
            param.add("-");
        }

        return param;
    }

    /**
     * Get params for converting audio to ogg by oggenc.
     *
     * @param album     Album object
     * @param track     Track object
     * @param fileName  Name of destination file
     * @param format    WAV or PCM
     * @return          ProcessParam object with command line parameters for encoding ogg
     * @throws          Exception Throws exception if error occured
     */
    public static ProcessParam getEncoder(Album album, Track track, String fileName, boolean useAlbum, Constants.DataFormat format) throws Exception {
        ProcessParam param = new ProcessParam();
        String encoder;
        String option;

        if (track.getEncoderIndex() < Constants.ENCODER_INDEX_COUNT) {
            encoder = Pref.getPref(Constants.OGG_ENCODER_KEY, Constants.OGG_ENCODER_DEFAULT);
            option = Pref.getPref(Constants.ENCODER_PARAM_KEYS[track.getEncoderIndex()], Constants.ENCODER_PARAM_DEFAULTS[track.getEncoderIndex()]);
        }
        else {
            throw new Exception("OGG ENCODER OUT OF RANGE, SHOULD NOT HAPPEN");
        }

        param.add(encoder);

        if (format == Constants.DataFormat.PCM) {
            param.add("--raw");
        }

        param.addSplitString(option);
        param.add("--quiet");
        param.add("--artist", album.aArtist);
        param.add("--album", album.aAlbum);
        param.add("--title", track.createTitleTag(album, useAlbum));
        param.add("--date", album.aYear);
        param.add("--tracknum", track.aTrack);
        param.add("--genre", album.aGenre);
        param.add("-c", "comment=" + album.aComment);
        param.add("-o");
        param.add(fileName);
        param.add("-");

        return param;
    }
}