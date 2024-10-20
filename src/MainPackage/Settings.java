package MainPackage;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import HelpPackage.Constants.icons;

public class Settings extends JFrame{
	
	private JPanel optionButtonsPanel;
	private JPanel userInfoPanel;
	private JButton changeNameButton;
	private JButton changePassButton;
	private JButton changeCurrencyTypeButton;
	private JButton cancelButton;
	private JLabel usersNameLabel;
	private JLabel usersCurrencyTypeLabel;
	
	public Settings(ResultSet accountInfo) {
		this.setTitle("Settings");
		this.setSize(700, 600);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setIconImage(icons.cogwheelIcon.getImage());
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLayout(new GridLayout(2,1));
		
		optionButtonsPanel = new JPanel();
		optionButtonsPanel.setPreferredSize(new Dimension(325, 600));
		optionButtonsPanel.setLayout(new FlowLayout(FlowLayout.CENTER,25,50));
		optionButtonsPanel.setBackground(new Color(176, 174, 167));
		
		changeNameButton = new JButton();
		changeNameButton.setText("Change name");
		changeNameButton.setFocusable(false);
		changeNameButton.setPreferredSize(new Dimension(270, 80));
		changeNameButton.setBackground(new Color(39, 255, 36));
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
		
		changePassButton = new JButton();
		changePassButton.setText("Change password");
		changePassButton.setFocusable(false);
		changePassButton.setPreferredSize(new Dimension(270, 80));
		changePassButton.setBackground(new Color(39, 255, 36));
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
		
		changeCurrencyTypeButton = new JButton();
		changeCurrencyTypeButton.setText("Change currrency type");
		changeCurrencyTypeButton.setFocusable(false);
		changeCurrencyTypeButton.setPreferredSize(new Dimension(270, 80));
		changeCurrencyTypeButton.setBackground(new Color(39, 255, 36));
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
		
		cancelButton = new JButton();
		cancelButton.setText("Cancel");
		cancelButton.setFocusable(false);
		cancelButton.setPreferredSize(new Dimension(270, 80));
		cancelButton.setBackground(new Color(39, 255, 36));
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
		
		userInfoPanel = new JPanel();
		userInfoPanel.setPreferredSize(new Dimension(350, 600));
		userInfoPanel.setLayout(new FlowLayout(FlowLayout.CENTER,0,60));
		//userInfoPanel.setBackground(Color.magenta);
		
		usersNameLabel = new JLabel();
		try {
			usersNameLabel.setText(accountInfo.getString(1));
			usersNameLabel.setFont(new Font("Agdasima", Font.ITALIC, 70));
		} catch (SQLException e1) {
			System.out.println("isim yok!!!");
		}
		
		usersCurrencyTypeLabel = new JLabel();
		usersCurrencyTypeLabel.setFont(new Font("Agdasima", Font.ITALIC, 45));
		try {
			usersCurrencyTypeLabel.setText("Currency type: " + accountInfo.getString(5));
		} catch (SQLException e1) {
			System.out.println("para birimi alma hatası!!");
		}
		
		userInfoPanel.add(usersNameLabel);
		userInfoPanel.add(usersCurrencyTypeLabel);
		
		this.add(userInfoPanel);
		this.add(optionButtonsPanel);

		this.setVisible(true);
	}
}
