package com.jeunesse.jdbc;

import com.jeunesse.pojo.Account;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class JDBCDemo6_UserLogin {
    // 目的：用户登录

    // 执行DQL语句
    @Test
    public void testLogin() throws Exception {
        // 2、获取连接：如果连接的是本机mysql并且端口是默认的3306可以简化书写
        String url = "jdbc:mysql:///test";
        String username = "root";
        String password = "1234";
        Connection conn = DriverManager.getConnection(url, username, password);

        // 接收用户输入的用户名和密码
        String name = "zhangsan";
        String pwd = "123";

        String sql = "select * from tb_user where username = '" + name + "' and password = '" + pwd + "'";

        // 获取statement对象
        Statement stmt = conn.createStatement();

        // 执行sql
        ResultSet rs = stmt.executeQuery(sql);

        // 判断登录是否成功
        if (rs.next()) {
            System.out.println("登录成功");
        } else {
            System.out.println("登录失败");
        }

        // 7、释放资源
        rs.close();
        stmt.close();
        conn.close();
    }

    /**
     * 演示SQL注入
     */
    @Test
    public void testLogin_Inject() throws Exception {
        // 2、获取连接：如果连接的是本机mysql并且端口是默认的3306可以简化书写
        String url = "jdbc:mysql:///test";
        String username = "root";
        String password = "1234";
        Connection conn = DriverManager.getConnection(url, username, password);

        // 接收用户输入的用户名和密码
        String name = "zhangsan";
        String pwd = "' or '1' = '1";

        String sql = "select * from tb_user where username = '" + name + "' and password = '" + pwd + "'";

        // 获取statement对象
        Statement stmt = conn.createStatement();

        // 执行sql
        ResultSet rs = stmt.executeQuery(sql);

        // 判断登录是否成功
        if (rs.next()) {
            System.out.println("登录成功");
        } else {
            System.out.println("登录失败");
        }

        // 7、释放资源
        rs.close();
        stmt.close();
        conn.close();
    }
}
