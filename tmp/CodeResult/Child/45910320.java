/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package examples.domain;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author user
 */
@Entity
@Table(name = "child")
@XmlRootElement
@NamedNativeQueries({
    @NamedNativeQuery(name = "Child.findAll", query = "SELECT * FROM Child c", resultClass = Child.class),
    @NamedNativeQuery(name = "Child.findByChildByParentid", query = "SELECT * FROM Child c WHERE c.parentid = ? ", resultClass = Child.class),
    @NamedNativeQuery(name = "Child.findByChildid", query = "SELECT * FROM Child c WHERE c.childid = :childid", resultClass = Child.class)})
public class Child implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @NotNull
    @Column(name = "CHILDID")
    private Integer childid;
    @Size(max = 50)
    @Column(name = "CHILDIDENTIFIER")
    private String childidentifier;
    @JoinColumn(name = "PARENTID", referencedColumnName = "PARENTID")
    @ManyToOne(fetch = FetchType.LAZY)
    private Parent parent;

    
    //Constructors
    public Child() {
    }

    public Child(Integer childid) {
        this.childidentifier = childidentifier;
        this.childid = childid;
    }

    //Getters and Setters
    public Integer getChildid() {
        return childid;
    }

    public void setChildid(Integer childid) {
        this.childid = childid;
    }

    public String getChildidentifier() {
        return childidentifier;
    }

    public void setChildidentifier(String childidentifier) {
        this.childidentifier = childidentifier;
    }

    public Parent getParent() {
        return parent;
    }

    public void setParent(Parent parent) {
        this.parent = parent;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (childid != null ? childid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Child)) {
            return false;
        }
        Child other = (Child) object;
        if ((this.childid == null && other.childid != null) || (this.childid != null && !this.childid.equals(other.childid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "examples.domain.Child[ childid=" + childid + " ]";
    }
    
}
