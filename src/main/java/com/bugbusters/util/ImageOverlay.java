package com.bugbusters.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImageOverlay {

    private static final Logger logger = LogManager.getLogger(ImageOverlay.class);

    public void overlayText(String inputImagePath, String outputImagePath,
                            String topText, String bottomText) {

        try {

            BufferedImage image = ImageIO.read(new File(inputImagePath));
            int width = image.getWidth();
            int height = image.getHeight();

            Graphics2D g2d = image.createGraphics();

            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

            Font font = new Font("Impact", Font.BOLD, width / 10);
            g2d.setFont(font);
            g2d.setColor(Color.WHITE);

            FontMetrics fm = g2d.getFontMetrics();

            drawCenteredText(g2d, topText, width, fm.getAscent() + 10, fm);
            drawCenteredText(g2d, bottomText, width, height - fm.getDescent() - 10, fm);

            g2d.dispose();

            ImageIO.write(image, "png", new File(outputImagePath));
            logger.info("Overlay complete. " + outputImagePath + " was successfully created.");

        } catch (IOException io) {
            logger.error(io);
        }

    }

    public void drawCenteredText(Graphics2D g2d, String text, int imageWidth, int y, FontMetrics fm) {
        int textWidth = fm.stringWidth(text);
        int x = (imageWidth - textWidth) / 2;

        g2d.setColor(Color.BLACK);
        for (int offsetX = -2; offsetX <= 2; offsetX++) {
            for (int offsetY = -2; offsetY <= 2; offsetY++) {
                if (offsetX != 0 || offsetY != 0) {
                    g2d.drawString(text, x + offsetX, y + offsetY);
                }
            }
        }

        g2d.setColor(Color.WHITE);
        g2d.drawString(text, x, y);
    }

}
