package refereehelper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hibernate.Session;
import org.hibernate.query.Query;
import refereehelper.models.Request;
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

		get("api/v1/request/", (req, res) -> {
			res.header("Content-Type", "application/json");

			Session session = HibernateUtil.getSessionFactory().openSession();
			session.beginTransaction();

			Query query = session.createQuery("from Request");
			List<Request> list = query.getResultList();

			ObjectMapper ow = new ObjectMapper();
			String json = ow.writeValueAsString(list);

			System.out.println(json);
			session.close();
			return json;
		});
	}
}
