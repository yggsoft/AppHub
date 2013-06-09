package com.ecwise.logger.client;

import java.awt.BorderLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import com.angelo.logging.db.DataCentre;
import com.angelo.logging.templete.StringRule;
import com.angelo.logging.templete.Templete;

public class Client extends JFrame {
	private static final long serialVersionUID = 1L;
	private JButton addBtn;
	private DataCentre dataCentre;

	public static void main(String[] args) {
		new Client();
	}
	
	public Client() {
		this.setTitle("Logger Analysis Terminal");
//		this.setSize(Toolkit.getDefaultToolkit().getScreenSize());
		this.setSize(800, 600);
		this.setVisible(true);
		this.setResizable(false);
		this.addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		initGUI();
	}

	private void initGUI() {
		//left panel
		JPanel leftPanel = new JPanel();
		leftPanel.setLayout(null);
		leftPanel.setSize(200, 600);
		
		JLabel urlLabel = new JLabel("JDBC URL");
		urlLabel.setBounds(10, 25, 150, 20);
		JTextField urlTextField = new JTextField();
		urlTextField.setBounds(10, 55, 150, 20);
		leftPanel.add(urlLabel);
		leftPanel.add(urlTextField);
		
		JLabel userNameLabel = new JLabel("UserName");
		userNameLabel.setBounds(10, 85, 150, 20);
		JTextField userNameTextField = new JTextField();
		userNameTextField.setBounds(10, 115, 150, 20);
		leftPanel.add(userNameLabel);
		leftPanel.add(userNameTextField);
		
		JLabel passwordLabel = new JLabel("Password");
		passwordLabel.setBounds(10, 145, 150, 20);
		JTextField passwordTextField = new JTextField();
		passwordTextField.setBounds(10, 175, 150, 20);
		leftPanel.add(passwordLabel);
		leftPanel.add(passwordTextField);
		
		leftPanel.setBorder(new TitledBorder("Database Info"));
		JButton left1Panel = new JButton("3");
		this.add(leftPanel, BorderLayout.WEST);
		
		//center panel
		JPanel centerPanel = new JPanel();
		centerPanel.setLayout(null);
		centerPanel.setSize(600, 600);
		
		JLabel titleLabel = new JLabel("Title");
		titleLabel.setBounds(10, 25, 150, 20);
		JTextField titleTextField = new JTextField();
		titleTextField.setBounds(10, 55, 150, 20);
		centerPanel.add(titleLabel);
		centerPanel.add(titleTextField);
		
		this.add(centerPanel, BorderLayout.CENTER);
		
		addBtn = new JButton("1");
		addBtn.addActionListener(new AddTempleteListener());
		JButton northPanel = new JButton("2");
		JButton southPanel = new JButton("3");
		
		
		this.add(addBtn, BorderLayout.EAST);
		this.add(northPanel, BorderLayout.NORTH);
		this.add(southPanel, BorderLayout.SOUTH);
	}
	
	private class AddTempleteListener implements ActionListener{

		public void actionPerformed(ActionEvent e) {
			if(e.getSource() == addBtn){
				dataCentre = new DataCentre("","","");
				
				Templete templete = new Templete();
//				templete.setTitle(title);
//				templete.setCategory(category);
//				templete.setName(name);
//				templete.setRCA(rCA);
//				templete.setReProduceSteps(reProduceSteps);
//				templete.setTimestamp(timestamp);
				
				StringRule rule = new StringRule();
//				rule.setFeature(feature);

				dataCentre.addTempleteRule(templete, rule);
			}
		}
	}
}
