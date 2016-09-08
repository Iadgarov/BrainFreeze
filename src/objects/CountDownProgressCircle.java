package objects;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JProgressBar;
import javax.swing.Timer;

import utility.Settings;
import windows_and_panels.TestWindow;

/**
 * Class dedicating to giving a time delay between stimulants during the examination. 
 * Shows an animated countdown circle. 
 * Circle is disabled in code to prevent unwanted visual stimulants during the test. 
 * Code remains because why not?
 * By defualt a glorified time delay method.. 
 * @author David
 *
 */
public class CountDownProgressCircle {

    Timer timer;
    JProgressBar progressBar;

    public CountDownProgressCircle() {
    	int time  = Settings.customDelayBetweenSounds == -1 ? Settings.defaultDelayBetweenSounds : Settings.customDelayBetweenSounds;
    	System.out.println(time);
        progressBar = new JProgressBar(JProgressBar.VERTICAL, 0, time);
        progressBar.setUI(new ProgressCircleUI());
        progressBar.setValue(time);
        progressBar.setBackground(Color.darkGray);
        progressBar.setBorder(null);
        ActionListener listener = new ActionListener() {
            int counter = time;
            public void actionPerformed(ActionEvent ae) {
                counter--;
                progressBar.setValue(counter);
                if (counter < 1) {
                    
                    timer.stop();
                    setVisible(false);	// circle load animation set to invisible by default
                    counter = time;
                    TestWindow.getInvokeButton().doClick();
                    
                } 
            }
        };
        timer = new Timer(time, listener);
        
        
    }
    
    public void start(){
    	timer.start();    	
    }
    
    public void setVisible(boolean b){
    	this.progressBar.setVisible(b);
    }
    
    public void setBounds(int a, int b, int c, int d){
    	this.progressBar.setBounds(a, b, c, d);
    }
    
    public JProgressBar getBar(){
    	return this.progressBar;
    }


}
