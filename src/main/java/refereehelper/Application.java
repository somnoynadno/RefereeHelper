package refereehelper;

import static spark.Spark.*;

// import refereehelper.Connector;

public class Application {
	
	public static void main(String[] args) {
		staticFileLocation("/");
		
		// Connector connector = new Connector();
		
		get("/api/v1/hello", (req, res) -> "Hello World");
	}

	
}
