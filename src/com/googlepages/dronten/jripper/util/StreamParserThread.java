/*
* Copyright 2004-2010 by dronten@gmail.com
*
* This source is distributed under the terms of the GNU PUBLIC LICENSE version 3
* http://www.gnu.org/licenses/gpl.html
*/

package com.googlepages.dronten.jripper.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Base class for parsing data from the decoders.
 */
public class StreamParserThread extends StreamThread {
    private int         aLastPercent = -10;
    private boolean     aToStdout = false;
    protected Pattern   aPattern = null;
    protected int       aMatchCount = 0;


    /**
     * @param toStdout Debug param, if true print some extra debug message
     */
    public StreamParserThread(boolean toStdout) {
        super(Log.get(), Progress.get(), ReadType.READ_STDERR_LINES, false);
        aToStdout = toStdout;
    }


    /**
     * Parse progress output from various programs.
     *
     * @param line Text string from decoder
     */
    public void data(String line) {
        Log.get().print(3, "QueryParserThread: <" + line);
        Matcher matcher;

        matcher = aPattern.matcher(line);
        if (matcher.find()) {
            Log.get().print(3, "MATCHER=" + matcher.group(1) + "    GROUPCOUNT=" + matcher.groupCount());

            try {
                int groupCount = matcher.groupCount();

                if (groupCount == aMatchCount && aMatchCount == 1) {
                    int v = Double.valueOf(matcher.group(1)).shortValue();

                    if (v >= 0 && v <= 100) {
                        Progress.get().setMinorProgress(v);
                    }
                }
                else if (groupCount == aMatchCount && aMatchCount == 2) {
                    double d1 = Double.valueOf(matcher.group(1));
                    double d2 = Double.valueOf(matcher.group(2));

                    if (d1 >= 0 && d1 <= d2 && d2 > 0) {
                        Progress.get().setMinorProgress((int) ((d1 / d2) * 100));
                    }
                }
            }
            catch (Exception e) {
                Log.get().print(3, e.toString());
            }

            try {
                int v = Progress.get().getMinorProgress();
                if ((v - 10) >= aLastPercent) {
                    aLastPercent = v;

                    if (aToStdout) {
                        Log.get().addTime(2, String.format("%d - %d%% done %s    %s", Progress.get().getMajorProgress(), Progress.get().getMinorProgress(), Progress.get().getMinorMessage(), Progress.get().getMajorMessage()));
                        Log.get().print(2, String.format("%d - %d%% done %s    %s", Progress.get().getMajorProgress(), Progress.get().getMinorProgress(), Progress.get().getMinorMessage(), Progress.get().getMajorMessage()));
                    }
                }
            }
            catch (Exception e) {
                Log.get().addTime(1, "QueryParserThread:Exception " + e.getMessage());
            }
        }
    }


    /**
     * @return Number of valid matches
     */
    public int getMatchCount() {
        return aMatchCount;
    }


    /**
     * @return Pattern object
     */
    public Pattern getPattern() {
        return aPattern;
    }
}
