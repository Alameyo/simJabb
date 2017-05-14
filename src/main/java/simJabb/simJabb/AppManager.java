package simJabb.simJabb;

import java.util.Scanner;

import org.jivesoftware.smack.AbstractXMPPConnection;
import org.jivesoftware.smack.ConnectionConfiguration.SecurityMode;
import org.jivesoftware.smack.SmackException.NotConnectedException;
import org.jivesoftware.smack.packet.Presence;
import org.jivesoftware.smack.tcp.XMPPTCPConnection;
import org.jivesoftware.smack.tcp.XMPPTCPConnectionConfiguration;

public class AppManager {

	private UserManager userManager;
	private XMPPTCPConnectionConfiguration config;
	private AbstractXMPPConnection connection;
	private MessagePrinter messagePrinter;
	private Scanner scanner;

	// SecurityMode securityMode;
	AppManager() {

		scanner = new Scanner(System.in);
		messagePrinter = new MessagePrinter(scanner);
	}

	public void login() {
		// TODO Auto-generated method stub
		userManager = new UserManager();
		userManager.getUser().setJID(messagePrinter.askForUsername());
		userManager.getUser().setPasswd(messagePrinter.askForPassword());
	}


	// "losalamos.alamos.im"
	public void startConnection() {
		try {
			System.out.println("config");
			// securityMode= new SecurityMode();
			config = XMPPTCPConnectionConfiguration.builder().setSecurityMode(SecurityMode.disabled)
					.setDebuggerEnabled(true).setXmppDomain("localhost").setHost("127.0.0.1").setPort(5222)
					.setUsernameAndPassword(userManager.getUser().getJID(), userManager.getUser().getPasswd())
					.setXmppDomain("losalamos.im").setPort(5222).build();

			System.out.println("connection config prepared");
			connection = new XMPPTCPConnection(config);
			connection.connect();
			System.out.println("connection estabilished");
			connection.login();
			System.out.println("logged in");

		} catch (Exception e) {
			System.out.println("Connection couldn't be established.");
		}

	}

	public void sendPresence() {
		// Create a new presence. Pass in false to indicate we're unavailable._
		Presence presence = new Presence(Presence.Type.available);
		presence.setStatus("Yarh");
		try {
			connection.sendStanza(presence);
		} catch (NotConnectedException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public AbstractXMPPConnection getConnection() {
		return connection;
	}
	
	public MessagePrinter getMessagePrinter() {
		return messagePrinter;
	}

	}

