/*
* Copyright 2004-2010 by dronten@gmail.com
*
* This source is distributed under the terms of the GNU PUBLIC LICENSE version 3
* http://www.gnu.org/licenses/gpl.html
*/

package com.googlepages.dronten.jripper.util;

/**
 * Process thread that uses other threads for reading from child thread outputs.
 * One of the reader can also write to another child process.
 */
public class ProcessRunnerReadProcWriteFile extends ProcessRunner {
    /**
     * Create a ProcessRunner object that can read from one process and write to a file.
     *
     * @param log
     * @param progress
     * @param readParam
     * @param writeFileName
     * @param thread1
     * @param thread2
     */
    public ProcessRunnerReadProcWriteFile(Log log, Progress progress, ProcessParam readParam, String writeFileName, StreamThread thread1, StreamThread thread2) {
        super(log, progress);
        aThread1 = thread1;
        aThread2 = thread2;
        aReadParam = readParam;
        aWriteFileName = writeFileName;
    }
}
