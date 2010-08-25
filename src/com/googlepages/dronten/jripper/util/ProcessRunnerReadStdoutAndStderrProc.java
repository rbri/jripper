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
public class ProcessRunnerReadStdoutAndStderrProc extends ProcessRunner {
    /**
     * Create a ProcessRunner object that executes a process and reads message from stderr and stdin.
     *
     * @param log
     * @param progress
     * @param readParam
     * @param stderrThread
     */
    public ProcessRunnerReadStdoutAndStderrProc(Log log, Progress progress, ProcessParam readParam, StreamThread stdinThread, StreamThread stderrThread) {
        super(log, progress);
        aThread1 = stdinThread;
        aThread2 = stderrThread;
        aReadParam = readParam;
    }
}
