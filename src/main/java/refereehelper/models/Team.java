package refereehelper.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Team implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer ID;
    private String name;
    private Integer gameTypeID;
    @JsonIgnore
    private Set<Match> matches = new HashSet<Match>(0);

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

    public Set<Match> getMatches() {
        return matches;
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

    public void setMatches(Set<Match> matches) {
        this.matches = matches;
    }
}
