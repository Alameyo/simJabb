package simJabb.simJabb;

/**
 * Main class of the messenger.
 *
 */
public class App {
	private static AppManager appManager;
	public static void main(String[] args) {

		appManager = new AppManager();

		appManager.login();
		appManager.isLoggedIn();
		appManager.startConnection();
		appManager.sendPresence();
		appManager.getMessagePrinter();
		appManager.addRoster();
		appManager.setUpConnection();
		appManager.startChonversation(appManager.getChat());

			System.out.println("unable to connect or send message");
		

	}
}
