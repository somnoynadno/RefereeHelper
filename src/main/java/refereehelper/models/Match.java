package refereehelper.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Match implements Serializable {
    private Integer ID;
    private Date date;
    private Integer gameTypeID;
    private Set<Team> teams = new HashSet<Team>(0);

    public Match() {

    }

    public Integer getGameTypeID() {
        return gameTypeID;
    }

    public Integer getID() {
        return ID;
    }

    public Date getDate() {
        return date;
    }

    public Set<Team> getTeams() {
        return teams;
    }

    public void setGameTypeID(Integer gameTypeID) {
        this.gameTypeID = gameTypeID;
    }

    public void setID(Integer ID) {
        this.ID = ID;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setTeams(Set<Team> teams) {
        this.teams = teams;
    }
}
