package net.ja.android.eduroamcompanion.model;

import java.io.Serializable;

/**
 *
 * @author Tim
 */
// TODO: Possibly reimplement as builder
public class Subsite implements Serializable {
    private final String name;
    private final int lat;
    private final int lng;
    private final String address;
    private final double altitude;
    private final String ssid;
    private final String encryptionInfo;
    private final int accessPoints;
    private Site parentSite;

    public Subsite(String subsiteName, int lat, int lng, String ssid, String address, String encryptionInfo, int accessPoints, double altitude) {
        this.name = subsiteName;
        this.lat = lat;
        this.lng = lng;
        this.ssid = ssid;
        this.address = address;
        this.encryptionInfo = encryptionInfo;
        this.accessPoints = accessPoints;
        this.altitude = altitude;
    }

    public void setParentSite(Site parentSite) {
        this.parentSite = parentSite;
    }
    
    public Site getParentSite() {
        return parentSite;
    }

    public String getName() {
        return name;
    }

    public int getLat() {
        return lat;
    }

    public int getLng() {
        return lng;
    }

    public String getAddress() {
        return address;
    }

    public int getAccessPoints() {
        return accessPoints;
    }

    public double getAltitude() {
        return altitude;
    }

    public String getSsid() {
        return ssid;
    }

    public String getEncryptionInfo() {
        return encryptionInfo;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Subsite other = (Subsite) obj;
        if ((this.name == null) ? (other.name != null) : !this.name.equals(other.name)) {
            return false;
        }
        if (this.lat != other.lat) {
            return false;
        }
        if (this.lng != other.lng) {
            return false;
        }
        if ((this.address == null) ? (other.address != null) : !this.address.equals(other.address)) {
            return false;
        }
        if (Double.doubleToLongBits(this.altitude) != Double.doubleToLongBits(other.altitude)) {
            return false;
        }
        if ((this.ssid == null) ? (other.ssid != null) : !this.ssid.equals(other.ssid)) {
            return false;
        }
        if ((this.encryptionInfo == null) ? (other.encryptionInfo != null) : !this.encryptionInfo.equals(other.encryptionInfo)) {
            return false;
        }
        if (this.accessPoints != other.accessPoints) {
            return false;
        }
        if (this.parentSite != other.parentSite && (this.parentSite == null || !this.parentSite.equals(other.parentSite))) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 61 * hash + (this.name != null ? this.name.hashCode() : 0);
        hash = 61 * hash + this.lat;
        hash = 61 * hash + this.lng;
        hash = 61 * hash + (this.address != null ? this.address.hashCode() : 0);
        hash = 61 * hash + (int) (Double.doubleToLongBits(this.altitude) ^ (Double.doubleToLongBits(this.altitude) >>> 32));
        hash = 61 * hash + (this.ssid != null ? this.ssid.hashCode() : 0);
        hash = 61 * hash + (this.encryptionInfo != null ? this.encryptionInfo.hashCode() : 0);
        hash = 61 * hash + this.accessPoints;
        hash = 61 * hash + (this.parentSite != null ? this.parentSite.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        return "Subsite{" + "name=" + name + ", lat=" + lat + ", lng=" + lng + ", address=" + address + ", altitude=" + altitude + ", ssid=" + ssid + ", encryptionInfo=" + encryptionInfo + ", parentSite=" + parentSite + '}';
    }
}
