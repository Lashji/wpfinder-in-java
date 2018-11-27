package utils;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

public class Settings {
    private static final String OS_DEFAULT_FILE_PATH = Utils.GET_OS_DEFAULT_FILE_PATH() + "/.wpf_settings.txt";
    private Map<String, String> settings;
    private Map<String, String> tmpSettings;


    public Settings() {
        this.settings = new HashMap<>();
        this.tmpSettings = this.settings;
    }

    public static List<String> GET_ALBUM_HASHES() {
        List<String> hashes = new ArrayList<>();



        hashes.add("4FClvOM");
        hashes.add("H2DLxYy");
        hashes.add("abaz1");

        return hashes;

    }

    public void loadSettings() {


        try {
            loadSettingsFile();
            this.tmpSettings = this.settings;

        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    private void loadSettingsFile() throws IOException {


        File settingsFile = new File(OS_DEFAULT_FILE_PATH);

        if (!settingsFilesExistInFilesystem()) {

            createSettingsFiles(settingsFile);

        }

        ArrayList<String> settingsAsList = new ArrayList<>();

        Scanner sc = new Scanner(new File(OS_DEFAULT_FILE_PATH));


        if (sc.hasNextLine()) {

            while (sc.hasNextLine()) {
                settingsAsList.add(sc.nextLine());

            }


            sc.close();
            addSettingsStringsToMap(settingsAsList);
        }

    }

    private void createSettingsFiles(File settingsFile) throws IOException {
        settingsFile.createNewFile();
        List<String> defaultSettingsAsStrings = getDefaultSettingsStrings();

        PrintWriter pw = new PrintWriter(OS_DEFAULT_FILE_PATH);

        defaultSettingsAsStrings.forEach(pw::println);
        pw.close();

    }

    private List<String> getDefaultSettingsStrings() {
        List<String> settings = new ArrayList();

        settings.add("mode:IMGUR");
        settings.add("default_save_uri:/home/lashj/test/");
        settings.add("default_ext:png");


        return settings;

    }


    private void addSettingsStringsToMap(List<String> list) {
        for (String s : list) {
            if (!s.isEmpty()) {

                String[] s_split = s.split(":");

                if (s_split[0] != null && s_split[1] != null) {
                    this.settings.put(s_split[0], s_split[1]);
                }

            }
        }
    }


    public String getSetting(String setting) {
        return this.settings.getOrDefault(setting, "undefined");
    }

    void writeSettings() throws FileNotFoundException {
        this.settings = tmpSettings;
        tmpSettings.clear();

        PrintWriter pw = new PrintWriter(OS_DEFAULT_FILE_PATH);

        this.settings.forEach((key, value) -> pw.println(key + ":" + value));


    }

    public void setTmpSettings(String key, String value) {
        this.settings.put(key, value);


    }

    private boolean settingsFilesExistInFilesystem() {






        return false;

    }
}
