package refereehelper.models.relations;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class EventPlayer implements Serializable {
    private Integer playerID;
    private Integer eventID;

    public EventPlayer(){

    }

    public Integer getEventID() {
        return eventID;
    }

    public Integer getPlayerID() {
        return playerID;
    }

    public void setEventID(Integer eventID) {
        this.eventID = eventID;
    }

    public void setPlayerID(Integer playerID) {
        this.playerID = playerID;
    }
}
