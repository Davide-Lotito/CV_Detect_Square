package it.unipv.cv.edge_detection;

import java.awt.image.BufferedImage;

/**
 * Generic Edge Detector Interface
 * 
 * @author Davide Pio Lotito - Aiman Al Masoud
 * Computer Vision Project - 2022 - UniPV
 *
 */
public interface EdgeDetector {
	public BufferedImage filtering(BufferedImage image);
}
