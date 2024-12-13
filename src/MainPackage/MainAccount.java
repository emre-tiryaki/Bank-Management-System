package MainPackage;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import HelpPackage.Constants.dimensions;
import HelpPackage.Constants.icons;
import HelpPackage.RoundedButton;
import IntroductionPackage.Login;

public class MainAccount extends JFrame{

	private JPanel usersInfoPanel;
	private JPanel usersNamePanel;
	private JLabel welcomeLabel;
	private JPanel usersMoneyPanel;
	private String usersCurrencyType;
	private float usersCurrency;
	private JLabel usersMoneyAmount;
	private JPanel transactionButonsPanel;
	private RoundedButton depositMoneyButton;
	private RoundedButton withdrawMoneyButton;
	private RoundedButton transferMoneyButton;
	private RoundedButton settingsButton;
	private RoundedButton quitButton;
	
	public MainAccount(ResultSet customerInfo) throws SQLException {
		this.setTitle("Tiryaki Bank");
		this.setSize(dimensions.wideFrameDimension);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setIconImage(icons.id_cardIcon.getImage());
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLayout(new GridLayout(1, 2));
		
		usersInfoPanel = new JPanel();
		usersInfoPanel.setPreferredSize(new Dimension(dimensions.wideFrameDimension.width/2, dimensions.wideFrameDimension.height));
		usersInfoPanel.setLayout(new BorderLayout());
		
		//main users name and welcome message panel
		usersNamePanel = new JPanel();
		usersNamePanel.setPreferredSize(new Dimension(dimensions.wideFrameDimension.width/2, 200));
		usersNamePanel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 20));
		//usersNamePanel.setBackground(new Color(194, 193, 192));
		
		welcomeLabel = new JLabel();
		welcomeLabel.setText("<html><body style='text-align:center'>" + "Welcome<br>" + customerInfo.getString(1) + "</body></html>");
		welcomeLabel.setFont(new Font("Robotic",Font.BOLD, 50));
		
		usersNamePanel.add(welcomeLabel, BorderLayout.CENTER);
		
		usersMoneyPanel = new JPanel();
		usersMoneyPanel.setPreferredSize(new Dimension(dimensions.wideFrameDimension.width/2, dimensions.wideFrameDimension.height/7));
		usersMoneyPanel.setLayout(new FlowLayout(FlowLayout.CENTER,0,0));
		usersMoneyPanel.setBackground(new Color(237, 148, 76));
		
		usersCurrencyType = customerInfo.getString(5);
		
		usersCurrency = customerInfo.getFloat(4);
		
		usersMoneyAmount = new JLabel();
		usersMoneyAmount.setText(usersCurrencyType + usersCurrency);
		usersMoneyAmount.setFont(new Font("",Font.PLAIN,55));
		usersMoneyAmount.setForeground(Color.white);
		
		usersMoneyPanel.add(usersMoneyAmount);
		
		usersInfoPanel.add(usersNamePanel, BorderLayout.NORTH);
		usersInfoPanel.add(usersMoneyPanel, BorderLayout.SOUTH);
		
		transactionButonsPanel = new JPanel();
		transactionButonsPanel.setPreferredSize(new Dimension(dimensions.wideFrameDimension.width, dimensions.wideFrameDimension.height));
		transactionButonsPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 40));
		//transactionButonsPanel.setBackground(Color.blue);
		
		depositMoneyButton = new RoundedButton("Deposit Money 💸");
		depositMoneyButton.setBorder(null);
		depositMoneyButton.setFocusable(false);
		depositMoneyButton.setSize(100, 20);
		depositMoneyButton.setBackground(new Color(101, 198, 201));
		depositMoneyButton.setForeground(Color.black);
		depositMoneyButton.setFont(new Font("",Font.PLAIN,35));
		depositMoneyButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
				new DepositMoney(customerInfo);
			}
		});
		
		withdrawMoneyButton = new RoundedButton("Withdraw Money 💲");
		withdrawMoneyButton.setBorder(null);
		withdrawMoneyButton.setFocusable(false);
		withdrawMoneyButton.setSize(100, 20);
		withdrawMoneyButton.setBackground(new Color(101, 198, 201));
		withdrawMoneyButton.setForeground(Color.black);
		withdrawMoneyButton.setFont(new Font("",Font.PLAIN,35));
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
					e1.printStackTrace();
				}
			}
		});
		
		transferMoneyButton = new RoundedButton("Transfer Money 💵💨");
		transferMoneyButton.setBorder(null);
		transferMoneyButton.setFocusable(false);
		transferMoneyButton.setSize(100, 20);
		transferMoneyButton.setBackground(new Color(101, 198, 201));
		transferMoneyButton.setForeground(Color.black);
		transferMoneyButton.setFont(new Font("",Font.PLAIN,35));
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
					e1.printStackTrace();
				}
			}
		});
		
		settingsButton = new RoundedButton("Settings ⚙");
		settingsButton.setBorder(null);
		settingsButton.setFocusable(false);
		settingsButton.setSize(100, 20);
		settingsButton.setBackground(new Color(101, 198, 201));
		settingsButton.setForeground(Color.black);
		settingsButton.setFont(new Font("",Font.PLAIN,35));
		settingsButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
				new Settings(customerInfo);
			}
		});
		
		quitButton = new RoundedButton("Quit ❌");
		quitButton.setBorder(null);
		quitButton.setFocusable(false);
		quitButton.setSize(100, 20);
		quitButton.setBackground(new Color(101, 198, 201));
		quitButton.setForeground(Color.black);
		quitButton.setFont(new Font("",Font.PLAIN,35));
		quitButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(JOptionPane.showConfirmDialog(null, "Are you sure to exit?", "Warning", JOptionPane.YES_NO_OPTION,JOptionPane.INFORMATION_MESSAGE) == 0) {
					dispose();
					new Login();					
				}
			}
		});

		transactionButonsPanel.add(depositMoneyButton);
		transactionButonsPanel.add(withdrawMoneyButton);
		transactionButonsPanel.add(transferMoneyButton);
		transactionButonsPanel.add(settingsButton);
		transactionButonsPanel.add(quitButton);
		
		this.add(usersInfoPanel);
		this.add(transactionButonsPanel);
		
		this.setVisible(true);
	}
}
