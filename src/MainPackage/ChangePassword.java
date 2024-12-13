package MainPackage;

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

import HelpPackage.Constants.dimensions;
import HelpPackage.Constants.icons;
import HelpPackage.DBConnectionHelper;
import HelpPackage.RoundedButton;
import HelpPackage.TextFieldFocusListener;
import HelpPackage.TextFieldMouseListener;

public class ChangePassword extends JFrame{
	private JLabel label;
	private JPasswordField newPasswordTextField;
	private JPanel buttonsPanel;
	private RoundedButton changeButton;
	private RoundedButton cancelButton;
	DBConnectionHelper helper;
	Connection connection;
	
	public ChangePassword(ResultSet accountInfo) {
		this.setTitle("Change Password");
		this.setSize(dimensions.smallFrameDimension);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setIconImage(icons.passwordIcon.getImage());
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.setLayout(new FlowLayout(FlowLayout.CENTER,0,40));
		
		label = new JLabel();
		label.setText("new password: ");
		label.setFont(new Font("", Font.BOLD, 25));
		
		newPasswordTextField = new JPasswordField();
		newPasswordTextField.setPreferredSize(new Dimension(dimensions.smallFrameDimension.width*8/10, 40));
		newPasswordTextField.setFont(new Font("",Font.PLAIN, 30));
		newPasswordTextField.setText("password");
		newPasswordTextField.setEchoChar('*');
		newPasswordTextField.setFocusable(false);
		newPasswordTextField.addMouseListener(new TextFieldMouseListener());
		newPasswordTextField.addFocusListener(new TextFieldFocusListener("password"));
		
		buttonsPanel= new JPanel();
		buttonsPanel.setSize(300, 100);
		//buttonsPanel.setBackground(Color.cyan);
		
		changeButton = new RoundedButton("Change");
		changeButton.setSize(100, 40);
		changeButton.addActionListener(new ActionListener() {	
			@Override
			public void actionPerformed(ActionEvent e) {
				int result = JOptionPane.showConfirmDialog(null, "Are you sure to change your password?", "Password", JOptionPane.YES_NO_OPTION,JOptionPane.WARNING_MESSAGE);
				if(result == 0) {
					int currentPassword = 0;
					int newPassword = 0;
					
					try {
						currentPassword = accountInfo.getInt(2);
						newPassword = String.valueOf(newPasswordTextField.getPassword()).trim().hashCode();
						if(currentPassword == newPassword)
							System.out.println("Aynı şifre olamaz");
						else {
							helper = new DBConnectionHelper();
							connection = helper.getConnection();
							PreparedStatement preStatement = connection.prepareStatement("UPDATE customers SET password = " + newPassword + " where full_name = '" + accountInfo.getString(1) + "' and password = " + currentPassword + ";");
							preStatement.executeUpdate();
						}
					} catch (SQLException e1) {
						System.out.println("şifre değiştirme hatası!!!");
					} finally {
						Statement statement;
						try {
							statement = connection.createStatement();
							ResultSet newResultSet = statement.executeQuery("SELECT * FROM customers WHERE full_name = '" + accountInfo.getString(1) + "' AND password = " + newPassword + ";");
							if(newResultSet.next()) {
								JOptionPane.showConfirmDialog(null, "Password has changed", "Succes", JOptionPane.YES_NO_OPTION,JOptionPane.INFORMATION_MESSAGE);
								dispose();
								new Settings(newResultSet);
								//connection.close();
							}
							else
								System.out.println("degistirilmedi!!!");
						} catch (SQLException e1) {
							System.out.println("resultset güncellem hatası");
						}
					}
				}
			}
		});
		
		cancelButton = new RoundedButton("Cancel");
		cancelButton.setSize(100, 40);
		cancelButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
				new Settings(accountInfo);
			}
		});
		
		buttonsPanel.add(changeButton);
		buttonsPanel.add(cancelButton);
		
		this.add(label);
		this.add(newPasswordTextField);
		this.add(buttonsPanel);
		
		this.setVisible(true);
	}
}