/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tct.dashboard.kpi.slideshow;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Tawat
 */
public class slideshow {

    private List<NextModelList> nextmodellist;
    private List<TargetResult> targetresultlist;
    private String currentline;  // line of slide
    private String currentmodel;  // model of slide
    private String standardtime;  // standard time
    private String standardworker; // standard worker
    private int index;
    private String linkPic;
    public slideshow(List<NextModelList> nextmodellist, List<TargetResult> targetresultlist,
            String currentline, String currentmodel, String standardtime, String standardworker,
            int index,String linkPic) {
        this.nextmodellist = nextmodellist;
        this.targetresultlist = targetresultlist;
        this.currentline = currentline;
        this.currentmodel = currentmodel;
        this.standardtime = standardtime;
        this.standardworker = standardworker;
        this.index = index;
        this.linkPic  = linkPic;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getCurrentline() {
        return currentline;
    }

    public void setCurrentline(String currentline) {
        this.currentline = currentline;
    }

    public String getCurrentmodel() {
        return currentmodel;
    }

    public void setCurrentmodel(String currentmodel) {
        this.currentmodel = currentmodel;
    }

    public List<NextModelList> getNextmodellist() {
        if (nextmodellist == null) {
            nextmodellist = new ArrayList<NextModelList>();
        }
        return nextmodellist;
    }

    public void setNextmodellist(List<NextModelList> nextmodellist) {
        this.nextmodellist = nextmodellist;
    }

    public String getStandardtime() {
        return standardtime;
    }

    public void setStandardtime(String standardtime) {
        this.standardtime = standardtime;
    }

    public String getStandardworker() {
        return standardworker;
    }

    public void setStandardworker(String standardworker) {
        this.standardworker = standardworker;
    }

    public List<TargetResult> getTargetresultlist() {
        if (targetresultlist == null) {
            targetresultlist = new ArrayList<TargetResult>();
        }
        return targetresultlist;
    }

    public void setTargetresultlist(List<TargetResult> targetresultlist) {
        this.targetresultlist = targetresultlist;
    }

    public String getLinkPic() {
        return linkPic;
    }

    public void setLinkPic(String linkPic) {
        this.linkPic = linkPic;
    } 
}
