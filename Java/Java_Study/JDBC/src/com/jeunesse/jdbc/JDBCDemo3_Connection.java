package com.jeunesse.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class JDBCDemo3_Connection {
    public static void main(String[] args) throws Exception {
        // 目的：JDBC API 详解：Connection
        // 2、获取连接：如果连接的是本机mysql并且端口是默认的3306可以简化书写
        String url = "jdbc:mysql:///db1";
        String username = "root";
        String password = "1234";
        Connection conn = DriverManager.getConnection(url, username, password);

        // 3、定义sql
        String sql1 = "update account set money = 3000 where id = 1";
        String sql2 = "update account set money = 3000 where id = 2";

        // 4、获取执行sql对象 Statement
        Statement stmt = conn.createStatement();

        try {
            // 开启事务
            conn.setAutoCommit(false);
            // 5、执行sql
            int count1 = stmt.executeUpdate(sql1); // 受影响的行数
            // 6、处理结果
            System.out.println(count1);

            int i = 1 / 0;

            // 5、执行sql
            int count2 = stmt.executeUpdate(sql2); // 受影响的行数
            // 6、处理结果
            System.out.println(count2);

            // 提交事务
            conn.commit();
        } catch (Exception e) {
            // 回滚事务
            conn.rollback();
            e.printStackTrace();
        }

        // 7、释放资源
        stmt.close();
        conn.close();
    }
}
