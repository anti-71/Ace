package com.jeunesse;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class JDBCDemo2_DriverManager {
    public static void main(String[] args) throws Exception {
        // 目的：JDBC API 详解：DriverManager
        // 2、获取连接：如果连接的是本机mysql并且端口是默认的3306可以简化书写
        String url = "jdbc:mysql:///db1";
        String username = "root";
        String password = "1234";
        Connection conn = DriverManager.getConnection(url, username, password);

        // 3、定义sql
        String sql = "update account set money = 2000 where id = 1";

        // 4、获取执行sql对象 Statement
        Statement stmt = conn.createStatement();

        // 5、执行sql
        int count = stmt.executeUpdate(sql); // 受影响的行数

        // 6、处理结果
        System.out.println(count);

        // 7、释放资源
        stmt.close();
        conn.close();
    }
}
