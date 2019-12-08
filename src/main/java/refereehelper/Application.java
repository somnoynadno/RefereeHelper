package refereehelper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hibernate.Session;
import org.hibernate.query.Query;
import refereehelper.models.*;
import refereehelper.utils.HibernateUtil;

import java.util.Date;
import java.util.List;
import java.util.Set;

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
			}
			catch (JsonProcessingException e) {
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
			}
			catch (JsonProcessingException e) {
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
			}
			catch (JsonProcessingException e) {
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
			}
			catch (JsonProcessingException e) {
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
			}
			catch (JsonProcessingException e) {
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

			return "Nice";
		});

		// POST method doesn't work the way that I want it to work
		// so it is GET
		get("/api/v1/match/add_event/", (req, res) -> {
			Integer id = Integer.parseInt(req.queryMap().value("id"));
			Integer player1ID = Integer.parseInt(req.queryMap().value("player1ID"));
			Integer player2ID = Integer.parseInt(req.queryMap().value("player2ID"));
			Integer eventTypeID = Integer.parseInt(req.queryMap().value("eventTypeID"));
			Integer time = Integer.parseInt(req.queryMap().value("time"));

			System.out.println(id);
			System.out.println(time);
			System.out.println(player1ID);
			System.out.println(player2ID);
			System.out.println(eventTypeID);

			return "Event added";
		});
	}

}
