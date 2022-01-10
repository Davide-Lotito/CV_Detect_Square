package it.unipv.cv.edge_detection;

import java.awt.image.BufferedImage;

public interface EdgeDetector {
	public BufferedImage filtering(BufferedImage image);
}
