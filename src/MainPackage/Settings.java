package MainPackage;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import HelpPackage.Constants.dimensions;
import HelpPackage.Constants.icons;
import HelpPackage.RoundedButton;

public class Settings extends JFrame{
	
	private JPanel optionButtonsPanel;
	private JPanel userInfoPanel;
	private RoundedButton changeNameButton;
	private RoundedButton changePassButton;
	private RoundedButton changeCurrencyTypeButton;
	private RoundedButton cancelButton;
	private JLabel usersNameLabel;
	private JLabel usersCurrencyTypeLabel;
	
	public Settings(ResultSet accountInfo) {
		this.setTitle("Settings");
		this.setSize(dimensions.tallFrameDimension);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setIconImage(icons.cogwheelIcon.getImage());
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLayout(new BorderLayout());
		
		userInfoPanel = new JPanel();
		userInfoPanel.setPreferredSize(new Dimension(dimensions.tallFrameDimension.width, dimensions.tallFrameDimension.height/3));
		userInfoPanel.setLayout(new FlowLayout(FlowLayout.CENTER,440,30));
		//userInfoPanel.setBackground(Color.magenta);
		
		usersNameLabel = new JLabel();
		try {
			usersNameLabel.setText(accountInfo.getString(1));
			usersNameLabel.setFont(new Font("Agdasima", Font.ITALIC, 50));
		} catch (SQLException e1) {
			System.out.println("isim yok!!!");
		}
		
		usersCurrencyTypeLabel = new JLabel();
		usersCurrencyTypeLabel.setFont(new Font("Agdasima", Font.ITALIC, 35));
		try {
			usersCurrencyTypeLabel.setText("Currency type: " + accountInfo.getString(5));
		} catch (SQLException e1) {
			System.out.println("para birimi alma hatası!!");
		}
		
		userInfoPanel.add(usersNameLabel);
		userInfoPanel.add(usersCurrencyTypeLabel);
		
		optionButtonsPanel = new JPanel();
		optionButtonsPanel.setPreferredSize(new Dimension(dimensions.tallFrameDimension.width, dimensions.tallFrameDimension.height*2/3));
		optionButtonsPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10,40));
		//optionButtonsPanel.setBackground(new Color(176, 174, 167));
		
		changeNameButton = new RoundedButton("Change name");
		changeNameButton.setFocusable(false);
		changeNameButton.setPreferredSize(new Dimension(dimensions.tallFrameDimension.width*4/10, dimensions.tallFrameDimension.height*4/30));
		changeNameButton.setBorder(null);
		changeNameButton.setForeground(new Color(13, 17, 84));
		changeNameButton.setFont(new Font("",Font.PLAIN,25));
		changeNameButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new ChangeName(accountInfo);
				dispose();
			}
		});
		
		changePassButton = new RoundedButton("Change password");
		changePassButton.setFocusable(false);
		changePassButton.setPreferredSize(new Dimension(dimensions.tallFrameDimension.width*4/10, dimensions.tallFrameDimension.height*4/30));
		changePassButton.setBorder(null);
		changePassButton.setForeground(new Color(13, 17, 84));
		changePassButton.setFont(new Font("",Font.PLAIN,25));
		changePassButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new ChangePassword(accountInfo);
				dispose();
			}
		});
		
		changeCurrencyTypeButton = new RoundedButton("Change currrency type");
		changeCurrencyTypeButton.setFocusable(false);
		changeCurrencyTypeButton.setPreferredSize(new Dimension(dimensions.tallFrameDimension.width*6/10, dimensions.tallFrameDimension.height*4/30));
		changeCurrencyTypeButton.setBorder(null);
		changeCurrencyTypeButton.setForeground(new Color(13, 17, 84));
		changeCurrencyTypeButton.setFont(new Font("",Font.PLAIN,25));
		changeCurrencyTypeButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new ChangeCurrencyType(accountInfo);
				dispose(); 
			}
		});
		
		cancelButton = new RoundedButton("Cancel");
		cancelButton.setFocusable(false);
		cancelButton.setPreferredSize(new Dimension(dimensions.tallFrameDimension.width*4/10, dimensions.tallFrameDimension.height*4/30));
		cancelButton.setBorder(null);
		cancelButton.setForeground(new Color(13, 17, 84));
		cancelButton.setFont(new Font("",Font.PLAIN,25));
		cancelButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					new MainAccount(accountInfo);
					dispose();
				} catch (SQLException e1) {
					System.out.println("Error back to account info!!!");
				}
			}
		});
		
		optionButtonsPanel.add(changeNameButton);
		optionButtonsPanel.add(changePassButton);
		optionButtonsPanel.add(changeCurrencyTypeButton);
		optionButtonsPanel.add(cancelButton);
		
		this.add(userInfoPanel, BorderLayout.NORTH);
		this.add(optionButtonsPanel, BorderLayout.SOUTH);

		this.setVisible(true);
	}
}
