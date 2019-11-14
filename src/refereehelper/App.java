package refereehelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import refereehelper.Connector;


public class App {
	
	private static Connector connector;
	private static Scanner scanner;
		
	private static void printMenu() {
		System.out.println("Choose your destiny:");
		System.out.println("[1] Create new match");
		System.out.println("[2] Create new team");
		System.out.println("[3] Create new player");
		System.out.println("[4] Show match statistics");
		System.out.println("[5] Exit");
	}
	
	public static void main(String[] args) {
		try {
			connector = new Connector();
		} catch (IllegalStateException e) {
			e.printStackTrace();
			return;
		}
		
		scanner = new Scanner(System.in);
		boolean exit = false;
		
		while (!exit) {
			printMenu();
			int i = scanner.nextInt();
			switch (i) {
			case 1:
				createMatch();
				System.out.println("Match recorded");
				break;
			case 2:
				createTeam();
				System.out.println("Team created");
				break;
			case 3:
				createPlayer();
				System.out.println("Player created");
				break;
			case 4:
				showMatchStatistics();
				break;
			case 5:
				exit = true;
				break;
			default:
				System.out.println("Wrong option");
				break;
			}
		}
		
		scanner.close();
		System.out.println("Exiting...");
	}
	
	private static void createMatch() {
		System.out.println("Choose game type ID:");
		connector.showGameTypes();
		String game_type_id = scanner.next();
		
		System.out.println("Choose ID of first team:");
		connector.showTeams(game_type_id);
		String team1 = scanner.next();
		
		System.out.println("Choose ID of second team:");
		connector.showTeams(game_type_id);
		String team2 = scanner.next();
		
		System.out.println("Enter date (yyyy-mm-dd):");
		String date = scanner.next();
		System.out.println("Enter time (hh:mm:ss):");
		String time = scanner.next();
		
		date = date + " " + time;
		
		connector.createNewMatch(game_type_id, date, team1, team2);
		
		System.out.println("Match added");
		String match_ID = connector.getLastMatchID();
	
		addEventsToMatch(match_ID, game_type_id);
	}
	
	private static void addEventsToMatch(String match_ID, String game_type_ID) {
		boolean exit = false;
		
		while (!exit) {
			System.out.println("Select event ID or type 'exit' to quit:");
			connector.showEventTypes(game_type_ID);
			String event_type_ID = scanner.next();
			
			if (new String(event_type_ID).equals("exit")) {
				exit = true;
				continue;
			}
			
			int players_num = Integer.parseInt(connector.getPlayersOfEventType(event_type_ID));
			
			System.out.println("Choose team:");
			connector.showTeamsOfTheGame(match_ID);
			String team_ID = scanner.next();
			
			ArrayList <String> players = new ArrayList<String>();
			
			for (int i = 0; i < players_num; i++) {
				System.out.println(String.format("Select player %d:", i+1));
				connector.showPlayers(team_ID);
				String player = scanner.next();
				players.add(player);
			}
			
			System.out.println("Enter time:");
			String time = scanner.next();
			
			connector.addEventToMatch(match_ID, event_type_ID, time, players);
			System.out.println("Event created");
		}
			
	}
	
	private static void showMatchStatistics() {
		System.out.println("Select ID of the match you want:");
		connector.showAllMatches();
		String id = scanner.next();
		
		connector.showMatchStatistics(id);
	}
	
	private static void createTeam() {
		System.out.println("Enter team name:");
		scanner.nextLine();
		String name = scanner.nextLine();
		
		System.out.println("Enter game type ID:");
		connector.showGameTypes();
		String game_type_ID = scanner.next();
		
		connector.createNewTeam(game_type_ID, name);
	}
	
	private static void createPlayer() {
		System.out.println("Enter player name:");
		String name = scanner.next();
		System.out.println("Enter player surname:");
		String surname = scanner.next();
		
		System.out.println("Enter game type ID:");
		connector.showGameTypes();
		String game_type_ID = scanner.next();
		
		System.out.println("Enter team ID:");
		connector.showTeams(game_type_ID);
		String team_ID = scanner.next();
		
		connector.createNewPlayer(game_type_ID, name, surname, team_ID);
	}
		
}
