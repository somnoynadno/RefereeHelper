package refereehelper;

import static spark.Spark.*;
import refereehelper.Connector;


public class RestAPI {

	public static void main(String[] args) {
		Connector connector = new Connector();
		get("/hello", (req, res) -> "Hello World");
	}
}
