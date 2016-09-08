package windows_and_panels;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridLayout;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import defualt.BrainFreezeMain;
import utility.DirectoryChooser;
import utility.HintTextField;
import utility.Settings;
import utility.UsefulMethods;

/**
 * Show program settings and allow them to be edited. 
 * Each subclass represents different setting
 * @author David
 *
 */
public class SettingsPanel extends JPanel{ // once again extending but not really using it... the thought was there at least. 

	private static final long serialVersionUID = -1839534030129102904L;

	private static JPanel settingsPane;
	private static JScrollPane scrollPane;
	private static JLabel settingsPageTitle = new JLabel("Program Settings:");
	
	static Font f = new Font("Serif", Font.PLAIN, 16); // font for various text in buttons and the like. 

	public static void getSettingsPanel(JLabel windowRight) {

		settingsPane = new JPanel();
		settingsPane.setLayout(new GridLayout(6,1));	// main grid, simply one line under the other 

		settingsPageTitle.setFont(new Font("Serif", Font.PLAIN, 24));
		settingsPageTitle.setHorizontalAlignment(JLabel.CENTER);
		settingsPageTitle.setBorder(BorderFactory.createEtchedBorder());
		settingsPane.add(settingsPageTitle);

		ClickBetweenSoundsSettings betweenSoundsSection = new ClickBetweenSoundsSettings();
		betweenSoundsSection.setBorder(BorderFactory.createEtchedBorder());
		settingsPane.add(betweenSoundsSection);

		DataPointCountSelection dataPointCountSection = new DataPointCountSelection();
		dataPointCountSection.setBorder(BorderFactory.createEtchedBorder());
		settingsPane.add(dataPointCountSection);



		ID_FileChoise fileSection = new ID_FileChoise();
		fileSection.setBorder(BorderFactory.createEtchedBorder());
		settingsPane.add(fileSection);

		Mean_SD_Choice mean_SD_section = new Mean_SD_Choice();
		mean_SD_section.setBorder(BorderFactory.createEtchedBorder());
		settingsPane.add(mean_SD_section);

		scrollPane = new JScrollPane(settingsPane);

		windowRight.add(scrollPane, BorderLayout.CENTER);
	}


	/**
	 * Class for subpanel responsible for transition between stimulants method setting
	 * @author David
	 *
	 */
	private static class ClickBetweenSoundsSettings extends JPanel{	// extends and this time it IS used. Hurah. 

		private static final long serialVersionUID = -1450084078879133120L;
		JRadioButton onClick;
		JRadioButton onDelay;
		HintTextField   delay;
		JLabel title;

		public ClickBetweenSoundsSettings() {

			title = new JLabel("- Transaction between stimulants in test:");
			title.setFont(new Font("Serif", Font.PLAIN, 20));

			onClick = new JRadioButton("On User Click");
			onClick.setFont(f);
			onDelay = new JRadioButton("After time delay [mSec]: ");
			onDelay.setFont(f);
			
			ButtonGroup choices = new ButtonGroup();
			choices.add(onClick);
			choices.add(onDelay);
			if (Settings.clickBtweenSounds)
				onClick.setSelected(true);
			else
				onDelay.setSelected(true);

			int temp  = (Settings.customDelayBetweenSounds == -1 ? Settings.defaultDelayBetweenSounds : Settings.customDelayBetweenSounds);
			delay = new HintTextField(Integer.toString(temp), utility.UsefulMethods.getNumberFormatter());
			delay.setToolTipText("The time delay, in miliseconds, between sounds in the test");


			onDelay.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					if (onDelay.isSelected()){
						delay.setEnabled(true);
						int temp  = (Settings.customDelayBetweenSounds == -1 ? Settings.defaultDelayBetweenSounds : Settings.customDelayBetweenSounds);
						delay.setText(Double.toString(temp));
						Settings.clickBtweenSounds = false;
						Settings.save();
					}

				}
			});

			onClick.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					if (onClick.isSelected()){
						delay.setEnabled(false);
						Settings.clickBtweenSounds = true;
						Settings.save();
					}

				}
			});

			delay.getDocument().addDocumentListener(new DocumentListener() {

				@Override
				public void removeUpdate(DocumentEvent e) {
					updateDelaySettings(delay.getText());
				}
				@Override
				public void insertUpdate(DocumentEvent e) {
					updateDelaySettings(delay.getText());					
				}
				@Override
				public void changedUpdate(DocumentEvent e) {
					updateDelaySettings(delay.getText());					
				}
			});

			JButton resetDefault = new JButton("Reset to Default Settings");
			resetDefault.setFont(new Font("Serif", Font.PLAIN, 14));
			resetDefault.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					onDelay.doClick();
					updateDelaySettings("-1");
					delay.setText(Integer.toString(Settings.defaultDelayBetweenSounds));
				}
			});

			this.setLayout(new GridLayout(0, 3));

			this.add(title);	this.add(new JLabel());	this.add(new JLabel());	
			this.add(new JLabel());	this.add(onClick); 	this.add(new JLabel());	
			
			JPanel delayHolder = new JPanel((LayoutManager) new GridLayout(1,3));
			delay.setColumns(10); 
			delayHolder.add(delay);	delayHolder.add(new JLabel());	
			
			this.add(new JLabel());	this.add(onDelay);	this.add(delayHolder);	
			this.add(new JLabel());	this.add(new JLabel());	this.add(new JLabel());	
			
			JPanel resetHolder = new JPanel();
			resetHolder.add(resetDefault);
			this.add(resetHolder);

		}
	}

	/**
	 * Class for subpanel responsible for the data point amount setting
	 * @author David
	 *
	 */
	private static class DataPointCountSelection extends JPanel{

		private static final long serialVersionUID = 8783734234262022652L;
		JRadioButton defaultCount;
		JRadioButton customCount;
		HintTextField   count;
		JLabel title;

		public DataPointCountSelection() {

			title = new JLabel("- No. of data points per stimulant:");
			title.setFont(new Font("Serif", Font.PLAIN, 18));

			defaultCount = new JRadioButton("Use Default Settings.");
			defaultCount.setFont(f);
			customCount = new JRadioButton("Choose Custom Data Point Count");
			customCount.setFont(f);
			
			ButtonGroup choices = new ButtonGroup();
			choices.add(defaultCount);
			choices.add(customCount);
			if (Settings.custom_dataPointCount == -1)
				defaultCount.setSelected(true);
			else
				customCount.setSelected(true);

			int temp  = (Settings.custom_dataPointCount == -1 ? Settings.default_dataPointCount : Settings.custom_dataPointCount);
			count = new HintTextField(Integer.toString(temp), utility.UsefulMethods.getNumberFormatter());
			count.setToolTipText("Number of times each stimulant will be presented to patient");


			customCount.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					if (customCount.isSelected()){
						count.setEnabled(true);
						int temp  = (Settings.custom_dataPointCount == -1 ? Settings.default_dataPointCount : Settings.custom_dataPointCount);
						count.setText(Double.toString(temp));
					}

				}
			});

			defaultCount.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					if (defaultCount.isSelected()){
						count.setEnabled(false);
						Settings.save();
					}

				}
			});

			count.getDocument().addDocumentListener(new DocumentListener() {

				@Override
				public void removeUpdate(DocumentEvent e) {
					updateDataPointSettings(count.getText());
				}
				@Override
				public void insertUpdate(DocumentEvent e) {
					updateDataPointSettings(count.getText());					
				}
				@Override
				public void changedUpdate(DocumentEvent e) {
					updateDataPointSettings(count.getText());					
				}
			});

			JButton resetDefault = new JButton("Reset to Default Settings");
			resetDefault.setFont(new Font("Serif", Font.PLAIN, 14));
			resetDefault.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					defaultCount.doClick();
					updateDelaySettings("-1");
					count.setText(Integer.toString(Settings.default_dataPointCount));
				}
			});

			this.setLayout(new GridLayout(0, 3));

			this.add(title);	this.add(new JLabel());	this.add(new JLabel());
			this.add(new JLabel());	this.add(defaultCount); 	this.add(new JLabel());	
			
			JPanel countHolder = new JPanel((LayoutManager) new GridLayout(1,3));
			count.setColumns(10); 
			countHolder.add(count);	countHolder.add(new JLabel());	
				
			this.add(new JLabel());	this.add(customCount);	this.add(countHolder);	
			this.add(new JLabel());	this.add(new JLabel());	this.add(new JLabel());	
			
			JPanel resetHolder = new JPanel();
			resetHolder.add(resetDefault);
			this.add(resetHolder);

		}
	}

	/**
	 * Class for subpanel responsible for which stimulants to use setting
	 * @author David
	 *
	 */
	private static class ID_FileChoise extends JPanel{

		private static final long serialVersionUID = -310684672389086176L;
		JRadioButton defaultFiles;
		JRadioButton customFiles;
		JLabel customLocation;
		JLabel title;
		DirectoryChooser fileChooser;
		JButton infoButton;
		JButton invokeDirectorySearch;

		public ID_FileChoise(){

			//String typename = type == Settings.ITD ? "ITD" : "ILD";

			defaultFiles = new JRadioButton("Use built in audio");
			defaultFiles.setFont(f);
			customFiles = new JRadioButton("Use custom audio files");
			customFiles.setFont(f);
			
			ButtonGroup choices = new ButtonGroup();
			choices.add(customFiles);
			choices.add(defaultFiles);

			

			if (Settings.useDefaultSounds_ILD && Settings.useDefaultSounds_ITD){
				defaultFiles.setSelected(true);
			}
			else{
				customFiles.setSelected(true);
			}
			
			invokeDirectorySearch = new JButton("Choose Folder");
			invokeDirectorySearch.setFont(f);
			invokeDirectorySearch.setEnabled(!defaultFiles.isSelected());

			defaultFiles.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					if (defaultFiles.isSelected()){

						Settings.useDefaultSounds_ITD = true;
						Settings.useDefaultSounds_ILD = true;

						Settings.save();
						customLocation.setText("Using built in default audio stimulants");
						invokeDirectorySearch.setEnabled(false);
						
						Settings.ITD_files = "";
						Settings.ILD_files = "";
						
					}
				}
			});

			customFiles.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					if (customFiles.isSelected()){

						Settings.useDefaultSounds_ITD = false;
						Settings.useDefaultSounds_ILD = false;
						String loc = Settings.ILD_files == "" ? "Choose custom directory" : Settings.ILD_files;
						customLocation.setText(loc);	

						Settings.save();
						invokeDirectorySearch.setEnabled(true);
					}
				}
			});

			customLocation = new JLabel();
			customLocation.setFont(f);
			customLocation.setFocusable(false);
			boolean temp = Settings.useDefaultSounds_ITD && Settings.useDefaultSounds_ILD;
			if (temp){
				customLocation.setText("Using built in default audio stimulants");
			}
			else{

				String loc = Settings.ILD_files == "" ? "Choose custom directory" : Settings.ILD_files;
				customLocation.setText(loc);	
			}

			title = new JLabel("- Audio stimulants for examination:");
			title.setFont(new Font("Serif", Font.PLAIN, 20));

			infoButton = new JButton();
			ImageIcon icon = new ImageIcon(BrainFreezeMain.
					class.getResource("/toolbarButtonGraphics/general/Information24.gif"));
			infoButton.setIcon(icon);
			infoButton.setText("Help");
			infoButton.setFont(f);
			infoButton.setBorder(BorderFactory.createEmptyBorder());
			infoButton.setContentAreaFilled(false);
			infoButton.setToolTipText("Help: Acpetable audio file format");
			infoButton.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					JOptionPane.showMessageDialog(null,
							"<html><pre>"
									+ "Select folder containing ITD sound files.<br>"
									+ "Files must be in .wav format and named \"ITD_X.wav\" or \"ILD_X.wav\"<br>"
									+ "Where X represents the bilateral location cooresponding to the sound stimulant [1,2,3,...,9].<br>"
									+ "A total of 18 wav files MUST exist in chosen folder, one per location, per stimulant type.<br>"
									+ "</pre></html>", "Help Message", JOptionPane.INFORMATION_MESSAGE);
				}
			});


			fileChooser = new DirectoryChooser();


			invokeDirectorySearch.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					fileChooser.actionPerformed(null);
					File result = fileChooser.getDirectory();
					if (result != null){
						String directoryName = result.getAbsolutePath();

						Settings.ITD_files = directoryName;
						Settings.ILD_files = directoryName;
						String loc = Settings.ILD_files == "" ? "Choose custom directory" : Settings.ILD_files;

						if (!UsefulMethods.checkForFiles(loc)){
							JOptionPane.showMessageDialog(null,
									"Invalid Audio File Location Chosen\n"
									+ "Make Sure All Files Exist In Supplied Directory.",
									"Error",
									JOptionPane.ERROR_MESSAGE);
							
							defaultFiles.doClick();
							return;
						}
						customLocation.setText(loc);
						Settings.save();

					}

				}
			});

			JButton resetDefault = new JButton("Reset to Default Settings");
			resetDefault.setFont(new Font("Serif", Font.PLAIN, 14));
			resetDefault.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					defaultFiles.doClick();
					updateDelaySettings("-1");
					customLocation.setText("Choose cutom file location");
					Settings.save();
				}
			});

			this.setLayout(new GridLayout(0, 3));

			this.add(title);	this.add(new JLabel());	this.add(new JLabel());
			this.add(new JLabel());	this.add(defaultFiles); 	this.add(new JLabel());	
			this.add(new JLabel());	this.add(customFiles);
			
			JPanel infoButtonHolder = new JPanel(new FlowLayout(FlowLayout.LEFT));
			infoButtonHolder.add(infoButton);
			this.add(infoButtonHolder);	
			
			this.add(new JLabel());	this.add(customLocation);
			JPanel tempPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
			tempPanel.add(invokeDirectorySearch);
			this.add(tempPanel);
			
			
			JPanel resetHolder = new JPanel();
			resetHolder.add(resetDefault);
			this.add(resetHolder);
		}

	}

	private static void updateDelaySettings(String s){
		int v;
		try{
			v = Integer.parseInt(s);
		}
		catch(Exception e){
			return;
		}
		Settings.customDelayBetweenSounds = v;
		Settings.save();
	}

	private static void updateDataPointSettings(String s){
		int v;
		try{
			v = Integer.parseInt(s);

		}
		catch(Exception e){
			return;
		}

		try{
			if (v < 1)
				throw new Exception();
		}
		catch(Exception e){
			JOptionPane.showMessageDialog(null,
					"Invalid Data Point Amount Chosen",
					"Error",
					JOptionPane.ERROR_MESSAGE);
			return;
		}
		Settings.custom_dataPointCount = v;
		Settings.save();
	}





	/**
	 * Class for subpanel responsible for SD and mean values setting
	 * @author David
	 *
	 */
	private static class Mean_SD_Choice extends JPanel{

		private static final long serialVersionUID = -5672822655986950046L;

		JCheckBox allowAdvancedEdit;

		// first 2 for ITD mean, next for ILD mean, next ITD SD, nxt ILD SD
		JFormattedTextField[] cells = new JFormattedTextField[8];
		JLabel[] locations = new JLabel[2];
		JLabel[] colNames = new JLabel[4]; // ITD mean, ILD mean, ITD SD, ILD SD 

		public Mean_SD_Choice() {

			allowAdvancedEdit = new JCheckBox("Select to enable editting");
			allowAdvancedEdit.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					if (allowAdvancedEdit.isSelected())
						enableFields();
					else
						disableFields();
				}
			});

			initLocations();
			initColNames();
			initCells();

			this.setLayout(new GridLayout(0, 5));
			this.add(allowAdvancedEdit);
			for (JLabel l : colNames){
				this.add(l);
			}

			int i = 0;
			for (JLabel l : locations){
				this.add(l);
				for (int j = 0; j < 4; j++){
					this.add(cells[i + 2 * j]);
				}
				i++;
			}

			JButton resetDefault = new JButton("Reset to Default Settings");
			resetDefault.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					Settings.thetaToDefault();
					valueToCells();
					if (allowAdvancedEdit.isSelected())
						allowAdvancedEdit.doClick();
				}
			});
			this.add(resetDefault);
		}

		private void enableFields(){
			for (JFormattedTextField t : cells)
				t.setEnabled(true);
		}
		private void disableFields(){
			for (JFormattedTextField t : cells)
				t.setEnabled(false);
		}

		private void initLocations(){
			locations[0] = new JLabel("Sides");
			locations[1] = new JLabel("Center");
			//locations[2] = new JLabel("Right");
		}

		private void initColNames(){
			colNames[0] = new JLabel("ITD mean");
			colNames[1] = new JLabel("ILD mean");
			colNames[2] = new JLabel("ITD SD");
			colNames[3] = new JLabel("ILD SD");
		}

		private void initCells(){

			for (int i = 0; i < cells.length; i++){
				cells[i] = new JFormattedTextField(utility.UsefulMethods.getNumberFormatter());
				cells[i].setEnabled(false);
			}

			valueToCells();
		}

		private void valueToCells(){
			for (int i = 0; i < 2; i++){
				cells[i].setText(Double.toString(Settings.thetaMean_ITD[i]));
				cells[i].getDocument().addDocumentListener(getCellActionListener(i, 0, Settings.thetaMean_ITD));
			}
			for (int i = 0; i < 2; i++){
				cells[i + 2].setText(Double.toString(Settings.thetaMean_ILD[i]));
				cells[i + 2].getDocument().addDocumentListener(getCellActionListener(i, 1, Settings.thetaMean_ILD));
			}
			for (int i = 0; i < 2; i++){
				cells[i + 4].setText(Double.toString(Settings.thetaSD_ITD[i]));
				cells[i + 4].getDocument().addDocumentListener(getCellActionListener(i, 2, Settings.thetaSD_ITD));
			}
			for (int i = 0; i < 2; i++){
				cells[i + 6].setText(Double.toString(Settings.thetaSD_ILD[i]));
				cells[i + 6].getDocument().addDocumentListener(getCellActionListener(i, 3, Settings.thetaSD_ILD));
			}
		}

		private DocumentListener getCellActionListener(int i, int j, double[] editMe){


			return new DocumentListener() {

				@Override
				public void removeUpdate(DocumentEvent e) {
					updateThetaSettings(i, j, editMe);
				}
				@Override
				public void insertUpdate(DocumentEvent e) {
					updateThetaSettings(i, j, editMe);					
				}
				@Override
				public void changedUpdate(DocumentEvent e) {
					//updateThetaSettings(i, j, editMe);					
				}
			};
		}

		private void updateThetaSettings(int i, int j, double[] editMe){
			String input = cells[i + 2*j].getText();
			try{
				editMe[i] = Double.parseDouble(input);
				Settings.save();
			}
			catch(Exception e){};
		}
	}


}

