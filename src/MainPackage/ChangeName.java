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
import javax.swing.JTextField;

import HelpPackage.Constants.dimensions;
import HelpPackage.Constants.icons;
import HelpPackage.DBConnectionHelper;
import HelpPackage.RoundedButton;
import HelpPackage.TextFieldFocusListener;
import HelpPackage.TextFieldMouseListener;

public class ChangeName extends JFrame{
	
	private JLabel currentNameLabel;
	private JTextField newNameTextField;
	private JTextField newSurnameTextField;
	private JPanel buttonsPanel;
	private RoundedButton changeButton;
	private RoundedButton cancelButton;
	DBConnectionHelper helper;
	Connection connection;
	
	public ChangeName(ResultSet accountInfo) {
		this.setTitle("Change Name");
		this.setSize(dimensions.smallFrameDimension);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setIconImage(icons.userIcon.getImage());
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.setLayout(new FlowLayout(FlowLayout.CENTER,0,30));
		
		currentNameLabel = new JLabel();
		try {
			currentNameLabel.setText("Current name: " + accountInfo.getString(1));
		} catch (SQLException e) {
			System.out.println("geçerli ismi almada hata");
		}
		currentNameLabel.setFont(new Font("",Font.PLAIN, 20));
		
		newNameTextField = new JTextField();
		newNameTextField.setPreferredSize(new Dimension(dimensions.smallFrameDimension.width*8/10, 40));
		newNameTextField.setFont(new Font("",Font.PLAIN, 30));
		newNameTextField.setText("new name");
		newNameTextField.setFocusable(false);
		newNameTextField.addMouseListener(new TextFieldMouseListener());
		newNameTextField.addFocusListener(new TextFieldFocusListener("new name"));
		
		newSurnameTextField = new JTextField();
		newSurnameTextField.setPreferredSize(new Dimension(dimensions.smallFrameDimension.width*8/10, 40));
		newSurnameTextField.setFont(new Font("",Font.PLAIN, 30));
		newSurnameTextField.setText("new surname");
		newSurnameTextField.setFocusable(false);
		newSurnameTextField.addMouseListener(new TextFieldMouseListener());
		newSurnameTextField.addFocusListener(new TextFieldFocusListener("new surname"));
		
		buttonsPanel= new JPanel();
		buttonsPanel.setPreferredSize(new Dimension(dimensions.smallFrameDimension.width, 60));
		//buttonsPanel.setBackground(Color.cyan);
		
		changeButton = new RoundedButton("Change");
		changeButton.setSize(100, 40);
		changeButton.addActionListener(new ActionListener() {	
			@Override
			public void actionPerformed(ActionEvent e) {
				if(e.getSource() == changeButton) {
					int result = JOptionPane.showConfirmDialog(null, "Are you sure to change your name?", "Warning", JOptionPane.YES_NO_OPTION,JOptionPane.WARNING_MESSAGE);
					if(result == 0) {
						String fullName = newNameTextField.getText().trim() + " " + newSurnameTextField.getText().trim();
						String currentName = null;
						String pass = null;
						try {
							currentName = accountInfo.getString(1);
							pass = accountInfo.getString(2);
							if(fullName == currentName)
								JOptionPane.showConfirmDialog(null, "You should enter a different name", "Warning", JOptionPane.YES_NO_OPTION,JOptionPane.INFORMATION_MESSAGE);
							else {
								helper = new DBConnectionHelper();
								connection = helper.getConnection();
								PreparedStatement preStatement = connection.prepareStatement("UPDATE customers SET full_name = '" + fullName + "' where full_name = '" + currentName + "' and password = '" + pass + "';");
								preStatement.executeUpdate();
							}
						} catch (SQLException e1) {
							System.out.println("baglanti hatasi!!");
						} finally {
							Statement statement;
							try {
								statement = connection.createStatement();
								ResultSet newResultSet = statement.executeQuery("SELECT * FROM customers WHERE full_name = '" + fullName + "' AND password = '" + pass + "';");
								if(newResultSet.next()) {
									JOptionPane.showConfirmDialog(null, "Name has changed to " + newResultSet.getString(1), "Succes", JOptionPane.YES_NO_OPTION,JOptionPane.INFORMATION_MESSAGE);
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
		
		this.add(currentNameLabel);
		this.add(newNameTextField);
		this.add(newSurnameTextField);
		this.add(buttonsPanel);
		
		this.setVisible(true);
	}
}
