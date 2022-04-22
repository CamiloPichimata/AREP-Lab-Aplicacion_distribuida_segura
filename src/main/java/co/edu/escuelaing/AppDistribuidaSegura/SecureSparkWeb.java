package co.edu.escuelaing.AppDistribuidaSegura;

import static spark.Spark.*;
import static co.edu.escuelaing.AppDistribuidaSegura.SecureURLReader.*;

public class SecureSparkWeb {
 
	public static void main(String[] args) {
		// Inicialización de usuarios registrados
		UserApp user1 = new UserApp("User1", "de7d0966dd91649474ecd891568e59977165c1d80a745a2e62599ec810b8c551");
		UserApp user2 = new UserApp("User2", "3a09990e3ab649f1c81c22d21c9a52f6f889632f41db9987e1d9c043626dd7d3");
		UserApp user3 = new UserApp("User3", "056f0753b4fa50c639811bd7500b2f3f5af973911a9da8e640f98769afa645f7");
		RegisteredUsers registeredUsers = new RegisteredUsers();
		registeredUsers.AddNewUser(user1);
		registeredUsers.AddNewUser(user2);
		registeredUsers.AddNewUser(user3);
		
		// Inicialización del servidor
		System.out.println("Inicializando Servidor...");
		// Estabaleciendo una conexión segura
		secure(getKeystore(), "123456", null, null);
		port(getPort());
		
		staticFiles.location("/");
		
		get("/Hello", (req, res)-> "Hello from a Secure Spark Web");
		
		get("/app", (req, res) -> {
			res.redirect("/index.html");
			res.status(200);
			return null;
		});
		
		get("/login", (req, res) -> {
			String uName = req.queryParams("uName");
			String uPasswd = req.queryParams("uPasswd");
			System.out.println("uName: " + uName + " - uPasswd: " + uPasswd);
			return registeredUsers.IsRegistered(uName, uPasswd);
		});

		get("getNotes", (req, res) -> {
			String responseURL = readURL("localhost:5001/getNotes");
			System.out.println("SECURE SPARK WEB\n    /getNotes: " + responseURL);
			return responseURL;
		});

		put("/putNotes", (req, res) -> {
			String body = req.body();
			System.out.println("SECURE SPARK WEB\n    /putNotes: " + body);
			String responseURL = putURL("localhost:5001/putNotes", body);
			System.out.println("---- responseURL: " + responseURL);
			return responseURL;
		});
	}
	
	static int getPort() {
		if (System.getenv("PORT") != null) {
			return Integer.parseInt(System.getenv("PORT"));
		}
		return 5000;
	}
	
	static String getKeystore() {
		if (System.getenv("KEYSTORE") != null) {
			return System.getenv("keystore");
		}
		return "keystores/loginkeystore.p12";
	}
}
