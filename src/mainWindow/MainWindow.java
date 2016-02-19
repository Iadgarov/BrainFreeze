package mainWindow;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;


import javax.swing.JSplitPane;




public class MainWindow extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3049174958821081866L;
	
	private JPanel contentPane;		// main content pane


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
		setBounds(100, 100, 1000, 800);
		
		// set program icon
		ImageIcon myAppImage = loadIcon("brain-icon-2.png");
        if(myAppImage != null)
            setIconImage(myAppImage.getImage());
		
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

   private ImageIcon loadIcon(String strPath){
        Image img = null;
		try {
			img = ImageIO.read(new File(strPath));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        if(img != null)
            return new ImageIcon(img);
        else
            return null;
        
    }

	

}
