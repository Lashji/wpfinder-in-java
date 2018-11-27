package logic;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import utils.Settings;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MediaLibrary {

    private MediaLoader loader;
    private List<Image> images;
    private int index;
    private Image activeImg;
    private Settings settings;
    public MediaLibrary(Settings settings) {
        this.settings = settings;
        this.loader = new MediaLoader(settings);
        this.images = new ArrayList<>();
        this.index = 0;
        this.activeImg = null;
    }

    private void loadNextImage() {
        try {

            this.images.add(loader.loadNext());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }


    public void init() {
        loadNextImage();
    }

    public Image getNextImage() {
        loadNextImage();


        if (this.images.size() > 0) {

            if (index == 0) {
                activeImg = this.images.get(0);
                addIndex();
            } else {

                activeImg = this.images.get(index);
                addIndex();
            }

            if (this.images.size() > 3) {

                removePreviousImage();
            }
            return activeImg;

        }
        return null;
    }

    private void removePreviousImage() {

        if (this.images.size() > 0) {
            this.images.remove(0);
        }

        if (index > 0) {
            removeIndex();
        }

    }

    private void removeNextImage() {

        if (this.images.size() > 0) {
            this.images.remove(this.images.size() - 1);
        }
        if (index < this.images.size()) {
            addIndex();
        }

    }

    public Image getPreviousImage() {
        loadPreviousImage();

        if (this.images.size() > 0) {

            if (index == 0) {
                activeImg = this.images.get(0);
                removeIndex();
            } else {
                removeIndex();
                activeImg = this.images.get(index);
            }

            if (this.images.size() > 3) {

                removeNextImage();
            }
            return activeImg;

        }
        return null;
    }


    private void loadPreviousImage() {
        try {

            this.images.add(0, loader.loadLast());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }


    }


    public String save() {

        String uri = settings.getSetting("default_save_uri");

        final File destinationdir = new File("/home/lashj/test");
        int index = Objects.requireNonNull(destinationdir.listFiles()).length;


        String fileName = uri + "/image_" + index + 1 +"."+ settings.getSetting("default_ext");

        BufferedImage bfimage = SwingFXUtils.fromFXImage(activeImg, null);
        File file = new File(fileName);

        try {
            ImageIO.write(bfimage, settings.getSetting("default_ext"), file);
        } catch (IOException e) {
            System.out.println(e.getMessage());

        }

        return fileName;
    }

    public boolean saveAsWallpaper() {

        String os = System.getProperties().getProperty("os.name");
        if (os.startsWith("Linux") && System.getenv("XDG_CURRENT_DESKTOP").equals("KDE")) {

            String fname = save();
            try {
                executeCommands(fname);
                return true;
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        return false;
    }

    private void executeCommands(String fname) throws IOException {

        File tempScript = createTempScript(fname);

        try {
            ProcessBuilder pb = new ProcessBuilder("bash", tempScript.toString());
            pb.inheritIO();
            Process process = pb.start();
            process.waitFor();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            tempScript.delete();
        }
    }

    private File createTempScript(String fileName) throws IOException {
        File tempScript = File.createTempFile("script", null);

        Writer streamWriter = new OutputStreamWriter(new FileOutputStream(
                tempScript));
        PrintWriter printWriter = new PrintWriter(streamWriter);

        printWriter.println("#!/bin/bash");
        printWriter.println("dbus-send --session --dest=org.kde.plasmashell --type=method_call /PlasmaShell org.kde.PlasmaShell.evaluateScript 'string:\n");
        printWriter.println("var Desktops = desktops(); \n");
        printWriter.println("for (i=0;i<Desktops.length;i++) { \n" +
                " d = Desktops[i];\n" +
                " d.wallpaperPlugin = \"org.kde.image\";\n" +
                "   d.currentConfigGroup = Array(\"Wallpaper\",\n" +
                " \"org.kde.image\",\n" +
                " \"General\");\n" +
                "        d.writeConfig(\"Image\", \"" + fileName + "\");\n" +
                "}'\n");


        printWriter.close();

        return tempScript;
    }


    private void addIndex() {
        if (this.index < this.images.size() - 1) {
            this.index++;
        }
    }

    private void removeIndex() {

        if (this.index > 0) {
            this.index--;
        }

    }

}
