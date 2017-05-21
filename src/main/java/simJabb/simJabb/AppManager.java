package simJabb.simJabb;

import java.time.ZonedDateTime;
import java.util.Collection;
import java.util.Scanner;

import org.jivesoftware.smack.AbstractXMPPConnection;
import org.jivesoftware.smack.ConnectionConfiguration.SecurityMode;
import org.jivesoftware.smack.SmackException.NoResponseException;
import org.jivesoftware.smack.SmackException.NotConnectedException;
import org.jivesoftware.smack.SmackException.NotLoggedInException;
import org.jivesoftware.smack.XMPPException.XMPPErrorException;
import org.jivesoftware.smack.chat2.Chat;
import org.jivesoftware.smack.chat2.ChatManager;
import org.jivesoftware.smack.chat2.IncomingChatMessageListener;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Presence;
import org.jivesoftware.smack.roster.Roster;
import org.jivesoftware.smack.roster.RosterListener;
import org.jivesoftware.smack.tcp.XMPPTCPConnection;
import org.jivesoftware.smack.tcp.XMPPTCPConnectionConfiguration;
import org.jxmpp.jid.EntityBareJid;
import org.jxmpp.jid.Jid;
import org.jxmpp.jid.impl.JidCreate;
import org.jxmpp.stringprep.XmppStringprepException;

public class AppManager {

	private static UserManager userManager;
	private XMPPTCPConnectionConfiguration config;
	private AbstractXMPPConnection connection;
	private MessagePrinter messagePrinter;
	private Scanner scanner;
	private static boolean loggedIn;
	private Roster roster;
	private static ChatManager chatManager;
	private EntityBareJid sendJid;
	private Chat chat;

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
		loggedIn = true;
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

	public void addRoster() {
		roster = Roster.getInstanceFor(connection);
		roster.addRosterListener(new RosterListener() {

			public void presenceChanged(Presence presence) {
				System.out.println("(" + ZonedDateTime.now() + "): " + presence);

			}

			public void entriesUpdated(Collection<Jid> arg0) {
				// TODO Auto-generated method stub

			}

			public void entriesDeleted(Collection<Jid> arg0) {
				// TODO Auto-generated method stub

			}

			public void entriesAdded(Collection<Jid> arg0) {
				// TODO Auto-generated method stub

			}
		});

	}

	public void addToRoster() {
		EntityBareJid JID;
		try {
			JID = JidCreate.entityBareFrom(messagePrinter.addToRoster());
			roster.createEntry(JID, JID.toString(), null);
			System.out.println("Contact added");
		} catch (XmppStringprepException | NotLoggedInException | NoResponseException | XMPPErrorException | NotConnectedException | InterruptedException e) {
			// TODO Auto-generated catch block
			System.out.println("Unable to add to roster");
		}
	}

	public void startChonversation(Chat chat) {
		String newMessage;
		while (connection.isConnected()) {
			newMessage = messagePrinter.scannMessage();
			if (newMessage.equals("/disconnect")) {
				connection.disconnect();
				System.out.println("Now you are disconnected");
			} else {
				try {
					chat.send(newMessage);
				} catch (NotConnectedException e) {
					e.printStackTrace();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	public void setUpConnection() {

		try {
			sendJid = JidCreate.entityBareFrom(messagePrinter.connectTo());
		} catch (XmppStringprepException e) {

			e.printStackTrace();
		}

		chatManager = ChatManager.getInstanceFor(connection);
		chat = chatManager.chatWith(sendJid);
		System.out.println("now you can start to chat");

		chatManager.addIncomingListener(new IncomingChatMessageListener() {

			public void newIncomingMessage(EntityBareJid from, Message message, Chat chat) {
				messagePrinter.printMessage(message);

			}
		});

	}

	public AbstractXMPPConnection getConnection() {
		return connection;
	}

	public Roster getRoster() {
		return roster;
	}

	public MessagePrinter getMessagePrinter() {
		return messagePrinter;
	}

	public boolean isLoggedIn() {
		return loggedIn;
	}

	public Chat getChat() {
		return chat;
	}
}