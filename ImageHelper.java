import java.awt.*;
import javax.swing.ImageIcon;
import java.awt.image.BufferedImage;

/*
 * This class helps the developer paint to ImageIcons
 * ImageIcons do not allow manipulation of their graphics so they must be converted to
 * BufferedImages which can be manipulated before being set back to the ImageIcon
 * See each method for a description of its specific behavior
 */
public abstract class ImageHelper {
    /*
     * Private member variables here
     */
    private static byte tileWidth = 64;
    private static byte tileHeight = 64;
    
    /*
     * Public member methods go here
     */
    
    // This method takes the param "paint" and draws it on to the "canvas" param at the "origin" param
    // then returns the modified ImageIcon
    // It accounts for "tiling" so it will only draw in intervals of tileWidth/tileHeight pixels
    public static ImageIcon drawImageToIcon(ImageIcon canvas, BufferedImage paint, Point origin) {
        
        // Convert the icon to a bufferedimage of the same dimensions so you can easily manipulate it
        BufferedImage img = new BufferedImage(canvas.getIconWidth(), canvas.getIconHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics g = img.createGraphics();
        g.drawImage(canvas.getImage(), 0, 0, null);
        
        // Draw the 'paint' BufferedImage param to the newly created BufferedImage
        g.drawImage(paint, origin.x - (origin.x % tileWidth), origin.y - (origin.y % tileHeight), null);
        
        // Set the modified BufferedImage back to the ImageIcon param
        canvas.setImage(img);
        
        // Finally, return the modified ImageIcon
        return canvas;
    }
    
    public static ImageIcon drawIconToIcon(ImageIcon canvas, ImageIcon paint, Point origin) {
        // Convert the "paint" ImageIcon to a BufferedImage
        BufferedImage img = new BufferedImage(tileWidth, tileHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics g = img.createGraphics();
        g.drawImage(paint.getImage(), 0, 0, null);
        
        // Now that the Icon is an image, call the drawImageToIcon method
        return drawImageToIcon(canvas, img, origin);
    }
    
    public static BufferedImage getIconSubimage(ImageIcon source, Point origin, int width, int height) {
        // Convert "source" ImageIcon to a BufferedImage so it can be sliced and diced
        BufferedImage img = new BufferedImage(source.getIconWidth(), source.getIconHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics g = img.createGraphics();
        g.drawImage(source.getImage(), 0, 0, null);

        // Use the magic of math to grab the tile the user clicked on and save it to be used elsewhere
        BufferedImage subimage = img.getSubimage(origin.x - (origin.x % tileWidth), 
                                    origin.y - (origin.y % tileHeight), width, height);
        
        // Return the subimage retrieved from the "source" ImageIcon
        return subimage;
    }
    
    public static BufferedImage drawIconToImageRepeatedly(BufferedImage canvas, ImageIcon paint) {
        // Convert the "canvas" param to a BufferedImage so it can be manipulated
        Graphics g = canvas.createGraphics();
        
        // Draw the "paint" on the "canvas" until the entire canvas is covered
        for (int row = 0; row < canvas.getWidth() / tileWidth; row++) {
            for (int col = 0; col < canvas.getHeight() / tileHeight; col++) {
                g.drawImage(paint.getImage(), row * tileWidth, col * tileHeight, null);
            }
        }
        
        // Return the modified BufferedImage
        return canvas;
    }
    
    public static ImageIcon drawIconToIconRepeatedly(ImageIcon canvas, ImageIcon paint) {
        // Translate the "paint" param to a BufferedImage
        BufferedImage source = new BufferedImage(paint.getIconWidth(), paint.getIconHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics g = source.createGraphics();
        g.drawImage(paint.getImage(), 0, 0, null);

        // Translate the canvas to a buffered img
        BufferedImage destination = new BufferedImage(canvas.getIconWidth(), canvas.getIconHeight(), BufferedImage.TYPE_INT_ARGB);
        g = destination.createGraphics();
        g.drawImage(canvas.getImage(), 0, 0, null);

        // Draw "paint" over the "canvas" until the canvas is completely covered
        for (int row = 0; row < destination.getWidth() / tileWidth; row++) {
            for (int col = 0; col < destination.getHeight() / tileHeight; col++) {
                g.drawImage(source, row * tileWidth, col * tileHeight, null);
            }
        }
        
        // Translate the modified canvas back into an ImageIcon
        canvas.setImage(destination);
        
        // Return the modified ImageIcon
        return canvas;
    }
            
    /*
     * Private (internal) helper methods go here
     */
}
