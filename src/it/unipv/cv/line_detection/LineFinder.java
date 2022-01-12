package it.unipv.cv.line_detection;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;


import it.unipv.cv.edge_detection.EdgeDetector;
import it.unipv.cv.edge_detection.SobelFilter;
import it.unipv.cv.edge_detection.Threshold;
import it.unipv.cv.utils.Coordinate;
import it.unipv.cv.utils.Utility;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Useful to find line
 * 
 * @author Davide Pio Lotito - Aiman Al Masoud
 * Computer Vision Project - 2022 - UniPV
 *
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
	
	
	private  Logger logger;
	
	


	public ArrayList<Line> detectLines(BufferedImage img){
		
		logger = Logger.getLogger("");
		
		
		MIN_VOTES = (int)(0.5*Math.min(img.getWidth(), img.getHeight()));
		imageSequence = new ArrayList<BufferedImage>();
		imageSequence.add(img);
		
		
		//get the edge-points as coordinates on a CARTESIAN plane. 
		BufferedImage grey = Utility.toGrayScale(img, img.getWidth(), img.getHeight());
		imageSequence.add(grey);
		logger.log(Level.INFO, "DONE: Grey level image.");
		EdgeDetector edgeDetector = new SobelFilter();
		BufferedImage i = edgeDetector.filtering(grey);
		imageSequence.add(i);
		logger.log(Level.INFO, "DONE: Detecting edges.");
		Threshold threshold = new Threshold();
		ArrayList<Coordinate> edgePoints = threshold.thresholding(i);
		//create and add edge-point cloud (for testing purposes)
		imageSequence.add(Utility.plotPoints(Utility.blankImage(img), edgePoints));
		logger.log(Level.INFO, "DONE: thresholding edge-points.");

		
		//get 'all possible' lines passing through the edge-points
		ArrayList<Line> lines = new ArrayList<Line>();
		for(Coordinate point : edgePoints) {
			lines.addAll(Line.getLinesFor(point));
		}
		
		logger.log(Level.INFO, "DONE: getting all possible lines.");


		// get the number of votes for each line
		Set<Line> linesSet = new HashSet<Line>(lines); //TODO this line of code is reeeeeally slow! It's the biggest bottleneck!
		
		logger.log(Level.INFO, "DONE: converting lines list to lines set.");

		
		
		HashMap<Line, Line> voteAccumulator = new HashMap<Line, Line>();

		for (Line line : linesSet) {
			voteAccumulator.put(line, line);
		}
		

		//TODO: this is also a bottleneck:
		for(Line line : lines) {
			voteAccumulator.get(line).addVote();
		}
		
		logger.log(Level.INFO, "DONE: getting line votes.");

		
		// filter out lines with too few votes
		ArrayList<Line> result = new ArrayList<Line>();
		for(Line line : voteAccumulator.values()) {
			if(line.getNumVotes()<MIN_VOTES) {
				continue;
			}
//			System.out.println(line);
			result.add(line);
		}
		
		logger.log(Level.INFO, "DONE: thresholding lines.");
		logger.log(Level.INFO, "Detected Lines:");

		for(Line line : result) {
			logger.log(Level.INFO, line.toString());
		}

		return result;
	}





}
