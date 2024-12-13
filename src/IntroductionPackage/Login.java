package IntroductionPackage;

import java.awt.Dimension;
import java.awt.FlowLayout;

import HelpPackage.Constants.icons;
import HelpPackage.Constants.dimensions;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Statement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

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
import MainPackage.MainAccount;

public class Login extends JFrame{
	
	private JPanel mainNamePanel;
	private JLabel mainNameLabel;
	private JPanel informationEntryPanel;
	private JTextField idTextField;
	private JPasswordField passwordTextField;
	private RoundedButton loginButton;
	private RoundedButton registerButton;
	private RoundedButton forgotPasswordButton;
	DBConnectionHelper helper;
	Connection connection;
	
	public Login() {
		this.setTitle("Zelom Bank");
		this.setSize(dimensions.NormalFrameDimension);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setIconImage(icons.bankIcon.getImage());
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
		
		mainNamePanel = new JPanel();
		mainNamePanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		mainNamePanel.setPreferredSize(new Dimension(dimensions.NormalFrameDimension.width, dimensions.NormalFrameDimension.height/5));
		//mainNamePanel.setBackground(new Color(194, 193, 192));
		
		mainNameLabel = new JLabel();
		mainNameLabel.setText("Zelom Bank");
		mainNameLabel.setIcon(icons.bankIcon);
		mainNameLabel.setIconTextGap(10);
		mainNameLabel.setFont(new Font("Roboto",Font.ITALIC,50));
		
		mainNamePanel.add(mainNameLabel);
		
		informationEntryPanel = new JPanel();
		informationEntryPanel.setPreferredSize(new Dimension(dimensions.NormalFrameDimension.width, (dimensions.NormalFrameDimension.height*4)/5));
		informationEntryPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 30));
		//informationEntryPanel.setBackground(Color.red);
		
		idTextField= new JTextField();
		idTextField.setText("id");
		idTextField.setPreferredSize(new Dimension((dimensions.NormalFrameDimension.width*70)/100, 50));
		idTextField.setFont(new Font("",Font.PLAIN, 25));
		idTextField.setFocusable(false);
		idTextField.addMouseListener(new TextFieldMouseListener());
		idTextField.addFocusListener(new TextFieldFocusListener("id"));
		
		passwordTextField = new JPasswordField();
		passwordTextField.setEchoChar('*');
		passwordTextField.setText("password");
		passwordTextField.setPreferredSize(new Dimension((dimensions.NormalFrameDimension.width*70)/100, 50));
		passwordTextField.setFont(new Font("",Font.PLAIN, 25));
		passwordTextField.setFocusable(false);
		passwordTextField.addMouseListener(new TextFieldMouseListener());
		passwordTextField.addFocusListener(new TextFieldFocusListener("password"));
		
		loginButton = new RoundedButton("LOGIN");
		loginButton.setPreferredSize(new Dimension(100, 40));
		loginButton.setBorder(null);
		loginButton.setFocusable(false);
		loginButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(e.getSource() == loginButton) {
					if(idTextField.getText().isEmpty())
						JOptionPane.showConfirmDialog(null, "You should enter an id", "Warning", JOptionPane.YES_NO_OPTION,JOptionPane.WARNING_MESSAGE);
					else if(String.valueOf(passwordTextField.getPassword()).isEmpty())
						JOptionPane.showConfirmDialog(null, "You should enter a password", "Warning", JOptionPane.YES_NO_OPTION,JOptionPane.WARNING_MESSAGE);
					else {
						String id = idTextField.getText().trim();
						int pass = String.valueOf(passwordTextField.getPassword()).trim().hashCode();
						Connection connection = null;
						DBConnectionHelper helper = new DBConnectionHelper();
						Statement statement = null;
						ResultSet resultSet;
						
						try {
							loginButton.setEnabled(false);
							connection = helper.getConnection();
							System.out.println("Veritabanına bağlanıldı!!");
							statement = connection.createStatement();
							resultSet = statement.executeQuery("SELECT * FROM customers WHERE id = '" + id + "' AND password = " + pass + ";");
							if (resultSet.next()) {
							    new MainAccount(resultSet);
							    dispose();
							} else {
								loginButton.setEnabled(true);
								JOptionPane.showConfirmDialog(null, "Login failed. Check the information you entered.", "Warning", JOptionPane.YES_NO_OPTION,JOptionPane.WARNING_MESSAGE);
							}
						} catch (SQLException e1) {
							System.out.println("giriş yaparken bağlantı hatası!!!\n" + e1.getMessage());
						}
					}
				}
			}
		});
		
		registerButton = new RoundedButton("REGISTER");
		registerButton.setPreferredSize(new Dimension(100, 40));
		registerButton.setFocusable(false);
		registerButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new Register();
				dispose();
			}
		});
		
		forgotPasswordButton = new RoundedButton("FORGOT PASSWORD");
		forgotPasswordButton.setBorder(null);
		forgotPasswordButton.setPreferredSize(new Dimension(150, 40));
		forgotPasswordButton.setFocusable(false);
		forgotPasswordButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new ForgotPassword();
				dispose();
			}
		});
		
		informationEntryPanel.add(idTextField);
		informationEntryPanel.add(passwordTextField);
		informationEntryPanel.add(loginButton);
		informationEntryPanel.add(registerButton);
		informationEntryPanel.add(forgotPasswordButton);
		
		this.add(mainNamePanel);
		this.add(informationEntryPanel);
		
		//this.pack();
		this.setVisible(true);
	}
}
