package ba.bitcamp.edibi.homework.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerConfigurationException;

import org.xml.sax.SAXException;

public class Server {

	public static final int port = 1717;

	/**
	 * 
	 * @throws IOException
	 * @throws TransformerConfigurationException
	 */
	public static void serverStart() throws IOException,
			TransformerConfigurationException {

		@SuppressWarnings("resource")
		ServerSocket server = new ServerSocket(port);
		ConnectionWriter cw = new ConnectionWriter();
		cw.start();

		while (true) {
			String str = "waiting for connection";
			System.out.println(str);
			Socket client = server.accept();
			String clientName = handShake(client.getInputStream());
			System.out.println(clientName);
			if (clientName != null) {
				while (ConnectionWriter.connections.containsKey(clientName)) {
					clientName += new Random().nextInt(1000);
				}
				ConnectionWriter.connections.put(clientName,
						client.getOutputStream());
				ConnectionListener cl = new ConnectionListener(
						client.getInputStream(), clientName);
				cl.start();
				new Message("join%" + clientName, "%server%");
				client.getOutputStream().write(0);
			} else {
				client.getOutputStream().write(-1);
			}

		}
		// ConnectionWriter.connections.remove(sender);
	}

	/**
	 * Main for: server start and creating new Xml Connection
	 * 
	 * @param args
	 */
	public static void main(String[] args) {

		try {
			new XmlConnection();

		} catch (ParserConfigurationException e1) {

			e1.printStackTrace();
		} catch (SAXException e1) {

			e1.printStackTrace();
		} catch (IOException e1) {

			e1.printStackTrace();
		}

		try {
			serverStart();
		} catch (IOException e) {

			e.printStackTrace();
		} catch (TransformerConfigurationException e) {

			e.printStackTrace();
		}
	}

	/**
	 * 
	 * @param is
	 *            input stream
	 * @return handshake
	 * @throws IOException
	 * @throws TransformerConfigurationException
	 */
	private static String handShake(InputStream is) throws IOException,
			TransformerConfigurationException {

		BufferedReader br = new BufferedReader(new InputStreamReader(is));

		String str = br.readLine();
		str = str.replaceAll("%", " ");
		String password = br.readLine();
		int resultat = XmlConnection.userLogin(str, password);
		if (resultat != 0) {
			return null;
		}
		return str;

	}
}