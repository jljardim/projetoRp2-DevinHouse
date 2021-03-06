package dev.in.villaDevin.model.repository;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.mchange.v2.c3p0.ComboPooledDataSource;

@Configuration
public class DBConnectionFactory {
    private final DataSource dataSource;

    public DBConnectionFactory(
            @Value("${jdbc.db.url}") String url,
            @Value("${jdbc.db.user}") String user,
            @Value("${jdbc.db.password}") String password) {
        ComboPooledDataSource comboPooledDataSource = new ComboPooledDataSource();
        comboPooledDataSource.setJdbcUrl(url);
        comboPooledDataSource.setUser(user);
        comboPooledDataSource.setPassword(password);

        comboPooledDataSource.setMaxPoolSize(15);
        this.dataSource = comboPooledDataSource;
    }

    @Bean
    public DataSource getDataSource() {
        return dataSource;
    }

    @Bean
    public Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }
}
