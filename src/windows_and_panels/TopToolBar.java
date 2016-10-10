package windows_and_panels;

import java.awt.BorderLayout;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JToolBar;

import buttons.HelpButton;
import buttons.LoadPatientButton;
import buttons.NewPatientButton;
import buttons.SavePatientButton;
import buttons.SettingsButton;

/**
 * The top toolbar
 * @author David Iadgarov
 *
 */
public class TopToolBar {
	
	static Font f = new Font("Serif", Font.PLAIN, 16);

	/**
	 * add tool bar to top of content pane
	 * @param contentPane
	 */
	protected static void addTopToolBar(JPanel contentPane){
		
		// set top toolbar
		JToolBar toolBar = new JToolBar();
		contentPane.add(toolBar, BorderLayout.NORTH);
		toolBar.setFloatable(false);
		toolBar.setBorder(BorderFactory.createEtchedBorder());
		
		
		addNewPatientToolBarButton(toolBar); // new patient button on toolbar at top
		toolBar.addSeparator();	toolBar.addSeparator();
		addLoadPatientToolBarButton(toolBar); // load patient button on toolbar at top
		toolBar.addSeparator();	toolBar.addSeparator();
		addSavePatientToolBarButton(toolBar);
		toolBar.addSeparator();	toolBar.addSeparator();
		
		addSettingsToolBarButton(toolBar);
		toolBar.addSeparator();	toolBar.addSeparator();
		
		addHelpToolBarButton(toolBar);
		toolBar.addSeparator();	toolBar.addSeparator();
		
	}
	
	/**
	 * adds new patient button to toolbar at screen top
	 * upon click data may be entered to create a new patient object
	 * @param toolBar
	 */
	protected static void addNewPatientToolBarButton(JToolBar toolBar){
		// create buttons to go inside toolbar
		NewPatientButton newPatientButton = new NewPatientButton();
		newPatientButton.setFont(f);
		newPatientButton.setListeners();
		toolBar.add(newPatientButton);
	}
	
	
	protected static void addLoadPatientToolBarButton(JToolBar toolBar){
		// create buttons to go inside toolbar
		LoadPatientButton loadPatientButton = new LoadPatientButton();
		loadPatientButton.setFont(f);
		loadPatientButton.setListeners(toolBar);
		toolBar.add(loadPatientButton);
		
	}
	
	protected static void addSavePatientToolBarButton(JToolBar toolBar){
		// create buttons to go inside toolbar
		SavePatientButton savePatientButton = new SavePatientButton();
		savePatientButton.setFont(f);
		savePatientButton.setListeners(toolBar);
		toolBar.add(savePatientButton);
		
	}
	
	protected static void addSettingsToolBarButton(JToolBar toolBar){
		// create buttons to go inside toolbar
		SettingsButton settingsButton = new SettingsButton();
		settingsButton.setFont(f);
		settingsButton.setListeners();
		toolBar.add(settingsButton);
		
	}
	
	protected static void addHelpToolBarButton(JToolBar toolBar){
		// create buttons to go inside toolbar
		HelpButton hb = new HelpButton();
		hb.setFont(f);
		hb.setListeners();
		toolBar.add(hb);
	}

}
