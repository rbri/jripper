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
public class ProcessRunnerReadProcWriteProc extends ProcessRunner {
    /**
     * Create a ProcessRunner object that can read from one process and write to another process.
     *
     * @param log
     * @param readParam
     * @param writeParam
     * @param stdinThread
     * @param stderrThread
     */
    public ProcessRunnerReadProcWriteProc(Log log, Progress progress, ProcessParam readParam, ProcessParam writeParam, StreamThread stdinThread, StreamThread stderrThread) {
        super(log, progress);
        aThread1 = stdinThread;
        aThread2 = stderrThread;
        aReadParam = readParam;
        aWriteParam = writeParam;
        //addLog(2, toString());
    }
}
