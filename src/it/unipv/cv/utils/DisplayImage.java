package it.unipv.cv.utils;

import java.awt.FlowLayout;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;




public class DisplayImage {


	/**
	 * To display an image from the path
	 * @param path		the path of the image to show
	 * @param title		the title of the JFrame
	 */
	public void displayOneImage(String path, String title){
		BufferedImage img = Utility.read(path);
		supportDisplay(img, title);
	}

	/**
	 * To display an image directly with BufferedImage
	 * @param img		the BufferedImage to show
	 * @param title		the title
	 */
	public void displayOneImage(BufferedImage img, String title) {
		supportDisplay(img, title);
	}

	/** 
	 * Support method for display images
	 * @param img
	 * @param title
	 */
	private void supportDisplay(BufferedImage img, String title) {
		ImageIcon icon = new ImageIcon(img);
		JFrame frame = new JFrame();
		frame.setLayout(new FlowLayout());
		frame.setSize(img.getWidth(),img.getHeight());
		frame.setTitle(title);
		JLabel lbl = new JLabel();
		lbl.setIcon(icon);
		frame.add(lbl);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public void displayMoreImages(ArrayList<BufferedImage> images) {
        JFrame frame = new JFrame();
        MyPanel e = new MyPanel(images);
        frame.add(e);
        frame.pack();
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);//full screen
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
	}
	
	private class MyPanel extends JPanel{
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public MyPanel(ArrayList<BufferedImage> images) {
			this.setLayout(new BoxLayout(this,BoxLayout.X_AXIS));
			for (BufferedImage bufferedImage : images) {
				ImageIcon icon = new ImageIcon(bufferedImage);
				JLabel label = new JLabel(icon); 
				add(label);
			}
	    }
	}
}
