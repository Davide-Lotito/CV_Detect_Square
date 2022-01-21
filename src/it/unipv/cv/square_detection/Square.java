package it.unipv.cv.square_detection;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import it.unipv.cv.utils.Coordinate;
import it.unipv.cv.utils.Utility;

/**
 * A class for the square
 * 
 * @author Davide Pio Lotito - Aiman Al Masoud
 * Computer Vision Project - 2022 - UniPV
 *
 */
public class Square {
	/**
	 * the TWO DIAGONAL POINTS of the square to identify it
	 */
	private Coordinate upperLeft;
	private Coordinate bottomRight;
	
	public Square(Coordinate upperLeft, Coordinate bottomRight) {
		super();
		this.upperLeft = upperLeft;
		this.bottomRight = bottomRight;
	}
	
	/**
	 * return the edge of the square
	 * @return
	 */
	public double getEdge() {
		return bottomRight.distance(upperLeft)/Math.sqrt(2.0);
		//return Math.abs(bottomRight.X - upperLeft.X);
	}
	
	/**
	 * Draw the edge
	 * @param b
	 * @return
	 */
	public BufferedImage draw(BufferedImage b) {

		b = Utility.copyImage(b);
		Graphics2D g = (Graphics2D) b.getGraphics();
		g.setColor(Color.ORANGE);
		g.setStroke(new BasicStroke(5));
		//draw the squaress
		g.drawRect(upperLeft.X, upperLeft.Y, (int)getEdge(), (int)getEdge());
		g.dispose();
		return b;
	}
}
