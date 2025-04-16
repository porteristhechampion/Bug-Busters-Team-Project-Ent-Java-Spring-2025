package com.bugbusters.util;

import java.util.Properties;

import com.bugbusters.util.PropertiesLoader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * This class takes an input image file, and two strings.
 * It reads the input file and overlays the first string on the top of the
 * image and the second string on the bottom of the image with a black border.
 * It then writes the newly created image with the overlay and returns it.
 *
 * @author ptaylor
 */
public class ImageOverlay {

    private static final Logger logger = LogManager.getLogger(ImageOverlay.class);

    private final String fontName;
    private final double fontSizeFactor;
    private final int offset;
    private final int paddingTop;
    private final int paddingBottom;

    public ImageOverlay() {
        Properties properties = PropertiesLoader.load("/overlay.properties");

        fontName = properties.getProperty("font.name");
        fontSizeFactor = Double.parseDouble(properties.getProperty("font.size.factor"));
        offset = Integer.parseInt(properties.getProperty("outline.offset"));
        paddingTop = Integer.parseInt(properties.getProperty("text.padding.top"));
        paddingBottom = Integer.parseInt(properties.getProperty("text.padding.bottom"));
    }

    /**
     * This method receives an image, overlays the inputted top and bottom
     * text in centered white Impact font, and returns the modified image.
     *
     * @param image      input image file
     * @param topText    text for top of overlay
     * @param bottomText text for bottom of overlay
     */
    public BufferedImage overlayText(BufferedImage image, String topText, String bottomText) {

        int width = image.getWidth();
        int height = image.getHeight();

        Graphics2D g2d = image.createGraphics();

        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        int fontSize = (int)(width * fontSizeFactor);

        Font font = new Font(fontName, Font.BOLD, fontSize);
        g2d.setFont(font);
        g2d.setColor(Color.WHITE);

        FontMetrics fm = g2d.getFontMetrics();

        drawCenteredText(g2d, topText, width, fm.getAscent() + paddingTop, fm);
        drawCenteredText(g2d, bottomText, width, height - fm.getDescent() - paddingBottom, fm);

        g2d.dispose();

        logger.info("Overlay complete.");
        return image;

    }

    /**
     * This method draws the text multiple times in black slightly offset to give the text a black
     * border. Then it draws the main white text on top. The text is centered horizontally based on
     * the image width and drawn at the top and bottom of the image.
     *
     * @param g2d        Graphics2D context
     * @param text       text string
     * @param imageWidth image width
     * @param y          y-coordinate
     * @param fm         font metrics
     */
    public void drawCenteredText(Graphics2D g2d, String text, int imageWidth, int y, FontMetrics fm) {
        int textWidth = fm.stringWidth(text);
        int x = (imageWidth - textWidth) / 2;

        g2d.setColor(Color.BLACK);
        for (int offsetX = -offset; offsetX <= offset; offsetX++) {
            for (int offsetY = -offset; offsetY <= offset; offsetY++) {
                if (offsetX != 0 || offsetY != 0) {
                    g2d.drawString(text, x + offsetX, y + offsetY);
                }
            }
        }

        g2d.setColor(Color.WHITE);
        g2d.drawString(text, x, y);
    }

}
