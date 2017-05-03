import java.awt.EventQueue;

import javax.swing.JFrame;

import com.alee.laf.WebLookAndFeel;

import java.awt.Color;
import java.awt.Toolkit;
import java.awt.Font;

import javax.swing.BoxLayout;
import javax.swing.JTabbedPane;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JLabel;



import javax.swing.JScrollPane;
import javax.swing.ImageIcon;
import javax.swing.JProgressBar;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JSlider;
import javax.swing.border.BevelBorder;
import javax.swing.SwingConstants;

import java.awt.Dimension;

import javax.swing.JRadioButton;

import java.awt.ComponentOrientation;

import javax.swing.JSeparator;
import javax.swing.ButtonGroup;

import java.awt.SystemColor;

import javax.swing.JTextArea;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;
import javax.swing.text.DefaultCaret;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;

import javax.swing.border.TitledBorder;
import javax.swing.UIManager;

import java.awt.Cursor;

public class GUI {

	public final String lstName = "Benchmark_listing.txt";
	
	public final String[] exe_names = {"cpp/AES.exe","cpp/Blowfish.exe","cpp/CAST5.exe","cpp/TDES.exe"}; 
	
	private 		JFrame frmBobcBeta;
	private final 	ButtonGroup buttonGroup = new ButtonGroup();
	public Process 	process;
	public Thread 	btnOneThread;
	String 			exe_name;
	FileWriter 		listing;
	
	CipherTestAll 	CTA;
	CipherTest 		CT;

	public static void main_GUI() throws Err {
		EventQueue.invokeLater(new Runnable() { 
			public void run() {
				try {
					//if (SystemUtils.isWindows())
					WebLookAndFeel.install();	// добавление библиотеки графических элементов
					GUI window = new GUI();
					window.frmBobcBeta.setLocationRelativeTo(null); // выравнивание окна
					window.frmBobcBeta.setVisible(true);
				} catch (Exception e) { new Err(0); }
			}
		});
	}

	public GUI() {
			initialize(); // инициализация графических элементов
	}

		private void initialize() {
		frmBobcBeta = new JFrame();
		frmBobcBeta.setResizable(false);
		frmBobcBeta.getContentPane().setFont(new Font("Segoe UI", Font.PLAIN, 12));
		frmBobcBeta.getContentPane().setBackground(Color.WHITE);
		frmBobcBeta.getContentPane().setLayout(new BoxLayout(frmBobcBeta.getContentPane(), BoxLayout.X_AXIS));
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setFont(new Font("Segoe UI", Font.PLAIN, 13));
		frmBobcBeta.getContentPane().add(tabbedPane);
		
		JPanel panel = new JPanel();
		panel.setToolTipText("DefuZe");
		panel.setPreferredSize(new Dimension(256, 10));
		panel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		tabbedPane.addTab("\u0411\u0435\u043D\u0447\u043C\u0430\u0440\u043A", new ImageIcon("imgs/bncmrk_logo.png"), panel, null);
		
		JLabel label = new JLabel("\u0420\u0435\u0430\u043B\u0438\u0437\u0430\u0446\u0438\u044F:");
		label.setToolTipText("\u0412\u044B\u0431\u043E\u0440 \u044F\u0437\u044B\u043A\u0430 \u0440\u0435\u0430\u043B\u0438\u0437\u0430\u0446\u0438\u0438 \u0430\u043B\u0433\u043E\u0440\u0438\u0442\u043C\u0430");
		label.setBounds(266, 26, 98, 19);
		label.setFont(new Font("Segoe UI", Font.PLAIN, 15));
		
		JLabel lblNewLabel = new JLabel("\u0410\u043B\u0433\u043E\u0440\u0438\u0442\u043C:");
		lblNewLabel.setBounds(6, 26, 82, 19);
		lblNewLabel.setFont(new Font("Segoe UI", Font.PLAIN, 15));
		
		JLabel lblNewLabel_1 = new JLabel("\u0411\u0443\u0444\u0435\u0440:");
		lblNewLabel_1.setBounds(6, 70, 73, 19);
		lblNewLabel_1.setFont(new Font("Segoe UI", Font.PLAIN, 15));
		
		JProgressBar progressBar = new JProgressBar();
		progressBar.setToolTipText("\u041E\u0442\u043E\u0431\u0440\u0430\u0436\u0435\u043D\u0438\u044F \u043F\u0440\u043E\u0446\u0435\u0441\u0441\u0430 \u0442\u0435\u0441\u0442\u0438\u0440\u043E\u0432\u0430\u043D\u0438\u044F");
		progressBar.setBackground(SystemColor.textHighlight);
		progressBar.setFont(new Font("Segoe UI Black", Font.PLAIN, 10));
		progressBar.setStringPainted(true);
		progressBar.setBounds(6, 386, 365, 19);


		
		JCheckBox chckbxNewCheckBox = new JCheckBox("\u0417\u0430\u043F\u0438\u0441\u044C \u0440\u0435\u0437\u0443\u043B\u044C\u0442\u0430\u0442\u043E\u0432 \u0432 \u0444\u0430\u0439\u043B");
		chckbxNewCheckBox.setToolTipText("\u0425\u043E\u0434 \u0442\u0435\u0441\u0442\u0438\u0440\u043E\u0432\u0430\u043D\u0438\u044F \u0431\u0443\u0434\u0435\u0442 \u0437\u0430\u043F\u0438\u0441\u0430\u043D \u0432 \u0444\u0430\u0439\u043B Listing.txt");
		chckbxNewCheckBox.setBounds(173, 113, 213, 27);
		chckbxNewCheckBox.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		
		JComboBox<String> comboBox = new JComboBox<String>();
		
		comboBox.setBounds(98, 23, 137, 25);
		comboBox.setModel(new DefaultComboBoxModel<String>(new String[] {"AES", "Blowfish", "CAST-128", "Triple-DES", "------------", "Bce"}));
		comboBox.setToolTipText("\u0412\u044B\u0431\u043E\u0440 \u043E\u0434\u043D\u043E\u0433\u043E \u0438\u043B\u0438 \u0432\u0441\u0435\u0445 \u0430\u043B\u0433\u043E\u0440\u0438\u0442\u043C\u043E\u0432");
		comboBox.setName("");
		comboBox.setFont(new Font("Calibri", Font.PLAIN, 15));
		
		
		
		JRadioButton rdbtnJava = new JRadioButton("Java\r\n");
		rdbtnJava.setBounds(362, 22, 53, 27);
		rdbtnJava.setFont(new Font("Segoe UI", Font.PLAIN, 15));
		buttonGroup.add(rdbtnJava);
		rdbtnJava.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		rdbtnJava.setSelected(true);
		
		JRadioButton rdbtnC = new JRadioButton("C++");
		rdbtnC.setBounds(417, 22, 66, 27);
		rdbtnC.setFont(new Font("Segoe UI", Font.PLAIN, 15));
		buttonGroup.add(rdbtnC);
		
		JSlider slider = new JSlider();
		slider.setBounds(71, 70, 346, 19);
		slider.setForeground(SystemColor.activeCaption);
		slider.setFont(new Font("Calibri", Font.PLAIN, 15));
		slider.setMajorTickSpacing(10);
		slider.setSnapToTicks(true);
		slider.setToolTipText("\u0420\u0430\u0437\u043C\u0435\u0440 \u0432\u0445\u043E\u0434\u043D\u044B\u0445 \u0434\u0430\u043D\u043D\u044B\u0445 \u0434\u043B\u044F \u0448\u0438\u0444\u0440\u043E\u0432\u0430\u043D\u0438\u044F");
		slider.setMinimum(10);
		slider.setMaximum(500);
		
		JLabel label_1 = new JLabel("50 \u041C\u0431\u0430\u0439\u0442");
		label_1.setBounds(421, 71, 64, 17);
		label_1.setFont(new Font("Calibri", Font.PLAIN, 14));		
		
		JButton btnNewButton = new JButton("\u0421\u0442\u0430\u0440\u0442");
		btnNewButton.setToolTipText("\u0421\u0442\u0430\u0440\u0442\u0443\u0435\u043C!");
		
		btnNewButton.setBounds(377, 142, 108, 29);
		btnNewButton.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		
		JLabel lblNewLabel_2 = new JLabel("<html>\u041D\u0430 \u0441\u043A\u043E\u0440\u043E\u0441\u0442\u044C<br>\r\n\u0432\u043B\u0438\u044F\u0435\u0442 \u0437\u0430\u0433\u0440\u0443\u0437\u043A\u0430<br>\r\n\u0426\u041F. \u0422\u0435\u0441\u0442\u044B <br>\r\n\u0432\u044B\u043F\u043E\u043B\u043D\u044F\u044E\u0442\u0441\u044F \u0432 <br>\r\n\u0432 \u041E\u0417\u0423 \u0438 \u043D\u0435 \u0437\u0430\u0432\u0438\u0441\u044F\u0442<br>\r\n\u043E\u0442 \u0445\u0430\u0440\u0430\u043A\u0442\u0435\u0440\u0438\u0441\u0442\u0438\u043A <br>\r\n\u0443\u0441\u0442\u0440\u043E\u0439\u0441\u0442\u0432 \u0445\u0440\u0430\u043D\u0435\u043D\u0438\u044F \u0434\u0430\u043D\u043D\u044B\u0445.\r\n</html>");
		lblNewLabel_2.setVerticalAlignment(SwingConstants.TOP);
		lblNewLabel_2.setToolTipText("\u0411\u043E\u043B\u044C\u0448\u0435 \u0438\u043D\u0444\u043E\u0440\u043C\u0430\u0446\u0438\u0438 \u043E \u043F\u0440\u043E\u0433\u0440\u0430\u043C\u043C\u0435 \u043C\u043E\u0436\u043D\u043E \u0443\u0437\u043D\u0430\u0442\u044C \u0443 \u0440\u0430\u0437\u0440\u0430\u0431\u043E\u0442\u0447\u0438\u043A\u0430");
		lblNewLabel_2.setBounds(377, 214, 112, 196);
		lblNewLabel_2.setHorizontalTextPosition(SwingConstants.LEADING);
		lblNewLabel_2.setMaximumSize(new Dimension(100, 14));
		lblNewLabel_2.setFont(new Font("Segoe UI", Font.PLAIN, 13));
		
		JLabel label_2 = new JLabel("\u0412\u044B\u0432\u043E\u0434 \u0440\u0435\u0437\u0443\u043B\u044C\u0442\u0430\u0442\u043E\u0432");
		label_2.setToolTipText("\u0417\u0434\u0435\u0441\u044C \u0432\u044B\u0432\u043E\u0434\u044F\u0442\u0441\u044F \u0440\u0435\u0437\u0443\u043B\u044C\u0442\u0430\u0442\u044B \u0442\u0435\u0441\u0442\u0430");
		label_2.setBounds(10, 117, 156, 19);
		label_2.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		
		JSeparator separator = new JSeparator();
		separator.setBounds(6, 101, 479, 2);
		
		JButton button = new JButton("\u041E\u0441\u0442\u0430\u043D\u043E\u0432\u0438\u0442\u044C");
		button.setToolTipText("\u0410\u0441\u0442\u0430\u043D\u0430\u0432\u0438\u0442\u0435\u0441\u044C!");
		
		button.setBounds(377, 174, 108, 29);
		button.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		panel.setLayout(null);
		panel.add(separator);
		panel.add(label_2);
		panel.add(chckbxNewCheckBox);
		panel.add(button);
		panel.add(btnNewButton);
		panel.add(lblNewLabel_2);
		panel.add(progressBar);
		panel.add(lblNewLabel);
		panel.add(comboBox);
		panel.add(label);
		panel.add(rdbtnJava);
		panel.add(rdbtnC);
		panel.add(lblNewLabel_1);
		panel.add(slider);
		panel.add(label_1);
		
		JSeparator separator_1 = new JSeparator();
		separator_1.setBounds(381, 209, 98, 2);
		panel.add(separator_1);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(6, 143, 365, 237);
		panel.add(scrollPane);
		
		JTextArea textArea = new JTextArea();
		scrollPane.setViewportView(textArea);
		textArea.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		textArea.setEditable(false);
		textArea.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		

		DefaultCaret caret = (DefaultCaret)textArea.getCaret();
		caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
		
		//-------------------------------------------------------------------------------
		slider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				label_1.setText(slider.getValue()+" Мбайт");
			}
		});
		
		
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				Date date = new Date();
				
				listing = null;
				if (chckbxNewCheckBox.isSelected())
			     {
			    	 try {
						listing = new FileWriter (lstName, true);
						listing.append("\n\n\n\t\t"+date.toString()+"\n");
					} catch (IOException e) {
						//e.printStackTrace();
					}
					

			     }
				
				btnNewButton.setEnabled(false);
				String alg_name = (String) comboBox.getSelectedItem();
				int size_mb = slider.getValue();
				
			if (alg_name=="------------"){
				btnNewButton.setEnabled(true);
				new Err(3);
				return;
			}
								
				if (rdbtnJava.isSelected())
				{
					try {
						listing.append("\t\t--- JAVA ---\t");
						listing.append(System.getProperty("os.name"));
						listing.append("\n");
						listing.close();
					} catch (NullPointerException | IOException e) {
							//e.printStackTrace();
					}
								switch (alg_name)
								{
								case "AES":
									CipherTest AES = new CipherTest(0, size_mb, textArea, progressBar, btnNewButton,lstName,0);
									
									btnOneThread = new Thread(AES,"btnOneThread");
									btnOneThread.start(); 
									break;
										
								case "Blowfish":
									CipherTest BF = new CipherTest(1, size_mb, textArea, progressBar, btnNewButton,lstName,0);
									btnOneThread = new Thread(BF,"btnOneThread");
									btnOneThread.start();
									break;
								
								case "CAST-128":
									CipherTest CAST = new CipherTest(2, size_mb, textArea, progressBar, btnNewButton,lstName,0);
									btnOneThread = new Thread(CAST,"btnOneThread");
									btnOneThread.start(); 
									break;
								
								case "Triple-DES":
									CipherTest TDES = new CipherTest(3, size_mb, textArea, progressBar, btnNewButton, lstName,0);
									btnOneThread = new Thread(TDES,"btnOneThread");
									btnOneThread.start(); 	
									break;
									
								
								case "Bce":
									CipherTestAll CiphAll = new CipherTestAll(size_mb, textArea, progressBar, btnNewButton, lstName);
									btnOneThread = new Thread(CiphAll,"btnOneThread");
									btnOneThread.start(); 
									break;
									
									default: break;
								}
								
								
						
				}
				else
				{			////////////////////C++
					
					try {
						listing.append("\t\t--- C++ ---\n");
						listing.close();
					} catch (NullPointerException | IOException e1) {}
					
					progressBar.setValue(2);
										
					switch (alg_name)
					{
					case "AES":
						 	CT = new CipherTest(size_mb, textArea, progressBar, btnNewButton, lstName, exe_names[0], 1);
							btnOneThread = new Thread(CT,"btnOneThread");
							btnOneThread.start();; 
						break;
					
					case "Blowfish":
						 CT = new CipherTest(size_mb, textArea, progressBar, btnNewButton, lstName, exe_names[1], 1);
							btnOneThread = new Thread(CT,"btnOneThread");
							btnOneThread.start(); 
						break;
										
					case "CAST-128":
						 CT = new CipherTest(size_mb, textArea, progressBar, btnNewButton, lstName, exe_names[2], 1);
							btnOneThread = new Thread(CT,"btnOneThread");
							btnOneThread.start();
						break;
						
					case "Triple-DES":
					    CT = new CipherTest(size_mb, textArea, progressBar, btnNewButton, lstName, exe_names[3], 1);
						btnOneThread = new Thread(CT,"btnOneThread");
						btnOneThread.start(); 
						break;
											
					case "Bce":
						CTA = new CipherTestAll(size_mb, textArea, progressBar, btnNewButton, lstName, exe_names);
					    btnOneThread = new Thread(CTA,"btnOneThread");
					    btnOneThread.start(); 
					    break;
					}
				}	
			} // end cpp block
		}	
	); // end ActionPerfomed()
							
		
		button.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
					try {
						if (btnOneThread.isAlive())
						{
						textArea.append("\nStopping... ");
						btnOneThread.interrupt();
						try {
							CTA.process.destroy();
						} catch (Exception e) {}
						try {
							CT.process.destroy();
						} catch (Exception e) {}
						
						Thread.sleep(500);
						textArea.append(" Done!");
						progressBar.setValue(0);
						btnNewButton.setEnabled(true);
						listing.close();
						} else {progressBar.setValue(0);btnNewButton.setEnabled(true);textArea.setText(null); new Err(1);}
					}
					catch (Exception e) 
					{
						btnNewButton.setEnabled(true); 
						progressBar.setValue(0); 
					}		
			}
		});
		
		JPanel panel_2 = new JPanel();
		tabbedPane.addTab("\u0421\u0432\u0435\u0434\u0435\u043D\u0438\u044F \u043E \u0441\u0438\u0441\u0442\u0435\u043C\u0435", new ImageIcon("imgs/sysinfo_logo.png"), panel_2, null);
		panel_2.setLayout(null);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(10, 64, 469, 312);
		panel_2.add(scrollPane_1);
		
		JTextArea textArea_1 = new JTextArea();
		textArea_1.setLineWrap(true);
		textArea_1.setFont(new Font("Segoe UI", Font.PLAIN, 13));
		textArea_1.setEditable(false);
		scrollPane_1.setViewportView(textArea_1);
		
		JProgressBar progressBar_1 = new JProgressBar();
		progressBar_1.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 12));
		progressBar_1.setBorder(null);
		progressBar_1.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		progressBar_1.setStringPainted(true);
		progressBar_1.setBounds(10, 381, 469, 24);
		
		panel_2.add(progressBar_1);
		
		JButton btnNewButton_1 = new JButton("\u0421\u043E\u0431\u0440\u0430\u0442\u044C \u0445\u0430\u0440\u0430\u043A\u0442\u0435\u0440\u0438\u0441\u0442\u0438\u043A\u0438");
		btnNewButton_1.setIcon(new ImageIcon("imgs/sysinfo_btn.png"));
		btnNewButton_1.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {		
				new SysProperties(textArea_1, progressBar_1,btnNewButton_1).start();
				
			}
		});
		
		
		
		btnNewButton_1.setBounds(10, 11, 469, 42);
		panel_2.add(btnNewButton_1);
		

		//-----------------------------------------------------------------------------------------------
		
		JPanel panel_1 = new JPanel();
		panel_1.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		tabbedPane.addTab("\u041E \u043F\u0440\u043E\u0433\u0440\u0430\u043C\u043C\u0435", new ImageIcon("imgs/help_logo.png"), panel_1, null);
		panel_1.setLayout(null);
		
		JPanel panel_3 = new JPanel();
		panel_3.setBounds(10, 11, 469, 319);
		panel_3.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "\u041E \u043F\u0440\u043E\u0433\u0440\u0430\u043C\u043C\u0435", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_1.add(panel_3);
		panel_3.setLayout(null);
		
		JLabel lblNewLabel_3 = new JLabel("<html>\u041F\u0440\u043E\u0433\u0440\u0430\u043C\u043C\u043D\u0430\u044F \u0441\u0438\u0441\u0442\u0435\u043C\u0430 Benchmark Of Block Ciphers (BOBC) \u043F\u0440\u0435\u0434\u043D\u0430\u0437\u043D\u0430\u0447\u0435\u043D\u0430\r\n\u0434\u043B\u044F \u0442\u0435\u0441\u0442\u0438\u0440\u043E\u0432\u0430\u043D\u0438\u044F \u0431\u044B\u0441\u0442\u0440\u043E\u0434\u0435\u0439\u0441\u0442\u0432\u0438\u044F (\u0431\u0435\u043D\u0447\u043C\u0430\u0440\u043A\u0430) \u0441\u0438\u043C\u043C\u0435\u0442\u0440\u0438\u0447\u043D\u044B\u0445 \u0430\u043B\u0433\u043E\u0440\u0438\u0442\u043C\u043E\u0432 \u0431\u043B\u043E\u0447\u043D\u043E\u0433\u043E \u0448\u0438\u0444\u0440\u043E\u0432\u0430\u043D\u0438\u044F AES, Blowfish, CAST-128 \u0438 Triple-DES.<br>\r\n\u0414\u043B\u044F \u0437\u0430\u043F\u0443\u0441\u043A\u0430 \u0431\u0435\u043D\u0447\u043C\u0430\u0440\u043A\u0430 \u043D\u0435\u043E\u0431\u0445\u043E\u0434\u0438\u043C\u043E \u0443\u043A\u0430\u0437\u0430\u0442\u044C \u0430\u043B\u0433\u043E\u0440\u0438\u0442\u043C \u0448\u0438\u0444\u0440\u043E\u0432\u0430\u043D\u0438\u044F(\u0435\u0441\u0442\u044C \u0432\u043E\u0437\u043C\u043E\u0436\u043D\u043E\u0441\u0442\u044C \u0443\u043A\u0430\u0437\u0430\u0442\u044C \u0432\u0441\u0435 \u0434\u043E\u0441\u0442\u0443\u043F\u043D\u044B\u0435 \u0430\u043B\u0433\u043E\u0440\u0438\u0442\u043C\u044B), \u0440\u0430\u0437\u043C\u0435\u0440 \u0442\u0435\u043A\u0441\u0442\u0430 \u0434\u043B\u044F \u0448\u0438\u0444\u0440\u043E\u0432\u0430\u043D\u0438\u044F (\u0431\u0443\u0444\u0435\u0440) \u0438 \u044F\u0437\u044B\u043A \u0440\u0435\u0430\u043B\u0438\u0437\u0430\u0446\u0438\u0438.\r\n\u0412\u0445\u043E\u0434\u043D\u044B\u0435 \u0434\u0430\u043D\u043D\u044B\u0435 \u043F\u0440\u0435\u0434\u0441\u0442\u0430\u0432\u043B\u0435\u043D\u044B \u0441\u043B\u0443\u0447\u0430\u0439\u043D\u043E \u0441\u0433\u0435\u043D\u0435\u0440\u0438\u0440\u043E\u0432\u0430\u043D\u043D\u044B\u043C\u0438 \u0431\u0430\u0439\u0442\u043E\u0432\u044B\u043C\u0438 \u043C\u0430\u0441\u0441\u0438\u0432\u0430\u043C\u0438 \u0437\u0430\u0434\u0430\u043D\u043D\u043E\u0433\u043E \u0431\u0443\u0444\u0435\u0440\u0430.<br>\r\n\u0421\u043A\u043E\u0440\u043E\u0441\u0442\u044C \u0448\u0438\u0444\u0440\u043E\u0432\u0430\u043D\u0438\u044F \u0437\u0430\u0432\u0438\u0441\u0438\u0442 \u043E\u0442 \u0437\u0430\u0433\u0440\u0443\u0436\u0435\u043D\u043D\u043E\u0441\u0442\u0438 \u0446\u0435\u043D\u0442\u0440\u0430\u043B\u044C\u043D\u043E\u0433\u043E \u043F\u0440\u043E\u0446\u0435\u0441\u0441\u043E\u0440\u0430 (\u0426\u041F). \u0422\u0435\u0441\u0442\u044B \u0432\u044B\u043F\u043E\u043B\u043D\u044F\u044E\u0442\u0441\u044F \u0432 \u043E\u043F\u0435\u0440\u0430\u0442\u0438\u0432\u043D\u043E\u0439 \u043F\u0430\u043C\u044F\u0442\u0438 \u0438 \u043D\u0435 \u0437\u0430\u0432\u0438\u0441\u044F\u0442 \u043E\u0442 \u0443\u0441\u0442\u0440\u043E\u0439\u0441\u0442\u0432 \u0445\u0440\u0430\u043D\u0435\u043D\u0438\u044F \u0434\u0430\u043D\u043D\u044B\u0445.<br><br><p align=\"center\">\u041F\u0440\u0438\u044F\u0442\u043D\u043E\u0439 \u0440\u0430\u0431\u043E\u0442\u044B!</p></html>\r\n");
		lblNewLabel_3.setToolTipText("\u0417\u0430 \u0434\u043E\u043F\u043E\u043B\u043D\u0438\u0442\u0435\u043B\u044C\u043D\u043E\u0439 \u0438\u043D\u0444\u043E\u0440\u043C\u0430\u0446\u0438\u0435\u0439 \u043E\u0431\u0440\u0430\u0449\u0430\u0439\u0442\u0435\u0441\u044C \u043A \u0440\u0430\u0437\u0440\u0430\u0431\u043E\u0442\u0447\u0438\u043A\u0443");
		lblNewLabel_3.setVerticalAlignment(SwingConstants.TOP);
		lblNewLabel_3.setBackground(SystemColor.text);
		lblNewLabel_3.setBounds(10, 25, 449, 283);
		panel_3.add(lblNewLabel_3);
		lblNewLabel_3.setHorizontalTextPosition(SwingConstants.CENTER);
		lblNewLabel_3.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_3.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		
		JPanel panel_4 = new JPanel();
		panel_4.setToolTipText("\u0411\u044B\u0441\u0442\u0440\u0435\u0435 \u0431\u0443\u0434\u0435\u0442 \u0441\u0432\u044F\u0437\u0430\u0442\u044C\u0441\u044F \u0447\u0435\u0440\u0435\u0437 Telegram :)");
		panel_4.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "\u041A\u043E\u043D\u0442\u0430\u043A\u0442\u044B \u0440\u0430\u0437\u0440\u0430\u0431\u043E\u0442\u0447\u0438\u043A\u0430", TitledBorder.CENTER, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel_4.setBounds(10, 341, 469, 64);
		panel_1.add(panel_4);
		panel_4.setLayout(null);
		
		JLabel lblNewLabel_4 = new JLabel("E-mail: defuze@etlgr.com\r\n");
		lblNewLabel_4.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		lblNewLabel_4.setBounds(10, 21, 264, 32);
		panel_4.add(lblNewLabel_4);
		
		JLabel lblNewLabel_5 = new JLabel("Telegram: defuze");
		lblNewLabel_5.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		lblNewLabel_5.setBounds(348, 27, 111, 21);
		panel_4.add(lblNewLabel_5);
		
		
		frmBobcBeta.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		frmBobcBeta.setIconImage(Toolkit.getDefaultToolkit().getImage("imgs/prgrm_logo.png"));
		frmBobcBeta.setBackground(Color.WHITE);
		frmBobcBeta.setForeground(Color.WHITE);
		frmBobcBeta.setTitle("BOBC 0.1");
		frmBobcBeta.setBounds(100, 100, 500, 480);
		frmBobcBeta.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
