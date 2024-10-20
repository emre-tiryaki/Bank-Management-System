package MainPackage;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import HelpPackage.Constants.icons;
import IntroductionPackage.Login;

public class MainAccount extends JFrame{

	private JPanel usersNamePanel;
	private JLabel welcomeLabel;
	private JPanel usersMoneyPanel;
	private String usersCurrencyType;
	private float usersCurrency;
	private JLabel usersMoneyAmount;
	private JPanel transactionButonsPanel;
	private JButton depositMoneyButton;
	private JButton withdrawMoneyButton;
	private JButton transferMoneyButton;
	private JButton settingsButton;
	private JButton quitButton;
	
	public MainAccount(ResultSet customerInfo) throws SQLException {
		this.setTitle("Tiryaki Bank");
		this.setSize(700, 800);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setIconImage(icons.id_cardIcon.getImage());
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLayout(new FlowLayout(FlowLayout.CENTER, 300000, 0));
		
		//main users name and welcome message panel
		usersNamePanel = new JPanel();
		usersNamePanel.setPreferredSize(new Dimension(650, 200));
		usersNamePanel.setLayout(new BorderLayout());
		//usersNamePanel.setBackground(new Color(194, 193, 192));
		
		welcomeLabel = new JLabel();
		welcomeLabel.setText("Welcome " + customerInfo.getString(1));
		welcomeLabel.setFont(new Font("Robotic",Font.BOLD, 60));
		
		usersNamePanel.add(welcomeLabel, BorderLayout.CENTER);
		
		usersMoneyPanel = new JPanel();
		usersMoneyPanel.setPreferredSize(new Dimension(700, 100));
		usersMoneyPanel.setLayout(new FlowLayout(FlowLayout.CENTER,0,10));
		usersMoneyPanel.setBackground(Color.red);
		
		usersCurrencyType = customerInfo.getString(5);
		
		usersCurrency = customerInfo.getFloat(4);
		
		usersMoneyAmount = new JLabel();
		usersMoneyAmount.setText(usersCurrencyType + usersCurrency);
		usersMoneyAmount.setFont(new Font("",Font.PLAIN,55));
		usersMoneyAmount.setForeground(Color.white);
		
		usersMoneyPanel.add(usersMoneyAmount);
		
		transactionButonsPanel = new JPanel();
		transactionButonsPanel.setPreferredSize(new Dimension(650, 450));
		transactionButonsPanel.setLayout(new GridLayout(5, 1,10, 20));
		//transactionButonsPanel.setBackground(Color.blue);
		
		depositMoneyButton = new JButton();
		depositMoneyButton.setText("Deposit Money 💸");
		depositMoneyButton.setBorder(null);
		depositMoneyButton.setFocusable(false);
		depositMoneyButton.setSize(100, 20);
		depositMoneyButton.setBackground(new Color(31, 165, 255));
		depositMoneyButton.setForeground(Color.black);
		depositMoneyButton.setFont(new Font("",Font.PLAIN,45));
		depositMoneyButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
				new DepositMoney(customerInfo);
			}
		});
		
		withdrawMoneyButton = new JButton();
		withdrawMoneyButton.setText("Withdraw Money 💲");
		withdrawMoneyButton.setBorder(null);
		withdrawMoneyButton.setFocusable(false);
		withdrawMoneyButton.setSize(100, 20);
		withdrawMoneyButton.setBackground(new Color(31, 165, 255));
		withdrawMoneyButton.setForeground(Color.black);
		withdrawMoneyButton.setFont(new Font("",Font.PLAIN,45));
		withdrawMoneyButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					if(customerInfo.getFloat(4) <= 0)
						JOptionPane.showConfirmDialog(null, "You can't withdraw money, you dont have enough money", "WARNING", JOptionPane.YES_NO_OPTION,JOptionPane.WARNING_MESSAGE);
					else {
						dispose();
						new WithdrawMoney(customerInfo);						
					}
					
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		
		transferMoneyButton = new JButton();
		transferMoneyButton.setText("Transfer Money 💵💨");
		transferMoneyButton.setBorder(null);
		transferMoneyButton.setFocusable(false);
		transferMoneyButton.setSize(100, 20);
		transferMoneyButton.setBackground(new Color(31, 165, 255));
		transferMoneyButton.setForeground(Color.black);
		transferMoneyButton.setFont(new Font("",Font.PLAIN,45));
		transferMoneyButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					if(customerInfo.getFloat(4) <= 0)
						JOptionPane.showConfirmDialog(null, "You can't transfer money, you dont have enough money", "WARNING", JOptionPane.YES_NO_OPTION,JOptionPane.WARNING_MESSAGE);
					else {
						dispose();
						new TransferMoney(customerInfo);						
					}
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		
		settingsButton = new JButton();
		settingsButton.setText("Settings ⚙");
		settingsButton.setBorder(null);
		settingsButton.setFocusable(false);
		settingsButton.setSize(100, 20);
		settingsButton.setBackground(new Color(31, 165, 255));
		settingsButton.setForeground(Color.black);
		settingsButton.setFont(new Font("",Font.PLAIN,45));
		settingsButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
				new Settings(customerInfo);
			}
		});
		
		quitButton = new JButton();
		quitButton.setText("Quit ❌");
		quitButton.setBorder(null);
		quitButton.setFocusable(false);
		quitButton.setSize(100, 20);
		quitButton.setBackground(new Color(31, 165, 255));
		quitButton.setForeground(Color.black);
		quitButton.setFont(new Font("",Font.PLAIN,45));
		quitButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
				new Login();
			}
		});

		transactionButonsPanel.add(depositMoneyButton);
		transactionButonsPanel.add(withdrawMoneyButton);
		transactionButonsPanel.add(transferMoneyButton);
		transactionButonsPanel.add(settingsButton);
		transactionButonsPanel.add(quitButton);
		
		this.add(usersNamePanel);
		this.add(usersMoneyPanel);
		this.add(transactionButonsPanel);
		
		this.setVisible(true);
	}
}
