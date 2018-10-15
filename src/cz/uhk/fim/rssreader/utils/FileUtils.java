package cz.uhk.fim.rssreader.utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.Path;

public class FileUtils {

    public static String loadStringFromFile(String filepath) throws IOException {
        return new String(Files.readAllBytes(Paths.get(filepath)));
    }

    public static void saveStringToPath(String filepath, byte[] data) throws IOException {
        Path path = Paths.get(filepath);
        Files.write(path, data);
    }
}
