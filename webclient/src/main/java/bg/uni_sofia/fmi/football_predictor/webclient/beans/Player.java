package bg.uni_sofia.fmi.football_predictor.webclient.beans;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Player implements Serializable {
	
    public String name;

    public Player(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
    
    public void setName(String name) {
    	this.name = name;
    }
    
    @Override
    public String toString() {
    	return name;
    }
}

