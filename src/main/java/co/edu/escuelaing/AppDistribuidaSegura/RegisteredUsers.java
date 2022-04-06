package co.edu.escuelaing.AppDistribuidaSegura;

import java.util.ArrayList;
import java.util.List;

public class RegisteredUsers {

	private List<UserApp> users;
	
	public RegisteredUsers() {
		users = new ArrayList<UserApp>();
	}
	
	public void AddNewUser(UserApp user) {
		users.add(user);
	}
		
	public boolean IsRegistered(String uName, String uPasswd) {
		boolean isRegistered = false;
		for (UserApp user: users) {
			if (user.getName().equals(uName) && user.getPassword().equals(uPasswd)) {
				isRegistered = true;
			}
		}
		return isRegistered;
	}
}
