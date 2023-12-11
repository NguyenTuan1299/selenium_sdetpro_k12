package test_data;

import com.google.gson.Gson;

import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;

public class DataObjectBuilder {

    public static <T> T buildDataObjectFrom(String relativeFileLocation, Class<T> dataTypeClass) {
        T data;
        String currentProjectLocation = System.getProperty("user.dir");
        String absoluteFileLocation = currentProjectLocation + relativeFileLocation;

        try (
                Reader jsonContentReader = Files.newBufferedReader(Paths.get(absoluteFileLocation));
        ) {
            Gson gson = new Gson();
            data = gson.fromJson(jsonContentReader, dataTypeClass);
        } catch (Exception e) {
            throw new RuntimeException("[ERR] Error while reading " + absoluteFileLocation);
        }

        return data;
    }
}