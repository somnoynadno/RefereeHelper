package refereehelper.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Event implements Serializable{
    private Integer ID;
    private Integer matchID;
    private Integer eventTypeID;
    private Integer time;
    private Set<Player> players = new HashSet<Player>(0);

    public Event(){

    }

    public Integer getID() {
        return ID;
    }

    public Integer getEventTypeID() {
        return eventTypeID;
    }

    public Integer getMatchID() {
        return matchID;
    }

    public Integer getTime() {
        return time;
    }

    public Set<Player> getPlayers() {
        return players;
    }

    public void setID(Integer ID) {
        this.ID = ID;
    }

    public void setEventTypeID(Integer eventTypeID) {
        this.eventTypeID = eventTypeID;
    }

    public void setMatchID(Integer matchID) {
        this.matchID = matchID;
    }

    public void setTime(Integer time) {
        this.time = time;
    }

    public void setPlayers(Set<Player> players) {
        this.players = players;
    }
}
