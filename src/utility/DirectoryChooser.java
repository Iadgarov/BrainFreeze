package utility;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JPanel;

/**
 * A file chooser that only accepts directories. Used to load custom sounds for program. 
 * Invoked in settings Panel when choose folder button is clicked 
 * @author David
 *
 */
public class DirectoryChooser extends JPanel implements ActionListener {

	private static final long serialVersionUID = -5841122766311402625L;

	JButton go;

	JFileChooser chooser;
	String choosertitle;
	File directory;

	public DirectoryChooser() {
		go = new JButton("Do it");
		go.addActionListener(this);
		add(go);
	}

	public void actionPerformed(ActionEvent e) {

		chooser = new JFileChooser(); 
		chooser.setCurrentDirectory(new java.io.File("."));
		chooser.setDialogTitle(choosertitle);
		chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		//
		// disable the "All files" option.
		//
		chooser.setAcceptAllFileFilterUsed(false);
		//    
		if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) { 
			directory = chooser.getSelectedFile();
		}
		else {
			directory = null;
		}
	}

	public Dimension getPreferredSize(){
		return new Dimension(200, 200);
	}
	
	public File getDirectory(){
		return directory;
	}

}
