package it.unipv.cv.line_detection;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import it.unipv.cv.edge_detection.EdgeDetector;
import it.unipv.cv.edge_detection.SobelFilter;
import it.unipv.cv.edge_detection.Threshold;
import it.unipv.cv.utils.Coordinate;
import it.unipv.cv.utils.Counter;
import it.unipv.cv.utils.Utility;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Useful to find line
 * 
 * @author Davide Pio Lotito - Aiman Al Masoud
 * Computer Vision Project - 2022 - UniPV
 *
 * //https://en.wikipedia.org/wiki/Hough_transform

 */
public class LineFinder {

	/**
	 * Minimum number of votes (edge-points) that a line needs to be considered a real edge-line.
	 */
	public int MIN_VOTES;
	
	/**
	 * To keep track of and visualize the transformations applied on the image.
	 */
	public static ArrayList<BufferedImage> imageSequence;
	
	/**
	 * Used to log messages of this class.
	 */
	private  Logger logger;
	
	
	public ArrayList<Line> detectLines(BufferedImage img){
		
		logger = Logger.getLogger("");
		
		MIN_VOTES = (int)(0.4*Math.min(img.getWidth(), img.getHeight()));
		imageSequence = new ArrayList<BufferedImage>();
		imageSequence.add(img);
		
		//get the edge-points as coordinates on a CARTESIAN plane. 
		BufferedImage grey = Utility.toGrayScale(img, img.getWidth(), img.getHeight());
		imageSequence.add(grey);
		logger.log(Level.INFO, "DONE: Obtained grey-level image.");
		EdgeDetector edgeDetector = new SobelFilter();
		BufferedImage i = edgeDetector.filtering(grey);
		imageSequence.add(i);
		logger.log(Level.INFO, "DONE: Detected edges.");
		Threshold threshold = new Threshold();
		ArrayList<Coordinate> edgePoints = threshold.thresholding(i);
		//create and add edge-point cloud (for testing purposes)
		imageSequence.add(Utility.plotPoints(Utility.blankImage(img), edgePoints));
		logger.log(Level.INFO, "DONE: Applied threshold to edge-points.");

		//get 'all possible' lines passing through the edge-points
		ArrayList<Line> lines = new ArrayList<Line>();
		for(Coordinate point : edgePoints) {
			lines.addAll(getLinesFor(point));
		}
		
		logger.log(Level.INFO, "DONE: Obtained all possible lines.");
		
		//count votes for each line
		ArrayList<Line> results = new ArrayList<Line>();		
		Counter counter = new Counter();
		for(Line line : lines) {
			counter.add(line);
		}
		
		//threshold lines by votes
		for(Object line : counter.keySet()) {
			if(counter.get(line) < MIN_VOTES) {
				continue;
			}
			results.add((Line)line);
		}
		
		logger.log(Level.INFO, "These lines where detected:");
		for(Line line : results) {
			logger.log(Level.INFO, line.toString());
		}
		
		return results;

	}
	
	/**
	 * 
	 * Returns a Line that passes through Point p, with chosen value of parameter theta, 
	 * and computed value of parameter rho.
	 * 
	 * Uses this line-parametrization to calculate rho:
	 * rho = x*cos(theta) + y*sin(theta)
	 * 
	 * @param point
	 * @param theta
	 * @return
	 */
	public static Line getLineFor(Coordinate p, double theta) {
		double rho = (p.X*Math.cos(theta) + p.Y*Math.sin(theta));
		int rho2 = (int) ((Math.round(rho)));
		return new Line(rho2, theta);
	}
	
	/**
	 * Like getLineFor but gets a bunch of lines for a predefined set of thetas 
	 * @param p
	 * @return
	 */
	public static ArrayList<Line> getLinesFor(Coordinate p){
		ArrayList<Line> lines = new ArrayList<Line>();
		
		int step = 1;//distance between two adjacent thetas
		Integer[] thetas = Utility.generateThetas(step);
		
		for(int theta : thetas){
			Line line = getLineFor(p, Math.toRadians(theta));
			lines.add(line);
		}
		return lines;
	}
}
