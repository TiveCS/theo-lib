package io.github.tivecs.theolib.util.storage;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class JsonStorage {

    public static final Gson GSON = new GsonBuilder()
            .setPrettyPrinting()
            .disableHtmlEscaping()
            .create();
    private final String fileName;
    private final File file;
    private JsonObject root;

    public JsonStorage(File jsonFile, boolean createFileIfNotExists) {
        this.file = jsonFile;
        this.fileName = this.file.getName();

        if (createFileIfNotExists) {
            try {
                this.file.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        read();
    }

    public JsonStorage(File folder, String fileName, boolean createFileIfNotExists) {
        this(new File(folder, fileName.endsWith(".json") ? fileName : fileName + ".json"), createFileIfNotExists);
    }

    private void read() {
        try (JsonReader reader = GSON.newJsonReader(new FileReader(this.file))) {
            JsonElement json = GSON.fromJson(reader, JsonElement.class);

            if (json instanceof JsonObject) {
                this.root = json.getAsJsonObject();
            } else if (json == null) {
                this.root = new JsonObject();
            } else {
                throw new RuntimeException("Root element on file is not JsonObject");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void save() {
        try (JsonWriter writer = GSON.newJsonWriter(new FileWriter(this.file))) {
            GSON.toJson(root, writer);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public File getFile() {
        return file;
    }

    public JsonObject getRoot() {
        return root;
    }

    public String getFileName() {
        return fileName;
    }
}
