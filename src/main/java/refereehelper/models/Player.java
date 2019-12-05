package refereehelper.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Player implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer ID;
    private String name;
    private String surname;
    private Integer gameTypeID;
    private Integer teamID;
    @JsonIgnore
    private Set<Event> events = new HashSet<Event>(0);

    public Player() {

    }

    public String getName() {
        return name;
    }

    public Integer getID() {
        return ID;
    }

    public Integer getGameTypeID() {
        return gameTypeID;
    }

    public Integer getTeamID() {
        return teamID;
    }

    public String getSurname() {
        return surname;
    }

    public Set<Event> getEvents() {
        return events;
    }

    public void setID(Integer ID) {
        this.ID = ID;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setGameTypeID(Integer gameTypeID) {
        this.gameTypeID = gameTypeID;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setTeamID(Integer teamID) {
        this.teamID = teamID;
    }

    public void setEvents(Set<Event> events) {
        this.events = events;
    }

}
