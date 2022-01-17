package it.unipv.cv.line_detection;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import it.unipv.cv.edge_detection.EdgeDetector;
import it.unipv.cv.edge_detection.SobelFilter;
import it.unipv.cv.edge_detection.Threshold;
import it.unipv.cv.utils.Coordinate;
import it.unipv.cv.utils.Counter;
import it.unipv.cv.utils.DisplayImage;
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
	 * Used to log messages of this class.
	 */
	private Logger logger;


	public ArrayList<Line> detectLines(BufferedImage img){

		logger = Logger.getLogger("");

		/**
		 * Min votes for the Hough Transform
		 */
		MIN_VOTES = (int)(0.5*Math.min(img.getWidth(), img.getHeight()));

		BufferedImage grayScale = Utility.toGrayScale(img);

		EdgeDetector edgeDetector = new SobelFilter();
		BufferedImage sobelImage = edgeDetector.filtering(grayScale);

		Threshold threshold = new Threshold();
		ArrayList<Coordinate> edgePoints = threshold.thresholding(sobelImage);

		/**
		 * Get 'all possible' lines passing through the edge-points
		 */
		ArrayList<Line> lines = new ArrayList<Line>();
		for(Coordinate point : edgePoints) {
			lines.addAll(getLinesFor(point));
		}

		logger.log(Level.INFO, "DONE: Obtained all possible lines.");

		/**
		 * Count votes for each line
		 */
		ArrayList<Line> results = new ArrayList<Line>();		
		Counter counter = new Counter();
		for(Line line : lines) {
			counter.add(line);
		}

		/**
		 * Threshold lines by votes
		 */
		for(Object line : counter.keySet()) {
			if(counter.get(line) < MIN_VOTES) {
				continue;
			}
			results.add((Line)line);
		}

		/**
		 * Remove equal lines
		 */
		logger.log(Level.WARNING, "These lines where removed because equals:");
		for(int j=0; j<results.size()-1; j++) {
			Line line1 = results.get(j);
			Line line2 = results.get(j+1);
			if(line1.equals(line2))
				logger.log(Level.WARNING,"removed "+line2.toString());
			results.remove(j);	
		}

		/**
		 * Remove non-valid "mathematically" lines
		 */
		logger.log(Level.WARNING, "These lines where removed because non-valid:");
		for(int k=0; k<results.size(); k++) {
			if(!(results.get(k).checkLine())) {
				logger.log(Level.WARNING,"removed "+results.get(k).toString());
				results.remove(k);
			}
		}

		//logger.log(Level.INFO, "These lines are the ones found and correct:");
		System.out.println("CORRECT LINES:");
		int COUNTER = 1;
		for(Line line : results) {
			//logger.log(Level.INFO, line.toString());
			System.out.println("["+COUNTER+"] "+line.toString());
			COUNTER++;
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

	/**
	 * Only to test! REMOVE IT BEFORE THE DELIVERY
	 * @param args
	 */
	public static void main(String[] args) {
		BufferedImage image = Utility.read("./images/input/square.png");
		//detect the lines on the image
		LineFinder lineFinder = new LineFinder();
		ArrayList<Line> lines = lineFinder.detectLines(image);

		//draw each line
		for(int i=0; i<lines.size(); i++) {
			Line line1 = lines.get(i);	
			image = line1.draw(image);
		}

		new DisplayImage().displayOneImage(image, "detected lines");
	}
}