package it.unipv.cv.edge_detection;

import java.awt.FlowLayout;
import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;


public class DisplayImage {

	public static void main(String[] args) {
		new DisplayImage("./images/input/architecture_C.png", "input image");
	}

	/**
	 * To display an image from the path
	 * @param path		the path of the image to show
	 * @param title		the title
	 */
	public DisplayImage(String path, String title){
		BufferedImage img = Utility.read(path);
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

	/**
	 * To display an image directly with BufferedImage
	 * @param img		the BufferedImage to show
	 * @param title		the title
	 */
	public DisplayImage(BufferedImage img, String title) {
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



}
