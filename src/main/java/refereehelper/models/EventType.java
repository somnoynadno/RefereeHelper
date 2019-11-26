package refereehelper.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class EventType implements Serializable{
    private Integer ID;
    private String name;
    private Integer playersNum;
    private String description;
    private Integer gameTypeID;

    public EventType(){

    }

    public Integer getID() {
        return ID;
    }

    public Integer getGameTypeID() {
        return gameTypeID;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Integer getPlayersNum() {
        return playersNum;
    }

    public void setID(Integer ID) {
        this.ID = ID;
    }

    public void setGameTypeID(Integer gameTypeID) {
        this.gameTypeID = gameTypeID;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPlayersNum(Integer playersNum) {
        this.playersNum = playersNum;
    }
}
