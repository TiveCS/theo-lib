package io.github.tivecs.theolib.util.sql;

import org.bukkit.plugin.java.JavaPlugin;

import java.sql.Connection;
import java.sql.SQLException;

public abstract class SqlConnector {

    protected final String BASE_STORAGE_DB_CONFIG_PATH = "storage.sql";

    private final JavaPlugin plugin;

    private String host;

    private String database;

    private String username;

    private String password;

    private String port;

    public SqlConnector(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    /**
     * Prepare connection based on created JDBC URL
     */
    public abstract void init();

    public abstract Connection getConnection() throws SQLException;

    protected void setHost(String host) {
        this.host = host;
    }

    protected void setDatabase(String database) {
        this.database = database;
    }

    protected void setUsername(String username) {
        this.username = username;
    }

    protected void setPassword(String password) {
        this.password = password;
    }

    protected void setPort(String port) {
        this.port = port;
    }

    public JavaPlugin getPlugin() {
        return plugin;
    }

    protected String getHost() {
        return host;
    }

    protected String getDatabase() {
        return database;
    }

    protected String getUsername() {
        return username;
    }

    protected String getPassword() {
        return password;
    }

    protected String getPort() {
        return port;
    }
}
