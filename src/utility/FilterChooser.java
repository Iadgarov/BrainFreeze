package utility;

import java.awt.Component;
import java.awt.Container;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;

import java.util.LinkedList;

import javax.swing.JFileChooser;
import javax.swing.JTextField;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * A basic filechoser for loading and saving patient files 
 * @author David
 *
 */
public class FilterChooser{

 
	public JFileChooser chooser; // would probably be better to just extend this

    public FilterChooser() {

    	super();
    	chooser = new JFileChooser();
    	this.setupListeners();
    	
    
    }
    
    private  JTextField dis(JFileChooser jf) {

        LinkedList<Component> queue = new LinkedList<Component>();
        queue.add(jf);
        JTextField jtf = new JTextField();

        while(queue.size() != 0) {
            Component[] c = ((Container) queue.removeFirst()).getComponents();   
            for(int i = 0; i < c.length; i++) {                        
                queue.add(c[i]);    

                if(c[i] instanceof JTextField) {
                    jtf = (JTextField) c[i];
                   
                    
                    jtf.setVisible(true);
                    jtf.setEnabled(true);
                    jtf.setText("");
                    return jtf;
                }


            }           
        }
        return jtf;
    }


    public void setupListeners()
    {
        JTextField fileChooserTextField = dis(chooser);
        //fileChooserTextField.setInheritsPopupMenu(true);
        
        fileChooserTextField.addKeyListener(new KeyListener()
        {
           @Override
           public void keyTyped(KeyEvent e){  }
           @Override
           public void keyReleased(KeyEvent e){  
        	   filterAsYouType(fileChooserTextField);
           }
           @Override
           public void keyPressed(KeyEvent e) {  }
        }); 
        
     
    }

    /**
     * Shows only files that match in name to the currently typed out prefix
     * @param tf = the tesxtfield from which to get the prefix 
     */
    private void filterAsYouType(final JTextField tf){
	    	
    	
	    final String text = tf.getText();
	
	    chooser.setFileFilter(new FileFilter()
		    {
		       @Override
		       public boolean accept(File f)
		       {
		    	  FileNameExtensionFilter patientFileFilter = new FileNameExtensionFilter(
							"Patient file type (*.patient)", "patient");
		    	  if (!patientFileFilter.accept(f))
		    		  return false;
		          if(text.equals("")){
		             return true;
		          }
		          if(f.getName().equals(text)){
		             chooser.setSelectedFile(f);
		             tf.setCaretPosition(text.length());
		             
		          }
		          if(f.getName().startsWith(text)){
		        	  
		        	  //chooser.setSelectedFile(f);
		        	  tf.setCaretPosition(text.length());
		        	  return true;
		           }
		          
		          return false;
		       }
		  
			@Override
		       public String getDescription(){
		          return null;
		       }
		    });
		    
		    
    }

    
}