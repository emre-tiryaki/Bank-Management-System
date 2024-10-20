package IntroductionPackage;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import com.mysql.cj.exceptions.DataTruncationException;

import HelpPackage.Constants.icons;
import HelpPackage.DBConnectionHelper;
import HelpPackage.TextFieldFocusListener;
import HelpPackage.TextFieldMouseListener;

public class Register extends JFrame{
	
	private JPanel mainNamePanel;
	private JLabel mainNameLabel;
	private JPanel textfieldPanel;
	private JTextField nameTextField;
	private JTextField surnameTextField;
	private JTextField DateOfBirthTextField;
	private JPasswordField passwordTextField;
	private JPanel buttonPanel;
	private JButton registerButton;
	private JButton backToLoginButton;
	DBConnectionHelper helper;
	Connection connection;
	
	Register() {
		this.setTitle("Register");
		this.setSize(700, 800);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setIconImage(icons.id_cardIcon.getImage());
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLayout(new BorderLayout());
		
		mainNamePanel = new JPanel();
		mainNamePanel.setSize(new Dimension(700, 200));
		mainNamePanel.setBackground(new Color(194, 193, 192));
		
		mainNameLabel = new JLabel();
		mainNameLabel.setText("Zelom Bank");
		mainNameLabel.setIcon(icons.bankIcon);
		mainNameLabel.setIconTextGap(50);
		mainNameLabel.setFont(new Font("Roboto",Font.ITALIC,80));
		
		mainNamePanel.add(mainNameLabel);
		
		textfieldPanel = new JPanel();
		textfieldPanel.setSize(new Dimension(700, 400));
		textfieldPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 60));
		//textfieldPanel.setBackground(Color.orange);
		
		nameTextField = new  JTextField();
		nameTextField.setPreferredSize(new Dimension(600, 50));
		nameTextField.setText("name");
		nameTextField.setFocusable(false);
		nameTextField.addMouseListener(new TextFieldMouseListener());
		nameTextField.addFocusListener(new TextFieldFocusListener("name"));
		
		surnameTextField = new  JTextField();
		surnameTextField.setPreferredSize(new Dimension(600, 50));
		surnameTextField.setText("surname");
		surnameTextField.setFocusable(false);
		surnameTextField.addMouseListener(new TextFieldMouseListener());
		surnameTextField.addFocusListener(new TextFieldFocusListener("surname"));
		
		DateOfBirthTextField = new  JTextField();
		DateOfBirthTextField.setPreferredSize(new Dimension(600, 50));
		DateOfBirthTextField.setText("dateOfBirth");
		DateOfBirthTextField.setFocusable(false);
		DateOfBirthTextField.addMouseListener(new TextFieldMouseListener());
		DateOfBirthTextField.addFocusListener(new TextFieldFocusListener("dateOfBirth"));
		
		passwordTextField = new  JPasswordField();
		passwordTextField.setPreferredSize(new Dimension(600, 50));
		passwordTextField.setText("password");
		passwordTextField.setEchoChar('*');
		passwordTextField.setFocusable(false);
		passwordTextField.addMouseListener(new TextFieldMouseListener());
		passwordTextField.addFocusListener(new TextFieldFocusListener("password"));
		
		textfieldPanel.add(nameTextField);
		textfieldPanel.add(surnameTextField);
		textfieldPanel.add(DateOfBirthTextField);
		textfieldPanel.add(passwordTextField);
		
		buttonPanel = new JPanel();
		buttonPanel.setSize(new Dimension(700,500));
		buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 10));
		//buttonPanel.setBackground(Color.pink);
		
		registerButton = new JButton();
		registerButton.setText("REGISTER");
		registerButton.setBorder(null);
		registerButton.setPreferredSize(new Dimension(100, 40));
		registerButton.setFocusable(false);
		registerButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String fullName = nameTextField.getText().trim() + " " + surnameTextField.getText().trim();
				int pass = String.valueOf(passwordTextField.getPassword()).trim().hashCode();
				String DOB = DateOfBirthTextField.getText().trim();
				int id;
				if(e.getSource() == registerButton) {
					helper = new DBConnectionHelper();
					try {
						connection = helper.getConnection();
						System.out.println("veritabanı bağlantısı başarılı!!!");
						Statement statement = connection.createStatement();
						ResultSet resultSet = statement.executeQuery("SELECT MAX(id) FROM your-initial-database-table-name;");
						if(resultSet.next())
							id = resultSet.getInt(1) + 1;
						else
							id = 0;
						PreparedStatement preStatement = connection.prepareStatement("insert into your-initial-database-table-name(full_name, password, DateOfBirth, id) values ('" + fullName + "',"+pass+",'"+DOB+"', "+id+");");
						preStatement.executeUpdate();
						JOptionPane.showConfirmDialog(null, "Your id is: " + id, "Warning", JOptionPane.YES_NO_OPTION,JOptionPane.INFORMATION_MESSAGE);
						connection.close();
							new Login();
							dispose();
					} catch(DataTruncationException e2) {
						System.out.println("veri çok uzun\n" + e2.getMessage());
					} catch (SQLException exception) {
						System.out.println("Kayıt olurken veritabanına bağlantıda hata yaşandı!!!!\n"+exception.getMessage());
					}				
				}
			}
		});
		
		backToLoginButton = new JButton();
		backToLoginButton.setText("BACK TO LOGIN");
		backToLoginButton.setBorder(null);
		backToLoginButton.setPreferredSize(new Dimension(100, 40));
		backToLoginButton.setFocusable(false);
		backToLoginButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				new Login();
				dispose();
			}
		});
		
		buttonPanel.add(registerButton);
		buttonPanel.add(backToLoginButton);
		
		this.add(mainNamePanel, BorderLayout.NORTH);
		this.add(textfieldPanel,BorderLayout.CENTER);
		this.add(buttonPanel, BorderLayout.SOUTH);
		
		this.setVisible(true);
	}
}
