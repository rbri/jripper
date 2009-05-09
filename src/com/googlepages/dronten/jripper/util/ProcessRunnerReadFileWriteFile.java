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
public class ProcessRunnerReadFileWriteFile extends ProcessRunner {
    /**
     * Create a ProcessRunner object that can read from one file and write to another file.
     *
     * @param log
     * @param readFileName
     * @param writeFileName
     * @param stdinThread
     */
    public ProcessRunnerReadFileWriteFile(Log log, Progress progress, String readFileName, String writeFileName, StreamThread stdinThread) {
        super(log, progress);
        aThread1 = stdinThread;
        aReadFileName = readFileName;
        aWriteFileName = writeFileName;
    }
}
