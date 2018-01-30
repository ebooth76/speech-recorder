package app;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseObject {

    @Value("${spring.datasource.url}")
    private String dbUrl;

    public static Connection getConnection() throws SQLException {
        //String dbUrl = System.getenv("JDBC_DATABASE_URL");
        String dbUrl = "jdbc:postgresql://ec2-54-225-112-61.compute-1.amazonaws.com:5432/ds0m2g3nacuvc?sslmode=require&user=huzjrznhoilodv&password=e3670ef2720dba4d5b47c5298a34e481fc0765298256e29faf0a6237d4726983";
        return DriverManager.getConnection(dbUrl);
    }
}
