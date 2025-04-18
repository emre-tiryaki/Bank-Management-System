package IntroductionPackage;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import HelpPackage.DBConnectionHelper;
import HelpPackage.RoundedButton;
import HelpPackage.TextFieldFocusListener;
import HelpPackage.TextFieldMouseListener;
import HelpPackage.Constants.dimensions;
import HelpPackage.Constants.icons;

public class ForgotPassword extends JFrame{

	private JPanel mainNamePanel;
	private JLabel mainNameLabel;
	private JPanel informationEntryPanel;
	private JTextField usernameTextField;
	private JTextField IdTextField;
	private JPasswordField newPasswordTextField;
	private JPanel buttonsPanel;
	private RoundedButton changeButton;
	private RoundedButton cancelButton;
	DBConnectionHelper helper;
	Connection connection;
	
	public ForgotPassword() {
		this.setTitle("Login");
		this.setSize(dimensions.NormalFrameDimension);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setIconImage(icons.id_cardIcon.getImage());
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLayout(new BorderLayout());
		
		mainNamePanel = new JPanel();
		mainNamePanel.setSize(new Dimension(dimensions.NormalFrameDimension.width, dimensions.NormalFrameDimension.height/5));
		//mainNamePanel.setBackground(new Color(194, 193, 192));
		
		mainNameLabel = new JLabel();
		mainNameLabel.setText("Tiryaki Bank");
		mainNameLabel.setIcon(icons.bankIcon);
		mainNameLabel.setIconTextGap(20);
		mainNameLabel.setFont(new Font("Roboto",Font.ITALIC,50));
		
		mainNamePanel.add(mainNameLabel);
		
		informationEntryPanel = new JPanel();
		informationEntryPanel.setSize(new Dimension(dimensions.NormalFrameDimension.width, dimensions.NormalFrameDimension.height*3/5));
		informationEntryPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 0 ,25));
		//informationEntryPanel.setBackground(Color.black);
		
		usernameTextField = new JTextField();
		usernameTextField.setPreferredSize(new Dimension(dimensions.NormalFrameDimension.width*7/10,50));
		usernameTextField.setText("username");
		usernameTextField.setFocusable(false);
		usernameTextField.setFont(new Font("",Font.PLAIN,20));
		usernameTextField.addFocusListener(new TextFieldFocusListener("username"));
		usernameTextField.addMouseListener(new TextFieldMouseListener());
		
		IdTextField = new JTextField();
		IdTextField.setPreferredSize(new Dimension(dimensions.NormalFrameDimension.width*7/10,50));
		IdTextField.setText("id");
		IdTextField.setFocusable(false);
		IdTextField.setFont(new Font("",Font.PLAIN,20));
		IdTextField.addFocusListener(new TextFieldFocusListener("id"));
		IdTextField.addMouseListener(new TextFieldMouseListener());
		
		newPasswordTextField = new JPasswordField();
		newPasswordTextField.setPreferredSize(new Dimension(dimensions.NormalFrameDimension.width*7/10, 50));
		newPasswordTextField.setText("new password");
		newPasswordTextField.setEchoChar('*');
		newPasswordTextField.setFocusable(false);
		newPasswordTextField.setFont(new Font("",Font.PLAIN,20));
		newPasswordTextField.addFocusListener(new TextFieldFocusListener("new password"));
		newPasswordTextField.addMouseListener(new TextFieldMouseListener());		
		
		buttonsPanel = new JPanel();
		buttonsPanel.setPreferredSize(new Dimension(dimensions.NormalFrameDimension.width, dimensions.NormalFrameDimension.height/6));
		buttonsPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 30));
		//buttonsPanel.setBackground(Color.BLUE);
		
		changeButton = new RoundedButton("CHANGE");
		changeButton.setPreferredSize(new Dimension(100, 40));
		changeButton.setBorder(null);
		changeButton.setFocusable(false);
		changeButton.addActionListener(new ActionListener() {	
			@Override
			public void actionPerformed(ActionEvent e) {
				if(e.getSource() == changeButton) {
					
					if(JOptionPane.showConfirmDialog(null, "Are you sure to change your password", "Warning", JOptionPane.YES_NO_OPTION,JOptionPane.WARNING_MESSAGE) == 0) {
						helper = new DBConnectionHelper();
						String fullName = usernameTextField.getText().trim();
						int id = Integer.parseInt(IdTextField.getText().trim());
						int newPassword = String.valueOf(newPasswordTextField.getPassword()).trim().hashCode();
						try {
							connection = helper.getConnection();
							Statement statement = connection.createStatement();
							ResultSet resultSet = statement.executeQuery("SELECT * FROM customers WHERE full_name = '"+fullName+"' and id = "+id+";");
							if(resultSet.next()) {
								PreparedStatement preStatement = connection.prepareStatement("UPDATE customers SET password = " + newPassword + " where full_name = '" + fullName + "' and id = " + id + ";");
								preStatement.executeUpdate();
								JOptionPane.showConfirmDialog(null, "password has changed!!", "Successful", JOptionPane.YES_NO_OPTION,JOptionPane.INFORMATION_MESSAGE);
								connection.close();
								new Login();
								dispose();
							}
							else
								System.out.println("hatali input");
						} catch (SQLException e1) {
							e1.printStackTrace();
						}
					}
				}
			}
		});
		
		cancelButton = new RoundedButton("CANCEL");
		cancelButton.setPreferredSize(new Dimension(100, 40));
		cancelButton.setBorder(null);
		cancelButton.setFocusable(false);
		cancelButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new Login();
				dispose();
			}
		});
		
		buttonsPanel.add(changeButton);
		buttonsPanel.add(cancelButton);
		
		informationEntryPanel.add(usernameTextField);
		informationEntryPanel.add(IdTextField);
		informationEntryPanel.add(newPasswordTextField);
		
		this.add(mainNamePanel, BorderLayout.NORTH);
		this.add(informationEntryPanel, BorderLayout.CENTER);
		this.add(buttonsPanel, BorderLayout.SOUTH);
		
		this.setVisible(true);
	}
	
}
