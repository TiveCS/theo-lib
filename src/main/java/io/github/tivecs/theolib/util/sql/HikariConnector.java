package io.github.tivecs.theolib.util.sql;

import com.zaxxer.hikari.HikariDataSource;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.Connection;
import java.sql.SQLException;

public final class HikariConnector extends SqlConnector {

    private HikariDataSource hikari = null;

    /**
     * Create Hikari MySQL connector using plugin config.yml on path 'storage.sql'
     * to create JDBC URL
     * @param plugin target plugin config.yml
     */
    public HikariConnector(JavaPlugin plugin) {
        super(plugin);
        readConfig();
        init();
    }

    /**
     * Create Hikari MySQL connector using given host, port, username, password and database
     * to create JDBC URL
     *
     * @param plugin connector plugin
     * @param host target mysql host
     * @param port target mysql port
     * @param username target mysql username
     * @param password target mysql user's password
     * @param database target database
     */
    public HikariConnector(JavaPlugin plugin, String host, String port, String username, String password, String database){
        super(plugin);

        setHost(host);
        setPort(port);
        setDatabase(database);
        setPassword(password);
        setUsername(username);

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
