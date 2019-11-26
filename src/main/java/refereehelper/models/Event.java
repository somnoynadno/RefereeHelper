package refereehelper.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Event implements Serializable{
    private Integer ID;
    private Integer matchID;
    private Integer eventTypeID;
    private Integer time;

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
}
