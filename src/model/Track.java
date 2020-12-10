/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author jonun
 */
public class Track {
    int trackid;
    String name;
    int albumid;    
    int mediatypeid;
    int genreid;
    String composer;
    int milliseconds;
    int bytes;
    double unitprice;

    public Track() {
    }

    public Track(int trackid, String name, int albumid, int mediatypeid, int genreid, String composer, int milliseconds, int bytes, double unitprice) {
        this.trackid = trackid;
        this.name = name;
        this.albumid = albumid;
        this.mediatypeid = mediatypeid;
        this.genreid = genreid;
        this.composer = composer;
        this.milliseconds = milliseconds;
        this.bytes = bytes;
        this.unitprice = unitprice;
    }

    public Track(int trackid, String name, int albumid, int mediatypeid, int genreid, int milliseconds, int bytes, double unitprice) {
        this.trackid = trackid;
        this.name = name;
        this.albumid = albumid;
        this.mediatypeid = mediatypeid;
        this.genreid = genreid;
        this.milliseconds = milliseconds;
        this.bytes = bytes;
        this.unitprice = unitprice;
    }
    
    public int getTrackid() {
        return trackid;
    }

    public void setTrackid(int trackid) {
        this.trackid = trackid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAlbumid() {
        return albumid;
    }

    public void setAlbumid(int albumid) {
        this.albumid = albumid;
    }

    public int getMediatypeid() {
        return mediatypeid;
    }

    public void setMediatypeid(int mediatypeid) {
        this.mediatypeid = mediatypeid;
    }

    public int getGenreid() {
        return genreid;
    }

    public void setGenreid(int genreid) {
        this.genreid = genreid;
    }

    public String getComposer() {
        return composer;
    }

    public void setComposer(String composer) {
        this.composer = composer;
    }

    public int getMilliseconds() {
        return milliseconds;
    }

    public void setMilliseconds(int milliseconds) {
        this.milliseconds = milliseconds;
    }

    public int getBytes() {
        return bytes;
    }

    public void setBytes(int bytes) {
        this.bytes = bytes;
    }

    public double getUnitprice() {
        return unitprice;
    }

    public void setUnitprice(double unitprice) {
        this.unitprice = unitprice;
    }

    @Override
    public String toString() {
        return "Id=" + trackid + ", Name=" + name + ", Composer=" + composer;
    }
    
}
