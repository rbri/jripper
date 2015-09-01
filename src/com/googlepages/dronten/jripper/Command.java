/*
* Copyright 2004-2010 by dronten@gmail.com
*
* This source is distributed under the terms of the GNU PUBLIC LICENSE version 3
* http://www.gnu.org/licenses/gpl.html
*/

package com.googlepages.dronten.jripper;

import com.googlepages.dronten.jripper.music.Album;
import com.googlepages.dronten.jripper.music.Track;
import com.googlepages.dronten.jripper.ogg.ProgressStreamParser;
import com.googlepages.dronten.jripper.util.*;

import java.io.File;
import java.util.Vector;


/**
 * Build shell commands to execute console applications in threads.
 */
public class Command {
    public static boolean DEBUG = false;


    /**
     * Encode streams from cd to wav/mp3/ogg/flac/aac files.
     *
     * @param album         Album object
     * @param track         Current cd track
     * @param threads       Thread vector, one thread for every track
     * @param outFileName   Result file
     * @param wholecd       true if whole cd is to be ripped into one track
     * @throws Exception    Throws exception if some error occured
     */
    public static void createEncoderForCD(Album album, Track track, Vector<BaseThread> threads, String outFileName, boolean wholecd) throws Exception {
        ProcessRunner       processRunner = null;
        StreamParserThread  streamParserThread = new com.googlepages.dronten.jripper.cdda2wav.ProgressStreamParser(Command.DEBUG);

        switch (track.getEncoder()) {
            case Constants.MP3_TRACK:
                processRunner = new ProcessRunnerReadProcWriteProc(Log.get(), null, com.googlepages.dronten.jripper.cdda2wav.Command.getDecoder(track.aTrack, null, wholecd), com.googlepages.dronten.jripper.lame.Command.getEncoder(album, track, outFileName, wholecd, Constants.DataFormat.WAV), new StreamThread(Log.get(), null, StreamThread.ReadType.READ_STDIN_BYTES, true), streamParserThread);
                break;

            case Constants.OGG_TRACK:
                processRunner = new ProcessRunnerReadProcWriteProc(Log.get(), null, com.googlepages.dronten.jripper.cdda2wav.Command.getDecoder(track.aTrack, null, wholecd), com.googlepages.dronten.jripper.ogg.Command.getEncoder(album, track, outFileName, wholecd, Constants.DataFormat.WAV), new StreamThread(Log.get(), null, StreamThread.ReadType.READ_STDIN_BYTES, true), streamParserThread);
                break;

            case Constants.M4A_TRACK:
                processRunner = new ProcessRunnerReadProcWriteProc(Log.get(), null, com.googlepages.dronten.jripper.cdda2wav.Command.getDecoder(track.aTrack, null, wholecd), com.googlepages.dronten.jripper.aac.Command.getEncoder(album, track, outFileName, wholecd), new StreamThread(Log.get(), null, StreamThread.ReadType.READ_STDIN_BYTES, true), streamParserThread);
                break;

            case Constants.FLAC_TRACK:
                processRunner = new ProcessRunnerReadProcWriteProc(Log.get(), null, com.googlepages.dronten.jripper.cdda2wav.Command.getDecoder(track.aTrack, null, wholecd), com.googlepages.dronten.jripper.flac.Command.getEncoder(album, track, outFileName, wholecd), new StreamThread(Log.get(), null, StreamThread.ReadType.READ_STDIN_BYTES, true), streamParserThread);
                break;

            case Constants.WAV_TRACK:
                processRunner = new ProcessRunnerReadProc(Log.get(), null, com.googlepages.dronten.jripper.cdda2wav.Command.getDecoder(track.aTrack, outFileName, wholecd), streamParserThread);
                break;

            default:
                assert false;
        }

        File file = new File(outFileName);

        if (wholecd) {
            Progress.get().appendTask(album.aTracks.size() * 100, outFileName);
        }
        else {
            Progress.get().appendTask(file.getName(), file.getParent());
            processRunner.setTrackNum(track.aTrack);
        }

        threads.add(processRunner);
    }


    /**
     * Encode wav/mp3/ogg/flac/aac files to wav/mp3/ogg/flac/aac.<br>
     * INPUT      OUTPUT    RESULT  PROGRESS<br>
     * cd   <=>   m4a       OK          OK<br>
     * cd   <=>   mp3       OK          OK<br>
     * cd   <=>   ogg       OK          OK<br>
     * cd   <=>   flac      OK          OK<br>
     * cd   <=>   wav       OK          OK<br>
     * <br>
     * m4a  <=>   m4a       ERROR       OK Patched exe<br>
     * m4a  <=>   mp3       OK          OK Patched exe<br>
     * m4a  <=>   ogg       OK          OK Patched exe<br>
     * m4a  <=>   flac      ERROR       OK Patched exe<br>
     * m4a  <=>   wav       OK          OK Patched exe<br>
     * <br>
     * mp3  <=>   m4a       OK          OK<br>
     * mp3  <=>   mp3       OK          OK<br>
     * mp3  <=>   ogg       OK          OK<br>
     * mp3  <=>   flac      OK!         OK<br>
     * mp3  <=>   wav       OK          OK<br>
     * <br>
     * ogg  <=>   m4a       OK          OK<br>
     * ogg  <=>   mp3       OK          OK<br>
     * ogg  <=>   ogg       OK          OK<br>
     * ogg  <=>   flac      OK!         OK<br>
     * ogg  <=>   wav       OK          OK<br>
     * <br>
     * flac <=>   m4a       OK          ERROR flac doesn't flush progress?<br>
     * flac <=>   mp3       OK          ERROR<br>
     * flac <=>   ogg       OK          ERROR<br>
     * flac <=>   flac      OK          ERROR<br>
     * flac <=>   wav       OK          ERROR<br>
     * <br>
     * wav  <=>   m4a       OK          OK<br>
     * wav  <=>   mp3       OK          OK<br>
     * wav  <=>   ogg       OK          OK<br>
     * wav  <=>   flac      OK          OK<br>
     * wav  <=>   wav       OK          OK<br>
     *
     * @param album         Album object
     * @param track         Track object
     * @param threads       Thread arrray which will conain this encoder/decoder task
     * @param outFileName   Name of encoded track
     * @throws              Exception Throws exception if some error occured
     */
    public static void createEncoderForFiles(Album album, Track track, Vector<BaseThread> threads, String outFileName) throws Exception {
        StreamParserThread streamParserThread = null;
        ProcessRunner processRunner = null;
        StreamThread    byteReaderThread = null;
        ProcessParam    decoderParam = null;
        ProcessParam    decoderParamWav = null;

        if (track.getDecoder() == Constants.M4A_TRACK && track.getEncoder() == Constants.MP3_TRACK) { // Workaround bug in faad <=> lame?
            streamParserThread = new com.googlepages.dronten.jripper.aac.ProgressStreamParser(Command.DEBUG);
            byteReaderThread    = new StreamThread(Log.get(), null, StreamThread.ReadType.READ_STDIN_BYTES, true);
            decoderParam        = com.googlepages.dronten.jripper.aac.Command.getDecoder(track.aFile, null, Constants.DataFormat.PCM);
            processRunner       = new ProcessRunnerReadProcWriteProc(Log.get(), null, decoderParam, com.googlepages.dronten.jripper.lame.Command.getEncoder(album, track, outFileName, false, Constants.DataFormat.PCM), byteReaderThread, streamParserThread);
        }
        else if (track.getDecoder() == Constants.M4A_TRACK && track.getEncoder() == Constants.OGG_TRACK) { // Workaround bug in faad <=> oggenc?
            streamParserThread = new com.googlepages.dronten.jripper.aac.ProgressStreamParser(Command.DEBUG);
            byteReaderThread    = new StreamThread(Log.get(), null, StreamThread.ReadType.READ_STDIN_BYTES, true);
            decoderParam        = com.googlepages.dronten.jripper.aac.Command.getDecoder(track.aFile, null, Constants.DataFormat.PCM);
            processRunner       = new ProcessRunnerReadProcWriteProc(Log.get(), null, decoderParam, com.googlepages.dronten.jripper.ogg.Command.getEncoder(album, track, outFileName, false, Constants.DataFormat.PCM), byteReaderThread, streamParserThread);
        }
        else {
            switch (track.getDecoder()) {
                case Constants.MP3_TRACK:
                    streamParserThread = new com.googlepages.dronten.jripper.lame.ProgressStreamParser(Command.DEBUG);
                    byteReaderThread = new StreamThread(Log.get(), null, StreamThread.ReadType.READ_STDIN_BYTES, true);
                    decoderParam = com.googlepages.dronten.jripper.lame.Command.getDecoder(track.aFile, null);
                    decoderParamWav = com.googlepages.dronten.jripper.lame.Command.getDecoder(track.aFile, outFileName);
                    break;

                case Constants.OGG_TRACK:
                    streamParserThread = new ProgressStreamParser(Command.DEBUG);
                    byteReaderThread = new StreamThread(Log.get(), null, StreamThread.ReadType.READ_STDIN_BYTES, true);
                    decoderParam = com.googlepages.dronten.jripper.ogg.Command.getDecoder(track.aFile, null);
                    decoderParamWav = com.googlepages.dronten.jripper.ogg.Command.getDecoder(track.aFile, outFileName);
                    break;

                case Constants.M4A_TRACK:
                    streamParserThread = new com.googlepages.dronten.jripper.aac.ProgressStreamParser(Command.DEBUG);
                    byteReaderThread = new StreamThread(Log.get(), null, StreamThread.ReadType.READ_STDIN_BYTES, true);
                    decoderParam = com.googlepages.dronten.jripper.aac.Command.getDecoder(track.aFile, null, Constants.DataFormat.WAV);
                    decoderParamWav = com.googlepages.dronten.jripper.aac.Command.getDecoder(track.aFile, outFileName, Constants.DataFormat.WAV);
                    break;

                case Constants.FLAC_TRACK:
                    streamParserThread = new com.googlepages.dronten.jripper.flac.ProgressStreamParser(Command.DEBUG);
                    byteReaderThread = new StreamThread(Log.get(), null, StreamThread.ReadType.READ_STDIN_BYTES, true);
                    decoderParam = com.googlepages.dronten.jripper.flac.Command.getDecoder(track.aFile, null);
                    decoderParamWav = com.googlepages.dronten.jripper.flac.Command.getDecoder(track.aFile, outFileName);
                    break;

                default:
                    assert false;
            }

            switch (track.getEncoder()) {
                case Constants.MP3_TRACK:
                    processRunner = new ProcessRunnerReadProcWriteProc(
                            Log.get(),
                            null,
                            decoderParam,
                            com.googlepages.dronten.jripper.lame.Command.getEncoder(album, track, outFileName, false, Constants.DataFormat.WAV),
                            byteReaderThread,
                            streamParserThread);
                    break;

                case Constants.OGG_TRACK:
                    processRunner = new ProcessRunnerReadProcWriteProc(
                            Log.get(),
                            null,
                            decoderParam,
                            com.googlepages.dronten.jripper.ogg.Command.getEncoder(album, track, outFileName, false, Constants.DataFormat.WAV),
                            byteReaderThread,
                            streamParserThread);
                    break;

                case Constants.M4A_TRACK:
                    processRunner = new ProcessRunnerReadProcWriteProc(
                            Log.get(),
                            null,
                            decoderParam,
                            com.googlepages.dronten.jripper.aac.Command.getEncoder(album, track, outFileName, false),
                            byteReaderThread,
                            streamParserThread);
                    break;

                case Constants.FLAC_TRACK:
                    processRunner = new ProcessRunnerReadProcWriteProc(
                            Log.get(),
                            null,
                            decoderParam,
                            com.googlepages.dronten.jripper.flac.Command.getEncoder(album, track, outFileName, false),
                            byteReaderThread,
                            streamParserThread);
                    break;

                case Constants.WAV_TRACK:
                    processRunner = new ProcessRunnerReadProc(
                            Log.get(),
                            null,
                            decoderParamWav,
                            streamParserThread);
                    break;

                default:
                    assert false;
            }
        }

        File file = new File(outFileName);
        Progress.get().appendTask(file.getName(), file.getParent());
        processRunner.setTrackNum(track.aTrack);
        threads.add(processRunner);
    }


    /**
     * Encode wav files to mp3/ogg/flac/aac.
     *
     * @param album         Album object
     * @param track         Track object
     * @param threads       Thread arrray which will conain this encoder/decoder task
     * @param outFileName   Name of encoded track
     * @throws Exception    Throws exception if some error occured
     */
    public static void createEncoderForWavFiles(Album album, Track track, Vector<BaseThread> threads, String outFileName) throws Exception {
        ProcessRunner processRunner = null;
        StreamThread byteReaderThread = new StreamThread(Log.get(), Progress.get(), StreamThread.ReadType.READ_FILE_BYTES, true);

        switch (track.getEncoder()) {
            case Constants.MP3_TRACK:
                processRunner = new ProcessRunnerReadFileWriteProc(Log.get(), null, track.aFile, com.googlepages.dronten.jripper.lame.Command.getEncoder(album, track, outFileName, false, Constants.DataFormat.WAV), byteReaderThread);
                break;

            case Constants.OGG_TRACK:
                processRunner = new ProcessRunnerReadFileWriteProc(Log.get(), null, track.aFile, com.googlepages.dronten.jripper.ogg.Command.getEncoder(album, track, outFileName, false, Constants.DataFormat.WAV), byteReaderThread);
                break;

            case Constants.M4A_TRACK:
                processRunner = new ProcessRunnerReadFileWriteProc(Log.get(), null, track.aFile, com.googlepages.dronten.jripper.aac.Command.getEncoder(album, track, outFileName, false), byteReaderThread);
                break;

            case Constants.FLAC_TRACK:
                processRunner = new ProcessRunnerReadFileWriteProc(Log.get(), null, track.aFile, com.googlepages.dronten.jripper.flac.Command.getEncoder(album, track, outFileName, false), byteReaderThread);
                break;

            case Constants.WAV_TRACK:
                processRunner = new ProcessRunnerReadFileWriteFile(Log.get(), null, track.aFile, outFileName, byteReaderThread);
                break;

            default:
                assert false;
        }

        File file = new File(outFileName);
        Progress.get().appendTask(new File(track.aFile).length(), file.getName(), file.getParent());
        processRunner.setTrackNum(track.aTrack);
        threads.add(processRunner);
    }
}
