/*
* Copyright 2004-2010 by dronten@gmail.com
*
* This source is distributed under the terms of the GNU PUBLIC LICENSE version 3
* http://www.gnu.org/licenses/gpl.html
*/

package com.googlepages.dronten.jripper.freedb;

import com.googlepages.dronten.jripper.Constants;
import com.googlepages.dronten.jripper.JRipper;
import com.googlepages.dronten.jripper.gui.dialog.ListDialog;
import com.googlepages.dronten.jripper.music.Album;
import com.googlepages.dronten.jripper.music.DiscID;
import com.googlepages.dronten.jripper.util.BaseThread;
import com.googlepages.dronten.jripper.util.Log;
import com.googlepages.dronten.jripper.util.Pref;
import com.googlepages.dronten.jripper.util.Progress;

import java.util.Collections;
import java.util.Vector;


/**
 * Get info about a record from cddb by the http protocol.
 */
public class QueryParserThread extends BaseThread {
    public Album aAlbum = null;


    /**
     * @param album
     * @param progress
     */
    public QueryParserThread(Album album, Progress progress) {
        super(Log.get(), progress);
        aAlbum = album;
        aProgress.appendTask(2000, "Contacting freeDB server...", "");
    }


    /**
     * call hasFailed for detecting if retrival has been successfull.
     */
    public void run() {
        try {
            String server   = Pref.getPref(Constants.FREEDB_SERVER_KEY, Constants.FREEDB_SERVER_DEFAULT);
            String port     = Pref.getPref(Constants.FREEDB_PORT_KEY, Constants.FREEDB_PORT_DEFAULT);

            Vector<DiscID> discs = HTTPQuery.queryServerForAlbum(getLog(), server, Integer.valueOf(port), aAlbum);

            if (discs.size() > 0) {
                int index = 0;

                if (discs.size() > 1) {
                    Collections.sort(discs);

                    if (aRunning) {
                        ListDialog dlg = new ListDialog(JRipper.get(), "Select Album", "There were more than one match. Select one of the albums.", "&Ok", "", discs, true);

                        dlg.centerOnApplication();
                        dlg.setVisible(true);

                        if (dlg.selectedIndex() >= 0 && dlg.selectedIndex() < discs.size()) {
                            index = dlg.selectedIndex();
                        }
                    }

                }

                if (!HTTPQuery.getAlbumData(getLog(), server, Integer.valueOf(port), aAlbum, discs.get(index))) {
                    setFailed(true);
                }
            }
            else {
                addLog(1, "The CDDB lookup failed. Either the cd record was not found or server was busy.");
                setFailed(true);
            }
        }
        catch (NumberFormatException e2) {
            addLog(1, e2.getMessage());
            addLog(1, "Have you set the port number to something strange?");
            setFailed(true);
        }
        catch (Exception e) {
            addLog(1, e.getMessage());
            setFailed(true);
        }
    }
}
