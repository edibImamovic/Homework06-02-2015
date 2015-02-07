package ba.bitcamp.edibi.homework.server;

import java.io.File;
import java.io.IOException;

import javax.swing.JOptionPane;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

/**
 * 
 * @author LabWork at BITCamp
 * @version Beta made for UNIX systems , Edib Imamovic add: commentaries and
 *          login messages
 * 
 *
 */
public class XmlConnection {

	private static Document xmlDoc;
	private static DocumentBuilder docreader;
	private static XPath xPath;

	/**
	 * 
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 * @throws IOException
	 */
	public XmlConnection() throws ParserConfigurationException, SAXException,
			IOException {

		docreader = DocumentBuilderFactory.newInstance().newDocumentBuilder();
		xmlDoc = docreader.parse(new File("UserDatabase.xml"));

		xPath = XPathFactory.newInstance().newXPath();

	}

	/**
	 * 
	 * @param username
	 * @param password
	 * @return
	 * @throws TransformerConfigurationException
	 */

	public static int userLogin(String username, String password)
			throws TransformerConfigurationException {

		String expresion = "//User[@name = \"" + username
				+ "\" and @password =\"" + password + "\"]";
		System.out.println(expresion);
		try {
			Node user = (Node) xPath.compile(expresion).evaluate(xmlDoc,
					XPathConstants.NODE);
			if (user == null) {
				String expresion2 = "//User[@name = \"" + username + "\"]";
				Node user2 = (Node) xPath.compile(expresion2).evaluate(xmlDoc,
						XPathConstants.NODE);
				if (user2 == null) {

					Element newUser = xmlDoc.createElement("User");
					newUser.setAttribute("name", username);
					newUser.setAttribute("password", password);
					xmlDoc.getElementsByTagName("Users").item(0)
							.appendChild(newUser);
					StreamResult file = new StreamResult(new File(
							"./File/New.xml"));
					Transformer transformer;

					transformer = TransformerFactory.newInstance()
							.newTransformer();
					DOMSource source = new DOMSource(xmlDoc);
					transformer.transform(source, file);
					showMessage("You have successfully logged in");
					return 0;

				} else {
					showMessage("You have successfully created your account");
				}

			}

		} catch (XPathExpressionException
				| TransformerFactoryConfigurationError | TransformerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			showMessage("Please try again");
		}
		return 0;

	}

	/**
	 * Added for homework, Edib Imamovic.
	 * 
	 * @param message
	 */
	private static void showMessage(String message) {
		JOptionPane.showMessageDialog(null, message, "ERROR",
				JOptionPane.WARNING_MESSAGE);

	}

	/**
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			XmlConnection test = new XmlConnection();

			try {
				XmlConnection.userLogin("Edibi", "password");

			} catch (TransformerConfigurationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} catch (ParserConfigurationException | SAXException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
