package windows_and_panels;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import defualt.BrainFreezeMain;
import utility.Settings;

import javax.swing.JSplitPane;



/**
 * The main program window, contains tool bar and below it two section, left and right as a splitpane 
 * @author David
 *
 */
public class MainWindow extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3049174958821081866L;
	
	private JPanel contentPane;		// main content pane
	public static ImageIcon myAppImage;


	/**
	 * Launch the application.
	 */
	public void start(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainWindow frame = new MainWindow();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame for the main window.
	 */
	public MainWindow() {
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		setBounds(100, 100, 1200, 800);
		
		// set program icon
		URL url = BrainFreezeMain.class.getResource("/brain-icon-2.png");
		myAppImage = new ImageIcon(url);
        if(myAppImage != null)
            setIconImage(myAppImage.getImage());
        
        this.setTitle("BrainFreeze " + Settings.version + " - " + System.getProperty("user.dir"));
		
		// add content pain to main window
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
	
		TopToolBar.addTopToolBar(contentPane);
		
		// split area below toolbar into two
		JSplitPane splitPane = new JSplitPane();
		contentPane.add(splitPane, BorderLayout.CENTER);
		
		// fill in the left area
		LeftPanel.addLeftPanel(splitPane);
		RightPanel.addRightPanel(splitPane);
		


	}


	

}
