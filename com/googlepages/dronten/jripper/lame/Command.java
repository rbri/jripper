/*
* Copyright 2004-2009 by dronten@gmail.com
*
* This source is distributed under the terms of the GNU PUBLIC LICENSE version 3
* http://www.gnu.org/licenses/gpl.html
*/

package com.googlepages.dronten.jripper.lame;

import com.googlepages.dronten.jripper.Constants;
import com.googlepages.dronten.jripper.music.Album;
import com.googlepages.dronten.jripper.music.Track;
import com.googlepages.dronten.jripper.util.Pref;
import com.googlepages.dronten.jripper.util.ProcessParam;


/**
 * Command for generation of mp3 files.
 */
public class Command {
    /**
     * Get params for converting wav to mp3.
     *
     * @param album     Album object
     * @param track     Track object
     * @param fileName  Destination filename
     * @param format    PCM or WAV
     * @return          ProcessParam object
     * @throws          Exception
     */
    public static ProcessParam getEncoder(Album album, Track track, String fileName, boolean useAlbum, Constants.DataFormat format) throws Exception {
        ProcessParam    param = new ProcessParam();
        String          encoder;
        String          option;

        if (track.getEncoderIndex() < Constants.ENCODER_INDEX_COUNT) {
            encoder = Pref.getPref(Constants.MP3_ENCODER_KEY, Constants.MP3_ENCODER_DEFAULT);
            option = Pref.getPref(Constants.ENCODER_PARAM_KEYS[track.getEncoderIndex()], Constants.ENCODER_PARAM_DEFAULTS[track.getEncoderIndex()]);
        }
        else {
            throw new Exception("MP3 ENCODER OUT OF RANGE, SHOULD NOT HAPPEN");
        }

        param.add(encoder);

        if (format == Constants.DataFormat.PCM) {
            param.add("-r");
        }

        param.addSplitString(option);
        param.add("--quiet");
        param.add("--add-id3v2");
        param.add("--ta", album.aArtist.replaceAll("\"", "'"));
        param.add("--tl", album.aAlbum.replaceAll("\"", "'"));
        param.add("--tt", track.createTitleTag(album, useAlbum).replaceAll("\"", "'"));
        param.add("--ty", album.aYear);
        param.add("--tn", track.aTrack);
        param.add("--tg", album.aGenre.replaceAll("\"", "'"));
        param.add("--tc", album.aComment.replaceAll("\"", "'"));
        param.add("-");
        param.add(fileName);

        return param;
    }

    
    /**
     * Get params for converting mp3 to wav data.
     *
     * @param inFileName    Source filename
     * @param outFileName   Destination file or null for stdout
     * @return              Params for decoding mp3 file to wav
     * @throws              Exception Throws exception if error occured
     */
    public static ProcessParam getDecoder(String inFileName, String outFileName) throws Exception {
        ProcessParam param = new ProcessParam();

        param.add(Pref.getPref(Constants.MP3_ENCODER_KEY, Constants.MP3_ENCODER_DEFAULT));
        param.add("--decode");
        param.add(inFileName);
        if (outFileName != null) {
            param.add(outFileName);
        }
        else {
            param.add("-");
        }

        return param;
    }
}