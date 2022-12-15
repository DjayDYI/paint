package app.util;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Util {

    protected ImageIcon createImageIcon(String path) throws IOException {
        java.net.URL imgURL = getClass().getResource(path);
        BufferedImage image = ImageIO.read(new File(path));
        Image icon = image.getScaledInstance(48, 48, Image.SCALE_SMOOTH);

        if (imgURL != null) {
            return new ImageIcon(icon, "");
        } else {
            System.err.println("Couldn't find file: " + path);
            return null;
        }
    }


}