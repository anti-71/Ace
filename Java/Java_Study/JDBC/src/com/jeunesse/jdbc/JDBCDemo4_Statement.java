package com.jeunesse.jdbc;

import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class JDBCDemo4_Statement {
    // 目的：JDBC API 详解：Statement

    // 执行DML语句
    @Test
    public void testDML() throws Exception{
        // 2、获取连接：如果连接的是本机mysql并且端口是默认的3306可以简化书写
        String url = "jdbc:mysql:///db1";
        String username = "root";
        String password = "1234";
        Connection conn = DriverManager.getConnection(url, username, password);

        // 3、定义sql
        String sql1 = "update account set money = 3000 where id = 5";

        // 4、获取执行sql对象 Statement
        Statement stmt = conn.createStatement();

        // 5、执行sql
        int count1 = stmt.executeUpdate(sql1); // 执行完DDL语句，可能是0

        // 6、处理结果
        if (count1 > 0){
            System.out.println("执行成功！");
        } else {
            System.out.println("执行失败！");
        }

        // 7、释放资源
        stmt.close();
        conn.close();
    }

    // 执行DDL语句
    @Test
    public void testDDL() throws Exception{
        // 2、获取连接：如果连接的是本机mysql并且端口是默认的3306可以简化书写
        String url = "jdbc:mysql:///db1";
        String username = "root";
        String password = "1234";
        Connection conn = DriverManager.getConnection(url, username, password);

        // 3、定义sql
        String sql = "drop database db2";

        // 4、获取执行sql对象 Statement
        Statement stmt = conn.createStatement();

        // 5、执行sql
        int count = stmt.executeUpdate(sql); // 执行完DML语句后，受影响的行数

        // 6、处理结果
        System.out.println(count);

        // 7、释放资源
        stmt.close();
        conn.close();
    }
}
