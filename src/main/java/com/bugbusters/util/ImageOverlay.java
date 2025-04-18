package com.bugbusters.util;

import java.util.List;
import java.util.ArrayList;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * This class takes an input image file, and two strings. It overlays the first
 * string on the top of the image and the second string on the bottom of the
 * image with a black border. It then returns the image with the text overlaid.
 *
 * @author ptaylor
 */
public class ImageOverlay {

    private static final Logger logger = LogManager.getLogger(ImageOverlay.class);

    private final String fontName;
    private final double fontSizeFactor;
    private final int offset;
    private final int paddingTop;

    /**
     * Constructs an ImageOverlay object and initializes properties
     * such as font name, size factor, outline offset, and padding
     * from the configuration file {@code overlay.properties}.
     */
    public ImageOverlay() {
        Properties properties = PropertiesLoader.load("/overlay.properties");

        fontName = properties.getProperty("font.name");
        fontSizeFactor = Double.parseDouble(properties.getProperty("font.size.factor"));
        offset = Integer.parseInt(properties.getProperty("outline.offset"));
        paddingTop = Integer.parseInt(properties.getProperty("text.padding.top"));
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

        // ANTIALIASING
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        int fontSize = (int) (width * fontSizeFactor);

        Font font = new Font(fontName, Font.BOLD, fontSize);
        g2d.setFont(font);
        g2d.setColor(Color.WHITE);

        FontMetrics fm = g2d.getFontMetrics();

        drawCenteredText(g2d, topText, width, fm.getAscent() + paddingTop, fm);

        List<String> bottomLines = getWrappedText(bottomText, fm, width);
        int totalBottomHeight = fm.getHeight() * bottomLines.size();

        int bottomStartY = height - totalBottomHeight + fm.getAscent();

        drawCenteredText(g2d, bottomText, width, bottomStartY, fm);

        g2d.dispose();

        logger.info("Overlay complete.");
        return image;

    }

    /**
     * This method centers the top and bottom text and overlays them to the image in white.
     * It then offsets the text and overlays it multiple times in black to give the text a
     * shadow.
     *
     * @param g2d        Graphics2D context
     * @param text       text string
     * @param imageWidth image width
     * @param startY     y-coordinate
     * @param fm         font metrics
     */
    public void drawCenteredText(Graphics2D g2d, String text, int imageWidth, int startY, FontMetrics fm) {
        int lineHeight = fm.getHeight();

        List<String> lines = getWrappedText(text, fm, imageWidth);

        int y = startY;

        for (String line : lines) {
            int textWidth = fm.stringWidth(line);
            int x = (imageWidth - textWidth) / 2;

            g2d.setColor(Color.BLACK);
            for (int offsetX = -offset; offsetX <= offset; offsetX++) {
                for (int offsetY = -offset; offsetY <= offset; offsetY++) {
                    if (offsetX != 0 || offsetY != 0) {
                        g2d.drawString(line, x + offsetX, y + offsetY);
                    }
                }
            }

            g2d.setColor(Color.WHITE);
            g2d.drawString(line, x, y);

            y += lineHeight;
        }
    }

    /**
     * This method splits the inputted text into individual words and adds them all to lines.
     * When the lines become wider than the image it begins a new line and adds the previous
     * line to a list. It returns the list at the end.
     *
     * @param text       inputted text
     * @param fm         font metrics
     * @param imageWidth image width
     * @return list of lines
     */
    public List<String> getWrappedText(String text, FontMetrics fm, int imageWidth) {
        List<String> lines = new ArrayList<>();
        String[] words = text.split(" ");
        StringBuilder currentLine = new StringBuilder();

        for (String word : words) {
            String testLine = currentLine.length() == 0 ? word : currentLine + " " + word;
            int testWidth = fm.stringWidth(testLine);
            if (testWidth > imageWidth) {
                lines.add(currentLine.toString());
                currentLine = new StringBuilder(word);
            } else {
                currentLine = new StringBuilder(testLine);
            }
        }
        if (currentLine.length() > 0) {
            lines.add(currentLine.toString());
        }

        return lines;
    }

}
