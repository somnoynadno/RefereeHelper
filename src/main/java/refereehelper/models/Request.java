package refereehelper.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;
import java.util.Date;

@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Request implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer ID;
    private Integer team1ID;
    private Integer team2ID;
    private Date date;
    private Byte isAccepted;
    private Integer gameTypeID;
    private String description;

    public Request(){

    }

    public Byte getIsAccepted() {
        return isAccepted;
    }

    public Date getDate() {
        return date;
    }

    public Integer getGameTypeID() {
        return gameTypeID;
    }

    public Integer getID() {
        return ID;
    }

    public Integer getTeam1ID() {
        return team1ID;
    }

    public Integer getTeam2ID() {
        return team2ID;
    }

    public String getDescription() {
        return description;
    }

    public void setIsAccepted(Byte accepted) {
        this.isAccepted = accepted;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setGameTypeID(Integer gameTypeID) {
        this.gameTypeID = gameTypeID;
    }

    public void setTeam1ID(Integer team1ID) {
        this.team1ID = team1ID;
    }

    public void setTeam2ID(Integer team2ID) {
        this.team2ID = team2ID;
    }

    public void setID(Integer ID) {
        this.ID = ID;
    }
}
