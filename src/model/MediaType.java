/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author unzalu.jon
 */
public class MediaType {
        
    int mediatypeid;
    String name;

    public MediaType() {
    }

    
    
    public MediaType(String name) {

        this.name = name;
    }

    
    
    public int getMediatypeid() {
        return mediatypeid;
    }

    public void setMediatypeid(int mediatypeid) {
        this.mediatypeid = mediatypeid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Id:" + mediatypeid + ", Izena:" + name;
    }
    
    
    
    
}
