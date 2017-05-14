package simJabb.simJabb;

public class UserManager {
private User user;
	
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	UserManager(){
		user = new User();
	}
}
