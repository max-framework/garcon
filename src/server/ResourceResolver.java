package server;

import exceptions.ResourceNotFoundException;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

public class ResourceResolver {
    private static final int BUFFER_SIZE = 1024;
    private static final String CONFIG_FILE = "garcon.config";

    public static File getFile(String path) {
        File file = new File(path);
        if (!file.exists())
            throw new ResourceNotFoundException(path);

        if (file.isDirectory())
            throw new ResourceNotFoundException(path);

        return file;
    }

    public static File getFileFromWebRoot(String fileName) {
        return getFile(Configuration.getInstance().getWebroot() + fileName);
    }

    public static byte[] getFileContentBytes(String fileName) {
        File file = getFileFromWebRoot(fileName);
        byte[] result;
        try {
            result = Files.readAllBytes(Path.of(file.getPath()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    public static String getFileContentString(String fileName) {
        File file = getFileFromWebRoot(fileName);
        String result;
        try {
            result = Files.readString(Path.of(file.getPath()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    public static Map<String, String> getConfigs() {
        File file = getFile(Configuration.ROOT + CONFIG_FILE);
        try {
            FileReader fileReader = new FileReader(file);
            BufferedReader reader = new BufferedReader(fileReader);
            Map<String, String> result = new HashMap<>();
            String line;
            while ((line = reader.readLine()) != null) {
                String[] lineSplit = line.split("=", 2);
                result.put(lineSplit[0], lineSplit[1]);
            }
            return result;
        } catch (FileNotFoundException e) {
            throw new ResourceNotFoundException(CONFIG_FILE);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}
