package refereehelper.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;
import java.util.Date;

@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Match implements Serializable {
    private Integer ID;
    private Date date;
    private Integer gameTypeID;

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

    public void setGameTypeID(Integer gameTypeID) {
        this.gameTypeID = gameTypeID;
    }

    public void setID(Integer ID) {
        this.ID = ID;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
