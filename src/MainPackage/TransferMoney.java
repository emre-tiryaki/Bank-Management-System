package MainPackage;

import java.awt.BorderLayout;
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

public class TransferMoney extends JFrame{
	
	private JPanel mainUserPanel;
	private JPanel transferUserPanel;
	private JPanel transferSymbolPanel;
	private JLabel transferSymbolLabel;
	private JLabel currentCurrencyLabel;
	private float currentCurrencyAmount;
	private JTextField amountOfMoneyTextField;
	private JPanel buttonsPanel;
	private RoundedButton allMoneyButton;
	private RoundedButton cancelButton;
	private JTextField fullNameTextField;
	private JTextField customerIdTextField;
	private RoundedButton sendButton;
	private float finalCurrencyAmount;
	private float finalCurrencyAmountOfTheTransfree;
	DBConnectionHelper helper;
	Connection connection;
	
	public TransferMoney(ResultSet accountInfo) {
		this.setTitle("TRANSFER");
		this.setSize(dimensions.wideFrameDimension);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setIconImage(icons.transferIcon.getImage());
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.setLayout(new GridLayout(1,3,0,0));
		
		//main user panel
		mainUserPanel = new JPanel();
		mainUserPanel.setSize(300, 400);
		//mainUserPanel.setBackground(Color.ORANGE);
		mainUserPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 80));
		
		try {
			this.currentCurrencyAmount = accountInfo.getFloat(4);
		} catch (SQLException e) {
			System.out.println("Bağlantı hatası!!" + e.getMessage());
		}
		
		currentCurrencyLabel = new JLabel();
		currentCurrencyLabel.setText("Current currency: " + Float.toString(this.currentCurrencyAmount));
		currentCurrencyLabel.setFont(new Font("", Font.ITALIC, 20));
		
		amountOfMoneyTextField = new JTextField();
		amountOfMoneyTextField.setPreferredSize(new Dimension(200,40));
		amountOfMoneyTextField.setFont(new Font("", Font.PLAIN, 45));
		amountOfMoneyTextField.setText("0.0");
		amountOfMoneyTextField.setFocusable(false);
		amountOfMoneyTextField.addMouseListener(new TextFieldMouseListener());
		amountOfMoneyTextField.addFocusListener(new TextFieldFocusListener("0.0"));
		
		buttonsPanel= new JPanel();
		buttonsPanel.setLayout(new FlowLayout());
		
		allMoneyButton = new RoundedButton("All money");
		allMoneyButton.setSize(100, 40);
		allMoneyButton.addActionListener(e -> amountOfMoneyTextField.setText(Float.toString(currentCurrencyAmount)));
		
		cancelButton = new RoundedButton("Cancel");
		cancelButton.setSize(100, 40);
		cancelButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
				try {
					new MainAccount(accountInfo);
				} catch (SQLException e1) {
					System.out.println("hata var abi\n"+e1.getMessage());
				}
			}
		});
		
		buttonsPanel.add(allMoneyButton);
		buttonsPanel.add(cancelButton);
		
		mainUserPanel.add(currentCurrencyLabel);
		mainUserPanel.add(amountOfMoneyTextField);
		mainUserPanel.add(buttonsPanel);
		
		//transfer symbol panel
		transferSymbolPanel = new JPanel();
		transferSymbolPanel.setSize(100, 400);
		//transferSymbolPanel.setBackground(Color.magenta);
		transferSymbolPanel.setLayout(new BorderLayout());
		
		transferSymbolLabel = new JLabel();
		transferSymbolLabel.setIcon(icons.forwardIcon);
		
		transferSymbolPanel.add(transferSymbolLabel, BorderLayout.EAST);
		
		//transfer user panel
		transferUserPanel = new JPanel();
		transferUserPanel.setSize(300, 400);
		//transferUserPanel.setBackground(Color.black);
		transferUserPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 80));
		
		fullNameTextField = new JTextField();
		fullNameTextField.setPreferredSize(new Dimension(200, 40));
		fullNameTextField.setFont(new Font("", Font.PLAIN, 25));
		fullNameTextField.setText("Receiver's name");
		fullNameTextField.addFocusListener(new TextFieldFocusListener("Receiver's name"));
		fullNameTextField.setFocusable(false);
		fullNameTextField.addMouseListener(new TextFieldMouseListener());
		fullNameTextField.addFocusListener(new TextFieldFocusListener("Receiver's name"));
		
		customerIdTextField = new JTextField();
		customerIdTextField.setPreferredSize(new Dimension(200, 40));
		customerIdTextField.setFont(new Font("", Font.PLAIN, 25));
		customerIdTextField.setText("Receiver's id");
		customerIdTextField.addFocusListener(new TextFieldFocusListener("Receiver's id"));
		customerIdTextField.setFocusable(false);
		customerIdTextField.addMouseListener(new TextFieldMouseListener());
		customerIdTextField.addFocusListener(new TextFieldFocusListener("Receiver's id"));
		
		sendButton = new RoundedButton("SEND");
		sendButton.setBorder(null);
		sendButton.setPreferredSize(new Dimension(100, 40));
		sendButton.setFocusable(false);
		sendButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(e.getSource() == sendButton) {
					
					if(amountOfMoneyTextField.getText().trim().isEmpty())
						JOptionPane.showConfirmDialog(null, "You should enter an amount of money", "Warning", JOptionPane.YES_NO_OPTION,JOptionPane.INFORMATION_MESSAGE);
					if(fullNameTextField.getText().trim().isEmpty())
						JOptionPane.showConfirmDialog(null, "You should enter the full name of the person you are sending to", "Warning", JOptionPane.YES_NO_OPTION,JOptionPane.INFORMATION_MESSAGE);
					if(customerIdTextField.getText().trim().isEmpty())
						JOptionPane.showConfirmDialog(null, "You should enter the id of the person you are sending to", "Warning", JOptionPane.YES_NO_OPTION,JOptionPane.INFORMATION_MESSAGE);
					else if(Float.parseFloat(amountOfMoneyTextField.getText().trim()) > currentCurrencyAmount)
						JOptionPane.showConfirmDialog(null, "Invalid money amount input", "Warning", JOptionPane.YES_NO_OPTION,JOptionPane.INFORMATION_MESSAGE);
					else {
						int result = JOptionPane.showConfirmDialog(null, "Are you sure to send money to this person?", "Warning", JOptionPane.YES_NO_OPTION,JOptionPane.WARNING_MESSAGE);
						if(result == 0) {
							sendButton.setEnabled(false);
							helper = new DBConnectionHelper();
							finalCurrencyAmount = currentCurrencyAmount - Float.parseFloat(amountOfMoneyTextField.getText().trim());
							try {
								connection = helper.getConnection();
								Statement statement = connection.createStatement();
								ResultSet resultSet = statement.executeQuery("SELECT * FROM customers WHERE full_name = '" + fullNameTextField.getText() + "' AND id = '" + customerIdTextField.getText() + "';");
								if(resultSet.next()) {
									System.out.println("Para göndermek için veritabanı bağlantısı sağlandı!!");
									PreparedStatement mainPreStatement = connection.prepareStatement("UPDATE customers SET money = "+finalCurrencyAmount+" where full_name = '"+accountInfo.getString(1)+"' and password = '"+accountInfo.getString(2)+"';");
									finalCurrencyAmountOfTheTransfree = resultSet.getFloat(4) + Float.parseFloat(amountOfMoneyTextField.getText().trim());
									PreparedStatement otherPreStatement = connection.prepareStatement("UPDATE customers SET money = "+finalCurrencyAmountOfTheTransfree+" where full_name = '"+resultSet.getString(1)+"' and id = '"+resultSet.getString(6)+"';");
									mainPreStatement.executeUpdate();
									otherPreStatement.executeUpdate();		
									JOptionPane.showConfirmDialog(null, "Money transferred", "Success", JOptionPane.YES_NO_OPTION,JOptionPane.INFORMATION_MESSAGE);
									ResultSet updatedAccountInfo = statement.executeQuery("SELECT * FROM customers WHERE full_name = '" + accountInfo.getString(1) + "' AND password = '" + accountInfo.getString(2) + "';");
									if(updatedAccountInfo.next()) {
										new MainAccount(updatedAccountInfo);
										dispose();
									}
								}
								else {
									JOptionPane.showConfirmDialog(null, "Money did not transferred", "Warning", JOptionPane.YES_NO_OPTION,JOptionPane.INFORMATION_MESSAGE);
									sendButton.setEnabled(true);									
								}
								connection.close();
							} catch (SQLException e1) {
								System.out.println("Para gönderirken veritabanı bağlantı hatası!!\n"+e1.getMessage());
							}
						}						
					}
				}
			}
		});
		
		transferUserPanel.add(fullNameTextField);
		transferUserPanel.add(customerIdTextField);
		transferUserPanel.add(sendButton);
		
		this.add(mainUserPanel);
		this.add(transferSymbolPanel);
		this.add(transferUserPanel);
		
		this.setVisible(true);
	}
}
