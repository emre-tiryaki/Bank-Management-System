package MainPackage;

import java.awt.Color;
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
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import HelpPackage.Constants.icons;
import HelpPackage.DBConnectionHelper;
import HelpPackage.TextFieldFocusListener;
import HelpPackage.TextFieldMouseListener;

public class DepositMoney extends JFrame{
	
	private float currentCurrencyAmount;
	private String currentCurrencyType;
	private JTextField amountOfMoneyToDepositTextField;
	private JLabel depositLabel;
	private JLabel currentCurrencyLabel;
	private JPanel buttonsPanel;
	private JButton depositButton;
	private JButton cancelButton;
	private float finalCurrencyAmount;
	DBConnectionHelper helper;
	Connection connection;
	
	public DepositMoney(ResultSet accountInfo) {
		this.setTitle("DEPOSIT");
		this.setSize(350, 400);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setIconImage(icons.salaryIcon.getImage());
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.setLayout(new FlowLayout(FlowLayout.CENTER,0,40));
		
		try {
			this.currentCurrencyAmount = accountInfo.getFloat(4);
			this.currentCurrencyType = accountInfo.getString(5);
			
			depositLabel = new JLabel();
			depositLabel.setText("Deposit");
			depositLabel.setIcon(icons.moneyBagIcon);
			depositLabel.setIconTextGap(15);
			depositLabel.setFont(new Font("", Font.BOLD, 50));
			
			currentCurrencyLabel = new JLabel();
			currentCurrencyLabel.setText("Current currency: " + this.currentCurrencyType + this.currentCurrencyAmount);
			currentCurrencyLabel.setFont(new Font("", Font.ITALIC, 20));
			
			amountOfMoneyToDepositTextField = new JTextField();
			amountOfMoneyToDepositTextField.setPreferredSize(new Dimension(250, 50));
			amountOfMoneyToDepositTextField.setFont(new Font("", Font.PLAIN, 50));
			amountOfMoneyToDepositTextField.setText("0.0");
			amountOfMoneyToDepositTextField.setFocusable(false);
			amountOfMoneyToDepositTextField.addMouseListener(new TextFieldMouseListener());
			amountOfMoneyToDepositTextField.addFocusListener(new TextFieldFocusListener("0.0"));
			
			buttonsPanel = new JPanel();
			//buttonsPanel.setBackground(Color.red);
			
			depositButton = new JButton();
			depositButton.setText("Deposit");
			depositButton.setSize(100, 40);
			depositButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					if(e.getSource() == depositButton) {
						if(Float.parseFloat(amountOfMoneyToDepositTextField.getText().trim()) >= 9999999.0)
							JOptionPane.showConfirmDialog(null, "you cant deposit this much money", "WARNING", JOptionPane.YES_NO_OPTION,JOptionPane.WARNING_MESSAGE);
						else {
							depositButton.setEnabled(false);
							finalCurrencyAmount = currentCurrencyAmount + Float.parseFloat(amountOfMoneyToDepositTextField.getText().trim());
							helper = new DBConnectionHelper();
							try {
								connection = helper.getConnection();
								System.out.println("Para yatırmak için veritabanı bağlantısı başarılı!!");
								PreparedStatement preStatement = connection.prepareStatement("UPDATE your-initial-database-table-name SET money = "+finalCurrencyAmount+" where full_name = '"+accountInfo.getString(1)+"' and password = '"+accountInfo.getString(2)+"';");
								preStatement.executeUpdate();
								JOptionPane.showConfirmDialog(null, "Money deposited", "Succes", JOptionPane.YES_NO_OPTION,JOptionPane.INFORMATION_MESSAGE);
								connection.close();
							} catch (SQLException e1) {
								System.out.println("Para yatırırken veritabanına bağlantı hatası!!!");
							} finally {
								try {
									connection = helper.getConnection();
									Statement statement = connection.createStatement();
									ResultSet updatedAccountInfo = statement.executeQuery("SELECT * FROM your-initial-database-table-name WHERE full_name = '" + accountInfo.getString(1) + "' AND password = '" + accountInfo.getString(2) + "';");
									if(updatedAccountInfo.next()) {
										new MainAccount(updatedAccountInfo);
										dispose();
									}
									else
										depositButton.setEnabled(true);
								} catch (SQLException e1) {
									System.out.println("para güncelleme hatasi!!!\n"+e1.getMessage());
								}
							}	
						}							
						}
				}
			});
			
			cancelButton = new JButton();
			cancelButton.setText("Cancel");
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
			
			buttonsPanel.add(depositButton);
			buttonsPanel.add(cancelButton);
			
			this.add(depositLabel);
			this.add(currentCurrencyLabel);
			this.add(amountOfMoneyToDepositTextField);
			this.add(buttonsPanel);
			
		} catch (SQLException e) {
			System.out.println("baglanti hatasi\n" + e.getMessage());
		}
		
		this.setVisible(true);
	}
}
