package refereehelper.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Team implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer ID;
    private String name;
    private Integer gameTypeID;

    public Team(){

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

    public void setID(Integer ID) {
        this.ID = ID;
    }

    public void setGameTypeID(Integer gameTypeID) {
        this.gameTypeID = gameTypeID;
    }

    public void setName(String name) {
        this.name = name;
    }
}
