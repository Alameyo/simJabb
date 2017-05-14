package simJabb.simJabb;

import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.SmackException.NotConnectedException;
import org.jivesoftware.smack.chat2.Chat;
import org.jivesoftware.smack.chat2.ChatManager;
import org.jivesoftware.smack.chat2.IncomingChatMessageListener;
import org.jivesoftware.smack.filter.AndFilter;
import org.jivesoftware.smack.filter.FromTypeFilter;
import org.jivesoftware.smack.filter.StanzaFilter;
import org.jivesoftware.smack.filter.StanzaTypeFilter;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.tcp.XMPPTCPConnectionConfiguration;
import org.jxmpp.jid.EntityBareJid;
import org.jxmpp.jid.impl.JidCreate;
import org.jxmpp.stringprep.XmppStringprepException;

/**
 * Hello world!
 *
 */
public class App {
	private static AppManager appManager;
	private static ChatManager chatManager;
	private static EntityBareJid sendJid;
	private static MessagePrinter messagePrinter;

	public static void main(String[] args) {

		appManager = new AppManager();
		appManager.login();
		appManager.startConnection();
		appManager.sendPresence();
		messagePrinter = appManager.getMessagePrinter();
		try {
			sendJid = JidCreate.entityBareFrom(appManager.getMessagePrinter().connectTo());
			chatManager = ChatManager.getInstanceFor(appManager.getConnection());
			Chat chat = chatManager.chatWith(sendJid);

			chatManager.addIncomingListener(new IncomingChatMessageListener() {

				public void newIncomingMessage(EntityBareJid from, Message message, Chat chat) {
					messagePrinter.printMessage(message);

				}
			});
			String newMessage;
			System.out.println("now you can start to chat");
			while (appManager.getConnection().isConnected()) {
				newMessage = messagePrinter.scannMessage();
				if (newMessage.equals("/disconnect")) {
					appManager.getConnection().disconnect();
					System.out.println("Now you are disconnected");
				} else {
					chat.send(newMessage);
				}
			}
		} catch (Exception e) {
			System.out.println("unable to connect or send message");
		}

	}
}
