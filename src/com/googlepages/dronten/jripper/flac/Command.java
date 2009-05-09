/*
* Copyright 2004-2009 by dronten@gmail.com
*
* This source is distributed under the terms of the GNU PUBLIC LICENSE version 3
* http://www.gnu.org/licenses/gpl.html
*/

package com.googlepages.dronten.jripper.flac;

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
     * Get params for converting flac to wav, either to file or stdout.
     *
     * @param inFileName  Name of source file
     * @param outFileName Source file or null for stdout
     * @return Params for decoding flac file to wav
     */
    public static ProcessParam getDecoder(String inFileName, String outFileName) {
        ProcessParam param = new ProcessParam();

        param.add(Pref.getPref(Constants.FLAC_ENCODER_KEY, Constants.FLAC_ENCODER_DEFAULT));
        param.add("-f");
        param.add("-d");
        if (outFileName != null) {
            param.add("-o");
            param.add(outFileName);
        }
        else {
            param.add("-c");
        }
        param.add(inFileName);

        return param;
    }

    /**
     * Get params for converting wav to flac.
     *
     * @param album    Album object
     * @param track    Track object
     * @param fileName Name of destination file
     * @return ProcessParam object
     * @throws Exception Throws exception if error occured
     */
    public static ProcessParam getEncoder(Album album, Track track, String fileName, boolean useAlbum) throws Exception {
        ProcessParam param = new ProcessParam();
        String encoder;
        String option;

        if (track.getEncoderIndex() < Constants.ENCODER_INDEX_COUNT) {
            encoder = Pref.getPref(Constants.FLAC_ENCODER_KEY, Constants.FLAC_ENCODER_DEFAULT);
            option = Pref.getPref(Constants.ENCODER_PARAM_KEYS[track.getEncoderIndex()], Constants.ENCODER_PARAM_DEFAULTS[track.getEncoderIndex()]);
        }
        else {
            throw new Exception("FLAC ENCODER OUT OF RANGE, SHOULD NOT HAPPEN");
        }

        param.add(encoder);
        param.addSplitString(option);
        param.add("-f");
        param.add("--totally-silent");
        param.add("--no-padding");
        param.add(String.format("--tag=artist=%s", album.aArtist.replaceAll("\"", "'")));
        param.add(String.format("--tag=album=%s", album.aAlbum.replaceAll("\"", "'")));
        param.add(String.format("--tag=title=%s", track.createTitleTag(album, useAlbum).replaceAll("\"", "'")));
        param.add(String.format("--tag=date=%s", album.aYear));
        param.add(String.format("--tag=tracknum=%s", track.aTrack));
        param.add(String.format("--tag=genre=%s", album.aGenre));
        param.add(String.format("--tag=comment=%s", album.aComment.replaceAll("\"", "'")));
        param.add("-o");
        param.add(fileName);
        param.add("-");

        return param;
    }
}