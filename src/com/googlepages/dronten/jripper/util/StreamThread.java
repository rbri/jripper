/*
* Copyright 2004-2009 by dronten@gmail.com
*
* This source is distributed under the terms of the GNU PUBLIC LICENSE version 3
* http://www.gnu.org/licenses/gpl.html
*/

package com.googlepages.dronten.jripper.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;


/**
 * Stream reader thread. For reading streams from process streams.
 * A reader can also write data to another process.
 */
public class StreamThread extends BaseThread {
    private static final int BUFFER_SIZE = 1024 * 128;


    public enum ReadType {
        UNDEFINED,
        READ_STDIN_BYTES,
        READ_STDIN_LINES,
        READ_STDERR_BYTES,
        READ_STDERR_LINES,
        READ_FILE_BYTES,
        READ_FILE_LINES,
    }


    public ReadType                 aType = ReadType.UNDEFINED;
    public boolean                  aWrite = false;
    private int                     aCount = 0;
    protected BufferedOutputStream  aByteWriter = null;
    protected BufferedInputStream   aByteReader = null;
    protected BufferedReader        aLineReader = null;


    /**
     * @param log
     * @param readType
     * @param write
     */
    public StreamThread(Log log, Progress progress, ReadType readType, boolean write) {
        super(log, progress);
        aType = readType;
        aWrite = write;
        //addLog(2, toString());
    }


    /**
     * Close streams.
     */
    public void close() {
        super.close();
        try {
            if (aByteWriter != null) {
                aByteWriter.close();
            }

            if (aByteReader != null) {
                aByteReader.close();
            }

            if (aLineReader != null) {
                aLineReader.close();
            }
        }
        catch (Exception e) {
            addLog(2, new StringBuffer().append("StreamThread::close() ").append(e.getMessage()).toString());
        }
    }


    /**
     * Default action is to send bytes to process writer.
     *
     * @param bytes
     * @param size
     * @throws Exception
     */
    public void data(byte[] bytes, int size) throws Exception {
        if (aWrite) {
            write(bytes, size);
        }
    }


    /**
     * Default action is to send bytes to process writer.
     *
     * @param line
     * @throws Exception
     */
    public void data(String line) throws Exception {
        byte[] bytes = line.getBytes();
        write(bytes, bytes.length);
    }


    /**
     * @return Get type of process thread
     */
    public ReadType getType() {
        return aType;
    }


    /**
     * @return true if read stream will write to output stream.
     */
    public boolean isWriting() {
        return aWrite;
    }


    /**
     * Run stream reader thread.
     */
    public void run() {
        aRunning = true;

        if (aByteReader != null) {
            byte[]  bytes = new byte[BUFFER_SIZE];
            int     count = 0;

            while (aRunning) {
                try {
                    count = aByteReader.read(bytes, 0, BUFFER_SIZE);

                    if (count == -1) {
                        aRunning = false;
                    }
                    else if (count > 0) {
                        aCount += count;
                        data(bytes, count);
                    }
                }
                catch (Exception e) {
                    setFailed(true);
                    close();
                    addLog(1, "StreamThread::run() " + e.getMessage() + " insputstream type=" + aType + "  class=" + e.getClass().getName());
                    break;
                }
            }
        }
        else if (aLineReader != null) {
            String tmp = null;

            while (aRunning) {
                try {
                    tmp = aLineReader.readLine();

                    if (tmp == null) {
                        aRunning = false;
                    }
                    else if (tmp.length() > 0) {
                        aCount += tmp.length();
                        data(tmp);
                    }
                }
                catch (Exception e) {
                    setFailed(true);
                    close();
                    addLog(1, "StreamThread::run() " + e.getMessage() + " reader type=" + aType + "  class=" + e.getClass().getName());
                    aRunning = false;
                }
            }
        }
        else {
            assert false;
        }
    }


    /**
     * @param stream
     */
    public void setByteReader(BufferedInputStream stream) {
        aByteReader = stream;
    }


    /**
     * @param stream
     */
    public void setByteWriter(BufferedOutputStream stream) {
        aByteWriter = stream;
    }


    /**
     * @param reader
     */
    public void setLineReader(BufferedReader reader) {
        aLineReader = reader;
    }


    /**
     * Write bytes to output process.
     *
     * @param bytes
     * @param size
     * @throws Exception
     */
    public void write(byte[] bytes, int size) throws Exception {
        if (aByteWriter != null) {
            aByteWriter.write(bytes, 0, size);
            aByteWriter.flush();

            if (aProgress != null) {
                aProgress.setMinorProgress(aCount);
            }
        }
    }
}
