package com.megawaretech.pim.client.entities;

import com.extjs.gxt.ui.client.data.BeanModelTag;
import com.google.gwt.user.client.rpc.IsSerializable;
import java.util.Date;

/**
 *
 * @author sandeepgr
 * @author K.R.Arun
 */
public class Notes implements BeanModelTag, IsSerializable {

    private static final long serialVersionUID = 1L;
    private Integer entryid;
    private String entryname;
    private String content;
    private Date entrydate;

    public Notes() {
    }

    public Notes(Integer entryid) {
        this.entryid = entryid;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getEntrydate() {
        return entrydate;
    }

    public void setEntrydate(Date entryDate) {
        this.entrydate = entryDate;
    }

    public String getEntryname() {
        return entryname;
    }

    public void setEntryname(String entryName) {
        this.entryname = entryName;
    }

    public Integer getEntryid() {
        return entryid;
    }

    public void setEntryid(Integer entryid) {
        this.entryid = entryid;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (entryid != null ? entryid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Notes)) {
            return false;
        }
        Notes other = (Notes) object;
        if ((this.entryid == null && other.entryid != null) || (this.entryid != null && !this.entryid.equals(other.entryid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.megawaretech.pim.server.client.Notes[entryid=" + entryid + "]";
    }
}
