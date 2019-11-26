package refereehelper.models.relations;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class MatchTeam implements Serializable{
    private Integer matchID;
    private Integer teamID;

    public MatchTeam(){

    }

    public Integer getMatchID() {
        return matchID;
    }

    public Integer getTeamID() {
        return teamID;
    }

    public void setMatchID(Integer matchID) {
        this.matchID = matchID;
    }

    public void setTeamID(Integer teamID) {
        this.teamID = teamID;
    }
}
