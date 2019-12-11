package refereehelper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.query.Query;
import refereehelper.models.*;
import refereehelper.utils.HibernateUtil;
import refereehelper.utils.Statistics;

import java.util.*;

import static spark.Spark.*;

public class Application {

	public static void main(String[] args) {
		staticFileLocation("/");

		get("/api/v1/", (req, res) -> "Hello from API");

		get("api/v1/request/:id", (req, res) -> {
			Session session = HibernateUtil.getSessionFactory().openSession();
			session.beginTransaction();

			ObjectMapper ow = new ObjectMapper();
			Integer id = Integer.parseInt(req.params(":id"));

			Request request = session.load(Request.class, id);
			session.close();
			try {
				res.header("Content-Type", "application/json");

				String json = ow.writeValueAsString(request);
				System.out.println(json);
				return json;
			} catch (JsonMappingException e) {
				System.out.println("Request not found");
				res.status(404);
				return "Request not found";
			} catch (JsonProcessingException e) {
				// catch various errors
				e.printStackTrace();
				res.status(500);
				return "Oh shit";
			}
		});

		get("api/v1/request/not_accepted/", (req, res) -> {
			res.header("Content-Type", "application/json");

			Session session = HibernateUtil.getSessionFactory().openSession();
			session.beginTransaction();

			Query query = session.createQuery("select R.ID, GT.name, T1.name, T2.name, R.date from Request as R " +
					"join Team as T1 on T1.ID = R.team1ID " +
					"join Team as T2 on T2.ID = R.team2ID " +
					"join GameType as GT on GT.ID = R.gameTypeID " +
					"where R.isAccepted='0'");
			List<Request> list = query.getResultList();

			ObjectMapper ow = new ObjectMapper();
			String json = ow.writeValueAsString(list);

			System.out.println(json);
			session.close();
			return json;
		});

		get("api/v1/request/accepted/", (req, res) -> {
			res.header("Content-Type", "application/json");

			Session session = HibernateUtil.getSessionFactory().openSession();
			session.beginTransaction();

			Query query = session.createQuery("select R.ID, GT.name, T1.name, T2.name, R.date from Request as R " +
					"join Team as T1 on T1.ID = R.team1ID " +
					"join Team as T2 on T2.ID = R.team2ID " +
					"join GameType as GT on GT.ID = R.gameTypeID " +
					"where R.isAccepted='1'");
			List<Request> list = query.getResultList();

			ObjectMapper ow = new ObjectMapper();
			String json = ow.writeValueAsString(list);

			System.out.println(json);
			session.close();
			return json;
		});

		get("api/v1/request/accept/:id", (req, res) -> {
			Session session = HibernateUtil.getSessionFactory().openSession();
			session.beginTransaction();

			ObjectMapper ow = new ObjectMapper();
			Integer id = Integer.parseInt(req.params(":id"));

			Request request = session.load(Request.class, id);
			request.setIsAccepted(Byte.valueOf("1"));
			session.update(request);

			session.getTransaction().commit();
			session.close();

			return "Fine";
		});

		get("api/v1/request/remove/:id", (req, res) -> {
			Session session = HibernateUtil.getSessionFactory().openSession();
			session.beginTransaction();

			ObjectMapper ow = new ObjectMapper();
			Integer id = Integer.parseInt(req.params(":id"));

			Request request = session.load(Request.class, id);

			session.remove(request);
			session.getTransaction().commit();
			session.close();

			return "Fine";
		});

		// POST method doesn't work the way that I want it to work
		// so it is GET
		get("/api/v1/request/create/", (req, res) -> {
			Integer team1ID = Integer.parseInt(req.queryMap().value("team1ID"));
			Integer team2ID = Integer.parseInt(req.queryMap().value("team2ID"));
			Integer gameTypeID = Integer.parseInt(req.queryMap().value("gameTypeID"));
			Date date = new Date(Long.parseLong(req.queryMap().value("date")));

			Session session = HibernateUtil.getSessionFactory().openSession();
			session.beginTransaction();

			Request request = new Request();
			request.setDate(date);
			request.setGameTypeID(gameTypeID);
			request.setTeam1ID(team1ID);
			request.setTeam2ID(team2ID);
			request.setIsAccepted(new Byte("0"));

			session.save(request);
			session.getTransaction().commit();
			session.close();

			return "Accepted";
		});

		get("api/v1/team/:id", (req, res) -> {
			Session session = HibernateUtil.getSessionFactory().openSession();
			session.beginTransaction();

			ObjectMapper ow = new ObjectMapper();
			Integer id = Integer.parseInt(req.params(":id"));

			Team team = session.load(Team.class, id);
			System.out.println(team);
			session.close();
			try {
				res.header("Content-Type", "application/json");

				String json = ow.writeValueAsString(team);
				System.out.println(json);
				return json;
			} catch (JsonMappingException e) {
				System.out.println("Team not found");
				res.status(404);
				return "Team not found";
			} catch (JsonProcessingException e) {
				// catch various errors
				e.printStackTrace();
				res.status(500);
				return "Oh shit";
			}
		});

		get("api/v1/team/", (req, res) -> {
			res.header("Content-Type", "application/json");

			Session session = HibernateUtil.getSessionFactory().openSession();
			session.beginTransaction();

			Query query = session.createQuery("from Team");
			List<Team> list = query.getResultList();

			ObjectMapper ow = new ObjectMapper();
			String json = ow.writeValueAsString(list);

			System.out.println(json);
			session.close();
			return json;
		});

		get("api/v1/game_type/:id", (req, res) -> {
			Session session = HibernateUtil.getSessionFactory().openSession();
			session.beginTransaction();

			ObjectMapper ow = new ObjectMapper();
			Integer id = Integer.parseInt(req.params(":id"));

			GameType gt = session.load(GameType.class, id);
			session.close();
			try {
				res.header("Content-Type", "application/json");

				String json = ow.writeValueAsString(gt);
				System.out.println(json);
				return json;
			} catch (JsonMappingException e) {
				System.out.println("Request not found");
				res.status(404);
				return "Request not found";
			} catch (JsonProcessingException e) {
				// catch various errors
				e.printStackTrace();
				res.status(500);
				return "Oh shit";
			}
		});

		get("api/v1/game_type/", (req, res) -> {
			res.header("Content-Type", "application/json");

			Session session = HibernateUtil.getSessionFactory().openSession();
			session.beginTransaction();

			Query query = session.createQuery("from GameType");
			List<GameType> list = query.getResultList();

			ObjectMapper ow = new ObjectMapper();
			String json = ow.writeValueAsString(list);

			System.out.println(json);
			session.close();
			return json;
		});

		get("api/v1/event_type/:id", (req, res) -> {
			Session session = HibernateUtil.getSessionFactory().openSession();
			session.beginTransaction();

			ObjectMapper ow = new ObjectMapper();
			Integer id = Integer.parseInt(req.params(":id"));

			EventType et = session.load(EventType.class, id);
			session.close();
			try {
				res.header("Content-Type", "application/json");

				String json = ow.writeValueAsString(et);
				System.out.println(json);
				return json;
			} catch (JsonMappingException e) {
				System.out.println("Event Type not found");
				res.status(404);
				return "Event type not found";
			} catch (JsonProcessingException e) {
				// catch various errors
				e.printStackTrace();
				res.status(500);
				return "Oh shit";
			}
		});

		get("api/v1/event_type/", (req, res) -> {
			res.header("Content-Type", "application/json");

			Session session = HibernateUtil.getSessionFactory().openSession();
			session.beginTransaction();

			Query query = session.createQuery("from EventType");
			List<EventType> list = query.getResultList();

			ObjectMapper ow = new ObjectMapper();
			String json = ow.writeValueAsString(list);

			System.out.println(json);
			session.close();
			return json;
		});

		get("api/v1/match/:id", (req, res) -> {
			Session session = HibernateUtil.getSessionFactory().openSession();
			session.beginTransaction();

			ObjectMapper ow = new ObjectMapper();
			Integer id = Integer.parseInt(req.params(":id"));

			Match m = session.load(Match.class, id);
			session.close();
			try {
				res.header("Content-Type", "application/json");

				String json = ow.writeValueAsString(m);
				System.out.println(json);
				return json;
			} catch (JsonMappingException e) {
				System.out.println("Match not found");
				res.status(404);
				return "Match not found";
			} catch (JsonProcessingException e) {
				// catch various errors
				e.printStackTrace();
				res.status(500);
				return "Oh shit";
			}
		});

		get("api/v1/match/", (req, res) -> {
			res.header("Content-Type", "application/json");

			Session session = HibernateUtil.getSessionFactory().openSession();
			session.beginTransaction();

			Query query = session.createNativeQuery("select M.ID, M.date, GT.name as n, T.name " +
					"from game_match as M " +
					"left join match_team as MT on MT.match_ID = M.ID " +
					"join game_type as GT on GT.ID = M.game_type_ID " +
					"left join team as T on T.ID = MT.team_ID;");
			List<Match> list = query.getResultList();

			ObjectMapper ow = new ObjectMapper();
			String json = ow.writeValueAsString(list);

			System.out.println(json);
			session.close();
			return json;
		});

		// POST method doesn't work the way that I want it to work
		// so it is GET
		get("/api/v1/match/create/", (req, res) -> {
			Integer id = Integer.parseInt(req.queryMap().value("id"));
			Integer team1ID = Integer.parseInt(req.queryMap().value("team1ID"));
			Integer team2ID = Integer.parseInt(req.queryMap().value("team2ID"));
			Date date = new Date(Long.parseLong(req.queryMap().value("date")));
			Integer gameTypeID = Integer.parseInt(req.queryMap().value("gameTypeID"));

			System.out.println(id);
			System.out.println(date);
			System.out.println(team1ID);
			System.out.println(team2ID);
			System.out.println(gameTypeID);

			Session session = HibernateUtil.getSessionFactory().openSession();
			session.beginTransaction();

			// delete request, create match and add relations with teams
			Request r = session.load(Request.class, id);
			session.delete(r);

			Match m = new Match();
			m.setDate(date);
			m.setGameTypeID(gameTypeID);
			m.setID(id);

			Set<Team> teams = new HashSet<Team>(0);
			Team team1 = session.load(Team.class, team1ID);
			Team team2 = session.load(Team.class, team2ID);
			teams.add(team1);
			teams.add(team2);

			m.setTeams(teams);
			session.save(m);

			session.getTransaction().commit();
			session.close();

			Session map_session = HibernateUtil.getSessionFactory().openSession();
			map_session.beginTransaction();

			// because he doesn't wan't to map many-to-many
			map_session.createSQLQuery("insert into match_team (match_ID, team_ID, score) " +
					"values (" + id.toString() + ", " + team1ID.toString() + ", 0);").executeUpdate();
			map_session.createSQLQuery("insert into match_team (match_ID, team_ID, score) " +
					"values (" + id.toString() + ", " + team2ID.toString() + ", 0);").executeUpdate();

			map_session.getTransaction().commit();
			map_session.close();

			return "Nice";
		});

		// POST method doesn't work the way that I want it to work
		// so it is GET
		get("/api/v1/match/add_event/", (req, res) -> {
			Integer id = Integer.parseInt(req.queryMap().value("id"));
			Integer player1ID = Integer.parseInt(req.queryMap().value("player1ID"));
			Integer player2ID;
			if (!req.queryMap().value("player2ID").equals("")) {
				player2ID = Integer.parseInt(req.queryMap().value("player2ID"));
			} else player2ID = 0;
			Integer eventTypeID = Integer.parseInt(req.queryMap().value("eventTypeID"));
			Integer time = Integer.parseInt(req.queryMap().value("time"));

			System.out.println(id);
			System.out.println(time);
			System.out.println(player1ID);
			System.out.println(player2ID);
			System.out.println(eventTypeID);

			Session session = HibernateUtil.getSessionFactory().openSession();
			session.beginTransaction();

			// save event and add relations with players
			Event e = new Event();
			e.setEventTypeID(eventTypeID);
			e.setMatchID(id);
			e.setTime(time);

			Set<Player> players = new HashSet<Player>(0);
			Player player1 = session.load(Player.class, player1ID);
			players.add(player1);

			if (player2ID != 0) {
				Player player2 = session.load(Player.class, player2ID);
				players.add(player2);
			}

			e.setPlayers(players);
			session.save(e);

			Query query = session.createQuery("from Event order by id desc").setMaxResults(1);
			List<Event> list = query.getResultList();
			Event event = list.get(0);
			Integer eventID = event.getID();

			// because he doesn't wan't to map many-to-many
			session.createSQLQuery("insert into event_player (event_ID, player_ID) " +
					"values (" + eventID.toString() + ", " + player1ID.toString() + ");").executeUpdate();
			if (player2ID != 0) {
				session.createSQLQuery("insert into event_player (event_ID, player_ID) " +
						"values (" + eventID.toString() + ", " + player2ID.toString() + ");").executeUpdate();
			}

			// update score if needed
			if (event.getEventTypeID() == 7) { // basketball 3 score goal
				session.createSQLQuery("update match_team set score = score + 3 " +
						"where match_ID = " + event.getMatchID().toString() + " " +
						"and team_ID = " + player1.getTeamID().toString() + ";").executeUpdate();
			} else if (event.getEventTypeID() == 6) { // basketball 2 score goal
				session.createSQLQuery("update match_team set score = score + 2 " +
						"where match_ID = " + event.getMatchID().toString() + " " +
						"and team_ID = " + player1.getTeamID().toString() + ";").executeUpdate();
			} else if (event.getEventTypeID() == 1) { // football goal
				session.createSQLQuery("update match_team set score = score + 1 " +
						"where match_ID = " + event.getMatchID().toString() + " " +
						"and team_ID = " + player1.getTeamID().toString() + ";").executeUpdate();
			}

			session.getTransaction().commit();
			session.close();

			return "Event added";
		});

		get("/api/v1/statistics/:id", (req, res) -> {
			Session session = HibernateUtil.getSessionFactory().openSession();
			session.beginTransaction();

			ObjectMapper ow = new ObjectMapper();
			Integer id = Integer.parseInt(req.params(":id"));

			try {
				session.get(Match.class, id);
			} catch (HibernateException e){
				System.out.println("Match not found");
				res.status(404);
				return "Match not found";
			}

			Match m = session.load(Match.class, id);
			List<Team> teamList = new ArrayList<Team>();
			teamList.addAll(m.getTeams());

			Query query1score = session.createNativeQuery("select score from match_team " +
					"where match_ID = " + id + " and team_ID = " + teamList.get(0).getID());
			Query query2score = session.createNativeQuery("select score from match_team " +
					"where match_ID = " + id + " and team_ID = " + teamList.get(1).getID());

			Query query1passes = session.createNativeQuery("select count(*) from event " +
					"join event_player on event_player.event_ID = event.ID " +
					"join player on player.ID = event_player.player_ID " +
					"where match_ID = " + id + " and team_ID = " + teamList.get(0).getID() + " " +
					"and event_type_ID in (2, 9)");
			Query query2passes = session.createNativeQuery("select count(*) from event " +
					"join event_player on event_player.event_ID = event.ID " +
					"join player on player.ID = event_player.player_ID " +
					"where match_ID = " + id + " and team_ID = " + teamList.get(1).getID() + " " +
					"and event_type_ID in (2, 9)");
			Query query1penalties = session.createNativeQuery("select count(*) from event " +
					"join event_player on event_player.event_ID = event.ID " +
					"join player on player.ID = event_player.player_ID " +
					"where match_ID = " + id + " and team_ID = " + teamList.get(0).getID() + " " +
					"and event_type_ID in (3, 4, 8)");
			Query query2penalties = session.createNativeQuery("select count(*) from event " +
					"join event_player on event_player.event_ID = event.ID " +
					"join player on player.ID = event_player.player_ID " +
					"where match_ID = " + id + " and team_ID = " + teamList.get(1).getID() + " " +
					"and event_type_ID in (3, 4, 8)");

			Integer score1 = Statistics.queryToInt(query1score);
			Integer score2 = Statistics.queryToInt(query2score);
			Integer passes1 = Statistics.queryToInt(query1passes);
			Integer passes2 = Statistics.queryToInt(query2passes);
			Integer penalties1 = Statistics.queryToInt(query1penalties);
			Integer penalties2 = Statistics.queryToInt(query2penalties);

			Statistics stats = new Statistics();

			stats.setTeam1(teamList.get(0));
			stats.setTeam2(teamList.get(1));
			stats.setScore1(score1);
			stats.setScore2(score2);
			stats.setPenalties1(penalties1);
			stats.setPenalties2(penalties2);
			stats.setPasses1(passes1);
			stats.setPasses2(passes2);
			stats.setDate(m.getDate());

			session.close();

			res.header("Content-Type", "application/json");
			String json = ow.writeValueAsString(stats);
			return json;
		});
	}
}
