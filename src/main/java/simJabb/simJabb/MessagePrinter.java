package simJabb.simJabb;

import java.time.LocalDateTime;
import java.util.Scanner;
import java.util.Set;

import org.jivesoftware.smack.SmackException.NotConnectedException;
import org.jivesoftware.smack.SmackException.NotLoggedInException;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.roster.Roster;
import org.jivesoftware.smack.roster.RosterEntry;
import org.jivesoftware.smack.tcp.XMPPTCPConnection;

public class MessagePrinter {
	private Scanner scanner;

	public MessagePrinter(Scanner scanner) {
		this.scanner = scanner;
	}

	public String askForUsername() {
		System.out.println("give your username");
		String username = scanner.nextLine();
		return username;
	}

	public String connectTo() {
		System.out.println("provide address of user to who you want to send message");
		return scanner.nextLine();
	}

	public String askForPassword() {
		System.out.println("give your password");
		String passwd = scanner.nextLine();
		System.out.println("password entred");
		return passwd;
	}

	public String scannMessage() {

		String messageText = scanner.nextLine();
		return messageText;
	}

	public String menuTree() {
		System.out.println("what you want to do?");
		String decision = scanner.nextLine();

		return decision;
	}

	public void printRoster(Roster roster) {
		if (!roster.isLoaded())
			try {
				roster.reloadAndWait();

		Set<RosterEntry> entries = roster.getEntries();
		System.out.println("Roster:");
		for(RosterEntry entry:entries){
			System.out.println(entry);
		}
		System.out.println("end of the roster");
			} catch (NotLoggedInException | NotConnectedException | InterruptedException e) {
				System.out.println("Unable to get the roster");
				e.printStackTrace();
			}

	}
	
	public String addToRoster(){
		System.out.println("Provide JID of person which you want to add to roster");
		
		String JID = scanner.nextLine();
		return JID;
	}

	public String rosterTree() {
		System.out.println(
				"to show roster type - '/contacts' \n to add contact type - '/add' \n to go back to main menu type - '/back'");
		String decision = "";
		while (decision != "/contacts" || decision != "/add" || decision != "/back") {
			decision = scanner.nextLine();
		}
		return decision;
	}

	public void talk(XMPPTCPConnection connection) {

	};

	public void printMessage(Message message) {
		System.out.println("(" + LocalDateTime.now().getHour() + ":" + LocalDateTime.now().getMinute() + ")"
				+ message.getFrom().asBareJid() + ": " + message.getBody());
	}

	public void sendMessage(String message) {

	}

}
