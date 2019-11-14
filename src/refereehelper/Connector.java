package refereehelper;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class Connector {
	private static final String url = "jdbc:mysql://localhost:3306/referee";
    private static final String username = "user";
    // очень секьюрно
    private static final String password = "123qweasdzxc_123QWEASDZXC";
    
    private Connection connection;
    private Statement statement;
    private ResultSet res;
    // each query vulnerable to SQL-injection
    private String query;
    
    // TODO: проброс всех исключений из класса
    public Connector() throws IllegalStateException {
    	try {
	    	connection = DriverManager.getConnection(url, username, password);
	    	statement = connection.createStatement();
    	} catch (SQLException e) {
    		throw new IllegalStateException("Cannot connect the database!", e);
    	}
    }
    
    public static String prettyID(String id) {
    	return "[" + id + "]";
    }
    
    public static String prettyName(String name) {
    	return "'" + name + "'";
    }
    
    public void addEventToMatch(String match_ID, String event_type_ID, String time, ArrayList <String> players) {
    	query = String.format("INSERT INTO event (match_ID, event_type_ID, time) VALUES ('%s', '%s', '%s');", match_ID, event_type_ID, time);
    	
    	try {
    		int res = statement.executeUpdate(query);
	    } catch (SQLException e) {
    		e.printStackTrace();
    	}
    	String event_ID = getLastEventID();
    	addPlayersToEvent(event_ID, players);
    }
    
    private void addPlayersToEvent(String event_ID, ArrayList <String> players) {
    	for (int i = 0; i < players.size(); i++) {
    		query = String.format("INSERT INTO event_player (event_ID, player_ID) VALUES ('%s', '%s');", event_ID, players.get(i));
   
        	try {
        		int res = statement.executeUpdate(query);
    	    } catch (SQLException e) {
        		e.printStackTrace();
        	}
    	}
    }
    
    private void addTeamsToMatch(String id, String team1, String team2) {
    	String query1 = String.format("INSERT INTO match_team (match_ID, team_ID) VALUES ('%s', '%s');", id, team1);
    	String query2 = String.format("INSERT INTO match_team (match_ID, team_ID) VALUES ('%s', '%s');", id, team2);
    	
    	try {
    		int res1 = statement.executeUpdate(query1);
    		int res2 = statement.executeUpdate(query2);
	    } catch (SQLException e) {
    		e.printStackTrace();
    	}
    }
    
    public void createNewMatch(String game_type_ID, String date, String team1, String team2) {
    	query = String.format("INSERT INTO game_match (game_type_ID, date) VALUES ('%s', '%s');", game_type_ID, date);
    	
    	try {
    		int res = statement.executeUpdate(query);
	    } catch (SQLException e) {
    		e.printStackTrace();
    	}
    	String id = getLastMatchID();
    	addTeamsToMatch(id, team1, team2);
    }
    
    public void createNewTeam(String game_type_ID, String name) {
    	query = String.format("INSERT INTO team (game_type_ID, name) VALUES ('%s', '%s');", game_type_ID, name);
    	
    	try {
    		statement.executeUpdate(query);
	    } catch (SQLException e) {
    		e.printStackTrace();
    	}
    }
    
    public void createNewPlayer(String game_type_ID, String name, String surname, String team_ID) {
    	query = String.format("INSERT INTO player (game_type_ID, name, surname, team_ID) VALUES ('%s', '%s', '%s', '%s');", game_type_ID, name, surname, team_ID);
    	
    	try {
    		statement.executeUpdate(query);
	    } catch (SQLException e) {
    		e.printStackTrace();
    	}
    }
    
    public String getLastMatchID() {
    	query = String.format("SELECT ID FROM game_match ORDER BY ID DESC LIMIT 1;");
    	
    	try {
    		res = statement.executeQuery(query);
    		
	    	while (res.next()) {
	    		String id = res.getString(1);
	    		return id;
	    	}
    	} catch (SQLException e) {
    		e.printStackTrace();
    	}
    	return "-1";
    }
    
    public String getLastEventID() {
    	query = String.format("SELECT ID FROM event ORDER BY ID DESC LIMIT 1;");
    	
    	try {
    		res = statement.executeQuery(query);
    		
	    	while (res.next()) {
	    		String id = res.getString(1);
	    		return id;
	    	}
    	} catch (SQLException e) {
    		e.printStackTrace();
    	}
    	return "-1";
    }
    
    public String getPlayersOfEventType(String event_type_ID) {
    	query = String.format("SELECT players_num FROM event_type WHERE ID = %s;", event_type_ID);
    	
    	try {
    		res = statement.executeQuery(query);
    		
	    	while (res.next()) {
	    		String num = res.getString(1);
	    		return num;
	    	}
    	} catch (SQLException e) {
    		e.printStackTrace();
    	}
    	return "-1";
    }
    
    public void showGameTypes() {
    	query = "SELECT ID, name FROM game_type;";
    	try {
    		res = statement.executeQuery(query);
    		
	    	while (res.next()) {
	    		String id = prettyID(res.getString(1));
	    		String name = res.getString(2);
	    		System.out.println(id + " " + name);
	    	}
    	} catch (SQLException e) {
    		e.printStackTrace();
    		return;
    	}
    }
    
    public void showEventTypes(String game_type_ID) {
    	query = String.format("SELECT ID, name FROM event_type WHERE game_type_ID = %s;", game_type_ID);
    	try {
    		res = statement.executeQuery(query);
    		
	    	while (res.next()) {
	    		String id = prettyID(res.getString(1));
	    		String name = res.getString(2);
	    		System.out.println(id + " " + name);
	    	}
    	} catch (SQLException e) {
    		e.printStackTrace();
    		return;
    	}
    }
    
    public void showTeams(String game_type_ID) {
    	query = String.format("SELECT ID, name FROM team WHERE game_type_ID = %s;", game_type_ID);
    	try {
    		res = statement.executeQuery(query);
    		
	    	while (res.next()) {
	    		String id = prettyID(res.getString(1));
	    		String name = res.getString(2);
	    		System.out.println(id + " " + name);
	    	}
    	} catch (SQLException e) {
    		e.printStackTrace();
    		return;
    	}
    }
    
    public void showPlayers(String team_ID) {
    	query = String.format("SELECT ID, name, surname FROM player WHERE team_ID = %s;", team_ID);
    	try {
    		res = statement.executeQuery(query);
    		
	    	while (res.next()) {
	    		String id = prettyID(res.getString(1));
	    		String name = res.getString(2);
	    		String surname = res.getString(3);
	    		System.out.println(id + " " + name + " " + surname);
	    	}
    	} catch (SQLException e) {
    		e.printStackTrace();
    		return;
    	}
    }
    
    public void showTeamsOfTheGame(String match_ID) {
    	query = String.format("SELECT team.ID, team.name FROM match_team JOIN team ON team.ID = match_team.team_ID WHERE match_team.match_ID = %s;", match_ID);
    	try {
    		res = statement.executeQuery(query);
    		
	    	while (res.next()) {
	    		String id = prettyID(res.getString(1));
	    		String name = res.getString(2);
	    		System.out.println(id + " " + name);
	    	}
    	} catch (SQLException e) {
    		e.printStackTrace();
    		return;
    	}
    }
    
    public void showAllMatches() {
    	query = "SELECT gm.ID, gm.date, game_type.name FROM game_match AS gm JOIN game_type ON game_type.ID = gm.game_type_ID;";
    	try {
    		res = statement.executeQuery(query);
    		
	    	while (res.next()) {
	    		String id = prettyID(res.getString(1));
	    		String date = res.getString(2);
	    		String name = res.getString(3);
	    		System.out.println(id + " " + name + ": " + date);
	    	}
    	} catch (SQLException e) {
    		e.printStackTrace();
    		return;
    	}
    }
    
    public void showMatchStatistics(String match_ID) {
    	query = String.format("SELECT team.name FROM match_team JOIN team ON team.ID = match_team.team_ID WHERE match_team.match_ID = %s", match_ID);
    	try {
    		res = statement.executeQuery(query);
    		
	    	res.next();
    		String team1 = res.getString(1);
    		res.next();
    		String team2 = res.getString(1);
	    		
	    	System.out.println(prettyName(team1) + " VS " + prettyName(team2));
	    	
    	} catch (SQLException e) {
    		e.printStackTrace();
    		return;
    	}
    	
    	showEventStatistics(match_ID);
    }
    
    private void showEventStatistics(String match_ID) {
    	query = String.format("SELECT event.time, event_type.name, player.surname FROM event "
    			+ "JOIN event_type ON event_type.ID = event.event_type_id "
    			+ "JOIN event_player ON event_player.event_ID = event.ID "
    			+ "JOIN player ON player.ID = event_player.player_ID "
    			+ "WHERE event.match_ID = %s AND event_type.players_num = 1 "
    			+ "ORDER BY event.time", match_ID);
    	
    	try {
    		res = statement.executeQuery(query);
    		
	    	while (res.next()) {
	    		String time = res.getString(1);
	    		String name = res.getString(2);
	    		String player = res.getString(3);
	    		System.out.println(time + " min: " + name + " by " + player);
	    	}
    	} catch (SQLException e) {
    		e.printStackTrace();
    		return;
    	}
    }
    
}
