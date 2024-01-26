// This class uses the Color class, which is part of a package called awt,
// which is part of Java's standard class library.
import java.awt.Color;

/** A library of image processing functions. */
public class Runigram {

	public static void main(String[] args) {
	    
		//// Hide / change / add to the testing code below, as needed.
		
		// Tests the reading and printing of an image:	
		Color[][] tinypic = read("tinypic.ppm");
		print(tinypic);

		// Creates an image which will be the result of various 
		// image processing operations:
		Color[][] imageOut;

		//Tests the horizontal flipping of an image:
		//imageOut = flippedHorizontally(tinypic);
		//System.out.println();
		//print(imageOut);

		//Tests the vertical flipping of an image:
		//imageOut = flippedVertically(tinypic);
		//System.out.println();
		//print(imageOut);

		//Tests the grey scale convertion of an image:
		//imageOut = grayScaled(tinypic);
		//System.out.println();
		//print(imageOut);

		//Tests the scale convertion of an image:
		//imageOut = scaled(tinypic,3,5);
		//System.out.println();
		//print(imageOut);

		//Tests the blend convertion of 2 colors:
		Color color1 = new Color(100,40,100);
		Color color2 = new Color(200,20,40);
		System.out.println(blend(color1, color2, 0.25));
		
		//// Write here whatever code you need in order to test your work.
		//// You can reuse / overide the contents of the imageOut array.
	}

	/** Returns a 2D array of Color values, representing the image data
	 * stored in the given PPM file. */
	public static Color[][] read(String fileName) {
		In in = new In(fileName);
		// Reads the file header, ignoring the first and the third lines.
		in.readString();
		int numCols = in.readInt();
		int numRows = in.readInt();
		in.readInt();
		// Creates the image array
		Color[][] image = new Color[numRows][numCols];
		// Reads the RGB values from the file, into the image array. 
		// For each pixel (i,j), reads 3 values from the file,
		// creates from the 3 colors a new Color object, and 
		// makes pixel (i,j) refer to that object.
		//// Replace the following statement with your code.
		int redValue, greenValue, blueValue;
		for (int i = 0; i < numRows; i++)
		{
			for(int j = 0; j < numCols; j++){
				redValue = in.readInt();
				greenValue = in.readInt();
				blueValue = in.readInt();
				image[i][j] = new Color(redValue, greenValue, blueValue);
			}
		}
		return image;
	}

    // Prints the RGB values of a given color.
	private static void print(Color c) {
	    System.out.print("(");
		System.out.printf("%3s,", c.getRed());   // Prints the red component
		System.out.printf("%3s,", c.getGreen()); // Prints the green component
        System.out.printf("%3s",  c.getBlue());  // Prints the blue component
        System.out.print(")  ");
	}

	// Prints the pixels of the given image.
	// Each pixel is printed as a triplet of (r,g,b) values.
	// This function is used for debugging purposes.
	// For example, to check that some image processing function works correctly,
	// we can apply the function and then use this function to print the resulting image.
	private static void print(Color[][] image) {
		int rows = image.length;
		int cols = image[0].length;
		for (int i = 0; i < rows; i++){
			for(int j = 0; j < cols; j++){
				print(image[i][j]);
				}
			System.out.println();
			}
		}
	
	/**
	 * Returns an image which is the horizontally flipped version of the given image. 
	 */
	public static Color[][] flippedHorizontally(Color[][] image) {
		Color[][] fhImage = new Color[image.length][image[0].length];	
		for(int i = 0; i < fhImage.length; i++){
			int j = 0;
			for(int imageColIndex = image[0].length - 1; imageColIndex >= 0; imageColIndex--){
				fhImage[i][j] = image[i][imageColIndex];
				j++;
			}
		}
		return fhImage;
	}
	
	/**
	 * Returns an image which is the vertically flipped version of the given image. 
	 */
	public static Color[][] flippedVertically(Color[][] image){
		Color[][] fvImage = new Color[image.length][image[0].length];
		int i = 0;	
		for(int imageRowIndex = image.length - 1; imageRowIndex >= 0; imageRowIndex--){
			for(int j = 0; j < fvImage[0].length; j++){
				fvImage[i][j] = image[imageRowIndex][j];
			}
			i++;
		}
		return fvImage;
	}
	
	// Computes the luminance of the RGB values of the given pixel, using the formula 
	// lum = 0.299 * r + 0.587 * g + 0.114 * b, and returns a Color object consisting
	// the three values r = lum, g = lum, b = lum.
	public static Color luminance(Color pixel) {
		double r = (pixel.getRed() * 0.299);
		double g = (pixel.getGreen() * 0.587);
		double b = (pixel.getBlue() * 0.114);
		int sum = (int)(r + g + b);
		Color lum = new Color(sum,sum,sum);
		return lum;
	}
	
	/**
	 * Returns an image which is the grayscaled version of the given image.
	 */
	public static Color[][] grayScaled(Color[][] image) {
		Color[][] grayScaledImage = new Color[image.length][image[0].length];
		for(int i = 0; i < image.length; i++){
			for(int j = 0; j < image[0].length; j++){
				grayScaledImage[i][j] = luminance(image[i][j]);
			}
		}
		return grayScaledImage;
	}	
	
	/**
	 * Returns an image which is the scaled version of the given image. 
	 * The image is scaled (resized) to have the given width and height.
	 */
	public static Color[][] scaled(Color[][] image, int width, int height){
		int originHeight = image.length;
		int originWidth = image[0].length;
		Color[][] scaledImage = new Color[height][width];
		double heightRatio = (double) originHeight / height;
		double widthRatio = (double) originWidth / width;
		for(int i = 0; i < height; i++){
			for(int j = 0; j < width; j++){
				int originalY = (int)(i * heightRatio);
				int originalX = (int)(j * widthRatio);
				scaledImage[i][j] = image[originalY][originalX];
			}
		}
		return scaledImage;
	}
	
	/**
	 * Computes and returns a blended color which is a linear combination of the two given
	 * colors. Each r, g, b, value v in the returned color is calculated using the formula 
	 * v = alpha * v1 + (1 - alpha) * v2, where v1 and v2 are the corresponding r, g, b
	 * values in the two input color.
	 */
	public static Color blend(Color c1, Color c2, double alpha) {
		int blendedRed = (int)(c1.getRed() * alpha + c2.getRed() * (1 - alpha));
		int blendedGreen =  (int)(c1.getGreen() * alpha + c2.getGreen() * (1 - alpha));
		int blendedBlue = (int)(c1.getBlue() * alpha + c2.getBlue() * (1 - alpha));
		Color blended = new Color(blendedRed,blendedGreen,blendedBlue);
		return blended;
	}
	
	/**
	 * Cosntructs and returns an image which is the blending of the two given images.
	 * The blended image is the linear combination of (alpha) part of the first image
	 * and (1 - alpha) part the second image.
	 * The two images must have the same dimensions.
	 */
	public static Color[][] blend(Color[][] image1, Color[][] image2, double alpha) {
		int rows = image1.length;
		int cols = image1[0].length;
		Color[][] blendImage = new Color[rows][cols];
		for(int i = 0; i < rows; i++){
			for(int j = 0; j < cols; j++){
				blendImage[i][j] = blend(image1[i][j], image2[i][j], alpha);
			}
		}
		return blendImage;
	}

	/**
	 * Morphs the source image into the target image, gradually, in n steps.
	 * Animates the morphing process by displaying the morphed image in each step.
	 * Before starting the process, scales the target image to the dimensions
	 * of the source image.
	 */
	public static void morph(Color[][] source, Color[][] target, int n) {
		Color[][] targetScaled = scaled(target, source[0].length, source.length);
		Color[][] blendedImage = new Color[source.length][source[0].length];
		for (int i = 0; i <= n; i++) {
			double alpha = (n - i) / (double) n;
			blendedImage = blend(source, targetScaled, alpha);
			Runigram.display(blendedImage);
			StdDraw.pause(500);
		}
	}
	
	/** Creates a canvas for the given image. */
	public static void setCanvas(Color[][] image) {
		StdDraw.setTitle("Runigram 2023");
		int height = image.length;
		int width = image[0].length;
		StdDraw.setCanvasSize(height, width);
		StdDraw.setXscale(0, width);
		StdDraw.setYscale(0, height);
        // Enables drawing graphics in memory and showing it on the screen only when
		// the StdDraw.show function is called.
		StdDraw.enableDoubleBuffering();
	}

	/** Displays the given image on the current canvas. */
	public static void display(Color[][] image) {
		int height = image.length;
		int width = image[0].length;
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				// Sets the pen color to the pixel color
				StdDraw.setPenColor( image[i][j].getRed(),
					                 image[i][j].getGreen(),
					                 image[i][j].getBlue() );
				// Draws the pixel as a filled square of size 1
				StdDraw.filledSquare(j + 0.5, height - i - 0.5, 0.5);
			}
		}
		StdDraw.show();
	}
}

