package windows_and_panels;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.KeyEventPostProcessor;
import java.awt.KeyboardFocusManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

import buttons.TestChoiceButton;
import defualt.BrainFreezeMain;
import objects.CountDownProgressCircle;
import objects.Test;
import utility.TestButtonListener;

/**
 * The window that contains the experiment itself
 * @author David
 *
 */
public class TestWindow extends JFrame{
	
	public static boolean testRunning = false;

	private static final long serialVersionUID = 9147108211616745510L;
	
	// error to show if user gives bad custom audio for test
	public static final String soudError = 
			"<html>Something is wrong with the supplied audio files - Cannot proceed with testing.<br>Double check your custom audio files or revert to defualt stimulants [in the program settings]."
			+ "<br>Note:" + "<pre>"
			
			+ "Sound files must be in .wav format and named \"ITD_X.wav\" or \"ILD_X.wav\"<br>"
			+ "Where X represents the bilateral location cooresponding to the sound stimulant [1,2,3,...,9].<br>"
			+ "A total of 18 wav files MUST exist in chosen folder, one per location, per stimulant type.<br>"
			+ "</pre></html>";

	private static ArrayList<JButton> buttons;
	private static JButton endTestButton;
	static Test test;

	public TestWindow() throws Exception{
		try {
			test = new Test();
		} catch (Exception e) {
			System.out.println("died in TestWindow in TestWindow");
			JOptionPane.showMessageDialog(null,
					soudError,
					"Error",
					JOptionPane.ERROR_MESSAGE);
			throw new Exception();
		}

		URL url = BrainFreezeMain.class.getResource("/brain-icon-2.png");
		ImageIcon myAppImage = new ImageIcon(url);
		if(myAppImage != null)
			setIconImage(myAppImage.getImage());

		this.setMinimumSize(new Dimension(500,500));
		this.setExtendedState(JFrame.MAXIMIZED_BOTH);
		System.out.println("height: " + JFrame.MAXIMIZED_VERT + " width: " + JFrame.MAXIMIZED_HORIZ);
		this.setUndecorated(true);

		// close window on ESC key press
		utility.UsefulMethods.installEscapeCloseOperation(this);

		this.setVisible(true);

	}

	public static void killTest(){
		endTestButton.doClick();
	
	}


	public void launchTestWindow (){

		Dimension temp = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
		int x = temp.width;
		int y = temp.height;
		System.out.println("height: " + x + " width: " + y);
		RADIUS = (int)(Math.max(x, y)*0.45);

		while (RADIUS > (int)((double)(Math.max(x, y))/1.9959)){
			RADIUS--;
		}

		System.out.println(RADIUS);
		SIDE = RADIUS/5;
		GAP = RADIUS/9;
		PREF_W = this.getWidth();
		PREF_H = this.getHeight();

		PanelsOnCircle mainPanel = new PanelsOnCircle(this);

		this.setLayout(new BorderLayout());
		this.add(mainPanel);

		this.pack();
		this.setVisible(true);
		
		testRunning = true;
	}

	//****************************************************//
	//||||||||||||||                        ||||||||||||||//
	//VVVVVVVVVVVVVV// Drawing the window //VVVVVVVVVVVVVV//
	//****************************************************//

	private static int RADIUS;//(int)radius/2;
	private static int GAP = 50;
	private static int PREF_W = 500;
	private static int PREF_H = PREF_W;
	private static final int SLICES = 18;
	private static int SIDE;

	static JButton invokeButton;
	static JButton demoButton;
	static JButton ITD_Demo;
	static JButton ILD_Demo;
	static CountDownProgressCircle delayer;

	public static JButton getInvokeButton(){
		return invokeButton;
	}

	public static JButton getDemoButton(){
		return demoButton;
	}

	public static CountDownProgressCircle getBar(){
		return delayer;
	}


	private static class PanelsOnCircle extends JPanel {

		private static final long serialVersionUID = 2290483979297106120L;
		JFrame parent;


		private PanelsOnCircle(JFrame p) {
			parent = p;
			buttons = new ArrayList<>();
			this.setBackground(Color.DARK_GRAY);
			this.setMaximumSize(p.getSize());

			int x, y;
			double phi;

			for (int i = (SLICES/4)+1; i < 3*SLICES/4+1; i++) {

				TestChoiceButton button = new TestChoiceButton(String.valueOf(9-(i-5)));

				phi = (i * Math.PI * 2) / SLICES; 

				button.setFont(new Font("Arial", Font.PLAIN, 40));
				button.setBackground(Color.GRAY);
				button.setForeground(Color.WHITE);
				button.setBorder(new LineBorder(Color.WHITE, 6));
				button.setFocusPainted(false);

				x = (int) (RADIUS * Math.sin(phi) + RADIUS - SIDE / 2) + GAP;
				y = (int) (RADIUS * Math.cos(phi) + RADIUS - SIDE / 2) + GAP;
				System.out.println("height: " + x + " width: " + y + " Button: " + String.valueOf(9-(i-5)) + " sin phi: " + Math.sin(phi) + " cos phi: " + Math.cos(phi));
				button.setBounds(x, y, SIDE, SIDE);

				button.addActionListener(new TestButtonListener());

				buttons.add(button); // in reverse order, from right to left
				this.add(button);
			}

			JLabel headImage = initHeadLabel();
			invokeButton = initStartButton();
			demoButton = initDemoButton();
			JButton endTestButton = testEndButton();

			this.add(headImage);
			this.add(invokeButton);
			this.add(demoButton);	this.add(ITD_Demo);	this.add(ILD_Demo);
			this.add(endTestButton);

			KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventPostProcessor(new KeyEventPostProcessor() {

				@Override
				public boolean postProcessKeyEvent(KeyEvent e) {
					if (TestButtonListener.getActive()){
						switch(e.getKeyCode()){
						// numbers are backwards because of the way buttons are created
						//case KeyEvent.VK_0: buttons.get(9).doClick(); break;
						case KeyEvent.VK_1: buttons.get(8).doClick(); break;
						case KeyEvent.VK_2: buttons.get(7).doClick(); break;
						case KeyEvent.VK_3: buttons.get(6).doClick(); break;
						case KeyEvent.VK_4: buttons.get(5).doClick(); break;
						case KeyEvent.VK_5: buttons.get(4).doClick(); break;
						case KeyEvent.VK_6: buttons.get(3).doClick(); break;
						case KeyEvent.VK_7: buttons.get(2).doClick(); break;
						case KeyEvent.VK_8: buttons.get(1).doClick(); break;
						case KeyEvent.VK_9: buttons.get(0).doClick(); break;

						}

					}
					return false;
				}
			});


			delayer = new CountDownProgressCircle();
			delayer.setVisible(false);
			int temp1 = (int) (RADIUS * Math.sin((9 * Math.PI * 2) / SLICES) + RADIUS - SIDE / 2) + GAP;
			int temp2 = (int) (RADIUS * Math.cos((5 * Math.PI * 2) / SLICES) + RADIUS - SIDE / 2) + GAP;
			delayer.setBounds(temp1, temp2-2*SIDE + SIDE/2, SIDE, SIDE/2);
			this.add(delayer.getBar());

			setLayout(new BorderLayout());
		}

		@Override
		public Dimension getPreferredSize() {
			return new Dimension(PREF_W, PREF_H);
		}

		@Override
		public void paint(Graphics g) {
			super.paint(g);

		}

		private static JLabel initHeadLabel(){

			JLabel temp = new JLabel();
			int x = (int) (RADIUS * Math.sin((9 * Math.PI * 2) / SLICES) + RADIUS - SIDE / 2) + GAP;
			int y = (int) (RADIUS * Math.cos((5 * Math.PI * 2) / SLICES) + RADIUS - SIDE / 2) + GAP;
			temp.setBounds(x-SIDE/2, y-SIDE, 2*SIDE, 2*SIDE);

			// load image
			BufferedImage _head = null;
			try {
				_head = ImageIO.read(BrainFreezeMain.class.getResource("/head.png"));
			} catch (IOException e) {
				e.printStackTrace();
			}

			//resize image
			ImageIcon head = new ImageIcon(
					_head.getScaledInstance(temp.getWidth(), temp.getHeight(),Image.SCALE_SMOOTH));

			temp.setIcon(head);
			return temp;
		}

		private static JButton initStartButton(){

			JButton temp = new JButton("Begin Test");
			temp.setFont(new Font("Serif", Font.BOLD, 20));
			int x = (int) (RADIUS * Math.sin((9 * Math.PI * 2) / SLICES) + RADIUS - SIDE / 2) + GAP;
			int y = (int) (RADIUS * Math.cos((5 * Math.PI * 2) / SLICES) + RADIUS - SIDE / 2) + GAP;
			temp.setBounds(x, y-2*SIDE + SIDE/2, SIDE, SIDE/2);
			temp.setForeground(Color.GREEN);
			temp.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					getDemoButton().setEnabled(false);
					getDemoButton().setVisible(false);
					if (temp.getText() == "Begin Test" || temp.getText() == "Next Sound"){
						if (!initiateTest(true))
							return;
						TestButtonListener.turnOn(true, false);
						temp.setText("Repeat Sound");
						temp.setVisible(true);
						temp.setEnabled(true);
					}
					else{ // if repeat
						if (!initiateTest(false)){
							return;
						}
						System.out.println("suspect 1");
						TestButtonListener.turnOn(true, false);
					}
				}
			});
			temp.setVisible(true);
			return temp;
		}

		private static JButton initDemoButton(){

			JButton temp = new JButton("Training");
			temp.setFont(new Font("Serif", Font.BOLD, 20));
			int x = (int) (RADIUS * Math.sin((9 * Math.PI * 2) / SLICES) + RADIUS - SIDE / 2) + GAP;
			int y = (int) (RADIUS * Math.cos((5 * Math.PI * 2) / SLICES) + RADIUS - SIDE / 2) + GAP;
			temp.setBounds(x, y-2*SIDE - SIDE/2, SIDE, SIDE/2);
			temp.setForeground(Color.BLUE);
			temp.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					if (temp.getText() == "Training"){
						temp.setText("End Training");
						getInvokeButton().setEnabled(false);
						ITD_Demo.setEnabled(true);	ITD_Demo.setVisible(true);
						ILD_Demo.setEnabled(true);	ILD_Demo.setVisible(true);
						ITD_Demo.doClick(); // active by default
						TestButtonListener.turnOn(true, true);
					}
					else{ // end training
						temp.setText("Training");
						getInvokeButton().setEnabled(true);
						ITD_Demo.setEnabled(false);	ITD_Demo.setVisible(false);
						ILD_Demo.setEnabled(false);	ILD_Demo.setVisible(false);
						TestButtonListener.turnOn(false, false);
					}
				}
			});
			temp.setVisible(true);

			ITD_Demo = new JButton("ITD");
			ITD_Demo.setFont(new Font("Serif", Font.BOLD, 20));
			ITD_Demo.setBounds(x - (int)((double)(3*SIDE)/4), y-2*SIDE - SIDE/2, SIDE/2, SIDE/2);
			demoChoiseListener(true, ITD_Demo);
			


			ILD_Demo = new JButton("ILD");
			ILD_Demo.setFont(new Font("Serif", Font.BOLD, 20));
			ILD_Demo.setBounds(x + (int)((double)(5*SIDE)/4), y-2*SIDE - SIDE/2, SIDE/2, SIDE/2);
			demoChoiseListener(false, ILD_Demo);
			
			//ILD_Demo.doClick();
			
			//show them when training is chosen only
			ITD_Demo.setEnabled(false);	ITD_Demo.setVisible(false);
			ILD_Demo.setEnabled(false);	ILD_Demo.setVisible(false);


			return temp;
		}

		private static void demoChoiseListener(boolean timeOrNotLevel, JButton source){
			source.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					
					System.out.println("Action for this guy: " + timeOrNotLevel);
					//source.setText("<html><font color = red>" + text + "</font></html>");
					source.setBackground(Color.RED);
					source.setForeground(Color.RED);
					source.setBorder(BorderFactory.createLineBorder(Color.RED, 10));
					source.setEnabled(false);

					JButton other = timeOrNotLevel ? ILD_Demo : ITD_Demo;
					//if (!other.isEnabled()){
						other.setEnabled(true);
						other.setBackground(new JButton().getBackground());
						source.setForeground(new JButton().getForeground());
						other.setBorder(null);
						other.setContentAreaFilled(true);
					//}
				}
			});

		}
		
		/**
		 * 
		 * @return true if in ITD button is enabled (the chosen button is disabled = already clicked) false otherwise
		 */
		private static boolean demoTimeOrNotLevel(){
			if (ITD_Demo.isEnabled()){
				return false;
			}
			return true;
		}

		private JButton testEndButton(){

			JButton temp = new JButton("End Test");

			int x = (int) (RADIUS * Math.sin((9 * Math.PI * 2) / SLICES) + RADIUS - SIDE / 2) + GAP;
			int y = (int) (RADIUS * Math.cos((5 * Math.PI * 2) / SLICES) + RADIUS - SIDE / 2) + GAP;
			temp.setBounds(x, y+SIDE+SIDE/8, SIDE, SIDE/4);
			temp.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					Test.endTest();
					testRunning = false;
					System.out.println("Settings test button to NOT active!!");
					TestButtonListener.turnOn(false, false);
					parent.dispatchEvent(new WindowEvent(parent, WindowEvent.WINDOW_CLOSING));
					LeftPanel.refreshPatientData();
					//RightPanel.swapRepresentation("~~");
				}
			});
			temp.setVisible(true);
			endTestButton = temp;
			return temp;
		}


		public static boolean initiateTest(boolean first){

			if (Test.isDone())
				return false;

			try {

				test.invokeStimulant(!first);
			} catch (Exception e) {
				System.out.println("died in initaiteTest in TestWindow");
				JOptionPane.showMessageDialog(null,
						soudError,
						"Error",
						JOptionPane.ERROR_MESSAGE);
				endTestButton.doClick();
				return false;
			} 
			return true;
		}

	}
	
	/**
	 * 
	 * @return true if ITD false if ILD
	 */
	public static boolean demoType(){
		return PanelsOnCircle.demoTimeOrNotLevel();
	}

}
