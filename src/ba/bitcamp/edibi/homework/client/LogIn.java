package ba.bitcamp.edibi.homework.client;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import ba.bitcamp.edibi.homework.ChatGui.ChatGui;

/**
 * 
 * @author LabWork at BITCamp
 * @version Beta , Edib Imamovic add: commentaries.
 *
 */

public class LogIn {
	private JTextField textField;
	private JPasswordField pass;
	private String host;
	private int port;

	/**
	 * Constructor with host and port input attributes.
	 * 
	 * @param host
	 * @param port
	 */
	public LogIn(String host, int port) {
		this.host = host;
		this.port = port;
		JFrame window = new JFrame("Login");
		JPanel content = new JPanel();
		textField = new JTextField(20);
		JButton loginButton = new JButton("Login");
		JButton quitButton = new JButton("Quit");
		JLabel labelUser = new JLabel("Username");
		JLabel labelPass = new JLabel("Password");
		pass = new JPasswordField(20);

		window.add(content);
		content.add(labelUser);
		content.add(textField);
		content.add(labelPass);
		content.add(pass);
		content.add(loginButton);

		loginButton.addActionListener(new ButtonHandler());
		quitButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				System.exit(0); // on click shutdown app.
			}
		});

		content.add(quitButton);

		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setSize(300, 200);
		window.setVisible(true);

	}

	private void showError(String message) {
		JOptionPane.showMessageDialog(null, message, "ERROR",
				JOptionPane.WARNING_MESSAGE);
	}

	/**
	 * Private class for handling login button.
	 * 
	 * @author Edib Imamovic
	 *
	 */
	private class ButtonHandler extends KeyAdapter implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {

			String username = textField.getText();
			String password = new String(pass.getPassword());

			// if user input blank spaces remove it
			username = username.replaceAll(" ", "");
			password = password.replaceAll(" ", "");

			System.out.println(username);
			System.out.println(password);

			// if user do not input username or password show Error
			if (username.equals("") || password.equals("")) {
				showError("Enter you'r username and pasword");
				return;
			}

			Socket client;
			try {
				client = new Socket(host, port);
				OutputStream os = client.getOutputStream();
				InputStream is = client.getInputStream();
				os.write((username + "\n").getBytes());
				os.write((password + "\n").getBytes());

				int result = is.read();

				if (result == 0) {

					ChatGui gui = new ChatGui(client);
					new Thread(gui).start();
				} else {
					showError("Username and password are not correct");
				}
			} catch (IOException e1) {
				e1.printStackTrace();
			}

		}

	}
}
