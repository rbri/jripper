/*
* Copyright 2004-2009 by dronten@gmail.com
*
* This source is distributed under the terms of the GNU PUBLIC LICENSE version 3
* http://www.gnu.org/licenses/gpl.html
*/

package com.googlepages.dronten.jripper.util;

import java.util.ArrayList;
import java.util.Vector;


/**
 * Collect threads and close them when they are finished.
 */
public class ThreadCollector extends Thread {
    public boolean                      IS_RUNNING = true;
    private final ArrayList<BaseThread> aThreadList = new ArrayList<BaseThread>();


    /**
     *
     */
    public ThreadCollector() {
    }


    /**
     * Add thread to collector.
     *
     * @param thread
     */
    public void add(BaseThread thread) {
        thread.stopLogging();
        synchronized (aThreadList) {
            aThreadList.add(thread);
        }
    }


    /**
     * Add threads to collector.
     *
     * @param threads
     */
    public void add(Vector<BaseThread> threads) {
        for (BaseThread thread : threads) {
            if (thread != null && thread.isAlive()) {
                thread.stopLogging();
                add(thread);
            }
        }
    }


    /**
     * Run thread and close all existing threads.
     */
    @Override public void run() {
        BaseThread thread;

        while (IS_RUNNING) {
            try {
                Thread.sleep(1000);
            }
            catch (InterruptedException e) {
            }

            synchronized (aThreadList) {
                if (aThreadList.size() > 0) {
                    thread = aThreadList.remove(0);
                }
                else {
                    thread = null;
                }
            }

            if (thread != null) {
                thread.close();
            }
        }
    }
}
