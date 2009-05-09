/*
* Copyright 2004-2009 by dronten@gmail.com
*
* This source is distributed under the terms of the GNU PUBLIC LICENSE version 3
* http://www.gnu.org/licenses/gpl.html
*/

package com.googlepages.dronten.jripper.util;

/**
 * Process thread that uses other threads for reading from child thread outputs.
 * One of the reader can also write to another child process.
 */
public class ProcessRunnerReadFileWriteProc extends ProcessRunner {
    /**
     * Create a ProcessRunner object that can read from one file and write to another process.
     *
     * @param log
     * @param progress
     * @param readFileName
     * @param writeParam
     * @param thread
     */
    public ProcessRunnerReadFileWriteProc(Log log, Progress progress, String readFileName, ProcessParam writeParam, StreamThread thread) {
        super(log, progress);
        aThread1 = thread;
        aReadFileName = readFileName;
        aWriteParam = writeParam;
    }
}
