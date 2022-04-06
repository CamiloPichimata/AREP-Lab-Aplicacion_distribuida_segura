package co.edu.escuelaing.AppDistribuidaSegura;

public class UserApp {
	
	private String name;
	private String password;
	
	public UserApp(String name, String password) {
		this.name = name;
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public String getPassword() {
		return password;
	}
}
