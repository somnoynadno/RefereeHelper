package refereehelper.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class GameType implements Serializable {
    private Integer ID;
    private String name;

    public GameType(){

    }

    public Integer getID() {
        return ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setID(Integer ID) {
        this.ID = ID;
    }
}
