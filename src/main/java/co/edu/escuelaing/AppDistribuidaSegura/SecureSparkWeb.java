package co.edu.escuelaing.AppDistribuidaSegura;

import static spark.Spark.*; 

public class SecureSparkWeb {
 
	public static void main(String[] args) {
		// Inicialización de usuarios registrados
		UserApp user1 = new UserApp("User1", "passwdu1");
		UserApp user2 = new UserApp("User2", "....");
		UserApp user3 = new UserApp("User3", "....");
		RegisteredUsers registeredUsers = new RegisteredUsers();
		registeredUsers.AddNewUser(user1);
		registeredUsers.AddNewUser(user2);
		registeredUsers.AddNewUser(user3);
		
		// Inicializar servidor
		System.out.println("Inicializando Servidor...");
		//secure(getKeystore(), "123456", null, null);
		port(getPort());
		
		staticFiles.location("/");
		get("/app", (req, res) -> {
			res.redirect("/index.html");
			res.status(200);
			return null;
		});
		
		get("/login", (req, res) -> {
			String uName = req.queryParams("uName");
			String uPasswd = req.queryParams("uPasswd");
			System.out.println("uName: " + uName + "uPasswd: " + uPasswd);
			return registeredUsers.IsRegistered(uName, uPasswd);
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
		return "keystores/---.p12";
	}
}
