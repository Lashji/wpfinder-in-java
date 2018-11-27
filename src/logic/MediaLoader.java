package logic;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import utils.Settings;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;
import java.util.*;

public class MediaLoader {
    private static final File dir = new File("/home/lashj/Dropbox/Pictures");
    private static final String[] EXTENSIONS = new String[]{
            "gif", "png", "jpg"
    };
    private Settings settings;
    private ImgurHandler imgur;
    private int index;
    private List<String> imageStrings;
    private File[] fileList;

    MediaLoader(Settings settings) {

        this.settings = settings;
        this.fileList = Objects.requireNonNull(dir.listFiles(IMAGE_FILTER));
        this.index = 0;
        this.imageStrings = new ArrayList<>();

        if (settings.getSetting("mode").equals("IMGUR")) {
            this.imgur = new ImgurHandler();
            imageStrings.addAll(imgur.getImageLinks());
        } else {
            this.imgur = null;

        }


    }



    private static final FilenameFilter IMAGE_FILTER = (dir, name) -> {
        for (final String ext : EXTENSIONS) {
            if (name.endsWith("." + ext)) {
                return (true);
            }
        }
        return (false);
    };


    Image loadNext() throws IOException {


        Image img = null;

        switch (settings.getSetting("mode")) {
            case "FILE":
                img = loadFromFileSystem();
                break;
            case "IMGUR":

                img = loadFromWeb();


                break;
        }
        if (index < fileList.length - 1) {

            index++;
        }
        return img;
    }

    private Image loadFromWeb() throws IOException {

        Image img = null;
        if (this.imgur != null) {

            if (index <= imageStrings.size()) {

                URL url = new URL(imageStrings.get(index));

                BufferedImage bfImage = ImageIO.read(url);

                img = SwingFXUtils.toFXImage(bfImage, null);


            }

        }
        return img;
    }

    Image loadLast() throws IOException {

        Image img = null;

        if (index > 0) {

            index--;
        }
        switch (settings.getSetting("mode")) {


            case "FILE":

                img = loadFromFileSystem();
                break;

            case "IMGUR":

                img = loadFromWeb();
                break;
        }


        return img;

    }

    private Image loadFromFileSystem() {

        Image img = null;
        BufferedImage bfImage;
        if (index <= fileList.length) {

            if (dir.isDirectory()) {


                final File f = fileList[index];


                try {
                    bfImage = ImageIO.read(f);
                    img = SwingFXUtils.toFXImage(bfImage, null);


                } catch (IOException e) {
                    System.out.println(Arrays.toString(e.getStackTrace()));
                }


            }

        }

        return img;
    }

    public void initImgur() {
        this.index = 0;
        this.imgur = new ImgurHandler();
    }
}
