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

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import HelpPackage.Constants.icons;
import HelpPackage.DBConnectionHelper;

public class ChangeCurrencyType extends JFrame{
	private JLabel currentNameLabel;
	private String[] currencyTypes = {"$-USD","€-EUR","₺-TL","¥-JPY"};
	private JComboBox<String> currencyTypesComboBox;
	private JPanel buttonsPanel;
	private JButton exitButton;
	DBConnectionHelper helper;
	Connection connection;
	ResultSet newResultSet = null;
	
	public ChangeCurrencyType(ResultSet accountInfo) {
		this.setTitle("Change currency type");
		this.setSize(300, 250);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setIconImage(icons.currencyIcon.getImage());
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.setLayout(new FlowLayout(FlowLayout.CENTER, 150, 40));
		
		currentNameLabel = new JLabel();
		try {
			currentNameLabel.setText("Current currency: " + accountInfo.getString(5) + accountInfo.getDouble(4));
		} catch (SQLException e) {
			System.out.println("geçerli para birimini almada hata");
		}
		currentNameLabel.setFont(new Font("",Font.PLAIN, 20));
		
		currencyTypesComboBox = new JComboBox<String>(currencyTypes);
		currencyTypesComboBox.setPreferredSize(new Dimension(100, 40));
		try {
			currencyTypesComboBox.setSelectedItem(accountInfo.getString(5));
		} catch (SQLException e) {
			System.out.println("para birimi hatası");
		}
		currencyTypesComboBox.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(e.getSource() == currencyTypesComboBox) {
					try {
						if(accountInfo.getString(5) != currencyTypesComboBox.getSelectedItem()) {
							helper = new DBConnectionHelper();
							connection = helper.getConnection();
							System.out.println("Veritabanına bağlandı!");
							Float newAmountOfMoney = convertCurrency(accountInfo.getString(5), (String)currencyTypesComboBox.getSelectedItem(), accountInfo.getFloat(4));
							PreparedStatement preStatement = connection.prepareStatement("UPDATE your-initial-database-table-name SET currency_type = '"+currencyTypesComboBox.getSelectedItem().toString().charAt(0)+"', money = "+newAmountOfMoney+" where full_name = '"+accountInfo.getString(1)+"' and password = '"+accountInfo.getString(2)+"';");
							preStatement.executeUpdate();
							currentNameLabel.setText("Current currency: " + currencyTypesComboBox.getSelectedItem().toString().charAt(0) + newAmountOfMoney);
							}
					} catch (SQLException e1) {
						System.out.println("hataaa!!");
					} finally {
						Statement statement;
						try {
							statement = connection.createStatement();
							newResultSet = statement.executeQuery("SELECT * FROM your-initial-database-table-name WHERE full_name = '" + accountInfo.getString(1) + "' AND password = '" + accountInfo.getString(2) + "';");
							if(!newResultSet.next())
								System.out.println("olmadi");
						} catch (SQLException e1) {
							System.out.println("ResultSet güncelleme hatasi!!!");
						}
					}
				}
			}
		});
		
		buttonsPanel= new JPanel();
		buttonsPanel.setSize(300, 100);
		//buttonsPanel.setBackground(Color.cyan);
				
		exitButton = new JButton();
		exitButton.setText("Exit");
		exitButton.setSize(100, 40);
		exitButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
				JOptionPane.showConfirmDialog(null, "Currency type changed", "Succes", JOptionPane.YES_NO_OPTION,JOptionPane.INFORMATION_MESSAGE);
				if(newResultSet == null)
					new Settings(accountInfo);
				else
					new Settings(newResultSet);
			}
		});
		
		buttonsPanel.add(exitButton);
		
		this.add(currentNameLabel);
		this.add(currencyTypesComboBox);
		this.add(buttonsPanel);
		
		this.setVisible(true);
	}
	
	private float convertCurrency(String oldCurrencyType, String newCurrencyType, float moneyAmount) {
		float finalMoney = moneyAmount;
		switch(oldCurrencyType) {
			case "$":
				switch(newCurrencyType) {
					case "₺-TL":
						finalMoney = (float) (moneyAmount * 34.18);
						break;
					case "€-EUR":
						finalMoney = (float) (moneyAmount * 0.9);
						break;
					case "¥-JPY":
						finalMoney = (float) (moneyAmount * 149.79);
						break;
				}
				break;
			case "€":
				switch(newCurrencyType) {
					case "₺-TL":
						finalMoney = (float) (moneyAmount * 37.1);
						break;
					case "$-USD":
						finalMoney = (float) (moneyAmount * 1.09);
						break;
					case "¥-JPY":
						finalMoney = (float) (moneyAmount * 162.56);
						break;
				}
				break;
			case "₺":
				switch(newCurrencyType) {
					case "$-USD":
						finalMoney = (float) (moneyAmount * 0.029);
						break;
					case "€-EUR":
						finalMoney = (float) (moneyAmount * 0.027);
						break;
					case "¥-JPY":
						finalMoney = (float) (moneyAmount * 4.38);
						break;
				}
				break;
			case "¥":
				switch(newCurrencyType) {
					case "$-USD":
						finalMoney = (float) (moneyAmount * 0.0067);
						break;
					case "€-EUR":
						finalMoney = (float) (moneyAmount * 0.0062);
						break;
					case "₺-TL":
						finalMoney = (float) (moneyAmount * 0.23);
						break;
				}
				break;
		}
			
		return finalMoney;
	}
}