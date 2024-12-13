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

import HelpPackage.Constants.icons;
import HelpPackage.DBConnectionHelper;
import HelpPackage.RoundedButton;
import HelpPackage.TextFieldFocusListener;
import HelpPackage.TextFieldMouseListener;

public class WithdrawMoney extends JFrame{
	
	private float currentCurrencyAmount;
	private String currentCurrencyType;
	private JTextField amountOfMoneyToWithdrawTextField;
	private JLabel withdrawLabel;
	private JLabel currentCurrencyLabel;
	private JPanel buttonsPanel;
	private RoundedButton withdrawButton;
	private RoundedButton withdrawAllButton;
	private RoundedButton cancelButton;
	private float finalCurrencyAmount;
	DBConnectionHelper helper;
	Connection connection;
	
	public WithdrawMoney(ResultSet accountInfo) {
		this.setTitle("WITHDRAW");
		this.setSize(350, 400);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setIconImage(icons.payingIcon.getImage());
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.setLayout(new FlowLayout(FlowLayout.CENTER,0,40));
		
		try {
			this.currentCurrencyAmount = accountInfo.getFloat(4);
			this.currentCurrencyType = accountInfo.getString(5);
			
			withdrawLabel = new JLabel();
			withdrawLabel.setText("Withdraw");
			//withdrawLabel.setIcon(moneyBagIcon);
			withdrawLabel.setIconTextGap(15);
			withdrawLabel.setFont(new Font("", Font.BOLD, 50));

			currentCurrencyLabel = new JLabel();
			currentCurrencyLabel.setText("Current currency: " + this.currentCurrencyType + this.currentCurrencyAmount);
			currentCurrencyLabel.setFont(new Font("", Font.ITALIC, 20));

			amountOfMoneyToWithdrawTextField = new JTextField();
			amountOfMoneyToWithdrawTextField.setPreferredSize(new Dimension(250, 40));
			amountOfMoneyToWithdrawTextField.setFont(new Font("", Font.PLAIN, 45));
			amountOfMoneyToWithdrawTextField.setText("0.0");
			amountOfMoneyToWithdrawTextField.setFocusable(false);
			amountOfMoneyToWithdrawTextField.addMouseListener(new TextFieldMouseListener());
			amountOfMoneyToWithdrawTextField.addFocusListener(new TextFieldFocusListener("0.0"));

			buttonsPanel = new JPanel();
			//buttonsPanel.setBackground(Color.red);
			
			withdrawButton = new RoundedButton("Withdraw");
			withdrawButton.setSize(100,40);
			withdrawButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					if(e.getSource() == withdrawButton) {
						if(Float.parseFloat(amountOfMoneyToWithdrawTextField.getText().trim()) > currentCurrencyAmount)
							JOptionPane.showConfirmDialog(null, "You can't withdraw this much money", "WARNING", JOptionPane.YES_NO_OPTION,JOptionPane.WARNING_MESSAGE);
						else {
							withdrawButton.setEnabled(false);
							helper = new DBConnectionHelper();
							finalCurrencyAmount = currentCurrencyAmount - Float.parseFloat(amountOfMoneyToWithdrawTextField.getText().trim());
							try {
								connection = helper.getConnection();
								System.out.println("Para çekmek için veritabanı bağlantısı sağlandı");
								PreparedStatement preStatement = connection.prepareStatement("UPDATE customers SET money = "+finalCurrencyAmount+" where full_name = '"+accountInfo.getString(1)+"' and password = '"+accountInfo.getString(2)+"';");
								preStatement.executeUpdate();
								JOptionPane.showConfirmDialog(null, "Money has been withdrawed", "Succes", JOptionPane.YES_NO_OPTION,JOptionPane.INFORMATION_MESSAGE);
								connection.close();
							} catch (SQLException e1) {
								System.out.println("Para çekerken veritabanına bağlantıda hata çıktı\n"+e1.getMessage());
							} finally {
								try {
									connection = helper.getConnection();
									Statement statement = connection.createStatement();
									ResultSet updatedAccountInfo = statement.executeQuery("SELECT * FROM customers WHERE full_name = '" + accountInfo.getString(1) + "' AND password = '" + accountInfo.getString(2) + "';");
									if(updatedAccountInfo.next()) {
										new MainAccount(updatedAccountInfo);
										dispose();
									}
									else
										withdrawButton.setEnabled(true);
								} catch (SQLException e1) {
									System.out.println("para güncelleme hatasi!!!\n"+e1.getMessage());
								}
							}
						}
					}
				}
			});
			
			withdrawAllButton = new RoundedButton("Withdraw All");
			withdrawAllButton.setSize(100,40);
			withdrawAllButton.addActionListener(e -> amountOfMoneyToWithdrawTextField.setText(Float.toString(currentCurrencyAmount)));
			
			cancelButton = new RoundedButton("Cancel");
			cancelButton.setSize(100,40);
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
			
			buttonsPanel.add(withdrawButton);
			buttonsPanel.add(withdrawAllButton);
			buttonsPanel.add(cancelButton);

			this.add(withdrawLabel);
			this.add(currentCurrencyLabel);
			this.add(amountOfMoneyToWithdrawTextField);
			this.add(buttonsPanel);
		} catch (SQLException e) {
			System.out.println("Baglanti hatasi!!\n"+e.getMessage());
		}
		
		this.setVisible(true);
	}
}
