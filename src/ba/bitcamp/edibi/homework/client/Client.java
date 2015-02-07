package ba.bitcamp.edibi.homework.client;

/**
 * 
 * @author LabWork at BITCamp
 * @version Beta , Edib Imamovic add: commentaries.
 *
 */
public class Client {
	public static final int port = 1717;
	public static final String host = "localhost";

	/**
	 * Main for test purposes
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		@SuppressWarnings("unused")
		LogIn log = new LogIn(host, port);
	}
}