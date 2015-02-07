package ba.bitcamp.edibi.homework.server;

import java.util.LinkedList;
import java.util.Queue;

/**
 * 
 * @author LabWork at BITCamp
 * @version Beta . Edib Imamovic add: commentaries,
 *
 */
public class Message {

	private String content;
	private String sender;
	private static volatile Queue<Message> msgQueue = new LinkedList();

	/**
	 * @param content
	 * @param sender
	 */
	public Message(String content, String sender) {
		this.content = content + "\n";
		this.sender = sender;
		msgQueue.add(this);
	}

	/**
	 * 
	 * @return
	 */
	public static boolean hasNext() {
		return !msgQueue.isEmpty();

	}

	/**
	 * 
	 * @return
	 */
	public static Message next() {
		return msgQueue.poll();

	}

	/**
	 * @return the sender
	 */
	public String getSender() {
		return sender;
	}

	/**
	 * @param sender
	 *            the sender to set
	 */
	public void setSender(String sender) {
		this.sender = sender;
	}

	/**
	 * @return the content
	 */
	public String getContent() {
		return content;
	}

	/**
	 * @param content
	 *            the content to set
	 */
	public void setContent(String content) {
		this.content = content;
	}

}
