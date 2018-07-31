package pl.bmiedlar.stats.events;

import pl.bmiedlar.stats.info.Player;
import pl.bmiedlar.stats.info.Team;
import pl.bmiedlar.stats.match.Match;

import javax.persistence.*;

/**
 * Created by Bartosz on 14.08.2015.
 */
@Entity
@Table
public class Substitute {

    @Id @GeneratedValue(strategy = GenerationType.TABLE)
    @Column
    private int id;

    @Column
    private String time;

    @ManyToOne
    private Player playerIn;

    @ManyToOne
    private Player playerOut;

    @ManyToOne
    private Team team;

    @ManyToOne
    private Match match;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Player getPlayerIn() {
        return playerIn;
    }

    public void setPlayerIn(Player playerIn) {
        this.playerIn = playerIn;
    }

    public Player getPlayerOut() {
        return playerOut;
    }

    public void setPlayerOut(Player playerOut) {
        this.playerOut = playerOut;
    }

    public Team getTeam() {
        return team;
    }


    public void setTeam(Team team) {
        this.team = team;
    }

    public Match getMatch() {
        return match;
    }

    public void setMatch(Match match) {
        this.match = match;
    }
}
