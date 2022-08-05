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

    /**
     * Create SQLite connector based on target dbFile to create JDBC URL.
     * @param plugin target plugin
     * @param dbFile target dbFile
     * @param createIfNotExists if true, create target dbFile if not exists. if false not create specified dbFile.
     */
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
