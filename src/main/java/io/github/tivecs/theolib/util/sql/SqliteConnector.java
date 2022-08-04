package io.github.tivecs.theolib.util.sql;

import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SqliteConnector extends SqlConnector {

    private final String sqlSourcePath;

    private Connection connection = null;

    public SqliteConnector(JavaPlugin plugin, File dbFile, boolean createIfNotExists) {
        super(plugin);
        if (createIfNotExists && !dbFile.exists()){
            try {
                dbFile.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        this.sqlSourcePath = "jdbc:sqlite:" + dbFile.getPath();
        init();
    }

    @Override
    public void init() {
        try {
            this.connection = getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Connection getConnection() throws SQLException {
        if (connection != null && !connection.isClosed()){
            return connection;
        }

        connection = DriverManager.getConnection(this.sqlSourcePath);

        return connection;
    }
}
