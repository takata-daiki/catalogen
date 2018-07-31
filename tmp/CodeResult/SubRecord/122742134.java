package com.atlassian.plugins.rest.sample.expansion.entity;

import com.atlassian.plugins.rest.common.expand.Expander;

import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import static javax.xml.bind.annotation.XmlAccessType.FIELD;

/**
 * The total points scoring record for a player.
 */
@XmlRootElement
@XmlAccessorType(FIELD)
@Expander(SubRecordExpander.class)
public class SubRecord {

    @XmlElement
    private Integer pointsScored;

    @XmlTransient
    private PlayerRecord playerRecord;

    public static SubRecord emptySubRecord(PlayerRecord playerRecord) {
        return new SubRecord(playerRecord);
    }

    public SubRecord() {
    }

    public SubRecord(final PlayerRecord playerRecord) {
        this.playerRecord = playerRecord;
    }

    public SubRecord(int pointsScored) {
        this.pointsScored = pointsScored;
    }

    public Integer getPointsScored() {
        return pointsScored;
    }

    public void setPointsScored(Integer pointsScored) {
        this.pointsScored = pointsScored;
    }

    public PlayerRecord getPlayerRecord() {
        return playerRecord;
    }
}