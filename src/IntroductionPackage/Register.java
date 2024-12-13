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

import com.mysql.cj.exceptions.DataTruncationException;

import HelpPackage.Constants.dimensions;
import HelpPackage.Constants.icons;
import HelpPackage.DBConnectionHelper;
import HelpPackage.RoundedButton;
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
	private RoundedButton registerButton;
	private RoundedButton backToLoginButton;
	DBConnectionHelper helper;
	Connection connection;
	
	Register() {
		this.setTitle("Zelom Bank");
		this.setSize(dimensions.bigFrameDimension);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setIconImage(icons.bankIcon.getImage());
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLayout(new BorderLayout());
		
		mainNamePanel = new JPanel();
		mainNamePanel.setPreferredSize(new Dimension(dimensions.bigFrameDimension.width, dimensions.bigFrameDimension.height/5));
		//mainNamePanel.setBackground(new Color(194, 193, 192));
		
		mainNameLabel = new JLabel();
		mainNameLabel.setText("Zelom Bank");
		mainNameLabel.setIcon(icons.bankIcon);
		mainNameLabel.setIconTextGap(50);
		mainNameLabel.setFont(new Font("Roboto",Font.ITALIC,80));
		
		mainNamePanel.add(mainNameLabel);
		
		textfieldPanel = new JPanel();
		textfieldPanel.setPreferredSize(new Dimension(dimensions.bigFrameDimension.width, dimensions.bigFrameDimension.height*3/5));
		textfieldPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 50, 40));
		//textfieldPanel.setBackground(Color.orange);
		
		nameTextField = new  JTextField();
		nameTextField.setPreferredSize(new Dimension(dimensions.bigFrameDimension.width*35/100, 60));
		nameTextField.setFont(new Font("",Font.PLAIN, 25));
		nameTextField.setText("name");
		nameTextField.setFocusable(false);
		nameTextField.addMouseListener(new TextFieldMouseListener());
		nameTextField.addFocusListener(new TextFieldFocusListener("name"));
		
		surnameTextField = new  JTextField();
		surnameTextField.setPreferredSize(new Dimension(dimensions.bigFrameDimension.width*35/100, 60));
		surnameTextField.setFont(new Font("",Font.PLAIN, 25));
		surnameTextField.setText("surname");
		surnameTextField.setFocusable(false);
		surnameTextField.addMouseListener(new TextFieldMouseListener());
		surnameTextField.addFocusListener(new TextFieldFocusListener("surname"));
		
		DateOfBirthTextField = new  JTextField();
		DateOfBirthTextField.setPreferredSize(new Dimension(dimensions.bigFrameDimension.width*8/10, 60));
		DateOfBirthTextField.setFont(new Font("",Font.PLAIN, 25));
		DateOfBirthTextField.setText("dateOfBirth");
		DateOfBirthTextField.setFocusable(false);
		DateOfBirthTextField.addMouseListener(new TextFieldMouseListener());
		DateOfBirthTextField.addFocusListener(new TextFieldFocusListener("dateOfBirth"));
		
		passwordTextField = new  JPasswordField();
		passwordTextField.setPreferredSize(new Dimension(dimensions.bigFrameDimension.width*8/10, 60));
		passwordTextField.setFont(new Font("",Font.PLAIN, 25));
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
		buttonPanel.setPreferredSize(new Dimension(dimensions.bigFrameDimension.width,dimensions.bigFrameDimension.height/5));
		buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 40));
		//buttonPanel.setBackground(Color.pink);
		
		registerButton = new RoundedButton("REGISTER");
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
						ResultSet resultSet = statement.executeQuery("SELECT MAX(id) FROM customers;");
						if(resultSet.next())
							id = resultSet.getInt(1) + 1;
						else
							id = 0;
						PreparedStatement preStatement = connection.prepareStatement("insert into customers(full_name, password, DateOfBirth, id) values ('" + fullName + "',"+pass+",'"+DOB+"', "+id+");");
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
		
		backToLoginButton = new RoundedButton("BACK TO LOGIN");
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
