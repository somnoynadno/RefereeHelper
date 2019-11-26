package refereehelper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hibernate.Session;
import org.hibernate.query.Query;
import refereehelper.models.GameType;
import refereehelper.models.Request;
import refereehelper.models.Team;
import refereehelper.utils.HibernateUtil;

import java.util.List;

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
			session.close();
			try {
				res.header("Content-Type", "application/json");

				String json = ow.writeValueAsString(team);
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

		get("api/v1/team/", (req, res) -> {
			res.header("Content-Type", "application/json");

			Session session = HibernateUtil.getSessionFactory().openSession();
			session.beginTransaction();

			Query query = session.createQuery("from Team");
			List<Request> list = query.getResultList();

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
			List<Request> list = query.getResultList();

			ObjectMapper ow = new ObjectMapper();
			String json = ow.writeValueAsString(list);

			System.out.println(json);
			session.close();
			return json;
		});
	}

}
