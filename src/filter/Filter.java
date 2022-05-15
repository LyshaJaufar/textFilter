package filter;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.awt.Color;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;

public class Filter{
	public static void filter (String[] args) {

		try {
			File input = new File("C:\\Users\\Admin\\eclipse-workspace\\filter\\src\\filter\\ratioedAayan.jpg");
			File output = new File("C:\\Users\\Admin\\eclipse-workspace\\filter\\src\\filter\\Output.jpg");
			
			ImageInputStream imgInputStream = ImageIO.createImageInputStream(input);
			Iterator<ImageReader> iterator = ImageIO.getImageReaders(imgInputStream);
			ImageReader reader = iterator.next();
			String imageFormat = reader.getFormatName();
			
			BufferedImage image = ImageIO.read(imgInputStream);
			int width = image.getWidth();
			int height = image.getHeight();
			
			System.out.println("Your first arg: " + input);
			System.out.println("Your sec arg: " + output);
			
			for (int i = 0; i < height; i++) {
				for (int j = 0; j < width; j++) {
					Color color = new Color(image.getRGB(j, i));
					int red = (int)(color.getRed());
					System.out.println(red);
				}
			}
		}
		catch (IOException ex) {
			ex.printStackTrace();
		}
	}	
}