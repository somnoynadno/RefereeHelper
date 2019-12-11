package refereehelper.utils;

import org.hibernate.query.Query;
import refereehelper.models.Team;

import java.io.Serializable;
import java.util.Date;

public class Statistics implements Serializable {
    private Team team1;
    private Team team2;

    private int score1;
    private int score2;

    private int penalties1;
    private int penalties2;

    private int passes1;
    private int passes2;

    private Date date;

    public Statistics(){

    }

    // work fine
    public static Integer queryToInt(Query query){
        return Integer.parseInt(query.getResultList().toString()
                .replace('[', ' ').replace(']', ' ').trim());
    }

    public int getScore1() {
        return score1;
    }

    public int getScore2() {
        return score2;
    }

    public Team getTeam1() {
        return team1;
    }

    public Team getTeam2() {
        return team2;
    }

    public Date getDate() {
        return date;
    }

    public int getPenalties1() {
        return penalties1;
    }

    public int getPenalties2() {
        return penalties2;
    }

    public int getPasses1() {
        return passes1;
    }

    public int getPasses2() {
        return passes2;
    }

    public void setScore1(int score1) {
        this.score1 = score1;
    }

    public void setScore2(int score2) {
        this.score2 = score2;
    }

    public void setTeam1(Team team1) {
        this.team1 = team1;
    }

    public void setTeam2(Team team2) {
        this.team2 = team2;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setPenalties1(int penalties1) {
        this.penalties1 = penalties1;
    }

    public void setPenalties2(int penalties2) {
        this.penalties2 = penalties2;
    }

    public void setPasses1(int passes1) {
        this.passes1 = passes1;
    }

    public void setPasses2(int passes2) {
        this.passes2 = passes2;
    }
}
