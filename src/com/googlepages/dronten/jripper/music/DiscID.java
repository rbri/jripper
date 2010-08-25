/*
* Copyright 2004-2010 by dronten@gmail.com
*
* This source is distributed under the terms of the GNU PUBLIC LICENSE version 3
* http://www.gnu.org/licenses/gpl.html
*/

package com.googlepages.dronten.jripper.music;


/**
 * A record id in freedb database.
 */
public class DiscID implements Comparable<DiscID> {
    private String  aGenre = "";
    private String  aDiscID = "";
    private String  aRecord = "";


    public DiscID(String genre, String discID, String record) {
        aGenre  = genre;
        aDiscID = discID.replaceAll("^0[xX]", "");
        aRecord = record;
    }


    /**
     *
     * @param id
     * @return
     */
    public int compareTo(DiscID id) {
        return aRecord.compareToIgnoreCase(id.aRecord);
    }


    /**
     *
     * @return
     */
    public String getDiscID() {
        return aDiscID;
    }


    /**
     *
     * @return
     */
    public String getGenre() {
        return aGenre;
    }


    /**
     *
     * @return
     */
    public String getRecord() {
        return aRecord;
    }


    /**
     *
     * @param aDiscID
     */
    public void setDiscID(String aDiscID) {
        this.aDiscID = aDiscID;
    }


    /**
     *
     * @param aGenre
     */
    public void setGenre(String aGenre) {
        this.aGenre = aGenre;
    }


    /**
     *
     * @param aRecord
     */
    public void setRecord(String aRecord) {
        this.aRecord = aRecord;
    }


    /**
     *
     * @return
     */
    public String toString() {
        return String.format("%-20s%-15s%s", aGenre, aDiscID, aRecord);
    }
}
