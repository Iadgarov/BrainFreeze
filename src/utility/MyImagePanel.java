package utility;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

public class MyImagePanel extends javax.swing.JPanel {
   
	/**
	 * 
	 */
	private static final long serialVersionUID = 8654906818756136361L;
	private BufferedImage image;
	
	private JPanel container;

	private String path;

    public MyImagePanel(String path, JPanel container) {
    	
    	//setPreferredSize(new Dimension(container.getWidth(),container.getHeight()));
        try {
            image = ImageIO.read(new File(path));
        } catch (IOException ex) {
            ex.printStackTrace();
        }  
     
        this.container = container;
        this.path = path;
      
    }
    

  
/*
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        setPreferredSize(new Dimension(container.getWidth(),container.getHeight()));
        
		double widthScaleFactor = container.getWidth() / (double)image.getWidth();
		double heightScaleFactor = container.getHeight() / (double)image.getHeight();
		double scaleFactor = (widthScaleFactor > heightScaleFactor)? heightScaleFactor : widthScaleFactor;
		
		int width = (int)(image.getWidth() * scaleFactor);
		int height = (int)(image.getHeight() * scaleFactor);
        
		g.drawImage(img.getScaledInstance(newWidth, -1, Image.SCALE_SMOOTH), x, y, this);
		
    }
    
  */  
    
    public double getScaleFactor(int iMasterSize, int iTargetSize) {

        double dScale = 1;
        if (iMasterSize > iTargetSize) {

            dScale = (double) iTargetSize / (double) iMasterSize;

        } else {

            dScale = (double) iTargetSize / (double) iMasterSize;

        }

        return dScale;

    }

    public double getScaleFactorToFit(Dimension original, Dimension toFit) {

        double dScale = 1d;

        if (original != null && toFit != null) {

            double dScaleWidth = getScaleFactor(original.width, toFit.width);
            double dScaleHeight = getScaleFactor(original.height, toFit.height);

            dScale = Math.min(dScaleHeight, dScaleWidth);

        }

        return dScale;

    }

    @Override
    protected void paintComponent(Graphics g) {

        super.paintComponent(g);

        double scaleFactor = Math.min(1d, getScaleFactorToFit(new Dimension(image.getWidth(), image.getHeight()), getSize()));

        int scaleWidth = (int) Math.round(image.getWidth() * scaleFactor);
        int scaleHeight = (int) Math.round(image.getHeight() * scaleFactor);

        Image scaled = image.getScaledInstance(scaleWidth, scaleHeight, Image.SCALE_SMOOTH);

        int width = getWidth() - 1;
        int height = getHeight() - 1;

        int x = (width - scaled.getWidth(this)) / 2;
        int y = (height - scaled.getHeight(this)) / 2;

        g.drawImage(scaled, x, y, this);

    }

    
    
 
}