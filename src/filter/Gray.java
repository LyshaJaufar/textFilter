package filter;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import javax.imageio.stream.ImageOutputStream;

public class Gray {

	public static void main(String[] args) {
		try {
			File input = new File("C:\\Users\\Admin\\eclipse-workspace\\filter\\src\\filter\\test.png");
			File temp = new File("C:\\Users\\Admin\\eclipse-workspace\\filter\\src\\filter\\temp.jpg");
			File output = new File("C:\\Users\\Admin\\eclipse-workspace\\filter\\src\\filter\\Output4.jpg");
			
			ImageInputStream imgInputStream = ImageIO.createImageInputStream(input);
			Iterator<ImageReader> iterator = ImageIO.getImageReaders(imgInputStream);
			
			ImageReader reader = iterator.next();												
			String imageFormat = reader.getFormatName();
			
			BufferedImage image = ImageIO.read(imgInputStream);
			int width = image.getWidth();
			int height = image.getHeight();
			
			ArrayList<Color> finalRGB = new ArrayList<Color>();
			int count = 0;

		    //Graph for sobel operator
		    int[][] graph = { 
		        {-1, 0, 1},
		        {-2, 0, 2},
		        {-1, 0, 1}
		    };
			
			for (int i = 0; i < height; i++) {
				for (int j = 0; j < width; j++) {
					float gxBlue = 0, gxGreen = 0, gxRed = 0;
					float gyBlue = 0, gyGreen = 0, gyRed = 0;
					
					for (int i_offset = -1; i_offset <= 1; i_offset++) {
						for (int j_offset = -1; j_offset <= 1; j_offset++) {
		                    if (i + i_offset < 0 || i + i_offset > height - 1 || j + j_offset < 0 || j + j_offset > width - 1) {
		                        continue;
		                    }

		                    // Compute Gx for each colour value
		                    gxBlue += new Color(image.getRGB(j + j_offset, i + i_offset)).getRed() * graph[j_offset + 1][i_offset + 1];
		                    gxRed += new Color(image.getRGB(j + j_offset, i + i_offset)).getGreen() * graph[j_offset + 1][i_offset + 1];
		                    gxGreen += new Color(image.getRGB(j + j_offset, i + i_offset)).getBlue() * graph[j_offset + 1][i_offset + 1];
		                    
		                    // Compute Gy for each colour value
		                    gyBlue += new Color(image.getRGB(j + j_offset, i + i_offset)).getRed() * graph[i_offset + 1][j_offset + 1];
		                    gyRed += new Color(image.getRGB(j + j_offset, i + i_offset)).getGreen() * graph[i_offset + 1][j_offset + 1];
		                    gyGreen += new Color(image.getRGB(j + j_offset, i + i_offset)).getBlue() * graph[i_offset + 1][j_offset + 1];
						}
					}
					
					// Compute new total value of each pixel
					float finalRed = (float)Math.floor(Math.sqrt((gxRed * gxRed) + (gyRed * gyRed)));
					float finalBlue = (float)Math.floor(Math.sqrt((gxBlue * gxBlue) + (gyBlue * gyBlue)));
					float finalGreen = (float)Math.floor(Math.sqrt((gxGreen * gxGreen) + (gyGreen * gyGreen)));
					
					// Ensure the new value is 0 - 255 & set each colour values of a pixel to the result 
					int rgbRed = (int)((finalRed > 255) ? 255 : finalRed);
					int rgbGreen = (int)((finalGreen > 255) ? 255 : finalGreen);
					int rgbBlue = (int)((finalBlue > 255) ? 255 : finalBlue);

					finalRGB.add(new Color(rgbRed, rgbGreen, rgbBlue));
					count++;

				}
			}
			//System.out.println("test: " + finalRGB);
			count = 0;
			for (int i = 0; i < height; i++) {
				for (int j = 0; j < width; j++) {
					image.setRGB(j, i, finalRGB.get(count).getRGB());
					count++;
				}
			}
			
			ImageIO.write(image, imageFormat, temp);
			System.out.println("Completed.");
		}
		catch (IOException ex) {
			ex.printStackTrace();
		}
	}
}
