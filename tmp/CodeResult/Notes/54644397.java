package com.megawaretech.pim.server.db.entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author K.R.Arun {code333@gmail.com}
 */
@Entity
@Table ( name = "NOTES",
         catalog = "",
         schema = "ADMIN" )
@NamedQueries ( {
    @NamedQuery ( name = "Notes.findAll",
                  query = "SELECT n FROM Notes n" ),
    @NamedQuery ( name = "Notes.findByEntryid",
                  query = "SELECT n FROM Notes n WHERE n.entryid = :entryid" ),
    @NamedQuery ( name = "Notes.findByEntryname",
                  query = "SELECT n FROM Notes n WHERE n.entryname = :entryname" ),
    @NamedQuery ( name = "Notes.findByContent",
                  query = "SELECT n FROM Notes n WHERE n.content = :content" ),
    @NamedQuery ( name = "Notes.findByEntrydate",
                  query = "SELECT n FROM Notes n WHERE n.entrydate = :entrydate" )
} )
public class Notes implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue ( strategy = GenerationType.IDENTITY )
    @Basic ( optional = false )
    @Column ( name = "ENTRYID",
              nullable = false )
    private Integer entryid;
    @Basic ( optional = false )
    @Column ( name = "ENTRYNAME",
              nullable = false,
              length = 100 )
    private String entryname;
    @Basic ( optional = false )
    @Column ( name = "CONTENT",
              nullable = false,
              length = 500 )
    private String content;
    @Basic ( optional = false )
    @Column ( name = "ENTRYDATE",
              nullable = false )
    @Temporal ( TemporalType.DATE )
    private Date entrydate;

    private int userid;

    public Notes() {
    }

    public Notes(Integer entryid) {
        this.entryid = entryid;
    }

    public Notes(Integer entryid, String entryname, String content, Date entrydate) {
        this.entryid = entryid;
        this.entryname = entryname;
        this.content = content;
        this.entrydate = entrydate;
    }

    public Integer getEntryid() {
        return entryid;
    }

    public void setEntryid(Integer entryid) {
        this.entryid = entryid;
    }

    public String getEntryname() {
        return entryname;
    }

    public void setEntryname(String entryname) {
        this.entryname = entryname;
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

    public void setEntrydate(Date entrydate) {
        this.entrydate = entrydate;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
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
        return "com.megawaretech.pim.server.db.entities.Notes[entryid=" + entryid + "]";
    }
}
