package io.github.tivecs.theolib.util.sql;

import com.zaxxer.hikari.HikariDataSource;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.Connection;
import java.sql.SQLException;

public final class HikariConnector extends SqlConnector {

    private HikariDataSource hikari = null;

    public HikariConnector(JavaPlugin plugin) {
        super(plugin);
        readConfig();
        init();
    }

    private void readConfig(){
        FileConfiguration config = getPlugin().getConfig();

        setHost(config.getString(BASE_STORAGE_DB_CONFIG_PATH + ".host"));
        setPort(config.getString(BASE_STORAGE_DB_CONFIG_PATH + ".port"));
        setDatabase(config.getString(BASE_STORAGE_DB_CONFIG_PATH + ".database"));
        setUsername(config.getString(BASE_STORAGE_DB_CONFIG_PATH + ".username"));
        setPassword(config.getString(BASE_STORAGE_DB_CONFIG_PATH + ".password"));
    }

    @Override
    public void init(){
        hikari = new HikariDataSource();
        hikari.setJdbcUrl("jdbc:mysql://" + getHost() + ":" + getPort() + "/");
        hikari.addDataSourceProperty("user", getUsername());
        if (getPassword().length() > 0) {
            hikari.addDataSourceProperty("password", getPassword());
        }

        final String initSql = "CREATE DATABASE IF NOT EXISTS `" + getDatabase() + "`;";
        hikari.setConnectionInitSql(initSql);
    }

    public HikariDataSource getHikari() {
        return hikari;
    }

    @Override
    public Connection getConnection() throws SQLException {
        Connection c = hikari.getConnection();
        c.setCatalog(getDatabase());

        return c;
    }
}
