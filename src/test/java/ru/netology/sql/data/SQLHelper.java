package ru.netology.sql.data;

import lombok.SneakyThrows;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SQLHelper {
    private static QueryRunner runner = new QueryRunner();

    private SQLHelper() {
    }

    @SneakyThrows
    private static Connection getConn() {
        return DriverManager.getConnection("jdbc:mysql://localhost:3306/app", "app", "pass");
    }

    public static DataHelper.VerificationCode getVerificationCode() {
        var codeSQl = "SELECT code FROM auth_codes ORDER BY created  DESC LIMIT 1;";
        try (var conn = getConn()) {
            var result = runner.query(conn, codeSQl, new ScalarHandler<String>());
            return new DataHelper.VerificationCode(result);
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return null;

    }

    @SneakyThrows
    public static void cleanDatabase() {
        var connection = getConn();
        runner.execute(connection, "delete from auth_codes");
        runner.execute(connection, "delete from card_transactions");
        runner.execute(connection, "delete from cards");
        runner.execute(connection, "delete from users");
    }

    @SneakyThrows
    public static void addTestUserToDB() {
        var connection = getConn();
        ScalarHandler<String> scalarHandler = new ScalarHandler<>();

        String insertSQL = "INSERT INTO users (id, login, password) "
                + "VALUES (?, ?, ?)";

        runner.update(
                connection, insertSQL,
                UserGenerator.getUserInfo().getId(),
                UserGenerator.getUserInfo().getLogin(),
                UserGenerator.getUserInfo().getPassword()
        );

    }
}
