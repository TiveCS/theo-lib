package io.github.tivecs.theolib.util.storage;

import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class YamlStorage {

    private final FileConfiguration config;

    private final File file;

    private final Configuration root;

    public YamlStorage(File file, boolean createIfNotExists) {
        this.file = file;
        if (createIfNotExists && !this.file.exists()){
            try {
                this.file.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        this.config = YamlConfiguration.loadConfiguration(this.file);
        this.root = config.getRoot();
    }

    public YamlStorage(File folder, String fileName, boolean createIfNotExists){
        this(new File(folder, fileName.endsWith(".yml") ? fileName : fileName + ".yml"), createIfNotExists);
    }

    public void save(){
        try {
            this.config.save(this.file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String saveToString(){
        return config.saveToString();
    }

    public Configuration getRoot() {
        return root;
    }

    public FileConfiguration getConfig() {
        return config;
    }

    public File getFile() {
        return file;
    }
}
