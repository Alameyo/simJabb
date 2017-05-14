package simJabb.simJabb;

import java.time.LocalDateTime;
import java.util.Scanner;

import org.jivesoftware.smack.packet.Message;
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
	
	public String connectTo(){
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

	public void talk(XMPPTCPConnection connection) {

	};

	public void printMessage(Message message) {
		System.out.println("(" + LocalDateTime.now().getHour() + ":" + LocalDateTime.now().getMinute() + ")"
				+ message.getFrom().asBareJid() + ": " + message.getBody());
	}

	public void sendMessage(String message) {

	}

}
