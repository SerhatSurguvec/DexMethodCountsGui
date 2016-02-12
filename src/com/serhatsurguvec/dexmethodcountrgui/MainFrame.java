package com.serhatsurguvec.dexmethodcountrgui;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileFilter;

import info.persistent.dex.Main;

import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;

public class MainFrame extends JFrame {

	private JPanel contentPane;
	private JTextField txtBrowseApk;
	private JFileChooser fc;
	private JButton btnStart;
	private JButton btnBrowse;
	private JTextArea label;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainFrame frame = new MainFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public MainFrame() {
		
		initFileChooser();
		
		this.setTitle("DexMethodCountsGui");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 600, 500);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		txtBrowseApk = new JTextField();
		txtBrowseApk.setText("Path to Apk");
		txtBrowseApk.setBounds(6, 6, 475, 26);
		contentPane.add(txtBrowseApk);
		txtBrowseApk.setColumns(10);
		
		btnBrowse = new JButton("Browse");
		btnBrowse.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
		        
				//In response to a button click:
				int returnVal = fc.showOpenDialog(btnBrowse);
				
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					File chosenFile = fc.getSelectedFile();
		            
		            if(chosenFile != null&& chosenFile.exists()){
		            	
						btnStart.setEnabled(true);
						txtBrowseApk.setText(chosenFile.getAbsolutePath());
						
					}
		           
		        }
				
			}
		});
		
		btnBrowse.setBounds(477, 6, 117, 29);
		contentPane.add(btnBrowse);
		
		btnStart = new JButton("Count");
		btnStart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String pathToApk = txtBrowseApk.getText();
				if(!isFileAvailable(pathToApk)){
					//Start Count
					Main main = new Main();
			        String str = main.run(new String[]{pathToApk});
			        label.setText(str);
				}else{
					
					JOptionPane.showMessageDialog(MainFrame.this,
						    "File related error occured",
						    "Error",
						    JOptionPane.ERROR_MESSAGE);
					
				}
		        
			}
		
		});
		
		btnStart.setBounds(477, 44, 117, 29);
		contentPane.add(btnStart);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setBounds(6, 85, 588, 387);
		scrollPane.setBorder(new EmptyBorder(10, 10, 10, 10));
		contentPane.add(scrollPane);
		
		label = new JTextArea("Please Choose Path To Apk");
		label.setOpaque(true);
		label.setEditable(false);
		label.setBackground(Color.WHITE);
		scrollPane.setViewportView(label);
	}
	
	private void initFileChooser() {
		
		fc = new JFileChooser();
		fc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
		fc.addChoosableFileFilter(new FileFilter() {

	        @Override
	        public boolean accept(File f) {
	            // TODO Auto-generated method stub
	            return f.getName().endsWith(".dex");
	        }

	        @Override
	        public String getDescription() {
	            return "Dex Files";
	        }

	    });
		
		
	}
	
	@SuppressWarnings("finally")
	private boolean isFileAvailable( String pathToApk) {
		
		try{
			if(!pathToApk.equals("")){
				File file = new File(pathToApk);
				if(file.exists()){
					if(file.getName().endsWith(".dex")){
						return true;
					}
				}
			}
		}catch(Exception ignored){}finally {
			return false;
		}
	
	}



	
}
