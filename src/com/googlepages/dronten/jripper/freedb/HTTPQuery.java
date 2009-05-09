/*
* Copyright 2004-2009 by dronten@gmail.com
*
* This source is distributed under the terms of the GNU PUBLIC LICENSE version 3
* http://www.gnu.org/licenses/gpl.html
*/

package com.googlepages.dronten.jripper.freedb;

import com.googlepages.dronten.jripper.Constants;
import com.googlepages.dronten.jripper.music.Album;
import com.googlepages.dronten.jripper.music.DiscID;
import com.googlepages.dronten.jripper.music.Track;
import com.googlepages.dronten.jripper.util.Log;
import com.googlepages.dronten.jripper.util.Pref;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * FreeDB query by http protocoll.
 */
public class HTTPQuery {
    private static final String     CGI_READ                = "/~cddb/cddb.cgi?cmd=cddb+read+";
    private static final String     CGI_QUERY               = "/~cddb/cddb.cgi?cmd=cddb+query+";
    private static final String     THE_END1                = "&hello=";
    private static final String     THE_END2                = "+localhost+" + Constants.APP_VERSION_FREEDB + "&proto=6";
    private static final Pattern    FOUND_EXACT_MATCH       = Pattern.compile("^(200)\\s(\\S*)\\s(\\S*)\\s(.*)");
    private static final Pattern    FOUND_EXACT_MATCHES     = Pattern.compile("^(210).*");
    private static final Pattern    FOUND_INEXACT_MATCHES   = Pattern.compile("^(211).*");
    private static final Pattern    FOUND_MATCHES           = Pattern.compile("(\\S*)\\s(\\S*)\\s(.*)");
    private static final Pattern    FOUND_NO_MATCH          = Pattern.compile("(202) No.*");
    private static final Pattern    RECORD_DTITLE           = Pattern.compile("DTITLE=(.*)");
    private static final Pattern    RECORD_DYEAR            = Pattern.compile("DYEAR=(.*)");
    private static final Pattern    RECORD_DGENRE           = Pattern.compile("DGENRE=(.*)");
    private static final Pattern    RECORD_TTITLEN          = Pattern.compile("TTITLE(\\d+)=(.*)");
    private static final Pattern    RECORD_EXTD             = Pattern.compile("EXTD=(.*)");
    public static int               CDDB_MATCH              = 0;


    /**
     * Read album data from server.
     * A query must be done first before getting the actual data.
     * http://uk.freedb.org/~cddb/cddb.cgi?cmd=cddb+read+folk+440a5905&hello=joe+my.host.com+appname+0.1&proto=6
     *
     * @param log       Log object
     * @param server    Server adress
     * @param port      Port number
     * @param album     Album to query
     * @param discID    CDDBI string
     * @return          true if ok
     */
    public static boolean getAlbumData(Log log, String server, int port, Album album, DiscID discID) {
        String          readCmd = "";
        String          reply = "";
        BufferedReader  is = null;
        URL             url = null;
        URLConnection   con = null;
        int             trackCount = -1;
        String          lastTitle = "-1";

        album.aArtist = "";
        album.aAlbum = "";
        album.aYear = "";
        album.aComment = "";

        try {
            readCmd = CGI_READ + discID.getGenre() + "+" + discID.getDiscID();
            readCmd += THE_END1;
            readCmd += Pref.getPref(Constants.FREEDB_EMAIL_KEY, Constants.FREEDB_EMAIL_DEFAULT);
            readCmd += THE_END2;
            log.addTime(1, server + ":" + port + "/" + readCmd);

            url     = new URL("http", server, port, readCmd);
            con     = url.openConnection();
            is      = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"));
            reply   = is.readLine();

            while (reply != null) {
                log.addTime(1, reply);

                Matcher m1 = RECORD_DTITLE.matcher(reply);
                Matcher m2 = RECORD_DYEAR.matcher(reply);
                Matcher m3 = RECORD_DGENRE.matcher(reply);
                Matcher m4 = RECORD_TTITLEN.matcher(reply);
                Matcher m5 = RECORD_EXTD.matcher(reply);

                if (m1.find()) {
                    album.aArtist += m1.group(1);
                }
                else if (m2.find()) {
                    album.aYear = m2.group(1);
                }
                else if (m3.find()) {
                    album.aGenre = m3.group(1);
                }
                else if (m4.find()) {
                    if (trackCount < album.aTracks.size()) {

                        if (m4.group(1).equalsIgnoreCase(lastTitle)) { // Append title string for those titles that are multiline
                            Track track = album.aTracks.get(trackCount);
                            track.setName(track.aName + m4.group(2));
                        }
                        else if (trackCount < (album.aTracks.size() - 1)) { // There might be an extra data track but ignore this
                            trackCount++;
                            album.aTracks.get(trackCount).setName(m4.group(2));
                        }
                        lastTitle = m4.group(1);
                    }
                }
                else if (m5.find()) {
                    album.aComment += m5.group(1);
                }

                reply = is.readLine();
            }

            is.close();

            String[] tmp = album.aArtist.split(" / ", 2);

            if (tmp.length > 0) {
                album.aArtist = tmp[0];
            }

            if (tmp.length > 1) {
                album.aAlbum = tmp[1];
            }

            album.aComment  = album.aComment.replaceAll("\\\\n", ". ");
            album.aDiscID   = discID.getDiscID();
            album.aArtist   = album.aArtist.trim();
            album.aAlbum    = album.aAlbum.trim();
            album.aYear     = album.aYear.trim();
            album.aComment  = album.aComment.trim();

            for (Track track : album.aTracks) {
                track.aName = track.aName.trim();
            }

            if (album.aTracks.size() > 0) {
                HTTPQuery.CDDB_MATCH = 2;
            }

            return true;
        }
        catch (Exception e) {
            log.addTime(1, e.getMessage());
            return false;
        }
    }


    /**
     * Query a freedb server if a record exists.
     * http://uk.freedb.org/~music/music.cgi?cmd=music+query+0xf12321+18+0+18020+33590+53442+69152+79436+97579+114228+137805+151740+164875+180579+196818+214996+229914+246361+274717+306266+4417&hello=joe+my.host.com+testing+0.1&proto=6
     *
     * @param log       Log object
     * @param server    Server name
     * @param port      Port number
     * @param album     Album data to be filled
     * @return          All found records or an empty vector if none is found
     */
    public static Vector<DiscID> queryServerForAlbum(Log log, String server, int port, Album album) {
        String          queryCmd;
        String          reply;
        BufferedReader  is;
        URL             url;
        URLConnection   con;
        Vector<DiscID>  availableAlbums = new Vector<DiscID>();
        boolean         ok = false;

        CDDB_MATCH = 0;

        try {
            queryCmd = CGI_QUERY + album.aDiscID + "+" + album.aTracksString;

            for (Track t : album.aTracks) {
                queryCmd += "+" + t.aSector;
            }

            if (album.aDataSector.length() > 0) {
                queryCmd += "+" + album.aDataSector;
            }
            
            queryCmd += "+" + album.aSeconds;
            queryCmd += THE_END1;
            queryCmd += Pref.getPref(Constants.FREEDB_EMAIL_KEY, Constants.FREEDB_EMAIL_DEFAULT);
            queryCmd += THE_END2;
            log.addTime(1, server + ":" + port + queryCmd);

            url     = new URL("http", server, port, queryCmd);
            con     = url.openConnection();
            is      = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"));
            reply   = is.readLine();

            while (reply != null) {
                log.addTime(1, reply);

                Matcher m1 = FOUND_EXACT_MATCH.matcher(reply);
                Matcher m2 = FOUND_EXACT_MATCHES.matcher(reply);
                Matcher m3 = FOUND_INEXACT_MATCHES.matcher(reply);
                Matcher m4 = FOUND_MATCHES.matcher(reply);
                Matcher m5 = FOUND_NO_MATCH.matcher(reply);

                if (m5.find()) {
                    break;
                }
                else if (m1.find()) {
                    availableAlbums.add(new DiscID(m1.group(2), m1.group(3), m1.group(4)));
                    break;
                }
                else if (m2.find() || m3.find()) {
                    // Eat this line
                    ok = true;
                }
                else {
                    if (!ok) {
                        break;
                    }
                    else if (m4.find()) {
                        availableAlbums.add(new DiscID(m4.group(1), m4.group(2), m4.group(3)));
                    }
                }

                reply = is.readLine();
            }

            is.close();

            if (availableAlbums.size() > 0) {
                CDDB_MATCH = 1;
            }
        }
        catch (Exception e) {
            log.addTime(1, e.getMessage());
            availableAlbums.clear();
        }

        return availableAlbums;
    }
}
