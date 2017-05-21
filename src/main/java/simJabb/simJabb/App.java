package simJabb.simJabb;

/**
 * Main class of the messenger.
 *
 */
public class App {
	private static AppManager appManager;
	private static boolean running;

	public static void main(String[] args) {
		running = true;
		appManager = new AppManager();

		while (running == true) {
			appManager.login();
			appManager.startConnection();
			appManager.sendPresence();
			appManager.addRoster();
			while (running == true) {
				menuSwitch: switch (appManager.getMessagePrinter().menuTree()) {
				case "/roster": {
					rosterSwitch: switch (appManager.getMessagePrinter().rosterTree()) {
					case "/contacts": {
						appManager.getMessagePrinter().printRoster(appManager.getRoster());
						break rosterSwitch;
					}
					case "/add": {
						appManager.addToRoster();
						break rosterSwitch;
					}
					case "/back": {
						break rosterSwitch;
					}

					}
					break menuSwitch;
				}

				case "/chat": {
					appManager.setUpConnection();
					appManager.startChonversation(appManager.getChat());
					break menuSwitch;
				}
				}
			}

		}
	}
}
