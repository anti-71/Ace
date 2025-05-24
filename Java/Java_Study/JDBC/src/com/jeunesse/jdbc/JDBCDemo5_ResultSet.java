package com.jeunesse;

import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class JDBCDemo5_ResultSet {
    // 目的：JDBC API 详解：ResultSet

    // 执行DQL语句
    @Test
    public void testResultSet() throws Exception {
        // 2、获取连接：如果连接的是本机mysql并且端口是默认的3306可以简化书写
        String url = "jdbc:mysql:///db1";
        String username = "root";
        String password = "1234";
        Connection conn = DriverManager.getConnection(url, username, password);

        // 3、定义sql
        String sql = "select * from account";

        // 4、获取statement对象
        Statement stmt = conn.createStatement();

        // 5、执行sql
        ResultSet rs = stmt.executeQuery(sql);

        // 6、处理结果，遍历rs中的所有数据
        // 光标向下移动一行，并且判断当前行是否有数据
        while (rs.next()) {
            // 获取数据 getXxx()
            int id = rs.getInt(1);
            String name = rs.getString(2);
            double money = rs.getDouble(3);

            System.out.println(id);
            System.out.println(name);
            System.out.println(money);

            System.out.println("---------------------------------------------");
        }

        // 7、释放资源
        rs.close();
        stmt.close();
        conn.close();
    }
}
